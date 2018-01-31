import React, { Component } from 'react';
import {withGoogleMap, GoogleMap, withScriptjs, Marker} from "react-google-maps"
import axios from 'axios';
import swal from 'sweetalert';
import IssueMarker from "./IssueMarker"


class Map extends Component {
    constructor(props){
        super(props);
        this.state={
        desc : false,
        point : null,
        imagePath: [],
        };
    }

    componentWillMount(){
        var _this = this;
        axios.get("/api/map/img")
            .then(function(response) {
                _this.setState({
                    imagePath: response.data
                })
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });
    }

    onClick(e,ID){
        this.setState({
            desc: true,
            point: ID,
        });
    }

   render() {
   console.log("this img path", this.state.imagePath);
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
              {this.state.desc && <IssueMarker ID = {this.state.point} imgPath= {this.state.imagePath}/>}
                </GoogleMap>
        )
    }
}


export default withScriptjs(withGoogleMap(Map));