import React, {Component} from 'react';
import {Nav, NavbarBrand, NavItem, NavLink, NavbarToggler,} from 'reactstrap';
import ClientHeaderDropdown from './ClientHeaderDropdown';

export default class ClientHeader extends Component {

    render() {
        return(
            <header className="app-header navbar">
                <NavbarBrand href="#"></NavbarBrand>

                <NavLink href="/">Issues</NavLink>
                <NavLink href="/">Events</NavLink>
                <NavLink href="/">Petitions</NavLink>

                <Nav className="ml-auto" navbar>
                    {ClientHeader.renderHeaderDropdown(this.props.userAuthorities)}
                </Nav>
            </header>
        )
    }

    static renderHeaderDropdown(authorities){
        if(authorities != null){
            if (!authorities.some(function(auth){return auth.authority === "ROLE_ANONYMOUS"})){
                return <ClientHeaderDropdown userAuthorities={authorities}/>
            }
        }
    }
}
