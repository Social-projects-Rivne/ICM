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
        imagePath: [],
        };
    }

    componentWillMount(){
        var _this = this;
        axios.get("/api/issues/img")
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

class IssueMarker extends Component{

    constructor(props) {
        super(props);

        this.state = {
            ID: this.props.ID,
            display: "issue_marker",
            response: {
                id: "",
                title: "",
                description: "",
                initialDate: "",
                category: ""
            },
            initialDate: true,
        };

    }

    componentWillMount() {
        var _this = this;
        axios.get("/api/issues/" + this.state.ID)
            .then(function(response) {
                _this.setState({
                    response: response.data
                })
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });
}

    componentWillReceiveProps(nextProps) {
        var _this = this;

        axios.get("/api/issues/" + nextProps.ID)
            .then(function(response) {
                _this.setState({
                    response: response.data,
                    display: "issue_marker",
                })
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });
}

    onClick(e){
        this.setState({
            display: "issue_marker_v1"
        });
    }

    render(){
        return(
            <div className = {this.state.display}>
             <div className="card">
               <div className="card-header">
                    <div className="title-card">
                        <h1>{this.state.response.title}</h1>
                    </div>

                    <button className="btn btn-primary close-button" onClick = {(e)=>this.onClick(e)}>
                        Close
                    </button>

               </div>

               <div className="card-img">
                    <img className=" response-img img" src= {this.props.imgPath + this.state.response.photo}
                    alt = {this.state.response.title + "photo"}
                    />
               </div>

               <div className="card-body">

                 <div className="description-card">
                     <label for="comment"><h3>Description:</h3></label>
                        <p className= "card-text">
                            {this.state.response.description}
                        </p>
                 </div>

                 <div className="comments-card">
                    <p><h3>Comments:</h3></p>
                    <div className= "comments"></div>
                 </div>

               </div>

             </div>
            </div>
        )

    }


}


export default withScriptjs(withGoogleMap(Map));