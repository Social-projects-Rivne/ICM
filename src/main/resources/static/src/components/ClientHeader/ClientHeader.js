import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import {Nav, NavbarBrand, NavItem, NavLink, NavbarToggler,} from 'reactstrap';
import ClientHeaderDropdown from './ClientHeaderDropdown';

export default class ClientHeader extends Component {

    constructor(props){
        super(props);

        this.state = {
            isAnonymousUser: true
        };
    }

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
            } else {
                return <Nav><NavItem className="d-md-down-none"><Link to="/login" className="nav-link">Log in</Link></NavItem><NavItem className="d-md-down-none"><Link to="/registration" className="nav-link">Sign up</Link></NavItem></Nav>
            }
        }
    }
}
