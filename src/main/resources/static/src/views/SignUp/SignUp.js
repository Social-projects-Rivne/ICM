import React from 'react';
import { Col, Button, Form, FormGroup, FormFeedback, Label, Input, FormText } from 'reactstrap';
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
            btnColor : "secondary"
        };

        this.sendUserToServer = this.sendUserToServer.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    sendUserToServer(event){
        const user = {
            firstName : this.state.firstName,
            lastName : this.state.lastName,
            email : this.state.email,
            password : this.state.password
        };

        axios.post('/api/registration', user)
            .then(function (response) {
                console.log(response.data, response);
            })
            .catch(function (error) {
                console.log(error.data, error);
            });
    }

    handleChange(event){
        const name = event.target.name;
        const value = event.target.value;

        console.log(name);
        this.setState({[name]: value},
            () => { this.validateField(name, value) });
    }

    validateField(fieldName, value) {
        let emailValid = this.state.emailValid;
        let passwordValid = this.state.passwordValid;
        let confirmPassValid = this.state.confirmPass;

        switch(fieldName) {
            case 'email':
                emailValid = (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/).test(value);
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
                console.log(response.data);
            });
    }

    render(){
        return(
            <div className="container login-page-center col-3">
                <Form  className="registration-form">
                    <h3 className="text-center">Sign Up</h3>
                    <hr/>
                    <FormGroup>
                        <Col sm={12}>
                            <Input type="text" name="firstName" id="firstName"
                                   size="lg"
                                   className="border-radius"
                                   placeholder="First Name"
                                   onChange={this.handleChange}
                                   valid={this.checkFirstName()}/>
                        </Col>
                    </FormGroup>

                    <FormGroup>
                        <Col sm={12}>
                            <Input type="text" name="lastName" id="lastName"
                                   size="lg"
                                   className="border-radius"
                                   placeholder="Last Name"
                                   onChange={this.handleChange}
                                   valid={this.checkLastName()}/>
                        </Col>
                    </FormGroup>

                    <FormGroup>
                        <Col sm={12}>
                            <Input type="email" name="email" id="exampleEmail"
                                   size="lg"
                                   className="border-radius"
                                   placeholder="E-Mail"
                                   onChange={this.handleChange}
                                   value={this.state.value}
                                   valid={this.checkEmail()}/>

                            <FormFeedback style={SignUp.visible(this.state.emailValid ^ this.state.email !== "")}>Email is incorrect. </FormFeedback>
                            <FormFeedback style={SignUp.visible(this.state.emailIsUsed)}>Oh noes! that name is already registered</FormFeedback>
                        </Col>
                    </FormGroup>

                    <FormGroup>
                        <Col sm={12}>
                            <Input type="password" name="password" id="password"
                                   size="lg"
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
                                   size="lg"
                                   className="border-radius"
                                   placeholder="Confirm Password"
                                   onChange={this.handleChange}
                                   value={this.state.confirmPass}
                                   valid={this.checkConfirmPassword()}/>
                        </Col>
                    </FormGroup>
                    <FormGroup>
                        <Col sm={12}>
                            <Button color={this.state.btnColor} size="lg" block>Sign Up</Button>
                        </Col>
                    </FormGroup>
                </Form>
                <p className="below-form">Already have an account? <a href="/login">Log in</a></p>

            </div>
        )
    }

    checkFirstName(){
        if (this.state.firstName === "") {
            return null;
        }
        if (this.state.firstName.trim().length > 1) {
            return true;
        }
        return false;
    }

    checkLastName(){
        if (this.state.lastName === "") {
            return null;
        }
        if (this.state.lastName.trim().length > 1) {
            return true;
        }
        return false;
    }


    checkEmail(){
        if (this.state.email === '') {
            return null;
        }
        if (this.state.emailValid && !this.state.emailIsUsed) {
            return true;
        }

        return false;
    }

    checkPassword(){
        if (this.state.password === '') {
            return null;
        }
        if (this.state.passwordValid) {
            return true;
        }
        return false;
    }

    checkConfirmPassword(){
        if (this.state.confirmPass === '') {
            return null;
        }
        if (this.state.confirmPassValid) {
            return true;
        }
        return false;
    }


    static visible(isTrue){
        if (isTrue) {
            return {display : 'inline'};
        } else {
            return {display : 'none'};
        }
    }
}
