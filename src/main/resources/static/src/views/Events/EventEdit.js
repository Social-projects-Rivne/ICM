import React, { Component } from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import {
    Button, Card, CardBody, CardFooter, CardHeader, Col, Form, FormGroup, Input, Label,
    Row
} from "reactstrap";
import {Link} from "react-router-dom";

class EventEdit extends Component {
    constructor(props) {
        super(props);
        this.state = {
            event: ""
        };
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

    handleChange(e) {
        const name = e.target.name;
        const value = e.target.value;
        this.setState(function(prevState) {
            return {
                event: {
                    ...prevState.event,
                    [name]: value
                }
            };
        });
    }

    handleSave(){
        axios.put("/api/events/"+this.props.match.params.id, this.state.event)
            .then(function(response) {
                swal({title: "Event record saved", icon: "success"})
            }).catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });

    }

    render() {
        return (
            <div className="animated fadeIn">

                <Row>
                    <Col xs="12" md="12">
                        <Card>
                            <Form className="form-horizontal">
                                <CardHeader>
                                    <strong>Event #{this.state.event.id} edit form</strong>
                                </CardHeader>
                                <CardBody>

                                    <FormGroup row>
                                        <Col md="3">
                                            <Label>Title</Label>
                                        </Col>
                                        <Col xs="12" md="9">
                                            <Input value={this.state.value} onChange={this.handleChange} type="text" name="title"
                                                   placeholder="Title"/>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                        <Col md="3">
                                            <Label>Description</Label>
                                        </Col>
                                        <Col xs="12" md="9">
                                            <Input value={this.state.value} onChange={this.handleChange} type="textarea" name="description" rows="9"
                                                   placeholder="Description"/>
                                        </Col>
                                    </FormGroup>

                                </CardBody>
                                <CardFooter className="text-right">
                                    <Button color="success" onClick={this.handleSave}>
                                        <i className="fa fa-dot-circle-o"></i>Save</Button>
                                    <Link to="/events"><Button color="primary">
                                        <i className="fa fa-ban"></i>Back</Button>
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