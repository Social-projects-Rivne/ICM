import React, { Component } from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import {
    Button, Card, CardBody, CardFooter, CardHeader, Col, Form, FormGroup, Input, Label,
    Row, FormFeedback
} from "reactstrap";
import {Link} from "react-router-dom";

class EventEdit extends Component {
    constructor(props) {
        super(props);

        this.state = {
            event: "",
            initDateValid: true
        };

        var reg = new RegExp("[0-3][0-9]/[0-1][0-2]/[0-9]{4,} [0-2][0-3]:[0-5][0-9]");

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
        var isValid = true;
        if(e.target.type == "datetime")
        {
            isValid = reg.test(value);
            this.setState({initDateValid: isValid});
            console.log(isValid);
        }
        if(isValid) {
            this.setState(function(prev) {
                return {
                    event: {
                        ...prev.event,
                        [name]: value
                    }
                }

            })
        }
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
                                            <Input value={this.state.event.title} onChange={this.handleChange}
                                                   type="text" name="title"
                                                   placeholder="Title"/>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                        <Col md="3">
                                            <Label>Description</Label>
                                        </Col>
                                        <Col xs="12" md="9">
                                            <Input value={this.state.event.description} onChange={this.handleChange}
                                                   type="textarea" name="description" rows="9"
                                                   placeholder="Description"/>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                        <Col md="3">
                                            <Label>Initial Date</Label>
                                        </Col>
                                        <Col xs="12" md="9">
                                            <Input value={this.state.event.initialDate} onChange={this.handleChange}
                                                   type="datetime" name="initialDate" rows="9"
                                                   placeholder="dd/MM/yyyy HH:mm" valid={this.state.initDateValid}/>
                                            <FormFeedback>Incorrect format</FormFeedback>
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