import React from "react";
//Template for making the items in the feed consistent.
const FeedItem = (props) => {
  return (
    <div className="event">
      <div className="ui card" style={{ width: "80%", margin: "auto" }}>
        <div className="content">
          <div className="ui blue header">{props.name}</div>
          <div className="meta">{props.title}</div>
          <div className="description">{props.content}</div>
        </div>
        <div className="content">
          <div className="meta">
            <i className="heart red icon"></i>
            {props.likes} likes
            <span style={{ float: "right" }}>{props.time}</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FeedItem;
