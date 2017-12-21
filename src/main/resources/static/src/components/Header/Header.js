import React, {Component} from 'react';
import {Button, Input, InputGroup, InputGroupButton, Nav, NavbarBrand, NavbarToggler, NavItem} from 'reactstrap';
import HeaderDropdown from './HeaderDropdown';
import {Link} from 'react-router-dom';

class Header extends Component {

    constructor(props) {
        super(props);

        this.state = {
            keywords: ""
        };

        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(e){
        this.setState({keywords: e.target.value});
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
                    <NavItem >
                        <InputGroup>
                            <Input value={this.state.keywords} type="text" name="search" placeholder="Search..."
                                   onChange={this.handleChange}/>
                            <InputGroupButton>
                                <Link to={{pathname: "/search", query: this.state.keywords}}>
                                    <Button className="btn btn-outline-secondary fa fa-search"></Button>
                                </Link>
                            </InputGroupButton>
                        </InputGroup>
                    </NavItem>
                </Nav>
                <Nav className="ml-auto" navbar>
                    <HeaderDropdown userAuthorities={this.props.userAuthorities}/>
                </Nav>
            </header>
        );
    }
}

export default Header;