import React, { Component } from "react";

import axios from "axios";
import sha256 from "js-sha256";
import Startnav from "./Startnav";

//Regexes to make sure the format for all input fields are correct.
const usernameRegex = new RegExp("[A-Za-z\\d]{3,32}");
const passwordRegex = new RegExp(
  "^(?=.*[0-9]+.*)(?=.*[a-zA-Z]+.*)[0-9a-zA-Z]{6,}$"
);

//^[A-Za-z\\d]{8,}");
const visningsnavnRegex = new RegExp("[A-Za-z\\d]{3,32}");

export default class Registrer extends Component {
  constructor() {
    super();

    this.state = {
      username: "",
      password: "",
      visningsnavn: "",
      registrert: false,
      registrationErrors: "",
    };

    //Bind the components to events.
    this.register = this.register.bind(this);
    this.visningsnavn = this.visningsnavn.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  //Change state when entering something in the input field
  handleChange(event) {
    this.setState({
      [event.target.name]: event.target.value,
    });
  }

  //Function for registering
  register(event) {
    event.preventDefault();

    // Client side validation start
    let err = "";
    if (!usernameRegex.test(this.state.username)) {
      //If the regex doesnt match the username then render an error
      err = (
        <div>
          <div className="ui from error">
            <div className="ui error message">
              <div className="header">Feilmelding</div>
              <p>
                Brukernavn må være mellom 3 og 32 bokstaver og kan bare
                inneholde tall og bokstaver
              </p>
            </div>
          </div>
        </div>
      );
    } else if (!passwordRegex.test(this.state.password)) {
      //If the password doesnt match the regex then render an error
      err = (
        <div>
          <div className="ui from error">
            <div className="ui error message">
              <div className="header">Feilmelding</div>
              <p>
                Passordet må inneholde minst én bokstav, minst ett tall og være
                lengre enn 6 tegn
              </p>
            </div>
          </div>
        </div>
      );
    } else if (!visningsnavnRegex.test(this.state.visningsnavn)) {
      //If the displayName doesnt match the regex then render an error
      err = (
        <div>
          <div className="ui from error">
            <div className="ui error message">
              <div className="header">Feilmelding</div>
              <p>
                Visningsnavnet ditt må være mellom 3 og 32 bokstaver og kan bare
                inneholde tall og bokstaver
              </p>
            </div>
          </div>
        </div>
      );
    }

    this.setState({ error: err }); //Set the state to match the error

    const sendUser = async () => {
      //Send the user info to the database
      try {
        const response = await axios.post("http://localhost:8080/users", null, {
          params: {
            username: this.state.username,
            password: sha256(this.state.password), //Hash the password before sending to prevent people from monitoring the traffic and stealing login credentials
            isAdmin: 1,
            displayName: this.state.visningsnavn,
          },
        });
        if (response.data === true) {
          changeRegistrer(response.data);
          err = (
            <div>
              <div className="ui from success">
                <div className="ui success message">
                  <div className="header">Gratulerer!</div>
                  <p>
                    Du har registrert en ny brukerkonto. Du kan nå logge inn med
                    ditt brukernavn og passord på forsiden.
                  </p>
                </div>
              </div>
            </div>
          );
        } else if (response.data === false) {
          err = (
            <div>
              <div className="ui from error">
                <div className="ui error message">
                  <div className="header">Feilmelding</div>
                  <p>Brukernavnet er allerede tatt.</p>
                </div>
              </div>
            </div>
          );
        }
        this.setState({ error: err });
      } catch (error) {
        console.log(error);
      }
    };

    const changeRegistrer = (value) => {
      this.setState({ registrert: value });
    };
    if (
      this.state.username.match(usernameRegex) &&
      this.state.password.match(passwordRegex) &&
      this.state.visningsnavn.match(visningsnavnRegex)
    ) {
      sendUser();
      return;
    }
    return;
  }
  //Prevent the button from
  visningsnavn(event) {
    event.preventDefault();
  }
  //Render the component
  render() {
    return (
      <div>
        <Startnav />
        <div className="ui container">
          <h1>Lag en bruker her</h1>
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
            <br></br>
            <div className="ui input">
              <input
                className="ui input"
                type="password"
                name="password"
                placeholder="Password"
                value={this.state.password}
                onChange={this.handleChange}
                required
              />
            </div>
            <br></br>
            <div className="ui input">
              <input
                type="text"
                name="visningsnavn"
                placeholder="Visningsnavn"
                value={this.state.visningsnavn}
                onChange={this.handleChange}
                required
              />
            </div>
            <br></br>
            <button
              className="ui primary button"
              type="button"
              onClick={this.register}
            >
              Register
            </button>

            {this.state.error}
          </form>
        </div>
      </div>
    );
  }
}
