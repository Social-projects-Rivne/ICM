import React, {Component} from 'react';
import {Link, Switch, Route, Redirect} from 'react-router-dom';
import Login from "../../views/Login/Login";
import SignUp from "../../views/SignUp/SignUp";
import ConfirmEmail from "../../views/ConfirmEmail/ConfirmEmail";


class Authorization extends Component{
    render() {
        return (
            <div className="app">
                <Route path="/login" name="Login" component={Login}/>
                <Route path="/registration" name="SignUp" component={SignUp}/>
                <Route path="/confirmEmail" name="ConfirmEmail" component={ConfirmEmail}/>
            </div>
        );
    }
}

export default Authorization;
