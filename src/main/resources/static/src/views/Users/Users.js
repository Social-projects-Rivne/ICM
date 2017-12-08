import React, { Component } from 'react';
import axios from 'axios';
import {Button, Card, CardBody, CardHeader, Col, Row, Table} from "reactstrap";
<<<<<<< HEAD
import swal from 'sweetalert';
=======

>>>>>>> a0c50887e90ccec00bb50cc2ea97bad942d7d479
import {Link} from "react-router-dom";
import qs from 'qs';

class User extends Component {

    constructor(props) {
        super(props);
        this.state = {
            userList: this.load(),
            users : []
        };
        this.load = this.load.bind(this);
    }

    load(event){
        let _this = this;
        axios.get("api/users")
            .then(
                function(response){
                    _this.setState({users: response.data});
                    console.log(response);
                })
            .catch (function (error) {
            swal({title: "Something went wrong!", text: error, icon: "error"});
        });
    }



    render() {
        return (
            <div className="animated fadeIn">
                <Row>
                    <Col xs="12" lg="12">
                        <Card>
                            <CardHeader>Users list</CardHeader>
                            <CardBody>
                                <Table responsive bordered>
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Role</th>
                                        <th>registrationDate</th>
                                        <th>First name</th>
                                        <th>Last name</th>
                                        <th>Pasword</th>
                                        <th>Email</th>
                                        <th>Phone</th>
                                        <th>User Agreement</th>
                                        <th>User status</th>
                                        <th>Delete date</th>
                                        <th>Avatar</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {this.state.users.map(function(user, i) {
                                        return (
                                            <User key={i} user={user}/>
                                        );
                                    })}
                                    </tbody>
                                </Table>
                            </CardBody>
                        </Card>
                    </Col>
                </Row>
            </div>
        )
    }


}
export default User;

<<<<<<< HEAD





 class Users extends Component {

     constructor(props) {
         super(props);

         this.state = {
             users: this.props.users,
             mounted: true
         };

         //this.handleDelete = this.handleDelete.bind(this);
         this.handleEdit = this.handleEdit.bind(this);
     }

     // handleDelete(){
     //     var _this = this;
     //     swal({
     //         title: "Are you sure?",
     //         text: "Once deleted, you will not be able to recover this data!",
     //         icon: "warning",
     //         buttons: true,
     //         dangerMode: true,
     //     })
     //         .then(function(willDelete) {
     //             if (willDelete) {
     //                 axios.delete("/api/use/"+_this.state.event.id)
     //                     .then(function(response) {
     //                         _this.setState({
     //                             mounted: false
     //                         });
     //                         swal({title: "Event record deleted", icon: "success"});
     //                     }).catch(function (error) {
     //                     swal({title: "Something went wrong!", text: error, icon: "error"});
     //                 });
     //             }
     //         });
     // }

     handleEdit(){
     }

     render() {
         const row = (
             <tr>
                 <td>{this.state.users.id}</td>
                 <td>{this.state.users.userRole}</td>
                 <td>{this.state.users.registrationDate}</td>
                 <td>{this.state.users.}</td>
                 <td>{this.state.users.}</td>
                 <td>{this.state.users.}</td>
                 <td>
                     <Link to={"/users/"+this.state.event.id+"/edit"}><Button color="info" size="sm">Edit</Button></Link>{' '}
                     <Button color="danger" size="sm" onClick={this.handleDelete}>Delete</Button>
                 </td>
             </tr>
         );
         return (this.state.mounted === true ? row : null)
     }
 }
export default Users;
=======
export default User;


>>>>>>> a0c50887e90ccec00bb50cc2ea97bad942d7d479
