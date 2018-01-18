import React, { Component } from 'react';
import {withGoogleMap, GoogleMap, withScriptjs, Marker} from "react-google-maps"
import axios from 'axios';
import swal from 'sweetalert';
import Issue from "../Issues/Issue";

class Map extends Component {
    constructor(props){
        super(props);
        this.state={
        desc : false,
        point : [],
        issues: null,
        };
    }

    componentWillMount(){
        this.setState({
            issues: this.props.issues,
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
                    <IssueMarker issuesID = {issues.id} issuesLat={issues.latitude} issuesLng={issues.longitude} />
                ))}
                </GoogleMap>
        )
    }
}

class IssueMarker extends Component{
    constructor(props){
        super(props);
        this.state = {
            id: this.props.issuesID,
            latitude: this.props.issuesLat,
            longitude: this.props.issuesLng,
            descriptionFlag: false,
            issue: "" ,
        };
        this.showMessage = this.showMessage.bind(this);
    }

    showMessage(){
            const _this = this;
            axios.get("/api/issues/" + this.state.id)
            .then(function(response) {
                _this.setState({
                    issue: response.data
                });
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });

    }

    render(){
        return(
            <Marker
                key={this.state.id}
                position={{ lat: this.state.latitude, lng: this.state.longitude }}
                onClick = { this.showMessage }
            />
        )
    }
}


export default withScriptjs(withGoogleMap(Map));