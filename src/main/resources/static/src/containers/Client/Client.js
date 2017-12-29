import React, { Component } from 'react';
import {Link, Switch, Route, Redirect} from 'react-router-dom';
import {Container} from 'reactstrap';
import ClientHeader from '../../components/ClientHeader/ClientHeader'
import Footer from '../../components/Footer/Footer';
import EditProfile from '../../views/Settings/EditProfile';
import axios from 'axios';

export default class Client extends Component{

    constructor(props) {
        super(props);

        this.state = {
            user:
                {
                    email: "",
                    firstName: "",
                    lastName: "",
                    phone: "",
                    userAuthorities : null
                }

        }
    }

    componentDidMount(){
        const _this = this;
        axios.get('/api/userDetails')
            .then(function (response) {
                _this.setState( prevState => ({
                    user: {...prevState.user, email: response.data.email,
                        firstName: response.data.firstName,
                        lastName: response.data.lastName,
                        phone: response.data.phone,
                        userAuthorities: response.data.authorities
                    }})
                );
            })
            .catch(function () {})
    }

    render(){
        return(
            <div className="app">
                <ClientHeader userAuthorities={this.state.user.userAuthorities}/>
                <div className="app-body">
                    <Container fluid>
                        <Switch>
                            <Route path="/settings/profile/" name="Client" component={() => (<EditProfile user={this.state.user}/>)}/>
                        </Switch>
                    </Container>
                </div>
                <Footer />
            </div>
        )
    }
}
