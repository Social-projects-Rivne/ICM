import React, { Component } from 'react';
import { withScriptjs, withGoogleMap, GoogleMap, Marker, InfoWindow } from "react-google-maps"
import axios from 'axios';
import swal from 'sweetalert';
import Map from './Map';

class descriptionIssues extends Component {

    constructor(props){
                super(props);

                console.log("Its working!");
            }
    componentWillMount() {
                    console.log("Its working!");

                }

   render() {

   return(
        <div className="map-responsive" >
            Hello!
        </div>
   )
  }
}


export default descriptionIssues;