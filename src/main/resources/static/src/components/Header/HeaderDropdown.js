import React, {Component} from 'react';
import axios from 'axios';

import {
    Badge,
    DropdownItem,
    DropdownMenu,
    DropdownToggle,
    Dropdown
} from 'reactstrap';

class HeaderDropdown extends Component {

    constructor(props) {
        super(props);

        this.toggle = this.toggle.bind(this);
        this.state = {
            dropdownOpen: false
        };
    }

    toggle() {
        this.setState({
            dropdownOpen: !this.state.dropdownOpen
        });
    }

    name() {

        var xhr = new XMLHttpRequest();
        xhr.open('GET', '/api/admin_name', false);
        xhr.send();
        return xhr.responseText;
        /*axios.get('/api/admin_name')
            .then(function (response) {
                console.log(response);
                return response
            })
            .catch(function (response) {
                console.log(response);
                return "no name"
            })*/
    }

    dropAccnt() {
        return (
            <Dropdown nav isOpen={this.state.dropdownOpen} toggle={this.toggle}>
                <DropdownToggle nav>
                    <img src={'img/avatars/6.jpg'} className="img-avatar" alt={this.name()}/>
                </DropdownToggle>
                <DropdownMenu right>
                    <DropdownItem header tag="div" className="text-center"><strong>Account</strong></DropdownItem>
                    <DropdownItem><i className="fa fa-bell-o"></i> Updates<Badge color="info">42</Badge></DropdownItem>
                    <DropdownItem><i className="fa fa-envelope-o"></i> Messages<Badge color="success">42</Badge></DropdownItem>
                    <DropdownItem><i className="fa fa-tasks"></i> Tasks<Badge color="danger">42</Badge></DropdownItem>
                    <DropdownItem><i className="fa fa-comments"></i> Comments<Badge color="warning">42</Badge></DropdownItem>
                    <DropdownItem header tag="div" className="text-center"><strong>Settings</strong></DropdownItem>
                    <DropdownItem><i className="fa fa-user"></i> Profile</DropdownItem>
                    <DropdownItem><i className="fa fa-wrench"></i> Settings</DropdownItem>
                    <DropdownItem><i className="fa fa-usd"></i> Payments<Badge color="secondary">42</Badge></DropdownItem>
                    <DropdownItem><i className="fa fa-file"></i> Projects<Badge color="primary">42</Badge></DropdownItem>
                    <DropdownItem divider/>
                    <DropdownItem><i className="fa fa-shield"></i> Lock Account</DropdownItem>
                    <DropdownItem><i className="fa fa-lock"></i><a href="/login"> Logout </a> </DropdownItem>
                </DropdownMenu>
            </Dropdown>
        );
    }

    render() {
        const {...attributes} = this.props;
        return (
            this.dropAccnt()
        );
    }
}

export default HeaderDropdown;