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
            firstName: "",
            lastName: "",
            phone: "",
            oldPassword: "",
            newPassword: "",
            newPasswordValid: "",
            confirmNewPassword: "",
            confirmNewPasswordValid: ""
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSetNewPasswordBtn = this.handleSetNewPasswordBtn.bind(this);
    }


    handleChange(event){
        const name = event.target.name;
        const value = event.target.value;

        this.setState({[name]: value},
            () => { this.validateField(name, value) });
    }

    validateField(fieldName, value){

    }


    handleSetNewPasswordBtn(){

    }

    render(){
        return(
            <Container style={{paddingTop: '30px', fontFamily: EditProfile.fonts()}}>
                <Col sm={10}>
                    <h3 >Profile Settings 2</h3>
                    <hr className="col-12"/>
                    <Row style={{marginBottom: '40px'}}>

                        <Col sm={8}>
                            <FormGroup>
                                <Label htmlFor='first-name' style={{fontWeight:'600'}}>First name</Label>
                                    <Input type="text" name="lastName" id="first-name"
                                           bsSize="lg"
                                           placeholder="first name"/>
                            </FormGroup>

                            <FormGroup>
                                <Label htmlFor='last-name' style={{fontWeight:'600'}}>Last name</Label>
                                    <Input type="text" name="lastName" id="last-name"
                                           bsSize="lg"
                                           placeholder="last name"/>

                            </FormGroup>

                            <FormGroup>
                                <Label htmlFor='phone' style={{fontWeight:'600'}}>Phone</Label>
                                    <Input type="text" name="lastName" id="phone"
                                           bsSize="lg"
                                           placeholder="+380958379474"/>
                            </FormGroup>

                            <Button size='lg'>Update contacts form</Button>

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
                                    <Input type="password" name="oldPassword" id="oldPassword"
                                           bsSize="lg"
                                           placeholder="Old Password"
                                           onChange={this.handleChange}
                                           value={this.state.value}
                                    />
                            </FormGroup>

                            <FormGroup>
                                <Label htmlFor='password' style={{fontWeight:'600'}}>Password</Label>
                                    <Input type="password" name="newPassword" id="newPassword"
                                           bsSize="lg"
                                           placeholder="Password"
                                           onChange={this.handleChange}
                                           value={this.state.value}
                                    />
                            </FormGroup>

                            <FormGroup>
                                <Label htmlFor='password2' style={{fontWeight:'600'}}>Password</Label>
                                    <Input type="password" name="confirmNewPassword" id="confirmNewPassword"
                                           bsSize="lg"
                                           placeholder="Password"
                                           onChange={this.handleChange}
                                           value={this.state.value}
                                    />
                            </FormGroup>

                            <Button size='lg' onClick={this.handleSetNewPasswordBtn}>Set the new password</Button>
                        </Col>
                    </Row>
                </Col>
            </Container>
        )
    }


    static fonts(){
        return '-apple-system, BlinkMacSystemFont, "Segoe UI", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol"';
    }
}
