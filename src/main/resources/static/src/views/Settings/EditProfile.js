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
            email: "",
            firstName: "",
            lastName: "",
            phone: "",
            oldPassword: "",
            newPassword: "",
            newPasswordValid: false,
            confirmNewPassword: "",
            confirmNewPasswordValid: false,
            responseIsSuccess : null,
            contactInfoResponseIsSuccess: null
         };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSetNewPasswordBtn = this.handleSetNewPasswordBtn.bind(this);
        this.updateContactsData = this.updateContactsData.bind(this);
    }

    componentWillMount(){
        let _this = this;
        axios.get('/api/userDetails').then(function (response) {
            _this.setState({
                email: response.data.email,
                firstName: response.data.firstName,
                lastName: response.data.lastName,
                phone: response.data.phone != null ? response.data.phone : "" 
            });
        });
    }

    handleInputChange(event){
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

    // TODO: rename to updatePasswordHandlerBtn
    handleSetNewPasswordBtn(){
        let data = new FormData();
        data.append('email', this.state.email);
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
    // TODO: rename to updateContanctHandlerBtn
    updateContactsData(){
        let data = new FormData();
        data.append('email', this.state.email);
        data.append('firstName', this.state.firstName);
        data.append('lastName', this.state.lastName);
        data.append('phone', this.state.phone);

        let _this = this;
        axios.post('/api/userSetting/updateContacts', data)
            .then(function(response){
                console.log('contact', response);
                _this.setState({contactInfoResponseIsSuccess: true});
            })
            .catch(function(error){
                _this.setState({contactInfoResponseIsSuccess: false});
            });
    }

    updateAlertState(responseIsSuccess){
        this.setState({
            responseIsSuccess : responseIsSuccess,
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
                                <Input type="text" name="firstName" id="firstName"
                                       bsSize="lg"
                                       placeholder="first name"
                                       onChange={this.handleInputChange}
                                       value={this.state.firstName}
                                />
                            </FormGroup>

                            <FormGroup>
                                <Label htmlFor='last-name' style={{fontWeight:'600'}}>Last name</Label>
                                <Input type="text" name="lastName" id="lastName"
                                       bsSize="lg"
                                       placeholder="last name"
                                       onChange={this.handleInputChange}
                                       value={this.state.lastName}
                                />

                            </FormGroup>

                            <FormGroup>
                                <Label htmlFor='phone' style={{fontWeight:'600'}}>Phone</Label>
                                <Input type="text" name="phone" id="phone"
                                       bsSize="lg"
                                       placeholder="+380123456789"
                                       onChange={this.handleInputChange}
                                       value={this.state.phone}
                                />
                            </FormGroup>

                            <Button size='lg' onClick={this.updateContactsData} color={this.buttonColorContacts()}>Update contacts form</Button>
                            {this.showContactAlert(this.state.contactInfoResponseIsSuccess)}

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
                                       onChange={this.handleInputChange}
                                       value={this.state.oldPassword}
                                />
                            </FormGroup>

                            <FormGroup>
                                <Label htmlFor='password' style={{fontWeight:'600'}}>Password</Label>
                                <Input type="password" name="newPassword" id="newPassword"
                                       bsSize="lg"
                                       placeholder="Password"
                                       onChange={this.handleInputChange}
                                       value={this.state.newPassword}
                                       valid={this.checkNewPassword()}
                                />
                            </FormGroup>

                            <FormGroup>
                                <Label htmlFor='password2' style={{fontWeight:'600'}}>Password</Label>
                                <Input type="password" name="confirmNewPassword" id="confirmNewPassword"
                                       bsSize="lg"
                                       placeholder="Password"
                                       onChange={this.handleInputChange}
                                       value={this.state.confirmNewPassword}
                                       valid={this.checkConfirmPassword()}
                                />
                            </FormGroup>

                            <Button size='lg' onClick={this.handleSetNewPasswordBtn} color={this.buttonColorPass()}>Set the new password</Button>

                            {this.showPassAlert(this.state.responseIsSuccess)}

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

    static successContactAlert(){
        return  <Alert color="success" className="alert-form">
            Contacts information changed successfully !
        </Alert>;
    }

    static errorAlert(){
        return  <Alert color="danger" className="alert-form">
            Error
        </Alert>
    }

    showPassAlert(response){
        if(response != null){
            return response ? EditProfile.successAlert() : EditProfile.errorAlert()
        } else {
            return null;
        }
    }

    showContactAlert(response){
        if(response != null){
            return response ? EditProfile.successContactAlert() : EditProfile.errorAlert()
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
        return (this.state.firstName.length >= 2) && (this.state.lastName.length >= 2) && (this.state.phone.length >= 5);
    }

    checkContactsFormValid(){
        return (this.state.oldPassword.length >= 3) && this.state.newPasswordValid && this.state.confirmNewPasswordValid;
    }

    buttonColorPass(){
        if (this.checkPasswordFormValid())
            return "success";
        else
            return "secondary";
    }

    buttonColorContacts(){
        if (this.checkPasswordFormValid())
            return "success";
        else
            return "secondary";
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
