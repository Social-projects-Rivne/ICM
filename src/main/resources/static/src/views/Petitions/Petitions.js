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
        this.setState({page: pageNum}, function() {
            this.makeQuery();
        });
    }

    makeQuery() {
        var _this = this;
        axios.get(["/api/petitions?page=", this.state.page].join(""))
            .then(function(response) {
                _this.setState({
                    petitions: response.data
                });
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });
    }
    render() {
        return (
            <PetitionsContainer data={this.state.petitions} onPageChange={this.handlePageChange}/>
        )
    }
}
export default Petitions;
