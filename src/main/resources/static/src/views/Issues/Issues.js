import React, { Component } from 'react';
import axios from 'axios';
import {Button, Card, CardBody, CardHeader, Col, Row, Table} from "reactstrap";

class Issues extends Component {

    constructor(props) {
        super(props);
        this.state = {
            issueList: this.load(),
            issues : []
        };
        this.load = this.load.bind(this);
    }

    load(event){
        axios.get("api/issues")
            .then(
                (response) => {
            this.setState({issues: response.data});
        console.log(response);
    });
    }

    render() {
        return (
            <div className="animated fadeIn">
                <Row>
                    <Col xs="12" lg="12">
                        <Card>
                            <CardHeader>Issues list</CardHeader>
                            <CardBody>
                                <Table responsive bordered>
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Title</th>
                                        <th>Initial Date</th>
                                        <th>End Date</th>
                                        <th>Category</th>
                                        <th>Actions</th>
                                        <th>User ID</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {this.state.issues}
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

export default Issues;