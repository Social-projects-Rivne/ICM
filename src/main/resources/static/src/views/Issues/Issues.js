import React, {Component} from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import IssuesContainer from "./IssuesContainer";

class Issues extends Component {
    constructor(props) {
        super(props);

        this.state = {
            issues: []
        };
    }

    componentWillMount() {
        var _this = this;
        axios.get("/api/issues")
            .then(function(response) {
                _this.setState({
                    issues: response.data
                });
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });
    }

    render() {
        return (
            <IssuesContainer data={this.state.issues}/>
        )
    }
}

export default Issues;