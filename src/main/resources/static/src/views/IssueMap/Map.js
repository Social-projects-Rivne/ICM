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
        issues: null,
    };
    }

    componentWillMount(){
        this.setState({
            issues: this.props.issues,
        });


    }
    onClick(e, issuesID){
        this.setState({
            desc: !this.state.desc,
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
//                    <MarkerComponent issuesID = {issues.id} issuesLat={issues.latitude} issuesLng={issues.longitude} />
            <Marker
                key={issues.id}
                position={{ lat: issues.latitude, lng: issues.longitude }}
                onClick={(e) => this.onClick(e, issues.id)}

            >
            {this.state.desc&& <DescriptionIssue id = {issues.id}/>}


            </Marker>
                ))}
                </GoogleMap>
        )
   }
}

class MarkerComponent extends Component{
    constructor(props){
        super(props);
        this.state = {
            id: this.props.issuesID,
            latitude: this.props.issuesLat,
            longitude: this.props.issuesLng,
            descriptionFlag: false,
        }
        this.showMessage = this.showMessage.bind(this);
    }

    showMessage(){
        this.setState({
            descriptionFlag: !this.state.descriptionFlag,
        });

    }
    render(){
        return(
            <Marker
                key={this.state.id}
                position={{ lat: this.state.latitude, lng: this.state.longitude }}
                onClick = { this.showMessage }

            >
            {this.state.descriptionFlag && <DescriptionIssue id = {this.state.id}/>}


            </Marker>

        )
    }
}


export default withScriptjs(withGoogleMap(Map));