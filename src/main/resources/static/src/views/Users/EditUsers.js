import React, { Component } from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import moment from 'moment';
import {
    Button, Card, CardBody, CardFooter, CardHeader, Col, Form, FormGroup, Input, InputGroup, InputGroupAddon,
    Label, Row
} from "reactstrap";
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
                password: "",
                email: "",
                phone: "",
                userStatus: "",
                avatarUrl: "",
            },
            registrationDate: true,
        };

        this.handleDateChange = this.handleDateChange.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }


    handleDateChange(e) {
        const name = e.target.name;
        const value = e.target.value;

        this.setState(function (prev) {
            return {
                event: {
                    ...prev.event,
                    [name]: value
                },
                [name]: moment(value, "DD/MM/YYYY HH:mm", true).isValid()
            }
        })
    }


    handleChange(e) {
        const name = e.target.name;
        const value = e.target.value;
        this.setState(function (prev) {
            return {
                event: {
                    ...prev.event,
                    [name]: value
                }
            }
        })


    }

    handleSave(){
        axios.put("/api/users/" + this.props.match.params.id, this.state.users)
            .then(function (response) {
                swal({title: "Event record saved", icon: "success"})
            }).catch(function (error) {
            swal({title: "Something went wrong!", text: error, icon: "error"});
        });
    }

    componentWillMount() {
        var _this = this;
        axios.get("/api/users" + this.props.match.params.id)
            .then(function (response) {
                _this.setState({
                    users: response.data
                })
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            })


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
                                            <Label>User role</Label>
                                        </Col>
                                        <Col xs="12" md="10">
                                            <Input value={this.state.users.userRole} onChange={this.handleChange}
                                                   type="text" name="title"
                                                   placeholder="Title">
                                                <option>ADMIN</option>
                                                <option>MODERATOR</option>
                                                <option>USER</option>
                                                <option>GUEST</option>
                                            </Input>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>Registration date</Label>
                                        </Col>
                                        <Col xs="12" md="4">
                                            <InputGroup>
                                                <Input value={this.state.users.registrationDate} type="text"
                                                       name="initialDate" placeholder="DD/MM/YYYY hh:mm"
                                                       onChange={this.handleDateChange}/>
                                                <InputGroupAddon className={this.state.users.registrationDate ?
                                                    "fa fa-calendar-check-o" : "fa fa-calendar-times-o"}/>
                                            </InputGroup>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>First Name</Label>
                                        </Col>
                                        <Col xs="12" md="10">
                                            <Input value={this.state.users.firstName} onChange={this.handleChange}
                                                   type="textarea" name="description" rows="9"
                                                   placeholder="Description"/>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>Last Name</Label>
                                        </Col>
                                        <Col xs="12" md="4">
                                            <Input value={this.state.users.lastName} onChange={this.handleChange}
                                                   type="select" name="category"
                                                   placeholder="Category">
                                            </Input>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>Password</Label>
                                        </Col>
                                        <Col xs="12" md="4">
                                            <Input value={this.state.users.password} onChange={this.handleChange}
                                                   type="select" name="category"
                                                   placeholder="Category">
                                            </Input>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>Email</Label>
                                        </Col>
                                        <Col xs="12" md="4">
                                            <Input value={this.state.users.email} onChange={this.handleChange}
                                                   type="select" name="category"
                                                   placeholder="Category">
                                            </Input>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>Phone number</Label>
                                        </Col>
                                        <Col xs="12" md="4">
                                            <Input value={this.state.users.phone} onChange={this.handleChange}
                                                   type="select" name="category"
                                                   placeholder="Category">
                                            </Input>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>User status</Label>
                                        </Col>
                                        <Col xs="12" md="4">
                                            <Input value={this.state.users.userStatus} onChange={this.handleChange}
                                                   type="select" name="category"
                                                   placeholder="Category">
                                                <option>DELETED</option>
                                                <option>BANNED</option>
                                                <option>ACTIVE</option>
                                                <option>UNCONFIRMED</option>
                                            </Input>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>Add photo</Label>
                                        </Col>
                                        <Col xs="12" md="4">
                                            <Input value={this.state.users.avatarUrl} onChange={this.handleChange}
                                                   type="select" name="category"
                                                   placeholder="Category">
                                            </Input>
                                        </Col>
                                    </FormGroup>

                                </CardBody>
                                <CardFooter className="text-right">

                                    <Button color="success" onClick={this.handleSave}>
                                        <i className="fa fa-dot-circle-o"/> Save</Button>
                                    <Link to="/issues"><Button color="primary">
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


