import React, { Component } from 'react';
import {withGoogleMap, GoogleMap, withScriptjs, Marker} from "react-google-maps"
import axios from 'axios';
import swal from 'sweetalert';

class IssueMarker extends Component{

    constructor(props){
        super(props);
    }

    render(){
        console.log("Working!");
        return(
            <div className = "issue_marker">
                <h1>Hello issues!</h1>

            </div>
        )

    }


}

export default IssueMarker;