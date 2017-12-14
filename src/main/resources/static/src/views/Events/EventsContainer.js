import React, {Component} from 'react';
import {Card, CardBody, CardHeader, Col, Row, Table} from "reactstrap";
import Event from "./Event";

class EventsContainer extends Component {
    constructor(props) {
        super(props);

    }

    table() {
        if(this.props.data.length !== 0) {
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
                    {this.props.data.map(function (event, i) {
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
