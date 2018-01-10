import React, { Component } from 'react';
import {withGoogleMap, GoogleMap, withScriptjs, Marker} from "react-google-maps"
import axios from 'axios';
import swal from 'sweetalert';
import descriptionIssues from './descriptionIssues';

function showComponent(props){
    return <descriptionIssues/>;
}

class Map extends Component {
    constructor(props){
    super(props);
    this.state={
        routeid:[]
    };

    this.showMessage = this.showMessage.bind(this);

    }

    showMessage(){
        console.log("Call issue with id");
    }
   render() {
        console.log(this.state.routeid);
        var _this = this;
        setState({
            routeid: _this.props.issues.id
        });

        console.log(this.state.routeid);

        return (
            <GoogleMap
                defaultZoom={this.props.zoom}
                defaultMaxZoom = {this.props.defaultmaxzoom}
                defaultCenter={{ lat: this.props.centlat, lng: this.props.centlng }}
              >
                {this.props.issues.map(issues => (
                              <Marker
                                  key={issues.id}
                                  position={{ lat: issues.latitude, lng: issues.longitude}}
                                  onClick = {this.showMessage}
                              >
                               </Marker>
                ))}
              </GoogleMap>

        )
   }
}


export default withScriptjs(withGoogleMap(Map));