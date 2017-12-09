import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter, Route, Switch} from 'react-router-dom';

// Styles
// Import Font Awesome Icons Set
import 'font-awesome/css/font-awesome.min.css';
// Import Simple Line Icons Set
import 'simple-line-icons/css/simple-line-icons.css';
// Import Main styles for this application
import '../scss/style.scss'
import '../scss/_custom.scss'
// Temp fix for reactstrap
import '../scss/core/_dropdown-menu-right.scss'

// Containers
import Full from './containers/Full/'
import Authorization from "./containers/Authorization/Authorization";


ReactDOM.render((
  <BrowserRouter>
    <Switch>
      <Route exact  path="/"            name="Home"         component={Full}/>
      <Route exact  path="/issues"      name="Issues"       component={Full}/>
      <Route        path="/issues"      name="Issues"       component={Full}/>
      <Route exact  path="/petitions"   name="Petitions"    component={Full}/>
      <Route        path="/petitions"   name="Petitions"    component={Full}/>
      <Route exact  path="/events"      name="Events"       component={Full}/>
      <Route        path="/events"      name="Events"       component={Full}/>
      <Route exact  path="/users"       name="Users"        component={Full}/>
      <Route        path="/users"       name="Users"        component={Full}/>
      <Route exact  path="/settings"    name="Settings"     component={Full}/>
      <Route        path="/settings"    name="Settings"     component={Full}/>

      <Route path="/login"      name="Login"        component={Authorization}/>
      <Route path="/registration"      name="SignUp"        component={Authorization}/>

    </Switch>
  </BrowserRouter>
), document.getElementById('root'));
