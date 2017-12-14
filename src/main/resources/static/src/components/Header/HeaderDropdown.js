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
        this.handleLogoutClick = this.handleLogoutClick.bind(this);
        this.state = {
            dropdownOpen: false,
        };
    }

    toggle() {
        this.setState({
            dropdownOpen: !this.state.dropdownOpen
        });
    }

    componentDidMount(){
        axios.get('/api/admin_name')
            .then(function (response) {
                document.getElementById('admin-logo').alt = response.data
            });
    }

    handleLogoutClick(){
        location.href = "/logout";
    }

    dropAccount() {
        return (
            <Dropdown nav isOpen={this.state.dropdownOpen} toggle={this.toggle}>
                <DropdownToggle nav>
                    <img src={''} id='admin-logo' className="img-avatar" />
                </DropdownToggle>
                <DropdownMenu right>
                    <DropdownItem header tag="div" className="text-center"><strong>Account</strong></DropdownItem>
                    <DropdownItem onClick={this.handleLogoutClick}><i className="fa fa-lock"/> Logout </DropdownItem>
                </DropdownMenu>
            </Dropdown>
        );
    }

    render() {
        const {...attributes} = this.props;
        return (
            this.dropAccount()
        );
    }
}

export default HeaderDropdown;