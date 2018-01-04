import React, { Component } from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import PetitionsContainer from "./PetitionsContainer";

class Petitions extends Component {
    constructor(props) {
        super(props);

        this.state = {
            petitions: "",
            page: 1
        };

        this.handlePageChange = this.handlePageChange.bind(this);
    }

    componentWillMount() {
        this.makeQuery();
    }

    handlePageChange(pageNum) {
        this.setState({page: pageNum}, function () {
            this.makeQuery();
        });
    }

    makeQuery() {
        var _this = this;
        axios.get(["/api/petitions?page=", this.state.page].join(""))
            .then(function (response) {
                _this.setState({
                    petitions: response.data
                });
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });
    }

    handleClick() {
        alert("ok");
    }


    render() {
        return (
            <div className="animated fadeIn">
                <Row>
                    <Col xs="12" lg="12">
                        <Card>
                            <CardHeader>Petition list <Link to={"/admin/petitions/add"}><Button className="pull-right"
                                                                                                color="success"
                                                                                                size="sm">Add
                                new</Button></Link> <Button className="pull-right mr-10" color="info" size="sm"
                                                            onClick={this.handleClick}>PDF</Button></CardHeader>
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
                                    {this.state.petitions.map(function (petition, i) {
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
            </div>)
    }


    render() {
        return (
            <PetitionsContainer data={this.state.petitions} onPageChange={this.handlePageChange}/>
        )
    }
}

export default Petitions;
