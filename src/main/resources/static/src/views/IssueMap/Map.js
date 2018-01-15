import React, { Component } from 'react';
import {withGoogleMap, GoogleMap, withScriptjs, Marker} from "react-google-maps"
import axios from 'axios';
import swal from 'sweetalert';
import DescriptionIssue from './DescriptionIssue'

class Map extends Component {
    constructor(props){
    super(props);
    this.state={
        desc : false,
        point : [],
    };

    this.showMessage = this.showMessage.bind(this);

    }

    showMessage(e){
        this.setState({
            desc:true,
        });
        console.log(e.target.key);
    }
   render() {
        const issues = this.props.issues
        return (
            <GoogleMap
                defaultZoom={this.props.zoom}
                defaultMaxZoom = {this.props.defaultmaxzoom}
                defaultCenter={{ lat: this.props.centlat, lng: this.props.centlng }}
              >
                {issues.forEach( (marker,i,issues) => (

                    <Marker
                       key={i}
                       position={{ lat: marker.latitude, lng: marker.longitude}}
                       onClick = { this.showMessage }

                    >



                    </Marker>


                ))}

                </GoogleMap>
        )
   }
}




export default withScriptjs(withGoogleMap(Map));