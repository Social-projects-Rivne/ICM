import React, { Component } from 'react';
import axios from 'axios';


class Issue extends Component {

    constructor(props) {
        super(props);
        this.state = {
            issueList: this.load(),
            issues : "Some init"
        };
        this.load = this.load.bind(this);
    }

    load(event){
        axios.get("api/issues")
            .then(
                (response) => {
            this.setState({issues: response.data});
        console.log(response);
    });
    }

    render() {
        return (
            <div className="animated fadeIn">
            {this.state.issues}
    </div>
    )
    }
}

export default Issue;