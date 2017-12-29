import React, { Component } from 'react';
import { withScriptjs, withGoogleMap, GoogleMap, Marker, InfoWindow } from "react-google-maps"
import axios from 'axios';
import swal from 'sweetalert';
import Map from './Map';

class IssueMap extends Component {
   render() {

   return(
        <div className="map-responsive">

            <Map
                googleMapURL="https://maps.googleapis.com/maps/api/js?key=AIzaSyAMDIyzcpoHdvK8MLCUhvrqZDyllGiyrnM &callback=initMap"
                loadingElement={<div style={{ height: `100%` }} />}
                containerElement={<div style={{ height: 100 + '%' }} />}
                mapElement={<div style={{ height: `100%` }} />}

            />
        </div>
   )
  }
}


export default IssueMap;