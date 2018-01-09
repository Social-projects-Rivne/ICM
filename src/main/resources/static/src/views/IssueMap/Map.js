import React, { Component } from 'react';
import {withGoogleMap, GoogleMap, withScriptjs, Marker} from "react-google-maps"
import axios from 'axios';
import swal from 'sweetalert';
class Map extends Component {

    constructor(props){
            super(props);


            this.state = {
                centlat:50.619900,
                centlng: 26.251617,
                zoom:5,
                issues: []
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
        return (
            <GoogleMap
                defaultZoom={this.state.zoom}
                defaultMaxZoom = {8}
                defaultCenter={{ lat: this.state.centlat, lng: this.state.centlng }}
              >
                {this.state.issues.map(issues => (
                              <Marker
                                  key={issues.id}
                                  position={{ lat: issues.latitude, lng: issues.longitude }}z
                              >
                               </Marker>
                ))}
              </GoogleMap>

        )
   }
}


export default withScriptjs(withGoogleMap(Map));