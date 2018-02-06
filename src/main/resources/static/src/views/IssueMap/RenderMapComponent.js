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
        <div className="map-responsive">
            <div className= "filter-map">
            <div className="collapse navbar-collapse" id="navbarsExampleDefault">
                <ul className= "navbar-nav mr-auto">
                    <li className="nav-item">
                        <input id="checkBox" type="checkbox"/>
                            Issues
                    </li>
                    <li className="nav-item">
                        <input id="checkBox" type="checkbox"/>
                            Events
                    </li>
                    <li className="nav-item dropdown">
                        <a className="nav-link dropdown-toggle" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">Dropdown</a>
                        <div className="dropdown-menu" aria-labelledby="dropdown01">
                            <input id="checkBox" type="checkbox"/>
                                Cat1!
                        </div>
                    </li>

                </ul>
            </div>
            </div>

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
   )
  }
}




export default RenderMapComponent;