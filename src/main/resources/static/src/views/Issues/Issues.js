import React, {Component} from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import IssuesContainer from "./IssuesContainer";

class Issues extends Component {
    constructor(props) {
        super(props);

        this.state = {
            issues: "",
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
        axios.get(["/api/issues?page=", this.state.page, "&direction=", this.state.direction,
                    "&sort=", this.state.sort].join(""))
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
                            onSortChangeDirection={this.handleSortChangeDirection}
                            onSortChangeColumn={this.handleSortChangeColumn}/>
        )
    }
}
export default Issues;