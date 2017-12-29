import React, { Component } from 'react';
import { withScriptjs, withGoogleMap, GoogleMap, Marker, InfoWindow } from "react-google-maps"
import axios from 'axios';
import swal from 'sweetalert';
import Map from './Map';

class IssueMap extends Component {
    constructor(props){
        super(props);


        this.state = {
            issues: [],
            width: props.width,
            height: props.height
        };

    }

    componentWillMount() {
        this.setState({height: window.innerHeight + 'px', width: window.innerWidth + 'px'});

        var _this = this;
        axios.get("/api/issues")
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
        <div style={{height: this.state.height, weight: this.state.width}}>
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