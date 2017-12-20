import React, {Component} from 'react';
import axios from 'axios';

import {DropdownItem,DropdownMenu,DropdownToggle,Dropdown} from 'reactstrap';
import {Link} from "react-router-dom";

class HeaderDropdown extends Component {

    constructor(props) {
        super(props);

        this.toggle = this.toggle.bind(this);
        HeaderDropdown.handleLogoutClick = HeaderDropdown.handleLogoutClick.bind(this);
        this.state = {
            dropdownOpen: false,
            userAuthorities : null
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

    render() {
        return (
            <Dropdown nav isOpen={this.state.dropdownOpen} toggle={this.toggle}>
                <DropdownToggle nav>
                    <img id='admin-logo' src="" className="img-avatar" />
                </DropdownToggle>
                <DropdownMenu right>
                    <DropdownItem header tag="div" className="text-center"><strong>Account</strong></DropdownItem>
                    {HeaderDropdown.renderUserPanelDropdownItem(this.props.userAuthorities)}
                    <DropdownItem onClick={HeaderDropdown.handleLogoutClick}><i className="fa fa-lock"/> Logout </DropdownItem>
                </DropdownMenu>
            </Dropdown>
        );
    }

    static renderUserPanelDropdownItem(authorities) {
        if (authorities != null)
            if (authorities.some(function(auth){return auth === "USER"}))
                return <DropdownItem><Link to="/"><i className="fa fa-users"/> User Panel</Link></DropdownItem>
        console.log("aith", authorities);
    }
}

export default HeaderDropdown;