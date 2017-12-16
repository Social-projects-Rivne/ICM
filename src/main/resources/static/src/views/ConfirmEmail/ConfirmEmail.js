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
            btnColor: "secondary",
            onSubmitClicked: false
        };

        this.handleChange = this.handleChange.bind(this);
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

    restoreAccount(){
        this.setState({onSubmitClicked: true});
        alert("sss");
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
                                   value={this.state.email}/>
                        </Col>
                    </FormGroup>

                    <FormGroup>
                        <Col sm={12}>
                            <Button onClick={this.restoreAccount} color={this.state.btnColor} size="lg" block>Send email</Button>

                            <Alert color="success" className="alert-form" style={ConfirmEmail.visible(this.state.onSubmitClicked)}>
                                Successful registration !
                            </Alert>


                        </Col>
                    </FormGroup>

                    <FormGroup className="below-form-div">
                        <Col sm={12}>
                            <Link className="below-form-text" to="/login">Log in</Link>
                        </Col>
                    </FormGroup>
                </Form>

            </div>
        )
    }

    static visible(isTrue){
        if (isTrue) {
            return {display : 'block'};
        } else {
            return {display : 'none'};
        }
    }
}
