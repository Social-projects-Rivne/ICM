import React, { Component } from 'react';
import {withGoogleMap, GoogleMap, withScriptjs, Marker} from "react-google-maps"
import axios from 'axios';
import swal from 'sweetalert';
class Map extends Component {

   render() {
        return (
            <GoogleMap
                defaultZoom={this.props.zoom}
                defaultMaxZoom = {this.props.defaultmaxzoom}
                defaultCenter={{ lat: this.props.centlat, lng: this.props.centlng }}
              >
                {this.props.issues.map(issues => (
                              console.log(this.props.issues.latitude + " / " + this.props.issues.longitude),
                              <Marker
                                  key={this.props.issues.id}
                                  position={{ lat: this.props.issues.latitude, lng: this.props.issues.longitude}}
                                  onClick = {console.log(this.props.kl)}
                              >
                               </Marker>
                ))}
              </GoogleMap>


        )
   }
}


export default withScriptjs(withGoogleMap(Map));