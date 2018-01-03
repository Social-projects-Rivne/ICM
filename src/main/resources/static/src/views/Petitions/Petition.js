import React, { Component } from 'react';
import axios from 'axios';
import {Button} from "reactstrap";
import swal from 'sweetalert';
import {Link} from "react-router-dom";

class Petition extends Component {

    constructor(props) {
        super(props);

        this.state = {
            petition: this.props.petition,
            mounted: true
        };

        this.handleDelete = this.handleDelete.bind(this);
    }

    componentWillReceiveProps(props) {
        this.setState({petition: props.petition});
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
                    axios.delete("/api/petitions/"+_this.state.petition.id)
                        .then(function(response) {
                            _this.setState({
                                mounted: false
                            });
                            swal({title: "Petition record deleted", icon: "success"});
                        }).catch(function (error) {
                        swal({title: "Something went wrong!", text: error, icon: "error"});
                    });
                }
            });
    }

    render() {
            const row = (
                <tr>
                    <td>{this.state.petition.id}</td>
                    <td>{this.state.petition.title}</td>
                    <td>{this.state.petition.initialDate}</td>
                    <td>{this.state.petition.category}</td>
                    <td>{this.state.petition.userDto.id}</td>
                    <td>
                        <Link to={"/admin/petitions/"+this.state.petition.id+"/edit"}>
                            <Button color="info" size="sm">Edit</Button></Link>{' '}
                        <Button color="danger" size="sm" onClick={this.handleDelete}>Delete</Button>
                    </td>
                </tr>
            );
            return (this.state.mounted === true ? row : null)
        }
}

export default Petition;