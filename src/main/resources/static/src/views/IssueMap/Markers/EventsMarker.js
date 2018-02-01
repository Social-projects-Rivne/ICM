import React, { Component } from 'react';
import {withGoogleMap, GoogleMap, withScriptjs, Marker} from "react-google-maps"
import axios from 'axios';
import swal from 'sweetalert';

class EventsMarker extends Component{

    constructor(props) {
        super(props);
        }







    render(){
    console.log("ID :", this.props.id);
        return(
        <h1>Hello!</h1>
        )

    }


}

export default EventsMarker;