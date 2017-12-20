import React, { Component } from 'react';
import axios from 'axios';
import {Button, Card, CardBody, CardHeader, Col, Row, Table} from "reactstrap";
import swal from 'sweetalert';
import {Link} from "react-router-dom";

class Petitions extends Component {
    constructor(props) {
        super(props);

        this.state = {
            petitions: []
        };
    }

    componentWillMount() {
        var _this = this;
        axios.get("/api/petitions")
            .then(function(response) {
                _this.setState({
                    petitions: response.data
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
                            <CardHeader>Petition list <Link to={"/petitions/add"}><Button className="pull-right" color="success" size="sm">Add new</Button></Link></CardHeader>
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
                                    <tbody>
                                    {this.state.petitions.map(function(petition, i) {
                                        return (
                                            <Petition key={i} petition={petition}/>
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

class Petition extends Component {

    constructor(props) {
        super(props);

        this.state = {
            petition: this.props.petition,
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
                    axios.delete("/api/petitions/"+_this.state.petition.id)
                        .then(function(response) {
                            _this.setState({
                                mounted: false
                            });
                            swal({title: "Petition record deleted", icon: "success"});
                        }).catch(function (error) {
                        swal({title: "Something went wrong!", text: error, icon: "error"});
                    });
                }
            });
    }

    render() {
        const row = (
            <tr>
                <td>{this.state.petition.id}</td>
                <td>{this.state.petition.title}</td>
                <td>{this.state.petition.initialDate}</td>
                <td>{this.state.petition.category}</td>
                <td>{this.state.petition.userDto.id}</td>
                <td>
                    <Link to={"/petitions/"+this.state.petition.id+"/edit"}>
                        <Button color="info" size="sm">Edit</Button></Link>{' '}
                    <Button color="danger" size="sm" onClick={this.handleDelete}>Delete</Button>
                </td>
            </tr>
        );
        return (this.state.mounted === true ? row : null)
    }
}

export default Petitions;