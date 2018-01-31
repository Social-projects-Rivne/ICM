import React, { Component } from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import Map from './Map';

class RenderMapComponent extends Component {

    constructor(props){
                super(props);


                this.state = {
                    centlat:50.619900,
                    centlng: 26.251617,
                    zoom:7,
                    defaultmaxzoom:10,
                    issues: [],
                    events: []
                };
            }

            componentWillMount() {

                const _this = this;
                axios.get("/api/issues/map")
                    .then(function(response) {
                        _this.setState({
                            issues: response.data,
                        });
                    })

                axios.get("/api/events/map")
                    .then(function(response){
                        _this.setState({
                            events: response.data
                        })
                    })


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
                zoom = {this.state.zoom}
                defaultmaxzoom = {this.state.defaultmaxzoom}
                centlng = {this.state.centlng}
                centlat = {this.state.centlat}
                issues = {this.state.issues}
                events = {this.state.events}
            />
        </div>
   </div>
   )
  }
}

export default RenderMapComponent;