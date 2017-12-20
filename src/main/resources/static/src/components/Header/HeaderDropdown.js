import React, {Component} from 'react';
import axios from 'axios';

import {DropdownItem,DropdownMenu,DropdownToggle,Dropdown} from 'reactstrap';

class HeaderDropdown extends Component {

    constructor(props) {
        super(props);

        this.toggle = this.toggle.bind(this);
        HeaderDropdown.handleLogoutClick = HeaderDropdown.handleLogoutClick.bind(this);
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

    static handleLogoutClick(){
        location.href = "/logout";
    }

    dropAccount() {
        return (
            <Dropdown nav isOpen={this.state.dropdownOpen} toggle={this.toggle}>
                <DropdownToggle nav>
                    <img id='admin-logo' src="" className="img-avatar" />
                </DropdownToggle>
                <DropdownMenu right>
                    <DropdownItem header tag="div" className="text-center"><strong>Account</strong></DropdownItem>
                    <DropdownItem onClick={HeaderDropdown.handleLogoutClick}><i className="fa fa-lock"/> Logout </DropdownItem>
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