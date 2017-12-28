import React, { Component } from 'react';
import { compose, withProps } from "recompose"
import { withScriptjs, withGoogleMap, GoogleMap, Marker } from "react-google-maps"
import axios from 'axios';
import swal from 'sweetalert';

class IssueMap extends Component {
    constructor(props){
        super(props);


        this.state = {
            issues: []
        };
    }

    componentWillMount() {
        var _this = this;
        axios.get("/api/issues")
            .then(function(response) {
                _this.setState({
                    issues: response.data
                });
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });
    }

   render() {
    const MyMapComponent = compose(
      withProps({
        googleMapURL: "https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=geometry,drawing,places",
        loadingElement: <div style={{ height: `100%` }} />,
        containerElement: <div style={{ height: `800px` }} />,
        mapElement: <div style={{ height: `100%` }} />,
      }),
      withScriptjs,
      withGoogleMap
    )((props) =>
      <GoogleMap
        defaultZoom={8}
        defaultCenter={{ lat: -34.397, lng: 150.644 }}
      >
          {this.state.issues.map(issues => (
              <Marker
                  key={issues.id}
                  position={{ lat: issues.latitude, lng: issues.longitude }}
              />
          ))}
      </GoogleMap>

    )
        return (
            <MyMapComponent/>
        )
   }
}


export default IssueMap;