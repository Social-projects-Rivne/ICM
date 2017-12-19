import React, { Component } from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import moment from 'moment';
import {
    Button, Card, CardBody, CardFooter, CardHeader, Col, Form, FormGroup, Input, InputGroup, InputGroupAddon,
    Label, Row
} from "reactstrap";
import {Link} from "react-router-dom";

class EventEdit extends Component {
    constructor(props) {
        super(props);

        this.state = {
            event: {
                id: "",
                title: "",
                description: "",
                initialDate: "",
                endDate: "",
                category: ""
            },
            initialDate: true,
            endDate: true
        };

        this.handleDateChange = this.handleDateChange.bind(this);
        this.handleSave = this.handleSave.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    componentWillMount() {
        var _this = this;
        axios.get("/api/events/"+this.props.match.params.id)
            .then(function(response) {
                _this.setState({
                    event: response.data
                })
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });
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
        this.setState(function(prev) {
            return {
                event: {
                    ...prev.event,
                    [name]: value
                }
            }
        })
    }

    handleSave(){
        axios.put("/api/events/" + this.props.match.params.id, this.state.event)
            .then(function (response) {
                swal({title: "Event record saved", icon: "success"})
            }).catch(function (error) {
            swal({title: "Something went wrong!", text: error, icon: "error"});
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
                                    <strong>Event #{this.state.event.id} edit form</strong>
                                </CardHeader>
                                <CardBody>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>Title</Label>
                                        </Col>
                                        <Col xs="12" md="10">
                                            <Input value={this.state.event.title} onChange={this.handleChange}
                                                   type="text" name="title"
                                                   placeholder="Title"/>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>Description</Label>
                                        </Col>
                                        <Col xs="12" md="10">
                                            <Input value={this.state.event.description} onChange={this.handleChange}
                                                   type="textarea" name="description" rows="9"
                                                   placeholder="Description"/>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>Initial Date</Label>
                                        </Col>
                                        <Col xs="12" md="4">
                                            <InputGroup>
                                                <Input value={this.state.event.initialDate} type="text"
                                                       name="initialDate" placeholder="DD/MM/YYYY hh:mm"
                                                       onChange={this.handleDateChange}/>
                                                <InputGroupAddon className={this.state.initialDate ?
                                                    "fa fa-calendar-check-o" : "fa fa-calendar-times-o"}/>
                                            </InputGroup>
                                        </Col>
                                        <Col md="2">
                                            <Label>End Date</Label>
                                        </Col>
                                        <Col xs="12" md="4">
                                            <InputGroup>
                                                <Input value={this.state.event.endDate} type="text"
                                                       name="endDate" placeholder="DD/MM/YYYY hh:mm"
                                                       onChange={this.handleDateChange}/>
                                                <InputGroupAddon className={this.state.endDate ?
                                                    "fa fa-calendar-check-o" : "fa fa-calendar-times-o"}/>
                                            </InputGroup>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>Category</Label>
                                        </Col>
                                        <Col xs="12" md="4">
                                            <Input value={this.state.event.category} onChange={this.handleChange}
                                                   type="select" name="category"
                                                   placeholder="Category">
                                                <option>CAT1</option>
                                                <option>CAT2</option>
                                                <option>CAT3</option>
                                            </Input>
                                        </Col>
                                    </FormGroup>

                                </CardBody>
                                <CardFooter className="text-right">
                                    {(this.state.endDate && this.state.initialDate) ?
                                        <Button color="success" onClick={this.handleSave}>
                                            <i className="fa fa-dot-circle-o"/> Save</Button> :
                                        <Button disabled color="success" onClick={this.handleSave}>
                                            <i className="fa fa-dot-circle-o"/> Save</Button>}
                                    <Link to="/admin/events"><Button color="primary">
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

export default EventEdit