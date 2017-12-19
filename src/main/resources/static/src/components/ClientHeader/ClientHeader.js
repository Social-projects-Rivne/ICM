import React, {Component} from 'react';
import {Nav, NavbarBrand, NavItem, NavLink, NavbarToggler,} from 'reactstrap';
import HeaderDropdown from './ClientHeaderDropdown';

export default class Header extends Component {

    render() {
        return(
            <header className="app-header navbar">
                <NavbarBrand href="#"></NavbarBrand>


                <NavLink href="/">Issues</NavLink>
                <NavLink href="/">Events</NavLink>
                <NavLink href="/">Petitions</NavLink>

                <Nav className="ml-auto" navbar>
                    <HeaderDropdown />
                </Nav>
            </header>
        )
    }
}