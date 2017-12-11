import React, { Component } from 'react';
import axios from 'axios';
import {Button, Card, CardBody, CardHeader, Col, Row, Table} from "reactstrap";
import swal from 'sweetalert';
import {Link} from "react-router-dom";
class User extends Component {

    constructor(props) {
        super(props);
        this.state = {
            users : []
        };
    }
    componentWillMount() {
            var _this = this;
            axios.get("/api/users")
                .then(function(response) {
                    _this.setState({
                        users: response.data
                    })
                })
                .catch(function (error) {
                    swal({title: "Something went wrong!", text: error, icon: "error"});
                })
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
                                        <th>Password</th>
                                        <th>Email</th>
                                        <th>Phone</th>
                                        {/*<th>User Agreement</th>*/}
                                        <th>User status</th>
                                        <th>Delete date</th>
                                        <th>Avatar</th>
                                        {/*<th>IsDeleted</th>*/}
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {this.state.users.map(
                                        function(user, i)
                                        {
                                            return ( <Users key={i} users={user}/> );
                                        }
                                        )
                                    }
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


 class Users extends Component {


     constructor(props) {
         super(props);

         this.state = {
             users: this.props.users,
             mounted: true
         };
         this.handleDelete = this.handleDelete.bind(this);

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
                     axios.delete("/api/users/delete/"+_this.state.users.id)
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
                 <td>{this.state.users.id}</td>
                 <td>{this.state.users.userRole}</td>
                 <td>{this.state.users.registrationDate}</td>
                 <td>{this.state.users.firstName}</td>
                 <td>{this.state.users.lastName}</td>
                 <td>{this.state.users.password}</td>
                 <td>{this.state.users.email}</td>
                 <td>{this.state.users.phone}</td>
                 {/*<td>{this.state.users.userAgreement}</td>*/}
                 <td>{this.state.users.userStatus}</td>
                 <td>{this.state.users.deleteDate}</td>
                 <td>{this.state.users.avatarUrl}</td>
                 {/*<td>{this.state.users.isDeleted}</td>*/}
                 <td>
                     <Link to={"/users/"+this.state.users.id+"/edit"}><Button color="info" size="sm">Edit</Button></Link>{' '}
                     <Button color="danger" size="sm" onClick={this.handleDelete}>Delete</Button>
                 </td>
             </tr>
         );
         return (this.state.mounted === true ? row : null)
     }
 }
export default User;



