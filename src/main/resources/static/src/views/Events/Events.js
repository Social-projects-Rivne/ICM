import React, { Component } from 'react';
import axios from 'axios';
import {Button, Card, CardBody, CardHeader, Col, Row, Table} from "reactstrap";
import swal from 'sweetalert';
import {Link} from "react-router-dom";

class Events extends Component {
    constructor(props) {
        super(props);

        this.state = {
            events: []
        };
    }

    componentWillMount() {
        var _this = this;
        axios.get("/api/events")
            .then(function(response) {
                _this.setState({
                    events: response.data
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
                    <Col xs="12" lg="12">
                        <Card>
                            <CardHeader>Event list</CardHeader>
                            <CardBody>
                                <Table responsive bordered>
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Title</th>
                                            <th>Initial Date</th>
                                            <th>End Date</th>
                                            <th>Category</th>
                                            <th>User ID</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {this.state.events.map(function(event, i) {
                                            return (
                                                <Event key={i} event={event}/>
                                            );
                                        })}
                                    </tbody>
                                </Table>
                            </CardBody>
                        </Card>
                    </Col>
                </Row>
            </div>
        )
    }
}

class Event extends Component {

    constructor(props) {
        super(props);

        this.state = {
            event: this.props.event,
            mounted: true
        };

        this.handleDelete = this.handleDelete.bind(this);
        this.handleEdit = this.handleEdit.bind(this);
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
                    axios.delete("/api/events/"+_this.state.event.id)
                        .then(function(response) {
                            _this.setState({
                                mounted: false
                            });
                            swal({title: "Event record deleted", icon: "success"});
                        }).catch(function (error) {
                            swal({title: "Something went wrong!", text: error, icon: "error"});
                        });
                }
            });
    }

    handleEdit(){
    }

    render() {
        const row = (
                <tr>
                    <td>{this.state.event.id}</td>
                    <td>{this.state.event.title}</td>
                    <td>{this.state.event.initialDate}</td>
                    <td>{this.state.event.endDate}</td>
                    <td>{this.state.event.category}</td>
                    <td>{this.state.event.userDto.id}</td>
                    <td>
                        <Link to={"/events/"+this.state.event.id+"/edit"}><Button color="info" size="sm">Edit</Button></Link>{' '}
                        <Button color="danger" size="sm" onClick={this.handleDelete}>Delete</Button>
                    </td>
                </tr>
            );
        return (this.state.mounted === true ? row : null)
    }
}

export default Events;
