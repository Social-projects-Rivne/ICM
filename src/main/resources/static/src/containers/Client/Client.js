import React, { Component } from 'react';
import {Link, Switch, Route, Redirect} from 'react-router-dom';
import {Container} from 'reactstrap';
import Login from "../../views/Login/Login";
import SignUp from "../../views/SignUp/SignUp";
import RestorePassword from "../../views/RestorePassword/RestorePassword";
import ClientHeader from '../../components/ClientHeader/ClientHeader'
import IssueMap from "../../views/IssueMap/IssueMap";
import Footer from '../../components/Footer/Footer';
import axios from 'axios';
import Page404 from "../../views/Page404/Page404";


export default class Client extends Component{

    constructor(props) {
        super(props);

        this.state = {
            userAuthorities : null
        }
    }

    componentDidMount(){
        const _this = this;
        axios.get("/api/authority")
            .then(function(response){
                _this.setState({userAuthorities: response.data});
                console.log("authority", response.data)
            })
    }

    render(){
        return(
            <div className="app">
                <ClientHeader userAuthorities={this.state.userAuthorities}/>
                <div className="app-body">
                    <Container fluid>
                        <Switch>
                            <Route exact path="/" name="Index" component={IssueMap}/>
                            <Route exact path="/login" name="Login" component={Login}/>
                            <Route exact path="/registration" name="SignUp" component={SignUp}/>
                            <Route exact path="/restore-password" name="RestorePassword" component={RestorePassword}/>
                            <Route path="/*" component={Page404} />
                        </Switch>
                    </Container>
                </div>
                <Footer />
            </div>
        )
    }
}
