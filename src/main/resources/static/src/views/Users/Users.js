import React, { Component } from 'react';
import axios from 'axios';
import {Button, Card, CardBody, CardHeader, Col, Row, Table} from "reactstrap";
import {Link} from "react-router-dom";
import qs from 'qs';

class User extends Component {

    constructor(props) {
        super(props);
        this.state = {
            userList: this.load(),
            users : "Init users!"
        };
        this.load = this.load.bind(this);
    }

    load(event){
        axios.get("api/users")
            .then(
                (response) => {
                    this.setState({users: response.data});
                    console.log(response);
                });
    }

    render() {
        return (
            <div className="animated fadeIn">
                <Row>
                    <Col xs="12" lg="12">
                        <Card>
                            <CardHeader>Users list</CardHeader>
                            <CardBody>
                                <Table responsive bordered>
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Role</th>
                                        <th>registrationDate</th>
                                        <th>First name</th>
                                        <th>Last name</th>
                                        <th>Pasword</th>
                                        <th>Email</th>
                                        <th>Phone</th>
                                        <th>User Agreement</th>
                                        <th>User status</th>
                                        <th>Delete date</th>
                                        <th>Avatar</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {this.state.users.map(function(user, i) {
                                        return (
                                            <User key={i} user={user}/>
                                        );
                                    })}
                                    </tbody>
                                </Table>
                            </CardBody>
                        </Card>
                    </Col>
                </Row>
            </div>
        )
    }


}

export default User;