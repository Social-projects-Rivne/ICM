import React, {Component} from 'react';
import {Card, CardBody, CardHeader, Col, Row, Table} from "reactstrap";
import Event from "./Event";
import axios from 'axios';
import swal from 'sweetalert';

class EventsContainer extends Component {
    constructor(props) {
        super(props);

        this.state = {
            data: "",
            queryUrl: this.props.queryUrl
        };
    }

    updateData() {
        var _this = this;
        axios.get(this.state.queryUrl)
            .then(function(response) {
                _this.setState({
                    data: response.data
                })
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });
    }

    componentWillMount() {
        this.updateData();
    }

    componentWillReceiveProps(props) {
        this.setState({queryUrl: props.queryUrl});
        this.updateData();
    }

    table() {
        if(this.state.data.length !== 0) {
            return (
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
                    {this.state.data.map(function (event, i) {
                        return (
                            <Event key={i} event={event}/>
                        );
                    })}
                    </tbody>
                </Table>
            )
        } else {
            return <div className="text-center">The list is empty</div>
        }
    }

    render() {
        return (
            <div className="animated fadeIn">
                <Row>
                    <Col xs="12" lg="12">
                        <Card>
                            <CardHeader>Event list</CardHeader>
                            <CardBody>
                                {this.table()}
                            </CardBody>
                        </Card>
                    </Col>
                </Row>
            </div>
        )
    }
}

export default EventsContainer;
