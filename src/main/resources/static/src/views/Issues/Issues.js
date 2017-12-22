import React, { Component } from 'react';
import axios from 'axios';
import {Button, Card, CardBody, CardHeader, CardFooter, Col, Row, Table} from "reactstrap";
import swal from 'sweetalert';
import {Link} from "react-router-dom";

class Issues extends Component {
    constructor(props) {
        super(props);

        this.state = {
            issues: [],
            page: 0,
            size: 10,
            step: 1
        },
        this.handleNavFirst = this.handleNavFirst.bind(this);
        this.handleNavPrev = this.handleNavPrev.bind(this);
        this.handleNavNext = this.handleNavNext.bind(this);
        this.handleNavLast = this.handleNavLast.bind(this);
    }

    componentWillMount() {
        this.onNavigate();
    }

    onNavigate() {
        var _this = this;
        axios.get(["/api/issues?page=", this.state.page, "&size=",this.state.size].join(""))
            .then(function(response) {
                _this.setState({
                    issues: response.data.content
                });
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });
    }

    handleNavFirst(){
    	this.setState({page: 0});
    	this.onNavigate();
    }

    handleNavPrev() {
    	this.setState({page: this.state.page-this.state.step})
    	this.onNavigate();
    }

    handleNavNext() {
    	this.setState({page: this.state.page+this.state.step})
    	this.onNavigate();
    }

    handleNavLast() {
    	this.setState({page: page})
    	this.onNavigate();
    }

    render() {

        return (
            <div className="animated fadeIn">
                <Row>
                    <Col xs="12" lg="12">
                        <Card>
                            <CardHeader>Issue list</CardHeader>
                            <CardBody>
                                <Table responsive bordered>
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Title</th>
                                        <th>Initial Date</th>
                                        <th>Category</th>
                                        <th>User ID</th>
                                        <th>Actions</th>
                                    </tr>
                                    </thead>
                                    <tbody>{this.forceUpdate}
                                        {this.state.issues.map(function(issue, i) {
                                            return (<Issue key={i} issue={issue}/>);
                                        })}
                                    </tbody>
                                </Table>
                            </CardBody>
                            <CardFooter className="text-center">
                                <Button color="info" onClick={this.handleNavFirst}>First</Button>
                                <Button color="info" onClick={this.handleNavPrev}>Previous</Button>
                                <Button color="info" onClick={this.handleNavNext}>Next</Button>
                                <Button color="info" onClick={this.handleNavLast}>Last</Button>
                            </CardFooter>
                        </Card>
                    </Col>
                </Row>
            </div>
        )
    }
}

class Issue extends Component {

    constructor(props) {
        super(props);

        this.state = {
            issue: this.props.issue,
            mounted: true
        };

        this.handleDelete = this.handleDelete.bind(this);
    }

    handleDelete(){
        var _this = this;
        swal({
            title: "Are you sure?",
            text: "Once deleted, you will not be able to recover this data!",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then(function(willDelete) {
                if (willDelete) {
                    axios.delete("/api/issues/"+_this.state.issue.id)
                        .then(function(response) {
                            _this.setState({
                                mounted: false
                            });
                            swal({title: "Issue record deleted", icon: "success"});
                        }).catch(function (error) {
                        swal({title: "Something went wrong!", text: error, icon: "error"});
                    });
                }
            });
    }

    render() {
        const row = (
            <tr>
                <td>{this.state.issue.id}</td>
                <td>{this.state.issue.title}</td>
                <td>{this.state.issue.initialDate}</td>
                <td>{this.state.issue.category}</td>
                <td>{this.state.issue.userDto.id}</td>
                <td>
                    <Link to={"/issues/"+this.state.issue.id+"/edit"}><Button color="info" size="sm">Edit</Button></Link>{' '}
                    <Button color="danger" size="sm" onClick={this.handleDelete}>Delete</Button>
                </td>
            </tr>
        );
        return (this.state.mounted === true ? row : null)
    }
}

export default Issues;