import React, { Component } from 'react';
import {withGoogleMap, GoogleMap, withScriptjs, Marker} from "react-google-maps"
import axios from 'axios';
import swal from 'sweetalert';

class DescriptionIssue extends Component {
    constructor(props){
    super(props);
    this.state = {
        id : this.props.id

    }

    }


   render() {
   console.log("id : " + this.state.id)
        return (
            <div className="description">
                {this.state.id}
            </div>

        )
   }
}


export default DescriptionIssue;