import React, {Component} from 'react';
import {Alert, Col, Button, Form, FormGroup, FormFeedback, Input} from 'reactstrap';
import axios from 'axios';
import {Link} from "react-router-dom";

export default class RestorePassword extends Component {
    constructor(props){
        super(props);
        this.state = {
            btnColor: "primary",
            email : "",
            emailValid : null,
            isEmailSent: false,
            password : "",
            passwordValid : false,
            confirmPass : "",
            confirmPassValid : false,
            token: "",
            tokenValid: false,
            formValid: false,
            responseError: false,
            responseTrue: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.createOrderRestorePassword = this.createOrderRestorePassword.bind(this);
        this.sendNewPassword = this.sendNewPassword.bind(this);
    }

    handleChange(event) {
        const name = event.target.name;
        const value = event.target.value;

        this.setState({[name]: value},
            () => { this.validateField(name, value) });
    }

    validateField(fieldName, value) {
        let btnColor = this.state.btnColor;
        let emailValid = this.state.emailValid;
        let passwordValid = this.state.passwordValid;
        let confirmPassValid = this.state.confirmPass;
        let tokenValid = this.state.tokenValid;

        switch(fieldName) {
            case 'email':
                emailValid = (/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i).test(value);
                if (emailValid) {
                    btnColor = "success";
                }
                break;
            case 'password':
                passwordValid = value.length >= 3;
                break;
            case 'confirmPass':
                confirmPassValid = (value === this.state.password);
                break;
            case 'token':
                tokenValid = (value.length === 36);
                break;
            default:
                break;
        }
        this.setState({
            btnColor: btnColor,
            emailValid: emailValid,
            passwordValid: passwordValid,
            confirmPassValid: confirmPassValid,
            tokenValid: tokenValid
        }, this.validateForm);
    }

    validateForm(){
        if (this.state.tokenValid && this.state.passwordValid && this.state.confirmPassValid)
            this.setState({formValid: true});
        else
            this.setState({formValid: false});

    }

    createOrderRestorePassword(){
        let data = new FormData();
        data.append("email", this.state.email);

        let _this = this;
        axios.post("/api/createResetToken", data)
            .then(function(response){_this.showHiddenForms()})
            .catch(function(error){_this.showHiddenForms()})
    }

    sendNewPassword(){
        let data = new FormData();
        data.append("email", this.state.email);
        data.append("password", this.state.password);
        data.append("token", this.state.token);

        let _this = this;
        axios.post("/api/createNewPassword", data)
            .then(function (response) {
                _this.setState({responseTrue: true});
                _this.setState({responseError: false});
            })
            .catch(function (error) {
                _this.setState({responseError: true});
                _this.setState({responseTrue: false});
            })
    }

    render(){
        return (
            <div className="container login-page-center col-3">
                <Form className="registration-form">
                    <h3 className="text-center">Restore account</h3>
                    <hr/>
                    <FormGroup>
                        <Col sm={12}>
                            <Input type="email" name="email" id="email"
                                   bsSize="lg"
                                   className="border-radius"
                                   placeholder="E-Mail"
                                   onChange={this.handleChange}
                                   value={this.state.email}
                                   valid={this.checkEmail()}/>
                        </Col>
                    </FormGroup>

                    <FormGroup>
                        <Col sm={12}>
                            <Button id="emailSend" onClick={this.createOrderRestorePassword} color={this.state.btnColor} size="lg" block>Send Email</Button>

                            <Alert color="info" className="alert-form" style={RestorePassword.visible(this.state.isEmailSent)}>
                                We sent you a letter, please copy the token from it.
                            </Alert>
                        </Col>
                    </FormGroup>

                    <div style={RestorePassword.visible(this.state.isEmailSent)}>
                        <FormGroup>
                            <Col sm={12}>
                                <Input type="text" name="token" id="token"
                                       bsSize="lg"
                                       className="border-radius"
                                       placeholder="Token"
                                       onChange={this.handleChange}
                                       value={this.state.token}
                                       valid={this.checkToken()}/>
                            </Col>
                        </FormGroup>

                        <FormGroup>
                            <Col sm={12}>
                                <Input type="password" name="password" id="password"
                                       bsSize="lg"
                                       className="border-radius"
                                       placeholder="Password"
                                       onChange={this.handleChange}
                                       value={this.state.password}
                                       valid={this.checkPassword()}/>
                            </Col>
                        </FormGroup>

                        <FormGroup>
                            <Col sm={12}>
                                <Input type="password" name="confirmPass" id="confirmPass"
                                       bsSize="lg"
                                       className="border-radius"
                                       placeholder="Confirm Password"
                                       onChange={this.handleChange}
                                       value={this.state.confirmPass}
                                       valid={this.checkConfirmPassword()}/>
                            </Col>
                        </FormGroup>

                        <FormGroup>
                            <Col sm={12}>
                                <Button onClick={this.sendNewPassword} color={this.setButtonColor()} size="lg" block>Set new password</Button>

                                <Alert color="success" className="alert-form" style={RestorePassword.visible(this.state.responseTrue)}>
                                    Successful restore password !
                                </Alert>

                                <Alert color="danger" className="alert-form" style={RestorePassword.visible(this.state.responseError)}>
                                    Restore password fail
                                </Alert>
                            </Col>
                        </FormGroup>
                    </div>

                    <FormGroup className="below-form-div">
                        <Col sm={12}>
                            <Link className="below-form-text" to="/login">Log in</Link>
                        </Col>
                    </FormGroup>
                </Form>

            </div>
        )
    }

    showHiddenForms(){
        this.setState({isEmailSent: true});
        document.getElementById("email").disabled = true;
        document.getElementById("emailSend").disabled = true;
    }

    checkEmail(){
        if (this.state.email === '') {
            return null;
        }
        return this.state.emailValid && !this.state.emailIsUsed;
    }

    checkToken(){
        if (this.state.token === '') {
            return null;
        }
        return this.state.tokenValid;
    }

    checkPassword(){
        if (this.state.password === '') {
            return null;
        }
        return this.state.passwordValid;

    }

    checkConfirmPassword(){
        if (this.state.confirmPass === '') {
            return null;
        }
        return this.state.confirmPassValid;

    }

    setButtonColor() {
        if (this.state.formValid)
            return "success";
        else
            return "secondary";
    }

    static visible(isTrue){
        if (isTrue) {
            return {display : 'block'};
        } else {
            return {display : 'none'};
        }
    }
}
