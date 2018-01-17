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
                    <MarkerComponent issuesID = {issues.id} issuesLat={issues.latitude} issuesLng={issues.longitude} />
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
            issue: null,
        }
        this.showMessage = this.showMessage.bind(this);
    }

    showMessage(){
            var _this = this;
            axios.get("/api/issues/" + this.state.id)
            .then(function(response) {
                _this.setState({
                    issue: response.data
                });
                swal({title: "asdasd", text: response});
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });
            console.log('issue',this.state.issue);


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