import React, {Component} from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import IssuesContainer from "./IssuesContainer";

class Issues extends Component {
    constructor(props) {
        super(props);

        this.state = {
            issues: "",
            sort: 'asc',
            page: 1
        };
        this.handleSortChange = this.handleSortChange.bind(this);
        this.handlePageChange = this.handlePageChange.bind(this);
    }

    componentWillMount() {
        this.makeQuery();
    }

    handleSortChange(sortOrder) {
        this.setState({sort: sortOrder}, function() {
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
            <IssuesContainer data={this.state.issues} onPageChange={this.handleSortChange}
                            onPageChange={this.handlePageChange}/>
        )
    }
}
export default Issues;