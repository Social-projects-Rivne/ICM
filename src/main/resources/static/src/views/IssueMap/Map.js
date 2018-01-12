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
    };

    this.showMessage = this.showMessage.bind(this);

    }

    showMessage(){
        this.setState({
            desc:true
        });
    }
   render() {
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
                                  onClick = { this.showMessage }

                              >
                              {this.state.desc && <DescriptionIssue
                              id = {issues.id}
                              />}


                               </Marker>
                ))}
              {this.state.desc}
              </GoogleMap>

        )
   }
}


export default withScriptjs(withGoogleMap(Map));