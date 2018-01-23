import React, { Component } from 'react';
import {withGoogleMap, GoogleMap, withScriptjs, Marker} from "react-google-maps"
import axios from 'axios';
import swal from 'sweetalert';


class Map extends Component {
    constructor(props){
        super(props);
        this.state={
        desc : false,
        point : null,
        issues: null,
        };
    }

    componentWillMount(){
        this.setState({
            issues: this.props.issues,
        });
    }

    onClick(e,ID){
        this.setState({
            desc: true,
            point: ID,
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
                <Marker key = {issues.id} position = {{lat: issues.latitude, lng: issues.longitude }}
                  onClick={(e)=>this.onClick(e,issues.id)}
                  >
                </Marker>
              ))}
              {this.state.desc && <IssueMarker ID = {this.state.point}/>}
                </GoogleMap>
        )
    }
}

class IssueMarker extends Component{

    constructor(props){
        super(props);
    }

    render(){
        console.log("Working!" + this.props.ID);
        return(
            <div className = "issue_marker">
                <h1>Hello issues + {this.props.ID}!</h1>

            </div>
        )

    }


}

export default withScriptjs(withGoogleMap(Map));