import React, {Component} from 'react';
import {
    Card, CardBody, CardFooter, CardHeader, Col, FormGroup, Input, Label, Nav, NavItem, NavLink,
    Row, Button, InputGroup, InputGroupButton
} from 'reactstrap';
import EventsContainer from "../Events/EventsContainer";
import IssuesContainer from "../Issues/IssuesContainer";
import PetitionsContainer from "../Petitions/PetitionsContainer";
import UsersContainer from "../Users/UsersContainer";
import axios from 'axios';
import swal from 'sweetalert';
import qs from 'qs';
import DateTime from 'react-datetime';
import 'react-datetime/css/react-datetime.css';

const text = "text";
const user = "user";
const fromDate = "fromDate";
const toDate = "toDate";
const radius = "radius";
const category = "category";
const name = "name";
const email = "email";

class Search extends Component {
    constructor(props) {
        super(props);

        this.state = {
            eventQuery: {
                text: this.props.location.query === undefined ? "" : this.props.location.query,
                user: "",
                fromDate: "",
                toDate: "",
                category: ""
            },
            issueQuery: {
                text: this.props.location.query === undefined ? "" : this.props.location.query,
                user: "",
                fromDate: "",
                toDate: "",
                category: ""
            },
            userQuery: {
                name: this.props.location.query === undefined ? "" : this.props.location.query,
                email: ""
            },
            petitionQuery: {
                text: this.props.location.query === undefined ? "" : this.props.location.query,
                user: "",
                fromDate: "",
                toDate: "",
                category: ""
            },
            events: "",
            issues: "",
            users: "",
            petitions: "",
            currentTab: "users"
        };
        this.handleTabClick = this.handleTabClick.bind(this);

        this.handleEventQueryChange = this.handleEventQueryChange.bind(this);
        this.handleUserQueryChange = this.handleUserQueryChange.bind(this);
        this.handleIssueQueryChange = this.handleIssueQueryChange.bind(this);
        this.handlePetitionQueryChange = this.handlePetitionQueryChange.bind(this);

        this.handleEventFromDateChange = this.handleEventFromDateChange.bind(this);
        this.handleEventToDateChange = this.handleEventToDateChange.bind(this);
        this.handleIssueFromDateChange = this.handleIssueFromDateChange.bind(this);
        this.handleIssueToDateChange = this.handleIssueToDateChange.bind(this);
        this.handlePetitionFromDateChange = this.handlePetitionFromDateChange.bind(this);
        this.handlePetitionToDateChange = this.handlePetitionToDateChange.bind(this);

        this.handleUsersPageChange = this.handleUsersPageChange.bind(this);
        this.handleEventsPageChange = this.handleEventsPageChange.bind(this);
        this.handleIssuesPageChange = this.handleIssuesPageChange.bind(this);
        this.handlePetitionsPageChange = this.handlePetitionsPageChange.bind(this);

        this.handleClear = this.handleClear.bind(this);

        this.handleSearch = this.handleSearch.bind(this);
    }

    componentWillMount() {
        this.makeQueries();
    }

    componentWillReceiveProps(props){
        this.setState(function(prev) {
            return {
                eventQuery: {
                    ...prev.eventQuery,
                    text: props.location.query === undefined ? "" : props.location.query
                },
                userQuery: {
                    ...prev.userQuery,
                    name: props.location.query === undefined ? "" : props.location.query
                },
                issueQuery: {
                    ...prev.issueQuery,
                    text: props.location.query === undefined ? "" : props.location.query
                },
                petitionQuery: {
                    ...prev.petitionQuery,
                    text: props.location.query === undefined ? "" : props.location.query
                }
            }
        }, function() {
            this.makeQueries();
        });
    }

    makeQueries() {
        this.makeQuery("events", this.state.eventQuery);
        this.makeQuery("users", this.state.userQuery);
        this.makeQuery("issues", this.state.issueQuery);
        this.makeQuery("petitions", this.state.petitionQuery);
    }

    makeQuery(type, queryObj) {
        var _this = this;
        axios.get(["/api/search/",type,qs.stringify(queryObj, { addQueryPrefix: true })].join(""))
            .then(function(response) {
                _this.setState({
                    [type]: response.data
                })
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            })
    }

    // Events
    handleEventQueryChange(e) {
        const value = e.target.value;
        const name = e.target.name;
        this.setState(function (prev) {
            return {
                eventQuery: {
                    ...prev.eventQuery,
                    [name]: value
                }
            }
        });
    }

    handleEventFromDateChange(m){
        this.setState(function(prev) {
            return {
                eventQuery: {
                    ...prev.eventQuery,
                    fromDate: m.format("DD/MM/YYYY HH:mm")
                }
            }
        });
    }

    handleEventToDateChange(m){
        this.setState(function(prev) {
            return {
                eventQuery: {
                    ...prev.eventQuery,
                    toDate: m.format("DD/MM/YYYY HH:mm")
                }
            }
        });
    }

    handleEventsPageChange(pageNum) {
        this.setState(function(prev) {
            return {
                eventQuery: {
                    ...prev.eventQuery,
                    page: pageNum
                }
            }
        }, function() {
            this.makeQuery("events", this.state.eventQuery);
        });
    }


    // Issues
    handleIssuesPageChange(pageNum) {
        this.setState(function(prev) {
            return {
                issueQuery: {
                    ...prev.issueQuery,
                    page: pageNum
                }
            }
        }, function() {
            this.makeQuery("issues", this.state.issueQuery);
        });
    }

    handleIssueQueryChange(e) {
        const value = e.target.value;
        const name = e.target.name;
        this.setState(function (prev) {
            return {
                issueQuery: {
                    ...prev.issueQuery,
                    [name]: value
                }
            }
        });
    }

    handleIssueFromDateChange(m){
        this.setState(function(prev) {
            return {
                issueQuery: {
                    ...prev.issueQuery,
                    fromDate: m.format("DD/MM/YYYY HH:mm")
                }
            }
        });
    }

    handleIssueToDateChange(m){
        this.setState(function(prev) {
            return {
                issueQuery: {
                    ...prev.issueQuery,
                    toDate: m.format("DD/MM/YYYY HH:mm")
                }
            }
        });
    }

    //Petitions
    handlePetitionQueryChange(e) {
        const value = e.target.value;
        const name = e.target.name;
        this.setState(function (prev) {
            return {
                petitionQuery: {
                    ...prev.petitionQuery,
                    [name]: value
                }
            }
        });
    }

    handlePetitionFromDateChange(m){
        this.setState(function(prev) {
            return {
                petitionQuery: {
                    ...prev.petitionQuery,
                    fromDate: m.format("DD/MM/YYYY HH:mm")
                }
            }
        });
    }

    handlePetitionToDateChange(m){
        this.setState(function(prev) {
            return {
                petitionQuery: {
                    ...prev.petitionQuery,
                    toDate: m.format("DD/MM/YYYY HH:mm")
                }
            }
        });
    }

    handlePetitionsPageChange(pageNum) {
        this.setState(function(prev) {
            return {
                petitionQuery: {
                    ...prev.petitionQuery,
                    page: pageNum
                }
            }
        }, function() {
            this.makeQuery("petitions", this.state.petitionQuery);
        });
    }

    // Users
    handleUserQueryChange(e) {
        const value = e.target.value;
        const name = e.target.name;
        this.setState(function (prev) {
            return {
                userQuery: {
                    ...prev.userQuery,
                    [name]: value
                }
            }
        });
    }

    handleUsersPageChange(pageNum) {
        this.setState(function(prev) {
            return {
                userQuery: {
                    ...prev.userQuery,
                    page: pageNum
                }
            }
        }, function() {
            this.makeQuery("users", this.state.userQuery);
        });
    }

    // Search
    handleClear(){
        this.setState(function(prev) {
            return {
                eventQuery: {
                    text: "",
                    user: "",
                    fromDate: "",
                    toDate: "",
                    category: ""
                },
                issueQuery: {
                    text: "",
                    user: "",
                    fromDate: "",
                    toDate: "",
                    category: ""
                },
                userQuery: {
                    name: "",
                    email: ""
                },
                petitionQuery: {
                    text: "",
                    user: "",
                    fromDate: "",
                    toDate: "",
                    category: ""
                },
            }
        });
    }

    handleSearch(){
        this.setState(function(prev) {
            return {
                eventQuery: {
                    ...prev.eventQuery,
                    page: 1
                },
                issueQuery: {
                    ...prev.issueQuery,
                    page: 1
                },
                userQuery: {
                    ...prev.userQuery,
                    page: 1
                },
                petitionQuery: {
                    ...prev.petitionQuery,
                    page: 1
                }
            }
        }, function() {
            this.makeQueries();
        });
    }

    handleTabClick(e){
        this.setState({currentTab: e.target.name});
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
                                {this.state.currentTab==="users" ? this.userSearchForm() :
                                    this.state.currentTab==="events" ? this.eventSearchForm() :
                                        this.state.currentTab==="issues" ? this.issueSearchForm() :
                                            this.state.currentTab==="petitions" ? this.petitionSearchForm() :
                                                null}
                            </CardBody>
                            <CardFooter className="text-right">
                                <Button className="btn btn-outline-secondary" onClick={this.handleClear}>
                                    Clear
                                </Button>{' '}
                                <Button className="btn btn-outline-secondary" onClick={this.handleSearch}>
                                    <i className="fa fa-search"/> Search
                                </Button>
                            </CardFooter>
                        </Card>
                    </Col>
                </Row>
                {this.state.currentTab==="users" ? <UsersContainer data={this.state.users} onPageChange={this.handleUsersPageChange}/> :
                    this.state.currentTab==="events" ? <EventsContainer data={this.state.events} onPageChange={this.handleEventsPageChange}/> :
                        this.state.currentTab==="issues" ? <IssuesContainer data={this.state.issues} onPageChange={this.handleIssuesPageChange}/> :
                            this.state.currentTab==="petitions" ? <PetitionsContainer data={this.state.petitions} onPageChange={this.handlePetitionsPageChange}/> :
                                null}
            </div>
        );
    }

    userSearchForm(){
        return (<div>
            <FormGroup row>
                <Col md="2">
                    <Label>By name</Label>
                </Col>
                <Col xs="12" md="10">
                    <Input type="text" value={this.state.userQuery.name} name={name} onChange={this.handleUserQueryChange}/>
                </Col>
            </FormGroup>
            <FormGroup row>
                <Col md="2">
                    <Label>By email</Label>
                </Col>
                <Col xs="12" md="10">
                    <Input type="text" value={this.state.userQuery.email} name={email} onChange={this.handleUserQueryChange}/>
                </Col>
            </FormGroup>
        </div>);
    }

    eventSearchForm(){
        return (<div>
            <FormGroup row>
                <Col md="2">
                    <Label>By text</Label>
                </Col>
                <Col xs="12" md="10">
                    <Input type="text" value={this.state.eventQuery.text} name={text} onChange={this.handleEventQueryChange}/>
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
                              timeFormat={false} onChange={this.handleEventFromDateChange}
                              inputProps={{readOnly: true, className: "form-control form-control-readonly"}} />
                </Col>
                <Col md="2">
                    <Label>To date</Label>
                </Col>
                <Col xs="12" md="4">
                    <DateTime value={this.state.eventQuery.toDate} dateFormat="DD/MM/YYYY"
                              timeFormat={false} onChange={this.handleEventToDateChange}
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

    issueSearchForm(){
        return (<div>
            <FormGroup row>
                <Col md="2">
                    <Label>By text</Label>
                </Col>
                <Col xs="12" md="10">
                    <Input type="text" value={this.state.issueQuery.text} name={text} onChange={this.handleIssueQueryChange}/>
                </Col>
            </FormGroup>
            <FormGroup row>
                <Col md="2">
                    <Label>By user</Label>
                </Col>
                <Col xs="12" md="10">
                    <Input type="text" value={this.state.issueQuery.user} name={user} onChange={this.handleIssueQueryChange}/>
                </Col>
            </FormGroup>
            <FormGroup row>
                <Col md="2">
                    <Label>From date</Label>
                </Col>
                <Col xs="12" md="4">
                    <DateTime value={this.state.issueQuery.fromDate} dateFormat="DD/MM/YYYY"
                              timeFormat={false} onChange={this.handleIssueFromDateChange}
                              inputProps={{readOnly: true, className: "form-control form-control-readonly"}} />
                </Col>
                <Col md="2">
                    <Label>To date</Label>
                </Col>
                <Col xs="12" md="4">
                    <DateTime value={this.state.issueQuery.toDate} dateFormat="DD/MM/YYYY"
                              timeFormat={false} onChange={this.handleIssueToDateChange}
                              inputProps={{readOnly: true, className: "form-control form-control-readonly"}} />
                </Col>
            </FormGroup>
            <FormGroup row>
                <Col md="2">
                    <Label>Category</Label>
                </Col>
                <Col xs="12" md="4">
                    <Input type="select" value={this.state.issueQuery.category} name={category} onChange={this.handleIssueQueryChange}>
                        <option>ANY</option>
                        <option>CAT1</option>
                        <option>CAT2</option>
                        <option>CAT3</option>
                    </Input>
                </Col>
            </FormGroup>
        </div>);
    }

    petitionSearchForm(){
        return (<div>
            <FormGroup row>
                <Col md="2">
                    <Label>By text</Label>
                </Col>
                <Col xs="12" md="10">
                    <Input type="text" value={this.state.petitionQuery.text} name={text} onChange={this.handlePetitionQueryChange}/>
                </Col>
            </FormGroup>
            <FormGroup row>
                <Col md="2">
                    <Label>By user</Label>
                </Col>
                <Col xs="12" md="10">
                    <Input type="text" value={this.state.petitionQuery.user} name={user} onChange={this.handlePetitionQueryChange}/>
                </Col>
            </FormGroup>
            <FormGroup row>
                <Col md="2">
                    <Label>From date</Label>
                </Col>
                <Col xs="12" md="4">
                    <DateTime value={this.state.petitionQuery.fromDate} dateFormat="DD/MM/YYYY"
                              timeFormat={false} onChange={this.handlePetitionFromDateChange}
                              inputProps={{readOnly: true, className: "form-control form-control-readonly"}} />
                </Col>
                <Col md="2">
                    <Label>To date</Label>
                </Col>
                <Col xs="12" md="4">
                    <DateTime value={this.state.petitionQuery.toDate} dateFormat="DD/MM/YYYY"
                              timeFormat={false} onChange={this.handlePetitionToDateChange}
                              inputProps={{readOnly: true, className: "form-control form-control-readonly"}} />
                </Col>
            </FormGroup>
            <FormGroup row>
                <Col md="2">
                    <Label>Category</Label>
                </Col>
                <Col xs="12" md="4">
                    <Input type="select" value={this.state.petitionQuery.category} name={category} onChange={this.handlePetitionQueryChange}>
                        <option>ANY</option>
                        <option>CAT1</option>
                        <option>CAT2</option>
                        <option>CAT3</option>
                    </Input>
                </Col>
            </FormGroup>
        </div>);
    }
}

export default Search

