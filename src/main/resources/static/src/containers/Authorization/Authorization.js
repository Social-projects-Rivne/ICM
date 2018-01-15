import React, {Component} from 'react';
import {Link, Switch, Route, Redirect} from 'react-router-dom';
import Login from "../../views/Login/Login";
import SignUp from "../../views/SignUp/SignUp";
import RestorePassword from "../../views/RestorePassword/RestorePassword";
import ClientHeader from "../../components/ClientHeader/ClientHeader";
import Footer from "../../components/Footer";
import axios from "axios/index";
import {Container} from "reactstrap";


class Authorization extends Component{

    constructor(props){
        super(props);

        this.state = {
            userAuthorities: null
        };

    }

    componentDidMount(){
        const _this = this;
        axios.get("/api/authority")
            .then(function(response){
                _this.setState({userAuthorities: response.data});
            })
    }


    render() {
        return (
            <div className="app">
                <ClientHeader userAuthorities={this.state.userAuthorities}/>
                <div className="app-body">
                    <Container fluid>
                        <Switch>
                            <Route path="/login" name="Login" component={Login}/>
                            <Route path="/registration" name="SignUp" component={SignUp}/>
                            <Route path="/restore-password" name="RestorePassword" component={RestorePassword}/>
                        </Switch>
                    </Container>
                </div>
                <Footer />
            </div>
        );
    }
}

export default Authorization;
