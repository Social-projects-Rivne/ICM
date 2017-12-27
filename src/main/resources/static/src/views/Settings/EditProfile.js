import React, { Component } from 'react';
import {Link, Switch, Route, Redirect} from 'react-router-dom';
import {Alert, Label, Col, Button, Row, Form, FormGroup, FormFeedback, Input, InputGroup, InputGroupAddon} from 'reactstrap';
import {Container} from 'reactstrap';
import ClientHeader from '../../components/ClientHeader/ClientHeader'
import Footer from '../../components/Footer/Footer';
import axios from 'axios';
import SignUp from "../SignUp";

export default class EditProfile extends Component{
    constructor(props){
        super(props);

        this.state = {
            firstName: "",
            lastName: "",
            phone: "",
            oldPassword: "",
            newPassword: "",
            newPasswordValid: false,
            confirmNewPassword: "",
            confirmNewPasswordValid: false,
            responseIsSuccses : null
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSetNewPasswordBtn = this.handleSetNewPasswordBtn.bind(this);
    }


    // TODO: rename to handleInputChange
    handleChange(event){
        const name = event.target.name;
        const value = event.target.value;

        this.setState({[name]: value},
            () => { this.validateField(name, value) });
    }

    validateField(fieldName, value){
        let newPasswordValid = this.state.newPasswordValid;
        let confirmNewPasswordValid = this.state.confirmNewPasswordValid;

        switch (fieldName){
            case 'newPassword':
                newPasswordValid = value.length >= 3;
                confirmNewPasswordValid = false;
                break;
            case 'confirmNewPassword':
                confirmNewPasswordValid = (value === this.state.newPassword);
                break;
            default:
                break;
        }

        this.setState({
            newPasswordValid: newPasswordValid,
            confirmNewPasswordValid: confirmNewPasswordValid
        });
    }


    handleSetNewPasswordBtn(){
        let data = new FormData();
        data.append('email', 'gefasim@mail.com');
        data.append('oldPassword', this.state.oldPassword);
        data.append('newPassword', this.state.newPassword);

        let _this = this;
        axios.post('/api/userSetting/updatePassword', data)
            .then(function (response) {
                console.log('password', response);
                _this.updateAlertState(true);
            })
            .catch(function (error) {
                console.log('error', error);
                _this.updateAlertState(false);
            });
    }

    updateAlertState(responseIsSuccses){
        this.setState({
            responseIsSuccses : responseIsSuccses,
            oldPassword: "",
            newPassword: "",
            confirmNewPassword: ""
        });
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
                                           valid={this.checkNewPassword()}
                                    />
                            </FormGroup>

                            <FormGroup>
                                <Label htmlFor='password2' style={{fontWeight:'600'}}>Password</Label>
                                    <Input type="password" name="confirmNewPassword" id="confirmNewPassword"
                                           bsSize="lg"
                                           placeholder="Password"
                                           onChange={this.handleChange}
                                           value={this.state.value}
                                           valid={this.checkConfirmPassword()}
                                    />
                            </FormGroup>

                            <Button size='lg' onClick={this.handleSetNewPasswordBtn} color={this.buttonColor()}>Set the new password</Button>

                            {this.showAlert(this.state.responseIsSuccses)}
                            
                        </Col>
                    </Row>
                </Col>
            </Container>
        )
    }

    static successAlert(){
        return  <Alert color="success" className="alert-form">
                    Password changed successfully ! 
                </Alert>;
    }

    static errorAlert(){
        return  <Alert color="danger" className="alert-form">
                    Error
                </Alert>
    }

    showAlert(response){
        if(response != null){
            return response ? EditProfile.successAlert() : EditProfile.errorAlert()
        } else {
            return null;                
        }
    }

    checkNewPassword(){
        if (this.state.newPassword === '') {
            return null;
        }
        return this.state.newPasswordValid;

    }

    checkConfirmPassword(){
        if (this.state.confirmNewPassword === '') {
            return null;
        }
        return this.state.confirmNewPasswordValid;

    }

    checkPasswordFormValid(){
        return (this.state.oldPassword.length >= 3) && this.state.newPasswordValid && this.state.confirmNewPasswordValid;
    }

    buttonColor(){
        if (this.checkPasswordFormValid())
            return "success";
        else
            return "secondary"
    }

    static fonts(){
        return '-apple-system, BlinkMacSystemFont, "Segoe UI", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol"';
    }

    static visible(isTrue){
        if (isTrue) {
            return {display : 'block'};
        } else {
            return {display : 'none'};
        }
    }
}
