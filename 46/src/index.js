import React from "react";
import ReactDOM from "react-dom";
import { HashRouter, Route } from "react-router-dom";

//import App from "./App.jsx";
import * as serviceWorker from "./serviceWorker";

import Startnav from "./components/Startnav";
import Login from "./components/Login";
import Registrer from "./components/Registrer";
import Startside from "./components/Startside";
import AdminSide from "./components/AdminSide";

import FinnesIkke from "./components/FinnesIkke";

//ReactDOM.render(<App />, document.getElementById('root'));

ReactDOM.render(
  //ruting
  <HashRouter>
    <div>
      <Route exact path="/" component={Startnav} />
      <Route exact path="/Login" component={Login} />
      <Route exact path="/Registrer" component={Registrer} />

      <Route exact path="/Startside/:id" component={Startside} />

      <Route exact path="/admin/innstillinger" component={AdminSide} />

      <Route exact path="/FinnesIkke" component={FinnesIkke} />
    </div>
  </HashRouter>,
  document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
