import React, { Component } from "react";
import axios from "axios";

//Component that renders the admin page
export default class AdminSide extends Component {
  constructor() {
    super();

    this.state = {
      liste: [], //Initiating empty array as state. This is where the list of users will be.
    };
  }
  //Functon for getting the user list from the server
  getUsers() {
    const getListe = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/users/withID/", //the endpoint for getting all users with ID
          null,
          {}
        );
        this.setState({ liste: response.data }); //Setting the state to the response from the server
      } catch (error) {
        console.log(error);
      }
      this.componentDidMount();
    };
    getListe();
  }

  render() {
    return (
      <div className="ui container">
        <h1>Adminpanel</h1>
        <div
          style={{
            display: "flex",
            flexWrap: "wrap",
            justifyContent: "center",
          }}
        >
          {this.state.liste}
        </div>
      </div>
    );
  }
  //Making it get the list every time the component updates
  componentDidMount() {
    const getList = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/users/withID/",
          null,
          {}
        );
        this.setState({
          //Updating the state to render the list
          liste: response.data.map((item) => (
            <div
              className="ui card"
              style={{ minHeight: "10vh", margin: "1em" }}
              key={item[1]}
            >
              <div className="content">
                <div className="ui blue header">{item[0]}</div>
                <div className="ui clearing divider"></div>
                <button
                  className="ui button"
                  style={{ color: "red", float: "right" }}
                  onClick={() => delUser(item[0])}
                >
                  Delete
                </button>
              </div>
            </div>
          )),
        });
      } catch (error) {
        console.log(error);
      }
    };
    getList();
    //function for deleting users when you press the button
    const delUser = async (username) => {
      try {
        await axios.delete("http://localhost:8080/users/" + username, null, {});
      } catch (error) {
        console.log(error);
      }
      //  console.log("???")
      this.componentDidMount();
    };
  }
}
