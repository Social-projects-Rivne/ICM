import React, { Component } from 'react';
import {Button} from "reactstrap";

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
            <div className="container login-page-center" >
                <form className="form-horizontal" role="form" method="POST" action="/login">
                    <div className="row">
                        <div className="col-md-3"/>
                        <div className="col-md-6">
                            <h2>Please Login</h2>
                            <hr/>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-3"/>
                        <div className="col-md-6">
                            <div className="form-group">
                                <label className="sr-only" htmlFor="email">E-Mail Address</label>
                                <div className="input-group mb-2 mr-sm-2 mb-sm-0">
                                    <div className="input-group-addon"><i className="fa fa-at"/></div>
                                    <input type="text" name="email" className="form-control" id="email"
                                           value={this.state.value} onChange={this.handleChange}
                                           placeholder="you@example.com" required autoFocus/>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-3">
                            <div className="form-control-feedback" style={this.emailHasError()}>
                                <span className="text-danger align-middle">
                                    <i className="fa fa-close"/> Email is incorrect
                                </span>
                            </div>
                        </div>

                    </div>
                    <div className="row">
                        <div className="col-md-3"/>
                        <div className="col-md-6">
                            <div className="form-group has-error">
                                <label className="sr-only" htmlFor="password">Password</label>
                                <div className="input-group mb-2 mr-sm-2 mb-sm-0">
                                    <div className="input-group-addon"><i className="fa fa-key"/></div>
                                    <input type="password" name="password" className="form-control" id="password"
                                           value={this.state.value} onChange={this.handleChange}
                                           placeholder="Password" required/>
                                </div>
                                <div className="form-control-feedback" style={this.incorrectUserEmailPass()}>
                                    <span className="text-danger align-middle">
                                        <i className="fa fa-close"/> Incorrect email or password
                                    </span>
                                </div>
                            </div>
                        </div>

                        <div className="col-md-3">
                            <div className="form-control-feedback" style={this.passHasError()}>
                                <span className="text-danger align-middle">
                                    <i className="fa fa-close"/> Password is too short
                                </span>
                            </div>
                        </div>
                    </div>

                    <div className="row">
                        <div className="col-md-3"/>
                        <div className="col-md-2">
                            <button type="submit" className="btn btn-success"><i className="fa fa-sign-in"/> Login </button>
                        </div>

                        <Button href="/registration" color="primary"> Sign Up </Button>
                    </div>
                </form>

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
            return {display:'inline'}
        } else
            return {display:'none'}
    }
}

export default Login;
