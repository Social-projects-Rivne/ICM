import React, { Component } from 'react';
import axios from 'axios';
import qs from 'qs';

class User extends Component {

    constructor(props) {
        super(props);
        this.state = {
            userList: this.load(),
            users : "Init users!"
        };
        this.load = this.load.bind(this);
    }

    load(event){
        axios.get("api/users")
            .then(
                (response) => {
                    this.setState({users: response.data});
                    console.log(response);
                });
    }

    render() {
        return (
            <div className="animated fadeIn">
                {this.state.users}
            </div>
        )
    }
}

export default User;