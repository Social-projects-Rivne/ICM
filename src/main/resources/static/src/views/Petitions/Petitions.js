import React, { Component } from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import PetitionsContainer from "./PetitionsContainer";

class Petitions extends Component {
    constructor(props) {
        super(props);

        this.state = {
            petitions: "",
            direction: "ASC",
            sort: "id",
            page: 1
        };
        this.handleSortChangeDirection = this.handleSortChangeDirection.bind(this);
        this.handleSortChangeColumn = this.handleSortChangeColumn.bind(this);
        this.handlePageChange = this.handlePageChange.bind(this);
    }

    componentWillMount() {
        this.makeQuery();
    }

    handleSortChangeDirection(sortDirection) {
        this.setState({direction: sortDirection}, function() {
            this.makeQuery();
        });
    }

    handleSortChangeColumn(sortColumn) {
        this.setState({sort: sortColumn}, function() {
            this.makeQuery();
        });
    }

    handlePageChange(pageNum) {
        this.setState({page: pageNum}, function() {
            this.makeQuery();
        });
    }

    makeQuery() {
        var _this = this;
        axios.get(["/api/petitions?page=", this.state.page, "&sort=", this.state.sort,
            "&direction=", this.state.direction].join(""))
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
            <PetitionsContainer data={this.state.petitions} onPageChange={this.handlePageChange}
                                onSortChangeDirection={this.handleSortChangeDirection}
                                onSortChangeColumn={this.handleSortChangeColumn}/>
        )
    }
}
export default Petitions;