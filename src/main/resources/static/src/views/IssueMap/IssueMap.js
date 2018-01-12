import React, { Component } from 'react';
import { withScriptjs, withGoogleMap, GoogleMap, Marker, InfoWindow } from "react-google-maps"
import axios from 'axios';
import swal from 'sweetalert';
import Map from './Map';
import DescriptionIssue from './DescriptionIssue'

class IssueMap extends Component {

    constructor(props){
                super(props);


                this.state = {
                    centlat:50.619900,
                    centlng: 26.251617,
                    zoom:5,
                    defaultmaxzoom:10,
                    issues: [],
                    point: false
                };
            }

            componentWillMount() {

                var _this = this;
                axios.get("/api/issues/map")
                    .then(function(response) {
                        _this.setState({
                            issues: response.data,
                            isOpen: false
                        });
                    })
                    .catch(function (error) {
                        swal({title: "Something went wrong!", text: error, icon: "error"});
                    });

            }



   render() {



   return(
   <div>
        <div className="map-responsive">

            <Map
                googleMapURL="https://maps.googleapis.com/maps/api/js?key=AIzaSyAMDIyzcpoHdvK8MLCUhvrqZDyllGiyrnM &callback=initMap"
                loadingElement={<div style={{ height: `100%` }} />}
                containerElement={<div style={{ height: 100 + '%' }} />}
                mapElement={<div style={{ height: `100%` }} />}
                kl = {2}
                zoom = {this.state.zoom}
                defaultmaxzoom = {this.state.defaultmaxzoom}
                centlng = {this.state.centlng}
                centlat = {this.state.centlat}
                issues = {this.state.issues}
            />
        </div>
   </div>
   )
  }
}


export default IssueMap;