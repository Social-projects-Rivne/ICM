import React, {Component} from 'react';
import {Card, CardBody, CardHeader, Col, Row, Table} from "reactstrap";
import Issue from "./Issue";

class IssuesContainer extends Component {
    constructor(props) {
        super(props);

        this.state = {
            data: this.props.data
        };
    }

    componentWillReceiveProps(props) {
        this.setState({data: props.data});
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
                        <th>Category</th>
                        <th>User ID</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.data.map(function(issue, i) {
                        return (
                            <Issue key={i} issue={issue}/>
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
                            <CardHeader>Issue list</CardHeader>
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
export default IssuesContainer;