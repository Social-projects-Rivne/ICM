import React, {Component} from 'react';
import {Link, Switch, Route, Redirect} from 'react-router-dom';
import {Container} from 'reactstrap';
import Header from '../../components/Header/';
import Sidebar from '../../components/Sidebar/';
import Breadcrumb from '../../components/Breadcrumb/';
import Footer from '../../components/Footer/';

import Dashboard from '../../views/Dashboard/';

import Petitions from "../../views/Petitions/";
import PetitionEdit from "../../views/Petitions/PetitionEdit";
import PetitionAdd from "../../views/Petitions/PetitionAdd";
import Issues from "../../views/Issues/";
import IssueEdit from "../../views/Issues/IssueEdit";
import Users from "../../views/Users/";
import EditUsers from "../../views/Users/EditUsers";
import Events from "../../views/Events/Events"
import EventEdit from "../../views/Events/EventEdit";
import Login from "../../views/Login/Login";
import Page404 from "../../views/Page404/Page404";

import axios from "axios/index";

class Full extends Component {

    constructor(props){
        super(props);

        this.state = {
          userAuthorities: null
        };
    }


    componentDidMount() {
        const _this = this;
        axios.get("/api/authority")
            .then(function (response) {
                _this.setState({userAuthorities: response.data});
            });
    }

  render() {
    return (
      <div className="app">
        <Header userAuthorities={this.state.userAuthorities}/>
        <div className="app-body">
          <Sidebar {...this.props}/>
          <main className="main">
            <Breadcrumb />
            <Container fluid>
              <Switch>
                <Route exact path="/admin/" name="Dashboard" component={Dashboard}/>
                <Route exact path="/admin/events" name="Events" component={Events}/>
                <Route path="/admin/events/:id/edit" name="Events" component={EventEdit}/>
                <Route exact path="/admin/petitions" name="Petitions" component={Petitions}/>
                <Route path="/admin/petitions/:id/edit" name="Petitions" component={PetitionEdit}/>
                <Route path="/admin/petitions/add" name="PetitionAdd" component={PetitionAdd}/>
                <Route exact path="/admin/issues" name="Issues" component={Issues}/>
                <Route path="/admin/issues/:id/edit" name="Issues" component={IssueEdit}/>
                <Route exact path="/admin/users" name="Users" component={Users}/>
                <Route path="/admin/users/:id/edit" name="EditUsers" component = {EditUsers}/>
                <Route path="*" component={Page404} />
              </Switch>
            </Container>
          </main>
        </div>
        <Footer />
      </div>
    );
  }
}


export default Full;