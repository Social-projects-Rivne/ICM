import React, { Component } from 'react';
import {Link, Switch, Route, Redirect} from 'react-router-dom';
import {Button, Container} from 'reactstrap';
import Login from "../../views/Login/Login";
import SignUp from "../../views/SignUp/SignUp";
import RestorePassword from "../../views/RestorePassword/RestorePassword";
import ClientHeader from '../../components/ClientHeader/ClientHeader'
import IssueMap from "../../views/IssueMap/IssueMap";
import Footer from '../../components/Footer/Footer';
import EditProfile from '../../views/Settings/EditProfile';
import axios from 'axios';
import Page404 from "../../views/Page404/Page404";

export default class Client extends Component{

    constructor(props) {
        super(props);

        this.state = {
            user:
                {
                    id: null,
                    email: "",
                    firstName: "",
                    lastName: "",
                    phone: "",
                    userAuthorities : null
                }
        };

        this.temp = this.temp.bind(this);
        this.randomNotification = this.randomNotification.bind(this);
    }

    componentDidMount(){
        const _this = this;
        axios.get('/api/userDetails')
            .then(function (response) {
                _this.setState( prevState => ({
                    user: {...prevState.user,
                        id: response.data.id,
                        email: response.data.email,
                        firstName: response.data.firstName,
                        lastName: response.data.lastName,
                        phone: response.data.phone,
                        userAuthorities: response.data.authorities
                    }})
                );
            })
            .catch(function () {})
    }

    temp(){
        Notification.requestPermission().then(function(permission) { console.log('permission', permission); });
    }

    randomNotification() {
        const options = {
            body: "Body push",
            icon: 'https://material.io/icons/static/images/icons-180x180.png',
        };

        const n = new Notification('Title says', options);
        setTimeout(n.close.bind(n), 4000);
    }

    render(){
        return(
            <div className="app">
                <ClientHeader userAuthorities={this.state.user.userAuthorities}/>
                <div className="app-body">
                    <Container fluid>
                        <Switch>
                            <Route exact path="/" name="Index" component={IssueMap}/>
                            <Route exact path="/login" name="Login" component={Login}/>
                            <Route exact path="/registration" name="SignUp" component={SignUp}/>
                            <Route exact path="/restore-password" name="RestorePassword" component={RestorePassword}/>
                            <Route exact path="/settings/profile/" name="Client" component={() => (<EditProfile user={this.state.user}/>)}/>
                            <Route path="/*" component={Page404} />
                        </Switch>
                    </Container>
                </div>
                <Button onClick={this.temp}>Prepare Push</Button>
                <Button onClick={this.randomNotification}>Make Push</Button>
                <Footer />
            </div>
        )
    }
}
