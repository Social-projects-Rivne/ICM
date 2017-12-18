import React, {Component} from 'react';
import {Link, Switch, Route, Redirect} from 'react-router-dom';
import IssueMap from "../../views/Map/IssueMap";


class Authorization extends Component{
    render() {
        return (
            <div className="app">
                <Route path="/maps" name="IssueMap" component={IssueMap}/>
            </div>
        );
    }
}

export default Authorization;
