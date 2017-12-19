import React, {Component} from 'react';
import {Route} from 'react-router-dom';
import Login from "../../views/Login/Login";
import SignUp from "../../views/SignUp/SignUp";
import RestorePassword from "../../views/RestorePassword/RestorePassword";


class Authorization extends Component{
    render() {
        return (
            <div className="app">
                <Route path="/login" name="Login" component={Login}/>
                <Route path="/registration" name="SignUp" component={SignUp}/>
                <Route path="/restore-password" name="RestorePassword" component={RestorePassword}/>
            </div>
        );
    }
}

export default Authorization;
