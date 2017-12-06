import React, {Component} from 'react';
import {Link, Switch, Route, Redirect} from 'react-router-dom';
import Login from "../../views/Login/Login";

class Authorization extends Component{
    render() {
        return (
            <div className="app">
                <Route path="/login" name="Login" component={Login}/>
            </div>
        );
    }
}

export default Authorization;
