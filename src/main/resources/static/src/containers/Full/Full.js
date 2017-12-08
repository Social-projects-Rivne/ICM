import React, {Component} from 'react';
import {Link, Switch, Route, Redirect} from 'react-router-dom';
import {Container} from 'reactstrap';
import Header from '../../components/Header/';
import Sidebar from '../../components/Sidebar/';
import Breadcrumb from '../../components/Breadcrumb/';
import Footer from '../../components/Footer/';

import Dashboard from '../../views/Dashboard/';
import Events from "../../views/Events/";
import Petitions from "../../views/Petitions/";
import Issues from "../../views/Issues/";
import IssueEdit from "../../views/Issues/IssueEdit";
import Users from "../../views/Users/";

class Full extends Component {
  render() {
    return (
      <div className="app">
        <Header />
        <div className="app-body">
          <Sidebar {...this.props}/>
          <main className="main">
            <Breadcrumb />
            <Container fluid>
              <Switch>
                <Route path="/dashboard" name="Dashboard" component={Dashboard}/>
                <Route path="/events" name="Events" component={Events}/>
                <Route path="/petitions" name="Petitions" component={Petitions}/>
                <Route exact path="/issues" name="Issues" component={Issues}/>
                <Route path="/issues/:id/edit" name="Issues" component={IssueEdit}/>
                <Route path="/users" name="Users" component={Users}/>
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
