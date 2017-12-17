import React, {Component} from 'react';
import {Alert, Col, Button, Form, FormGroup, FormFeedback, Input} from 'reactstrap';
import axios from 'axios';
import {Link} from "react-router-dom";

export default class ConfirmEmail extends Component {
    constructor(props){
        super(props);
        this.state = {
            email : "",
            emailValid : null,
            btnColor: "primary",
            isEmailSent: false,
            password: "",
            confirmPass: "",
            token: ""
        };

        this.handleChange = this.handleChange.bind(this);
        this.createOrderRestorePassword = this.createOrderRestorePassword.bind(this);
        this.sendNewPassword = this.sendNewPassword.bind(this);
    }

    handleChange(event) {
        const name = event.target.name;
        const value = event.target.value;

        this.setState({[name]: value},
            () => { this.validateEmail(value)});
    }

    validateEmail(value){
        let btnColor = this.state.btnColor;
        let emailValid = (/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i).test(value);
        if (emailValid)
            btnColor = "success";
        else
            btnColor = "secondary";
        this.setState({emailValid: emailValid, btnColor: btnColor});
    }

    createOrderRestorePassword(){
        let data = new FormData();
        data.append("email", this.state.email);

        let _this = this;
        axios.post("/api/createOrderRestorePassword", data)
            .then(function(response){_this.showHiddenForms()})
            .catch(function(error){})
    }

    sendNewPassword(){
        let data = {
            email: this.state.email,
            password: this.state.password
        };

        axios.post("/api/createNewPassword", data)
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error)
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

                            <Alert color="info" className="alert-form" style={ConfirmEmail.visible(this.state.isEmailSent)}>
                                Please insert the token from your email and new password
                            </Alert>
                        </Col>
                    </FormGroup>

                    <div style={ConfirmEmail.visible(this.state.isEmailSent)}>
                        <FormGroup>
                            <Col sm={12}>
                                <Input type="text" name="token" id="token"
                                       bsSize="lg"
                                       className="border-radius"
                                       placeholder="Token"
                                       onChange={this.handleChange}
                                       value={this.state.token}/>
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
                                <Button onClick={this.sendNewPassword} size="lg" block>Set new password</Button>
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

    static visible(isTrue){
        if (isTrue) {
            return {display : 'block'};
        } else {
            return {display : 'none'};
        }
    }
}
