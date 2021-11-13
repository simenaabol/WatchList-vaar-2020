import React, { Component } from "react";
import FeedItem from "./FeedItem";
//Started the code for making the feed, although currently it only contains mock data
export default class Feed extends Component {
  constructor() {
    super();

    this.state = {
      informasjon: [],
    };
  }

  render() {
    return (
      <div className="twelve wide column">
        <div className="ui container">
          <div className="ui feed">
            <h1 className="ui title">Feed</h1>
            <div className="ui clearing divider"></div>
            <div className="ui container">
              <div
                className="ui card"
                style={{ width: "80%", margin: "auto", marginBottom: "2em" }}
              >
                <div className="content">
                  <h2>Post something to your feed!</h2>
                </div>
                <div className="content">
                  <div className="ui input" style={{ width: "80%" }}>
                    <input type="text" placeholder="A fun post" />
                  </div>
                  <button
                    className="ui primary button"
                    style={{ float: "right" }}
                  >
                    Submit
                  </button>
                </div>
              </div>
            </div>
            <FeedItem
              name="Elliott Fu"
              title="Friend"
              content="Wow! Dette var tøft. Jeg kan legge til filmer i listene mine!"
              likes="5"
              time="23/03/2020"
            />
            <FeedItem
              name="Bob Pedersen"
              title="Friend"
              content="Watchlist må være den beste nettsiden for å lagre film og
              dele anmeldelser med venner! Jeg tar av meg hatten"
              likes="290"
              time="23/03/2020"
            />
          </div>
        </div>
      </div>
    );
  }
}
