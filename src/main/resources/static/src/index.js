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
// Temp fix for reactstrap
import '../scss/core/_dropdown-menu-right.scss'

// Containers
import Full from './containers/Full/'
import Users from "./views/Users/Users";

ReactDOM.render((
    <BrowserRouter>
        <Switch>
            <Route exact path="/registration" name="SignUp" component={Authorization}/>
            <Route exact path="/login" name="Login" component={Authorization}/>
            <Route path="/" name="Home" component={Full}/>
            <Route path="/users" name="Users" component={Users}/>
        </Switch>
    </BrowserRouter>
), document.getElementById('root'));
