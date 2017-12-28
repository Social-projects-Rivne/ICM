import React, { Component } from 'react';
import { compose, withProps } from "recompose"
import { withScriptjs, withGoogleMap, GoogleMap, Marker, InfoWindow } from "react-google-maps"
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
                    issues: response.data,
                    isOpen: false
                });
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });
    }

   render() {
    const MyMapComponent = compose(
      withProps({
       googleMapURL: "https://maps.googleapis.com/maps/api/js?key=AIzaSyCWeUPkEU7NK94ImWCjXVIMvrO9R3Lz08A&callback=initMap",
        loadingElement: <div style={{ height: `100%` }} />,
        containerElement: <div style={{ height: `1000px` }} />,
        mapElement: <div style={{ height: `100%` }} />,
      }),
      withScriptjs,
      withGoogleMap
    )((props) =>
      <GoogleMap
        defaultZoom={15}
        defaultCenter={{ lat: 	50.619900, lng: 26.251617 }}
      >
          {this.state.issues.map(issues => (
              <Marker
                  key={issues.id}
                  position={{ lat: issues.latitude, lng: issues.longitude }}
              >
              <InfoWindow>
                      <div>
                        {"It is marker!"}
                      </div>
              </InfoWindow>
               </Marker>
          ))}
      </GoogleMap>

    )
        return (
            <MyMapComponent/>
        )
   }
}


export default IssueMap;