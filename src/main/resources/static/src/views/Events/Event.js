import React, {Component} from 'react';
import axios from 'axios';
import {Button} from "reactstrap";
import swal from 'sweetalert';
import {Link} from "react-router-dom";

class Event extends Component {

    constructor(props) {
        super(props);

        this.state = {
            event: this.props.event,
            mounted: true
        };

        this.handleDelete = this.handleDelete.bind(this);
    }

    componentWillReceiveProps(props) {
        this.setState({event: props.event});
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
                    axios.delete("/api/events/"+_this.state.event.id)
                        .then(function(response) {
                            _this.setState({
                                mounted: false
                            });
                            swal({title: "Event record deleted", icon: "success"});
                        }).catch(function (error) {
                        swal({title: "Something went wrong!", text: error, icon: "error"});
                    });
                }
            });
    }

    render() {
        const row = (
            <tr>
                <td>{this.state.event.id}</td>
                <td>{this.state.event.title}</td>
                <td>{this.state.event.initialDate}</td>
                <td>{this.state.event.endDate}</td>
                <td>{this.state.event.category}</td>
                <td>{this.state.event.userDto.id}</td>
                <td>
                    <Link to={"/admin/events/"+this.state.event.id+"/edit"}>
                        <Button color="info" size="sm">Edit</Button></Link>{' '}
                    <Button color="danger" size="sm" onClick={this.handleDelete}>Delete</Button>
                </td>
            </tr>
        );
        return (this.state.mounted === true ? row : null)
    }
}

export default Event;