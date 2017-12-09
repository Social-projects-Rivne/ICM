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
            isVisible : false
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
        }

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
                confirmPassValid = (value === this.state.password)
                break;
            default:
                break;
        }
        this.setState({
            emailValid: emailValid,
            passwordValid: passwordValid,
            confirmPassValid: confirmPassValid
        }, this.validateForm);
    }

    findExistEmail(){
        const params = new FormData();
        params.append("email", this.state.email);

        const _this = this;
        axios.post('/api/checkEmail', params)
            .then(function (response) {
                _this.setState({emailIsUsed : response.data});
                console.log(response.data);
            });
    }

    render(){
        return(
            <div className="container login-page-center col-6">
                <Form>
                    <h2>ICM Registration form</h2>
                    <hr/>
                    <FormGroup row>
                        <Label for="firstName" sm={2}>First Name</Label>
                        <Col sm={10}>
                            <Input type="text" name="firstName" id="firstName"
                                   onChange={this.handleChange}
                                   valid={this.checkFirstName()}
                                   style={this.borderRadius()}/>
                        </Col>
                    </FormGroup>

                    <FormGroup row>
                        <Label for="lastName" sm={2}>Last Name</Label>
                        <Col sm={10}>
                            <Input type="text" name="lastName" id="lastName"
                                   onChange={this.handleChange}
                                   valid={this.checkLastName()}
                                   style={this.borderRadius()}/>
                        </Col>
                    </FormGroup>

                    <FormGroup row>
                        <Label for="exampleEmail" sm={2}>Email</Label>
                        <Col sm={10}>
                            <Input type="email" name="email" id="exampleEmail" style={this.borderRadius()}
                                   placeholder="tom@example.rv.ua" value={this.state.value} onChange={this.handleChange}
                                   valid={this.checkEmail()}/>
                            <FormFeedback style={this.visible(this.state.emailValid ^ this.state.email !== "")}>Email is incorrect. </FormFeedback>
                            <FormFeedback style={this.visible(this.state.emailIsUsed)}>Oh noes! that name is already registered</FormFeedback>
                        </Col>
                    </FormGroup>

                    <FormGroup row>
                        <Label for="password" sm={2}>Password</Label>
                        <Col sm={10}>
                            <Input type="password" name="password" id="password"
                                   value={this.state.password}
                                   onChange={this.handleChange}
                                   valid={this.checkPassword()}
                                   style={this.borderRadius()}/>
                        </Col>
                    </FormGroup>

                    <FormGroup row>
                        <Label for="confirmPass" sm={2}>Repeat password</Label>
                        <Col sm={10}>
                            <Input type="password" name="confirmPass" id="confirmPass"
                                   value={this.state.confirmPass}
                                   onChange={this.handleChange}
                                   valid={this.checkConfirmPassword()}

                                   style={this.borderRadius()}/>
                        </Col>
                    </FormGroup>
                    <Button onClick={this.sendUserToServer}>Change</Button>
                </Form>
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


    visible(isTrue){
        if (isTrue) {
            return {display : 'inline'};
        } else {
            return {display : 'none'};
        }
    }


    borderRadius(){
        return {borderRadius: '5px'};
    }
}
