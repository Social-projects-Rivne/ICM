import React, { Component } from 'react';
import axios from 'axios';
import qs from 'qs';

class User extends Component {

    constructor(props) {
        super(props);
        this.state = {
            userList: this.load(),
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
    renderUserList () {
        if (this.state.users) {
            return (
                <ul>
                    {this.state.users.map(
                        (user, i) => <tr key={i}>
                            <td>{user.id}</td>


                            </tr>)}
                </ul>
            )
        }

        return <p>Loading tasks...</p>
    }
    render() {
        return (
            <div className="animated fadeIn">
                {this.renderUserList()}

            </div>
        )
    }
}

export default User;