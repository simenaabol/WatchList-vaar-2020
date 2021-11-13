import React, { Component } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

import Feed from "./Feed";
import "../index.css";
//Component for the main page when logged in
export default class StartSideSerNå extends Component {
  constructor() {
    super();

    this.state = {
      username: "",
      id: {},
      liste: [],
      venneListe: ["Du har ingen venner"], //Default friend list to show that you have no friends when the friendlist is empty
      søkVenner: "",
      søkVennerListe: [""],
      admin: {},
      adminInnhold: "",
      medieListe: [],
      søkMedium: [],
      søkListe: [],
      currentList: "SerNå",
      feed: true,
    };
    //Bind events to components
    this.byttSkalSe = this.byttSkalSe.bind(this);
    this.byttSerNå = this.byttSerNå.bind(this);
    this.byttHarSett = this.byttHarSett.bind(this);
    this.byttMineFavoritter = this.byttMineFavoritter.bind(this);
    this.handleMediumChange = this.handleMediumChange.bind(this);
    this.handleFriendChange = this.handleFriendChange.bind(this);
    this.checkBoxHandler = this.checkBoxHandler.bind(this);
  }
  //Change state when clicking different lists
  byttSkalSe(event) {
    this.setState({ currentList: "SkalSe" }, this.getMediumList);
    this.setState({ feed: false });
  }
  byttSerNå(event) {
    this.setState({ currentList: "SerNå" }, this.getMediumList);
    this.setState({ feed: false });
  }
  byttHarSett(event) {
    this.setState({ currentList: "HarSett" }, this.getMediumList);
    this.setState({ feed: false });
  }
  byttMineFavoritter(event) {
    this.setState({ currentList: "MineFavoritter" }, this.getMediumList);
    this.setState({ feed: false });
  }
  //Change state to update page to show either feed or lists
  checkBoxHandler(event) {
    this.setState({ feed: !this.state.feed });
  }
  //Change state on input when searching for friends
  handleFriendChange(event) {
    this.setState(
      {
        søkVenner: event.target.value,
      },
      this.søkEtterVenner
    );
  }
  //Change state on input when searching for media
  handleMediumChange(event) {
    this.setState(
      {
        søkMedium: event.target.value,
      },
      this.søkEtterMedium
    );
    console.log(this.state.søkMedium);
  }
  //Get a list of all your media in the current list
  getMediumList() {
    const getListe = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/list/byID/" + this.props.match.params.id,
          {
            params: {
              listName: this.state.currentList,
            },
          }
        );
        this.setState({ medieListe: response.data });
      } catch (error) {
        console.log(error);
      }
      this.componentDidMount();
    };
    getListe();
  }
  //Get a list of media that matches your search
  søkEtterMedium() {
    let tempMedium = this.state.søkMedium;

    const getSøkListe = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/list/filteredMedium",
          {
            params: {
              filter: tempMedium,
            },
          }
        );
        setSøkListe(response.data);
      } catch (error) {
        console.log(error);
      }
      this.componentDidMount();
    };
    //Render the media in the DOM
    const setSøkListe = (value) => {
      this.setState({ søkListe: value });

      let søkListElements = this.state.søkListe.map((item) => (
        <tr>
          <td>{item[1]}</td>
          <td>{item[3]}</td>
          <td>
            <button className="ui button" onClick={() => this.add(item[0])}>
              {" "}
              Legg Til
            </button>
          </td>
        </tr>
      ));
      this.setState({ søkListe: søkListElements });
    };
    getSøkListe();
  }
  //Function for adding medium to your list
  add(inn) {
    let medID = parseInt(inn);
    let userID = this.props.match.params.id;

    const addMedium = async () => {
      try {
        await axios.post(
          "http://localhost:8080/list/" + userID + "/addMedium/" + medID,
          null,
          {
            params: {
              listName: this.state.currentList,
            },
          }
        );
      } catch (error) {
        console.log(error);
      }

      this.componentDidMount();
    };

    addMedium();
  }
  //Send a request to search for a user
  søkEtterVenner() {
    console.log(this.state.søkVenner);
    let tempVenn = this.state.søkVenner;

    const getSøkVenneListe = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/users/findUser",
          {
            params: {
              displayName: tempVenn,
            },
          }
        );
        setSøkVenneListe(response.data);
      } catch (error) {
        console.log(error);
      }
    };
    //Render the result of the search
    const setSøkVenneListe = async (value) => {
      this.setState({ søkVennerListe: value });

      let søkVenneListElements = this.state.søkVennerListe.map((item) => (
        <div
          className="item"
          style={{ height: "20%", padding: "1em", width: "100%" }}
        >
          <span>{item[1]} </span>
          <button
            className="ui button"
            style={{ float: "right" }}
            onClick={() => this.addFriend(item[0])}
          >
            {" "}
            Legg Til
          </button>
          <div className="ui clearing divider"></div>
        </div>
      ));
      this.setState({ søkVennerListe: søkVenneListElements });
    };
    getSøkVenneListe();
  }
  //Function for adding the friend
  addFriend(inn) {
    let vennID = inn;
    let userID = this.props.match.params.id;
    console.log(vennID);
    console.log(userID);

    const addFriendToList = async () => {
      try {
        const response = await axios.post(
          "http://localhost:8080//friends/" + userID + "/addFriend/" + vennID,
          null,
          {}
        );
      } catch (error) {
        console.log(error);
      }
      this.componentDidMount();
    };

    addFriendToList();
  }
  //Render the page
  render() {
    const list = ( //if the list is selected this is what renders
      <div className="twelve wide column">
        <div className="ui container">
          <h1>Her er {this.state.currentList}-listen din </h1>
          <table className="ui celled table">
            <thead>
              <tr>
                <th className="t1">Navn:</th>
                <th className="t2">Beskrivelse:</th>
              </tr>
            </thead>
            <tbody>
              {/*  <tr> */}
              {this.state.medieListe}
              {/*  </tr> */}
            </tbody>
          </table>
        </div>
        <div className="ui clearing divider"></div>
        <h3>Legg til filmer og serier til listen din</h3>
        <div className="ui input">
          <input
            name="søkMedium"
            type="text"
            value={this.state.søkMedium}
            placeholder="Søk etter film eller serie"
            onChange={this.handleMediumChange}
          />
        </div>{" "}
        {this.state.søkListe.length > 0 && (
          <table className="ui celled table">
            <thead>
              <tr>
                <th className="t1">Navn:</th>
                <th className="t2">Beskrivelse:</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {/*  <tr> */}
              {this.state.søkListe}
              {/*  </tr> */}
            </tbody>
          </table>
        )}
      </div>
    );
    return (
      <div className="ui container" style={{ width: "80%" }}>
        <div className="row">
          <div className="ui secondary  menu">
            <Link
              to={"/Startside/" + this.props.match.params.id}
              className="item"
            >
              WatchList
            </Link>

            {this.state.adminInnhold}

            <div className="right menu">
              <div className="item">{this.state.username}</div>
              <div className="item">
                <div className="item">
                  <div className="ui toggle checkbox">
                    <input
                      type="checkbox"
                      name="public"
                      onChange={this.checkBoxHandler}
                      checked={this.state.feed}
                    />
                    <label>Feed</label>
                  </div>
                </div>

                <div className="item">
                  <i className="power off icon"></i>
                  <Link to="/">Logg ut</Link>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="row">
          <div
            className="ui three column centered padded grid"
            style={{ height: "95vh" }}
          >
            <div className="four wide teal column">
              {/*<MineVenner />  */}

              <div className="ui container">
                <h1>
                  <i className="users icon"></i>
                  Mine venner
                </h1>
                <div className="ui selection list">
                  {/* Liste over alle vennene man har */}
                  {this.state.venneListe}
                  <br></br>
                  <p>Legg til venner:</p>
                  <div className="ui clearing divider"></div>
                  <div className="ui input">
                    <input
                      name="søkVenner"
                      type="text"
                      value={this.state.søkVenner}
                      placeholder="Søk etter venner.."
                      onChange={this.handleFriendChange}
                    />
                  </div>
                  <br></br>
                  <div style={{ maxHeight: "40vh", overflowY: "scroll" }}>
                    {this.state.søkVennerListe}
                  </div>
                </div>
              </div>
              <div className="ui clearing divider"></div>
              <div className="ui container">
                <h1>
                  <i className="folder icon"></i>
                  Mine Lister
                </h1>
                <div className="ui divided selection list">
                  <a className="item" onClick={this.byttSkalSe}>
                    Skal se
                  </a>
                  <a className="item" onClick={this.byttSerNå}>
                    Ser nå
                  </a>
                  <a className="item" onClick={this.byttHarSett}>
                    Har sett
                  </a>
                  <a className="item" onClick={this.byttMineFavoritter}>
                    Mine favoritter
                  </a>
                </div>
                <div className="ui container">
                  <h1>
                    <i className="folder icon"></i>
                    Mine egendefinerte lister
                  </h1>
                  <p>Du har ingen egendefinerte lister</p>
                </div>
              </div>
            </div>
            {this.state.feed ? <Feed /> : list}{" "}
            {/* If the state is set to feed it will render the feed, if not it will render the list */}
          </div>
        </div>
      </div>
    );
  }
  //Call these functions every time the state changes
  componentDidMount() {
    let tempID = this.props.match.params.id;
    //Get the username for the logged in user
    const getUserName = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/users/byID/" + tempID,
          null,
          {}
        );
        setUserName(response.data);
      } catch (error) {
        console.log(error);
      }
    };
    //Render the username
    const setUserName = (value) => {
      this.setState({ username: value }); //Value?
    };

    getUserName();

    //Render the current list
    const setListe = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/list/byID/" + this.props.match.params.id,
          {
            params: {
              listName: this.state.currentList,
            },
          }
        );
        let listElements = response.data.map((item, index) => (
          <tr key={index}>
            <td>{item[0]}</td>
            <td>{item[2]}</td>
          </tr>
        ));
        this.setState({ medieListe: listElements });
      } catch (error) {
        console.log(error);
      }
    };
    setListe();

    //Retrieve the friend list
    const getVenneListe = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/friends/" + tempID + "/getAllFriends",
          null,
          {}
        );
        setVenneListe(response.data);
      } catch (error) {
        console.log(error);
      }
    };
    //Render the friend list every time the component updates.
    const setVenneListe = (value) => {
      this.setState({ venneListe: value });

      let listFriendsElements = this.state.venneListe.map((item) => (
        <li className="item" key={item[0]}>
          <i className="user icon"></i>
          {item[1]}
        </li>
      ));
      this.setState({ venneListe: listFriendsElements });
    };
    getVenneListe();

    //Check if the user is admin
    const sjekkAdmin = async () => {
      let tempUsername = this.state.username;
      console.log(tempUsername);

      try {
        const response = await axios.get(
          "http://localhost:8080//users/" + tempUsername + "/checkAdmin",
          null,
          {}
        );
        setAdmin(response.data);
      } catch (error) {
        console.log(error);
      }
    };

    //Render the admin page button if the user is admin
    const setAdmin = (value) => {
      this.setState({ admin: value });

      let tempAdmin = this.state.admin;

      if ((tempAdmin = 0)) {
        alert("Du er ikke admin");
      } else {
        this.setState({
          adminInnhold: (
            <Link to="/admin/innstillinger" className="item">
              AdminInnstillinger
            </Link>
          ),
        });
      }
    };
    sjekkAdmin();
  }
}
