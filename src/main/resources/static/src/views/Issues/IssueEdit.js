import React, {Component} from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import {Button, Card, CardBody, CardFooter, CardHeader, Col, Form, FormGroup, Input, Label, Row} from "reactstrap";
import DateTime from 'react-datetime';
import 'react-datetime/css/react-datetime.css';

class IssueEdit extends Component {
    constructor(props) {
        super(props);

        this.state = {
            issue: {
                id: "",
                title: "",
                description: "",
                initialDate: "",
                category: ""
            },
            initialDate: true
        };

        this.handleInitialDateChange = this.handleInitialDateChange.bind(this);
        this.handleSave = this.handleSave.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleBack = this.handleBack.bind(this);
    }

    componentWillMount() {
        var _this = this;
        axios.get("/api/issues/"+this.props.match.params.id)
            .then(function(response) {
                _this.setState({
                    issue: response.data
                })
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });
    }

    handleInitialDateChange(m){
            this.setState(function(prev) {
                return {
                    issue: {
                        ...prev.issue,
                        initialDate: m.format("DD/MM/YYYY HH:mm")
                    }
                }
            });
        }

    handleChange(e) {
        const name = e.target.name;
        const value = e.target.value;
        this.setState(function(prev) {
            return {
                issue: {
                    ...prev.issue,
                    [name]: value
                }
            }
        })
    }

    handleSave(){
        axios.put("/api/issues/" + this.props.match.params.id, this.state.issue)
            .then(function (response) {
                swal({title: "Issue record saved", icon: "success"})
            }).catch(function (error) {
            swal({title: "Something went wrong!", text: error, icon: "error"});
        });
    }

    handleBack() {
        this.props.history.goBack();
    }

    render() {
        return (
            <div className="animated fadeIn">
                <Row>
                    <Col xs="24" md="12">
                        <Card>
                            <Form className="form-horizontal">
                                <CardHeader>
                                    <strong>Issue {this.state.issue.id} edit form</strong>
                                </CardHeader>
                                <CardBody>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>Title</Label>
                                        </Col>
                                        <Col xs="12" md="10">
                                            <Input value={this.state.issue.title} onChange={this.handleChange}
                                                   type="text" name="title"
                                                   placeholder="Title"/>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>Description</Label>
                                        </Col>
                                        <Col xs="12" md="10">
                                            <Input value={this.state.issue.description} onChange={this.handleChange}
                                                   type="textarea" name="description" rows="9"
                                                   placeholder="Description"/>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>Initial Date</Label>
                                        </Col>
                                        <Col xs="12" md="4">
                                            <DateTime value={this.state.issue.initialDate} dateFormat="DD/MM/YYYY"
                                                timeFormat="HH:mm" onChange={this.handleInitialDateChange}
                                                inputProps={{readOnly: true, className: "form-control form-control-readonly"}} />
                                        </Col>
                                        </FormGroup>

                                    <FormGroup row>
                                        <Col md="2">
                                            <Label>Category</Label>
                                        </Col>
                                        <Col xs="12" md="4">
                                            <Input value={this.state.issue.category} onChange={this.handleChange}
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
                                    <Button color="success" onClick={this.handleSave}>
                                            <i className="fa fa-dot-circle-o"/> Save</Button>
                                    <Button color="primary" onClick={this.handleBack}>
                                        <i className="fa fa-ban"/> Back</Button>
                                </CardFooter>
                            </Form>
                        </Card>
                    </Col>
                </Row>
            </div>
        )
    }
}

export default IssueEdit