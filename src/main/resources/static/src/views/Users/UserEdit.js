import React, { Component } from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import moment from 'moment';
import {Button, Card, CardBody, CardFooter, CardHeader, Col, Form, FormGroup, Input, Label, Row} from "reactstrap";
import DateTime from 'react-datetime';
import 'react-datetime/css/react-datetime.css';
import {Link} from "react-router-dom";

class EditUsers extends Component {
    constructor(props) {
        super(props);

        this.state = {
            users: {
                id: "",
                userRole: "",
                registrationDate: "",
                firstName: "",
                lastName: "",
                // phone: "",
                userStatus: "",
                username:"",
            },
            registrationDate: true,
        };

        this.handleDateChange = this.handleDateChange.bind(this);
        this.handleSave = this.handleSave.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    componentWillMount() {
        var _this = this;
        axios.get("/api/users/"+this.props.match.params.id)
            .then(function(response) {
                _this.setState({
                    users: response.data
                })
            })
            .catch(function (error) {
                swal({title: "Something went wrong with data!", text: error, icon: "error"});
            });

    }

    handleDateChange(e) {
        const name = e.target.name;
        const value = e.target.value;

        this.setState(function (prev) {
            return {
                users: {
                    ...prev.users,
                    [name]: value
                },
                [name]: moment(value, "DD/MM/YYYY HH:mm", true).isValid()
            }
        })
    }

    handleChange(e) {
        const name = e.target.name;
        const value = e.target.value;
        this.setState(function(prev) {
            return {
                users: {
                    ...prev.users,
                    [name]: value
                }
            }
        })
    }

    handleSave(){
        var _this = this;
        axios.put("/api/users/" + this.props.match.params.id, this.state.users)
            .then(function(response) {
                swal({title: "Users record saved", icon: "success"});
                if(document.cookie === response.data.email)
                    _this.props.history.push("/registration");
            })
            .catch(function (error) {
            swal({title: "Something went wrong!!!!", text: error, icon: "error"});
        });
    }

    render() {
        return (
            <div className="animated fadeIn">
                <Row>
                    <Col xs="24" md="12">
                        <Card>
                            <Form className="form-horizontal">
                                <CardHeader>
                                    <strong>User {this.state.users.id} edit form</strong>
                                </CardHeader>
                                <CardBody>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>First Name</Label>
                                        </Col>
                                        <Col xs="12" md="10">
                                            <Input value={this.state.users.firstName} onChange={this.handleChange}
                                                   type="text" name="firstName"
                                                   placeholder="Name"/>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>Second Name</Label>
                                        </Col>
                                        <Col xs="12" md="10">
                                            <Input value={this.state.users.lastName} onChange={this.handleChange}
                                                   type="text" name="lastName"
                                                   placeholder="lastName"/>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>Registration Date</Label>
                                        </Col>
                                        <Col xs="12" md="4">
                                            <DateTime value={this.state.users.registrationDate} dateFormat="DD/MM/YYYY"
                                             timeFormat="HH:mm" onChange={this.handleInitialDateChange}
                                             inputProps={{readOnly: true, className: "form-control form-control-readonly"}} />
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                    <Col md="2">
                                        <Label>Role</Label>
                                    </Col>
                                    <Col xs="12" md="4">
                                        <Input value={this.state.users.userRole} onChange={this.handleChange}
                                               type="select" name="userRole"
                                               placeholder="Role">
                                            <option>ADMIN</option>
                                            <option>USER</option>
                                            <option>MODERATOR</option>
                                        </Input>
                                    </Col>
                                </FormGroup>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>Status</Label>
                                        </Col>
                                        <Col xs="12" md="4">
                                            <Input value={this.state.users.userStatus} onChange={this.handleChange}
                                                   type="select" name="userStatus"
                                                   placeholder="Role">
                                                <option>BANNED</option>
                                                <option>ACTIVE</option>
                                                <option>UNCONFIRMED</option>
                                            </Input>
                                        </Col>
                                    </FormGroup>

                                </CardBody>
                                <CardFooter className="text-right">

                                    <Button color="success" onClick={this.handleSave}>
                                        <i className="fa fa-dot-circle-o"/> Save</Button>
                                    <Link to="/admin/users"><Button color="primary">
                                        <i className="fa fa-ban"/> Back</Button>
                                    </Link>
                                </CardFooter>
                            </Form>
                        </Card>
                    </Col>
                </Row>
            </div>
        )
    }
}

export default EditUsers