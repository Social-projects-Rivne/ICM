import React, {Component} from 'react';
import {
    Card, CardBody, CardFooter, CardHeader, Col, FormGroup, Input, Label, Nav, NavItem, NavLink,
    Row, Button
} from 'reactstrap';
import EventsContainer from "../Events/EventsContainer";
import axios from 'axios';
import swal from 'sweetalert';
import qs from 'qs';
import DateTime from 'react-datetime';
import 'react-datetime/css/react-datetime.css';

const title = "title";
const description = "description";
const user = "user";
const fromDate = "fromDate";
const toDate = "toDate";
const radius = "radius";
const category = "category";

class Search extends Component {
    constructor(props) {
        super(props);

        this.state = {
            eventQuery: {
                title: "",
                description: ""
            },
            /*issueQuery: {
                title: "",
                description: ""
            },
            userQuery: {
                firstName: this.props.location.query,
                lastName: this.props.location.query
            },
            petitionQuery: {
                title: this.props.location.query,
                description: this.props.location.query
            },*/
            currentTab: "users",
            events: [],
            users: [],
            petitions: [],
            issues: []
        };
        this.handleTabClick = this.handleTabClick.bind(this);
        this.handleEventQueryChange = this.handleEventQueryChange.bind(this);
        this.handleFromDateChange = this.handleFromDateChange.bind(this);
        this.handleToDateChange = this.handleToDateChange.bind(this);
        this.handleSearch = this.handleSearch.bind(this);
    }

    componentWillMount(){
        this.makeEventQuery();
        //TODO other queries
    }

    makeEventQuery() {
        console.log(["/api/search/events?", qs.stringify(this.state.eventQuery)].join(""));
        var _this = this;
        axios.get(["/api/search/events?", qs.stringify(this.state.eventQuery)].join(""))
            .then(function(response) {
                _this.setState({
                    events: response.data
                })
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            })
    }

    handleEventQueryChange(e) {
        const value = e.target.value;
        const name = e.target.name;
        if(name!=="category" && value!=="ANY") {
            this.setState(function (prev) {
                return {
                    eventQuery: {
                        ...prev.eventQuery,
                        [name]: value
                    }
                }
            });
        }
    }

    handleFromDateChange(m){
        this.setState(function(prev) {
            return {
                eventQuery: {
                    ...prev.eventQuery,
                    fromDate: m.format("DD/MM/YYYY HH:mm")
                }
            }
        });
    }

    handleToDateChange(m){
        this.setState(function(prev) {
            return {
                eventQuery: {
                    ...prev.eventQuery,
                    toDate: m.format("DD/MM/YYYY HH:mm")
                }
            }
        });
    }

    handleSearch(){
        this.makeEventQuery();
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
                    <Input type="text" value={this.state.eventQuery.title} name={title} onChange={this.handleEventQueryChange}/>
                </Col>
            </FormGroup>
            <FormGroup row>
                <Col md="2">
                    <Label>By description</Label>
                </Col>
                <Col xs="12" md="10">
                    <Input type="text" value={this.state.eventQuery.description} name={description} onChange={this.handleEventQueryChange}/>
                </Col>
            </FormGroup>
            <FormGroup row>
                <Col md="2">
                    <Label>By user</Label>
                </Col>
                <Col xs="12" md="10">
                    <Input type="text" value={this.state.eventQuery.user} name={user} onChange={this.handleEventQueryChange}/>
                </Col>
            </FormGroup>
            <FormGroup row>
                <Col md="2">
                    <Label>From date</Label>
                </Col>
                <Col xs="12" md="4">
                    <DateTime value={this.state.eventQuery.fromDate} dateFormat="DD/MM/YYYY"
                              timeFormat="HH:mm" onChange={this.handleFromDateChange}
                              inputProps={{readOnly: true, className: "form-control form-control-readonly"}} />
                </Col>
                <Col md="2">
                    <Label>To date</Label>
                </Col>
                <Col xs="12" md="4">
                    <DateTime value={this.state.eventQuery.toDate} dateFormat="DD/MM/YYYY"
                              timeFormat="HH:mm" onChange={this.handleToDateChange}
                              inputProps={{readOnly: true, className: "form-control form-control-readonly"}} />
                </Col>
            </FormGroup>
            <FormGroup row>
                <Col md="2">
                    <Label>Category</Label>
                </Col>
                <Col xs="12" md="4">
                    <Input type="select" value={this.state.eventQuery.category} name={category} onChange={this.handleEventQueryChange}>
                        <option>ANY</option>
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
                            <CardFooter className="text-right">
                                <Button className="btn btn-outline-secondary" onClick={this.handleSearch}>
                                    <i className="fa fa-search"/> Search
                                </Button>
                            </CardFooter>
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

