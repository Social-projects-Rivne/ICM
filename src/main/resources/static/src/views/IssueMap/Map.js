import React, { Component } from 'react';
import {withGoogleMap, GoogleMap, withScriptjs, Marker} from "react-google-maps"
import axios from 'axios';
import swal from 'sweetalert';
import IssueMarker from "./Markers/IssueMarker"
import EventsMarker from "./Markers/EventsMarker"
const { MarkerClusterer } = require("react-google-maps/lib/components/addons/MarkerClusterer");

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

    onClick(e,ID,type){
        this.setState({
            desc: true,
            point: ID,
            type: type,
        });
    }

   render() {

        return (
            <GoogleMap
                defaultZoom={this.props.zoom}
                defaultMaxZoom = {this.props.defaultmaxzoom}
                defaultCenter={{ lat: this.props.centlat, lng: this.props.centlng }}
              >
              <MarkerClusterer
                 averageCenter
                 enableRetinaIcons
                 gridSize={100}
              >
                  {this.props.issues.map(issues => (
                    <Marker key = {issues.id} position = {{lat: issues.latitude, lng: issues.longitude }}
                        onClick={(e)=>this.onClick(e,issues.id,"issue")}
                      >
                    </Marker>
                  ))}

                  {this.props.events.map(events => (
                    <Marker key = {events.id} position = {{lat: events.latitude, lng: events.longitude }}
                      onClick={(e)=>this.onClick(e,events.id,"event")}
                      >
                    </Marker>
                  ))}
              </MarkerClusterer>
              {(this.state.type = "issue" && this.state.desc) &&
                <IssueMarker ID = {this.state.point} imgPath= {this.state.imagePath}/>

               (this.state.type == "event" && this.state.desc) &&
                <EventsMarker ID = {this.state.point} />
                }
                </GoogleMap>
        )
    }
}


export default withScriptjs(withGoogleMap(Map));