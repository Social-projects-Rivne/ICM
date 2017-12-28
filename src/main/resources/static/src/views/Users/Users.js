import React, { Component } from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import UsersContainer from "./UsersContainer";

class Users extends Component {

    constructor(props) {
        super(props);

        this.state = {
            users: "",
            page: 1
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
        axios.get(["/api/users?page=", this.state.page].join(""))
             .then(function(response) {
                  _this.setState({
                        users: response.data
                   });
             })
            .catch(function (error) {
                  swal({title: "Something went wrong!", text: error, icon: "error"});
            });
    }

    render() {
        return (
           <UsersContainer data={this.state.users} onPageChange={this.handlePageChange}/>
        )
    }

}
export default Users;



