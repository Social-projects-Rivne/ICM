import React, {Component} from 'react';
import {Badge, DropdownItem, DropdownMenu, DropdownToggle, Dropdown, NavLink} from 'reactstrap';
import {Link} from "react-router-dom";

class ClientHeaderDropdown extends Component {

    constructor(props) {
        super(props);
        this.state = {
            dropdownOpen: false,
            userName: "user"
        };

        this.toggle = this.toggle.bind(this);
        ClientHeaderDropdown.handleLogoutClick = ClientHeaderDropdown.handleLogoutClick.bind(this);
    }

    toggle() {
        this.setState({
            dropdownOpen: !this.state.dropdownOpen
        });
    }

    render() {
        return (
            <Dropdown nav isOpen={this.state.dropdownOpen} toggle={this.toggle}>
                <DropdownToggle nav>
                    <img src={'https://www.shareicon.net/data/128x128/2016/09/07/827169_man_512x512.png'} className="img-avatar " alt="admin@bootstrapmaster.com"/>
                </DropdownToggle>

                <DropdownMenu right>
                    <DropdownItem header tag="div" className="text-center"><strong>Account</strong></DropdownItem>

                    {ClientHeaderDropdown.renderAdminDropdownItem(this.props.userAuthorities)}
                    <DropdownItem><i className="fa fa-user"/> Profile</DropdownItem>
                    <DropdownItem tag={Link} to="/settings/profile/"><i className="fa fa-wrench"/> Settings</DropdownItem>
                    <DropdownItem divider/>
                    <DropdownItem onClick={ClientHeaderDropdown.handleLogoutClick}><i className="fa fa-lock"/> Logout </DropdownItem>
                </DropdownMenu>
            </Dropdown>
        );
    }

    static renderAdminDropdownItem(authorities){
        if (authorities != null)
            if (authorities.some(function(auth){return auth === "ADMIN" || auth === "MODERATOR"}))
                return <DropdownItem tag={Link} to="/admin">><i className="fa fa-users"/> Admin Panel</DropdownItem>
    }

    static handleLogoutClick(){
        location.href = "/logout";
    }
}

export default ClientHeaderDropdown;
