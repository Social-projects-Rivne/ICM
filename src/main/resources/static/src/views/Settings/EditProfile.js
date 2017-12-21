import React, { Component } from 'react';
import {Link, Switch, Route, Redirect} from 'react-router-dom';
import {Alert, Label, Col, Button, Row, Form, FormGroup, FormFeedback, Input, InputGroup, InputGroupAddon} from 'reactstrap';
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

    font(){
        return '-apple-system, BlinkMacSystemFont, "Segoe UI", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol"';
    }

    render(){
        return(
            <Container style={{paddingTop: '30px', fontFamily: this.font()}}>
    <Col sm={10}>
            <h3 >Profile Settings</h3>
        <hr className="col-12"/>
            <Row style={{marginBottom: '40px'}}>

    <Col sm={8}>
            <FormGroup>
            <Label htmlFor='first-name' style={{fontWeight:'600'}}>First name</Label>
        <InputGroup>
        <InputGroupAddon><i className="fa fa-user fa-fw"/></InputGroupAddon>
            <Input type="text" name="lastName" id="first-name"
        bsSize="lg"
        placeholder="first name"/>
            </InputGroup>

            </FormGroup>

            <FormGroup>
            <Label htmlFor='last-name' style={{fontWeight:'600'}}>Last name</Label>
        <InputGroup>
        <InputGroupAddon><i className="fa fa-user fa-fw"/></InputGroupAddon>
            <Input type="text" name="lastName" id="last-name"
        bsSize="lg"
        placeholder="last name"/>
            </InputGroup>

            </FormGroup>

            <FormGroup>
            <Label htmlFor='phone' style={{fontWeight:'600'}}>Phone</Label>
        <InputGroup>
        <InputGroupAddon><i className="fa fa-phone fa-fw"/></InputGroupAddon>
            <Input type="text" name="lastName" id="phone"
        bsSize="lg"
        placeholder="+380958379474"/>
            </InputGroup>

            </FormGroup>

            </Col>
            <Col sm={4} style={{paddingLeft: '60px'}}>
    <label htmlFor='profile-photo' style={{fontWeight:'600'}}>Profile picture</label>
        <div id='profile-photo' className='border-radius' style={{height:'200px', width:'200px', background: '#336fce'}}></div>
        </Col>

        </Row>


        <h3>Change password</h3>
        <hr className="col-12"/>
            <Row>
            <Col sm={8}>
            <FormGroup>
            <Label htmlFor='password' style={{fontWeight:'600'}}>Old password</Label>
        <InputGroup>
        <InputGroupAddon><i className="fa fa-key fa-fw"/></InputGroupAddon>
            <Input type="password" name="password" id="password"
        bsSize="lg"
        placeholder="Password"/>
            </InputGroup>
            </FormGroup>

            <FormGroup>
            <Label htmlFor='password' style={{fontWeight:'600'}}>Password</Label>
        <InputGroup>
        <InputGroupAddon><i className="fa fa-key fa-fw"/></InputGroupAddon>
            <Input type="password" name="password" id="password"
        bsSize="lg"
        placeholder="Password"/>
            </InputGroup>
            </FormGroup>

            <FormGroup>
            <Label htmlFor='password2' style={{fontWeight:'600'}}>Password</Label>
        <InputGroup>
        <InputGroupAddon><i className="fa fa-key fa-fw"/></InputGroupAddon>
            <Input type="password" name="password" id="password2"
        bsSize="lg"
        placeholder="Password"/>
            </InputGroup>
            </FormGroup>
            </Col>
            </Row>


            </Col>
            </Container>
    )
    }
}
