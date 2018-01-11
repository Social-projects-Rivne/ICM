import React, { Component } from 'react';
import axios from 'axios';
import {Button} from "reactstrap";
import swal from 'sweetalert';
import {Link} from "react-router-dom";

class User extends Component {

     constructor(props) {
         super(props);

         this.state = {
             user: this.props.user
         };
         this.handleDelete = this.handleDelete.bind(this);
     }

     componentWillReceiveProps(props) {
         this.setState({user: props.user});
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
                     axios.delete("/api/users/delete/"+_this.state.user.id)
                         .then(function(response) {
                             _this.props.onDelete();
                             swal({title: "User record deleted", icon: "success"});
                         }).catch(function (error) {
                         swal({title: "Something went wrong!", text: error, icon: "error"});
                     });
                 }
             });
     }

     render() {
         const row = (
             <tr>
                 <td>{this.state.user.id}</td>
                 <td>{this.state.user.userRole}</td>
                 <td>{this.state.user.registrationDate}</td>
                 <td>{this.state.user.firstName}</td>
                 <td>{this.state.user.lastName}</td>
                 <td>{this.state.user.email}</td>
                 <td>{this.state.user.phone}</td>
                 <td>{this.state.user.userStatus}</td>
                 <td>
                     <Link to={"/admin/users/"+this.state.user.id+"/edit"}><Button color="info" size="sm">Edit</Button></Link>{' '}
                     <Button color="danger" size="sm" onClick={this.handleDelete}>Delete</Button>
                 </td>
             </tr>
         );
         return (row)
     }
 }

export default User;