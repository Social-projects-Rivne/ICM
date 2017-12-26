import React, { Component } from 'react';
import {Link, Switch, Route, Redirect} from 'react-router-dom';
import {Container} from 'reactstrap';
import ClientHeader from '../../components/ClientHeader/ClientHeader'
import Footer from '../../components/Footer/Footer';
import axios from 'axios';
import Client from "./containers/Client/Client";


export default class SignUp extends Component{

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
                        <Route exact path="/maps" name="IssueMap" component={IssueMap}/>
                        </Switch>
                    </Container>
                </div>
                <Footer />
            </div>
        )
    }
}
