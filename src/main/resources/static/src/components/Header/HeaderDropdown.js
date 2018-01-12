import React, {Component} from 'react';
import axios from 'axios';
import {Link} from 'react-router-dom';

import {Dropdown, DropdownItem, DropdownMenu, DropdownToggle} from 'reactstrap';

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
        axios.get('/api/userName')
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
                    <img id='admin-logo' src={'https://www.shareicon.net/data/128x128/2016/09/07/827169_man_512x512.png'} className="img-avatar" />
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
        if (authorities !== null) {
            if (authorities.some(function (auth) {
                    return auth === "USER"
                })) {
                return <DropdownItem tag={Link} to="/"><i className="fa fa-users"/> User Panel</DropdownItem>
            }
        }
    }
}

export default HeaderDropdown;