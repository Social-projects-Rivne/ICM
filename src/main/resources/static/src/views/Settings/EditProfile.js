import React, { Component } from 'react';
import {Link, Switch, Route, Redirect} from 'react-router-dom';
import {Alert, Col, Button, Row, Form, FormGroup, FormFeedback, Input, InputGroup, InputGroupAddon} from 'reactstrap';
import {Container} from 'reactstrap';
import ClientHeader from '../../components/ClientHeader/ClientHeader'
import Footer from '../../components/Footer/Footer';
import axios from 'axios';

export default class EditProfile extends Component{
    constructor(props){
        super(props);

        this.state = {

        }
    }

    render(){
        return(
            <div className="container col-6">
                <h3>Profile Settings</h3>
                <hr/>
                <Row>

                    <Col sm={8}>
                                    <FormGroup>
                                        <Col sm={12}>
                                            <InputGroup>
                                                <InputGroupAddon><i className="fa fa-user fa-fw"/></InputGroupAddon>
                                                <Input type="text" name="lastName" id="lastName"
                                                       bsSize="lg"
                                                       placeholder="first name"/>
                                            </InputGroup>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup>
                                        <Col sm={12}>
                                            <InputGroup>
                                                <InputGroupAddon><i className="fa fa-user fa-fw"/></InputGroupAddon>
                                                <Input type="text" name="lastName" id="lastName"
                                                       bsSize="lg"
                                                       placeholder="last name"/>
                                            </InputGroup>
                                        </Col>
                                    </FormGroup>
                    </Col>
                    <Col>
                        <div style={{height:'150px', background: 'red'}}></div>
                    </Col>

                </Row>
            </div>
        )
    }
}