import React, { Component } from 'react';
import {Link, Switch, Route, Redirect} from 'react-router-dom';
import {Container} from 'reactstrap';
import ClientHeader from '../../components/ClientHeader/ClientHeader'
import Footer from '../../components/Footer/';

export default class SignUp extends Component{
    render(){
        return(
            <div className="app">
                <ClientHeader />
                <div className="app-body">
                    <Container fluid>
                        <Switch>

                        </Switch>
                    </Container>
                </div>
                <Footer />
            </div>
        )
    }
}