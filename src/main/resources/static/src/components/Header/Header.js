import React, {Component} from 'react';
import {Nav, NavbarBrand, NavbarToggler,} from 'reactstrap';
import HeaderDropdown from './HeaderDropdown';

class Header extends Component {

    constructor(props) {
        super(props);
    }

    sidebarToggle(e) {
        e.preventDefault();
        document.body.classList.toggle('sidebar-hidden');
    }


    mobileSidebarToggle(e) {
        e.preventDefault();
        document.body.classList.toggle('sidebar-mobile-show');
    }

    render() {
        return (
            <header className="app-header navbar">
                <NavbarToggler className="d-lg-none" onClick={this.mobileSidebarToggle}>
                    <span className="navbar-toggler-icon"></span>
                </NavbarToggler>
                <NavbarBrand href="#"></NavbarBrand>
                <NavbarToggler className="d-md-down-none" onClick={this.sidebarToggle}>
                    <span className="navbar-toggler-icon"></span>
                </NavbarToggler>

                <Nav className="ml-auto" navbar>
                    <HeaderDropdown/>
                </Nav>
            </header>
        );
    }

}

export default Header;