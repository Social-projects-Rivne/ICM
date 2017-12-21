import React from 'react';
import {Alert, Col, Button, Form, FormGroup, FormFeedback, Input, InputGroup, InputGroupAddon} from 'reactstrap';
import {Link} from "react-router-dom";
import axios from 'axios';

export default class SignUp extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            email : "",
            emailValid : false,
            emailIsUsed : false,
            password : "",
            passwordValid : false,
            confirmPass : "",
            confirmPassValid : false,
            firstName : "",
            lastName : "",
            btnColor : "secondary",
            alertColor: "primary",
            registrationIsSuccessful: false,
            registrationIsNotSuccessful: false
        };

        this.sendUserToServer = this.sendUserToServer.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    sendUserToServer(){
        const user = {
            firstName : this.state.firstName,
            lastName : this.state.lastName,
            email : this.state.email,
            password : this.state.password
        };

        const _this = this;
        axios.post('api/registration', user)
            .then(function () {
                _this.setState({registrationIsSuccessful: true});
                _this.setState({registrationIsNotSuccessful: false});
                _this.autoLogIn(user.email, user.password)
            })
            .catch(function () {
                _this.setState({registrationIsSuccessful: false});
                _this.setState({registrationIsNotSuccessful: true});
            });

    }

    autoLogIn(email, password){
        const formData = new FormData();
        formData.append("email", email);
        formData.append("password", password);
        axios.post("/login", formData).then(function () {
            location.href = "/";
        });
    }

    handleChange(event){
        const name = event.target.name;
        const value = event.target.value;

        this.setState({[name]: value},
            () => { this.validateField(name, value) });
    }

    validateField(fieldName, value) {
        let emailValid = this.state.emailValid;
        let passwordValid = this.state.passwordValid;
        let confirmPassValid = this.state.confirmPass;

        switch(fieldName) {
            case 'email':
                emailValid = (/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i).test(value);
                if (emailValid) {
                    this.findExistEmail()
                }
                break;
            case 'password':
                passwordValid = value.length >= 3;
                break;
            case 'confirmPass':
                confirmPassValid = (value === this.state.password);
                break;
            default:
                break;
        }
        this.setState({
            emailValid: emailValid,
            passwordValid: passwordValid,
            confirmPassValid: confirmPassValid
        }, this.validateForm);

        if(emailValid && passwordValid && confirmPassValid && this.checkFirstName() && this.checkLastName()){
            this.setState({btnColor : "success"});
        } else {
            this.setState({btnColor : "secondary"});
        }
    }

    findExistEmail(){
        const params = new FormData();
        params.append("email", this.state.email);

        const _this = this;
        axios.post('/api/checkEmail ', params)
            .then(function (response) {
                _this.setState({emailIsUsed : response.data});
            });
    }

    render(){
        return(
            <div className="container login-page-center col-3">
                <Form className="registration-form">
                    <h3 className="text-center">Sign Up</h3>
                    <hr/>
                    <FormGroup>
                        <Col sm={12}>
                            <InputGroup>
                                <InputGroupAddon><i className="fa fa-user fa-fw"/></InputGroupAddon>
                                <Input type="text" name="firstName" id="firstName"
                                    bsSize="lg"
                                    placeholder="First Name"
                                    onChange={this.handleChange}
                                    valid={this.checkFirstName()}/>
                            </InputGroup>
                        </Col>
                    </FormGroup>

                    <FormGroup>
                        <Col sm={12}>
                            <InputGroup>
                                <InputGroupAddon><i className="fa fa-user fa-fw"/></InputGroupAddon>
                                <Input type="text" name="lastName" id="lastName"
                                       bsSize="lg"
                                       placeholder="Last Name"
                                       onChange={this.handleChange}
                                       valid={this.checkLastName()}/>
                            </InputGroup>
                        </Col>
                    </FormGroup>

                    <FormGroup>
                        <Col sm={12}>
                            <InputGroup>
                                <InputGroupAddon><i className="fa fa-envelope-o fa-fw"/></InputGroupAddon>
                                <Input type="email" name="email" id="exampleEmail"
                                       bsSize="lg"
                                       placeholder="E-Mail"
                                       onChange={this.handleChange}
                                       value={this.state.value}
                                       valid={this.checkEmail()}/>
                            </InputGroup>

                            <FormFeedback style={SignUp.visible(this.state.emailValid ^ this.state.email !== "")}>Email is incorrect. </FormFeedback>
                            <FormFeedback style={SignUp.visible(this.state.emailIsUsed)}>Oh noes! that name is already registered</FormFeedback>
                        </Col>
                    </FormGroup>

                    <FormGroup>
                        <Col sm={12}>
                            <InputGroup>
                                <InputGroupAddon><i className="fa fa-key fa-fw"/></InputGroupAddon>
                                <Input type="password" name="password" id="password"
                                       bsSize="lg"
                                       placeholder="Password"
                                       onChange={this.handleChange}
                                       value={this.state.password}
                                       valid={this.checkPassword()}/>
                            </InputGroup>
                        </Col>
                    </FormGroup>

                    <FormGroup>
                        <Col sm={12}>
                            <InputGroup>
                                <InputGroupAddon><i className="fa fa-key fa-fw"/></InputGroupAddon>
                                    <Input type="password" name="confirmPass" id="confirmPass"
                                           bsSize="lg"
                                           placeholder="Confirm Password"
                                           onChange={this.handleChange}
                                           value={this.state.confirmPass}
                                           valid={this.checkConfirmPassword()}/>
                            </InputGroup>
                        </Col>
                    </FormGroup>
                    <FormGroup>
                        <Col sm={12}>
                            <Button onClick={this.sendUserToServer} color={this.state.btnColor} size="lg" block>Sign Up</Button>

                            <Alert color="success" className="alert-form" style={SignUp.visible(this.state.registrationIsSuccessful)}>
                                Successful registration !
                            </Alert>

                            <Alert color="danger" className="alert-form" style={SignUp.visible(this.state.registrationIsNotSuccessful)}>
                                Registration error
                            </Alert>
                        </Col>
                    </FormGroup>
                </Form>
                <p className="below-form-text">Already have an account? <Link to="/login">Log in</Link></p>
            </div>
        )
    }

    checkFirstName(){
        if (this.state.firstName === "") {
            return null;
        }
        return this.state.firstName.trim().length > 1;

    }

    checkLastName(){
        if (this.state.lastName === "") {
            return null;
        }
        return this.state.lastName.trim().length > 1;

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
