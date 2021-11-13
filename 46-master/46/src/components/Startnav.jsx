import React, { Component } from "react";
import { Link } from "react-router-dom";

export default class Startnav extends Component {
  //Render the login and register page
  render() {
    return (
      <div className="ui placeholder segment">
        <div className="ui two column very relaxed stackable grid">
          <div className="middle aligned column">
            <div className="ui big button">
              <i className="user outline icon"></i>
              <Link to="../Login">Logg inn</Link>
            </div>
          </div>

          <div className="middle aligned column">
            <div className="ui big button">
              <i className="signup icon"></i>
              <Link to="/Registrer">Registrer</Link>
            </div>
          </div>
        </div>
        <div className="ui vertical divider">Eller</div>
      </div>
    );
  }
}
