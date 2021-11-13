import React, { Component } from "react";
import axios from "axios";
import sha256 from "js-sha256";

import Startnav from "./Startnav";

//The component for logging in.
export default class Login extends Component {
  constructor() {
    super();

    this.state = {
      username: "",
      password: "",
      loggedIn: false,
      error: "",
      id: null,
    };

    this.login = this.login.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(event) {
    this.setState({
      [event.target.name]: event.target.value,
    });
  }

  login(event) {
    //Function for checking with the database if the user is registered and that the username and password are corerct.
    event.preventDefault();

    const loginUser = async () => {
      try {
        const response = await axios.post("http://localhost:8080/login", null, {
          params: {
            username: this.state.username,
            password: sha256(this.state.password),
          },
        });
        changeLogin(response.data);
      } catch (error) {
        console.log(error);
      }

      try {
        let err = "";
        if (this.state.loggedIn) {
          this.props.history.push("/Startside/" + this.state.id); //If the login succeeds then you get redirected to the main page
        } else {
          err = (
            <div>
              <div className="ui from error">
                <div className="ui error message">
                  <div className="header">Feilmelding</div>
                  <p>Brukernavn eller passord er feil, prøv igjen</p>
                </div>
              </div>
            </div>
          );
        }
        this.setState({ error: err });
      } catch (error) {
        alert(error.message);
      }
    };

    const changeLogin = (value) => {
      this.setState({ loggedIn: value[0], username: "", id: value[1] }); //Setting the state to the values that they tried to log in with.
    };

    loginUser();
  }
  //Render the component which the user uses to log in.
  render() {
    return (
      <div>
        <Startnav />
        <div className="ui container" style={{ margin: "auto" }}>
          <h1>Logg inn her</h1>
          <form className="field">
            <div className="ui input">
              <input
                type="text"
                name="username"
                placeholder="Username"
                value={this.state.username}
                onChange={this.handleChange}
                required
                autoFocus
              />
            </div>
            <br />
            <div className="ui input">
              <input
                type="password"
                name="password"
                placeholder="Password"
                value={this.state.password}
                onChange={this.handleChange}
                required
              />
            </div>
            <br />

            <button
              className="ui primary button"
              type="submit"
              onClick={this.login}
            >
              Logg inn
            </button>
            {this.state.error}
          </form>
        </div>
      </div>
    );
  }
}
