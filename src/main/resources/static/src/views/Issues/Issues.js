import React, {Component} from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import IssuesContainer from "./IssuesContainer";

class Issues extends Component {
    constructor(props) {
        super(props);

        this.state = {
            issues: "",
            page: 0,
            size: 10
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
        axios.get(["/api/issues?page=", this.state.page, "$size=", this.state.size].join(""))
            .then(function(response) {
                _this.setState({
                    issues: response.data.content
                });
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });
    }
    render() {
        return (
            <IssuesContainer data={this.state.issues} onPageChange={this.handlePageChange}/>
        )
    }
}
export default Issues;