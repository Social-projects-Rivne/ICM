import React, {Component} from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import IssuesContainer from "./IssuesContainer";

class Issues extends Component {
    constructor(props) {
        super(props);

        this.state = {
            issues: "",
            page: 1
        };

        this.handlePageChange = this.handlePageChange.bind(this);
        this.handlePageSort = this.handlePageSort.bind(this);
    }

    componentWillMount() {
        this.makeQuery();
    }

    handlePageChange(pageNum) {
        this.setState({page: pageNum}, function() {
            this.makeQuery();
        });
    }

    handlePageSort(sort) {
        this.setState(function() {
            this.makeQuery();
        });
    }

    makeQuery() {
        var _this = this;
        axios.get(["/api/issues?page=", this.state.page].join(""))
            .then(function(response) {
                _this.setState({
                    issues: response.data
                });
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });
    }

    render() {
        return (
            <IssuesContainer data={this.state.issues} onPageChange={this.handlePageChange}
            onPageChange={this.handlePageSort}/>
        )
    }
}
export default Issues;