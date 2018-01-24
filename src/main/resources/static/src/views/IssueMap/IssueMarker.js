import React, { Component } from 'react';
import {withGoogleMap, GoogleMap, withScriptjs, Marker} from "react-google-maps"
import axios from 'axios';
import swal from 'sweetalert';

class IssueMarker extends Component{

    constructor(props) {
        super(props);

        this.state = {
            response: {
                id: "",
                title: "",
                description: "",
                initialDate: "",
                category: ""
            },
            initialDate: true
        };

    }


    componentWillMount() {
        var _this = this;
        axios.get("/api/issues/" + this.props.ID)
            .then(function(response) {
                _this.setState({
                    response: response.data
                })
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });
}

    render(){
        console.log("issues1: ", this.state.response);
        return(
            <div className = "issue_marker" >
             <div className="card">
               <div className="card-header">
                    {this.props.ID}
               </div>
               <div className="card-body">
                 <h4 className="card-title">Special title treatment</h4>
                 <p className="card-text">With supporting text below as a natural lead-in to additional content.</p>
                 <a href="#" className="btn btn-primary">Go somewhere</a>
               </div>
             </div>
            </div>
        )

    }


}

export default IssueMarker;