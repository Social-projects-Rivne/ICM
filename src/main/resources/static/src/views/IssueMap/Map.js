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




   render() {
        return (
            <GoogleMap
                defaultZoom={this.props.zoom}
                defaultMaxZoom = {this.props.defaultmaxzoom}
                defaultCenter={{ lat: this.props.centlat, lng: this.props.centlng }}
              >
                {this.props.issues.map(issues => (
                    <MarkerComponent/>

                ))}
                </GoogleMap>
        )
   }
}

class MarkerComponent extends Component{
    constructor(props){
        super(props);
        this.showMessage = this.showMessage.bind(this);
    }

    showMessage(){
        console.log('bind id');

    }
    render(){
        return(
            <Marker
                key={1}
                position={{ lat: 1, lng: 1 }}
                onClick = { this.showMessage }

            >



            </Marker>

        )
    }
}


export default withScriptjs(withGoogleMap(Map));