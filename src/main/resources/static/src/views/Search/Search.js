import React, {Component} from 'react';
import {Card, CardBody, CardHeader, Col, FormGroup, Input, Label, Nav, NavItem, NavLink, Row} from 'reactstrap';
import EventsContainer from "../Events/EventsContainer";
import axios from 'axios';
import swal from 'sweetalert';

const title = "title";
const description = "description";
const owner = "user";
const fromDate = "fromDate";
const toDate = "toDate";
const radius = "radius";
const category = "category";

class Search extends Component {
    constructor(props) {
        super(props);

        this.state = {
            query: "",
            currentTab: "users",
            events: [],
            users: [],
            petitions: [],
            issues: [],
            event: "",
            issue: "",
            user: "",
            petition: ""
        };
        this.handleTabClick = this.handleTabClick.bind(this);
        this.handleSearchEventChange = this.handleSearchEventChange.bind(this);
    }

    componentWillMount(){
        var _this = this;
        axios.get("/api/search/events?"+this.state.query)
            .then(function(response) {
                _this.setState({
                    events: response.data
                })
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            })
    }

    handleSearchEventChange(e) {
        const value = e.target.value;
        const name = e.target.name;
        this.setState(function(prev) {
           return {
               event: {
                   ...prev.event,
                   [name]: value
               }
           }
        });
    }

    handleTabClick(e){
        this.setState({currentTab: e.target.name});
    }

    eventSearchForm(){
        return (<div>
            <FormGroup row>
                <Col md="2">
                    <Label>By title</Label>
                </Col>
                <Col xs="12" md="10">
                    <Input type="text" value={this.state.event.title} name={title} onChange={this.handleSearchEventChange}/>
                </Col>
            </FormGroup>
            <FormGroup row>
                <Col md="2">
                    <Label>By description</Label>
                </Col>
                <Col xs="12" md="10">
                    <Input type="text" value={this.state.event.description} name={description} onChange={this.handleSearchEventChange}/>
                </Col>
            </FormGroup>
            <FormGroup row>
                <Col md="2">
                    <Label>By user</Label>
                </Col>
                <Col xs="12" md="10">
                    <Input type="text" value={this.state.event.owner} name={owner} onChange={this.handleSearchEventChange}/>
                </Col>
            </FormGroup>
            <FormGroup row>
                <Col md="2">
                    <Label>From date</Label>
                </Col>
                <Col xs="12" md="4">
                    <Input type="text" value={this.state.event.fromDate} name={fromDate} onChange={this.handleSearchEventChange}/>
                </Col>
                <Col md="2">
                    <Label>To date</Label>
                </Col>
                <Col xs="12" md="4">
                    <Input type="text" value={this.state.event.toDate} name={toDate} onChange={this.handleSearchEventChange}/>
                </Col>
            </FormGroup>
            <FormGroup row>
                <Col md="2">
                    <Label>Category</Label>
                </Col>
                <Col xs="12" md="4">
                    <Input type="select" value={this.state.event.category} name={category} onChange={this.handleSearchEventChange}>
                        <option>CAT1</option>
                        <option>CAT2</option>
                        <option>CAT3</option>
                    </Input>
                </Col>
            </FormGroup>
        </div>);
    }

    render() {
        return(
            <div className="animated fadeIn">
                <Row>
                    <Col xs="12" lg="12">
                        <Card>
                            <CardHeader>Search</CardHeader>
                            <CardBody>
                                <Nav tabs>
                                    <NavItem>
                                        {(this.state.currentTab==="users") ?
                                            <NavLink active>Users</NavLink> :
                                            <NavLink name="users" onClick={this.handleTabClick}>Users</NavLink>}
                                    </NavItem>
                                    <NavItem>
                                        {(this.state.currentTab==="events") ?
                                            <NavLink active>Events</NavLink> :
                                            <NavLink name="events" onClick={this.handleTabClick}>Events</NavLink>}
                                    </NavItem>
                                    <NavItem>
                                        {(this.state.currentTab==="issues") ?
                                            <NavLink active>Issues</NavLink> :
                                            <NavLink name="issues" onClick={this.handleTabClick}>Issues</NavLink>}
                                    </NavItem>
                                    <NavItem>
                                        {(this.state.currentTab==="petitions") ?
                                            <NavLink active>Petitions</NavLink> :
                                            <NavLink name="petitions" onClick={this.handleTabClick}>Petitions</NavLink>}
                                    </NavItem>
                                </Nav>
                                <br/>
                                <FormGroup row>
                                    <Col md="2">
                                        <Label>Search parameters:</Label>
                                    </Col>
                                </FormGroup>
                                {this.eventSearchForm()}
                            </CardBody>
                        </Card>
                    </Col>
                </Row>
                {this.state.currentTab==="users" ? null :
                    this.state.currentTab==="events" ? <EventsContainer data={this.state.events}/> :
                        this.state.currentTab==="issues" ? null :
                            this.state.currentTab==="petitions" ? null :
                                null}
            </div>
        );
    }
}

export default Search

