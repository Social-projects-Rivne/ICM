import React, {Component} from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import IssuesContainer from "./IssuesContainer";

class Issues extends Component {
    constructor(props) {
        super(props);

        this.state = {
            issues: "",
            direction: "DESC",
            sort: "initialDate",
            page: 1
        };
        this.handleSortChange = this.handleSortChange.bind(this);
        this.handlePageChange = this.handlePageChange.bind(this);
    }

    componentWillMount() {
        this.makeQuery();
    }

    handleSortChange(sortColumn, sortDirection) {
        this.setState({sort: sortColumn, direction: sortDirection}, function() {
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
        axios.get(["/api/issues?page=", this.state.page, "&sort=", this.state.sort,
                    "&direction=", this.state.direction,].join(""))
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
                            onSortChange={this.handleSortChange}/>
        )
    }
}
export default Issues;