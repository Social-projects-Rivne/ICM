import React, { Component } from 'react';
import {Alert, InputGroup, InputGroupAddon, Col, Button, Form, FormGroup, FormFeedback, Input} from 'reactstrap';
import {Link} from 'react-router-dom';

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            email: "",
            password: "",
            emailValid: false,
            passwordValid: false,
            formValid: false
        };

        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        const name = event.target.name;
        const value = event.target.value;
        this.setState({[name]: value},
            () => { this.validateField(name, value) });
        console.log(name, value);
    }

    validateField(fieldName, value) {
        let emailValid = this.state.emailValid;
        let passwordValid = this.state.passwordValid;

        switch(fieldName) {
            case 'email':
                emailValid = value.match(/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i);
                break;
            case 'password':
                passwordValid = value.length >= 3;
                break;
            default:
                break;
        }
        this.setState({
            emailValid: emailValid,
            passwordValid: passwordValid
        }, this.validateForm);
    }

    validateForm() {
        this.setState({formValid: this.state.emailValid && this.state.passwordValid});
    }

    render() {
        return (
            <div className="container login-page-center col-3">
                <Form className="registration-form" method="POST" action="/login">
                    <h3 className="text-center">Log In</h3>
                    <hr/>
                    <FormGroup>
                        <Col sm={12}>
                           <InputGroup>
                                  <InputGroupAddon>@</InputGroupAddon>
                                  <Input id="email" name="email" placeholder="email" type="email" value={this.state.email} onChange={this.handleChange}/>
                           </InputGroup>
                           <FormFeedback style={this.emailHasError()}>Email is incorrect. </FormFeedback>
                        </Col>
                    </FormGroup>

                    <FormGroup>
                        <Col sm={12}>
                            <InputGroup>
                                <InputGroupAddon><i className="fa fa-key"/></InputGroupAddon>
                                <Input id="password" name="password" placeholder="password" type="password" value={this.state.password} onChange={this.handleChange}/>
                            </InputGroup>
                        </Col>
                    </FormGroup>

                    <FormGroup>
                        <Col sm={12}>
                            <Button color="success" size="lg" block>Sign In</Button>

                            <Alert color="danger" className="alert-form" style={this.incorrectUserEmailPass()}>
                                Incorrect email or password
                            </Alert>


                        </Col>
                    </FormGroup>
                    <FormGroup className="below-form-div">
                        <Col sm={12}>
                            <Link className="below-form-text" to='/restore-password'>Forgot password?</Link>
                        </Col>
                    </FormGroup>
                </Form>

                <p className="below-form-text">Don`t have an account? <Link to="/registration">Sign up</Link></p>
            </div>
        )
    }

    emailHasError(){
        if (!this.state.emailValid && this.state.email !== "")
            return {display : 'inline'};
        else
            return {display : 'none'};
    }

    passHasError(){
        if (!this.state.passwordValid && this.state.password !== "")
            return {display : 'inline'};
        else
            return {display : 'none'};
    }

    incorrectUserEmailPass() {
        if (this.props.history.location.search !== ""){
            console.log(this.props);
            console.log(this.props.history.location.search);
            return {display:'block'}
        } else
            return {display:'none'}
    }
}

export default Login;
