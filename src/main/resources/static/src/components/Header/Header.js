import React, {Component} from 'react';
import {Input, Nav, NavbarBrand, NavbarToggler, NavItem, NavLink, InputGroupButton, InputGroup, Button} from 'reactstrap';
import HeaderDropdown from './HeaderDropdown';
import {Link} from 'react-router-dom';

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
                <Nav navbar>
                    <NavItem>
                        <InputGroup>
                            <Input type="text" name="search" placeholder="Search..."/>
                            <InputGroupButton>
                                <Button className="btn btn-outline-secondary fa fa-search"></Button>
                                <Link to="/search">
                                    <Button className="btn btn-outline-secondary fa fa-sliders"></Button>
                                </Link>
                            </InputGroupButton>
                        </InputGroup>
                    </NavItem>
                </Nav>
                <Nav className="ml-auto" navbar>
                    <HeaderDropdown/>
                </Nav>
            </header>
        );
    }
}

export default Header;