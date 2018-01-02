import React, { Component } from 'react';
import axios from 'axios';
import {Button} from "reactstrap";
import swal from 'sweetalert';
import {Link} from "react-router-dom";

class Issue extends Component {

    constructor(props) {
        super(props);

        this.state = {
            issue: this.props.issue,
            mounted: true
        };

        this.handleDelete = this.handleDelete.bind(this);
    }

    componentWillReceiveProps(props) {
        this.setState({issue: props.issue});
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
                    axios.delete("/api/issues/"+_this.state.issue.id)
                        .then(function(response) {
                            _this.setState({
                                mounted: false
                            });
                            swal({title: "Issue record deleted", icon: "success"});
                        }).catch(function (error) {
                        swal({title: "Something went wrong!", text: error, icon: "error"});
                    });
                }
            });
    }

    render() {
        const row = (
            <tr>
                <td>{this.state.issue.id}</td>
                <td>{this.state.issue.title}</td>
                <td>{this.state.issue.initialDate}</td>
                <td>{this.state.issue.category}</td>
                <td>{this.state.issue.userDto.id}</td>
                <td>
                    <Link to={"/admin/issues/"+this.state.issue.id+"/edit"}>
                        <Button color="info" size="sm">Edit</Button></Link>{' '}
                    <Button color="danger" size="sm" onClick={this.handleDelete}>Delete</Button>
                </td>
            </tr>
        );
        return (this.state.mounted === true ? row : null)
    }
}

export default Issue;