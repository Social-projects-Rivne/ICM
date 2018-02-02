import React, { Component } from 'react';
import {withGoogleMap, GoogleMap, withScriptjs, Marker} from "react-google-maps"
import axios from 'axios';
import swal from 'sweetalert';

class EventsMarker extends Component{

    constructor(props) {
        super(props);

        this.state = {
            ID: this.props.ID,
            display: "event_marker",
            events: {
                id: "",
                title: "",
                description: "",
                initialDate: "",
                endDate: "",
            },
            initialDate: true,
        };

    }

    componentWillMount() {
        var _this = this;
        axios.get("/api/events" + this.state.ID)
            .then(function(response) {
                _this.setState({
                    events: response.data
                })
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });
}

    componentWillReceiveProps(nextProps) {
        var _this = this;

        axios.get("/api/events/" + nextProps.ID)
            .then(function(response) {
                _this.setState({
                    events: response.data,
                    display: "event_marker",
                });
                console.log("response: ", response.data);
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
    console.log("Event working", this.state.events.photo);
        return(
               <div className = {this.state.display}>
               <div className="card-header">
                    <div className="title-card">
                        <h1>{this.state.events.title}</h1>
                    </div>

                    <button className="btn btn-primary close-button" onClick = {(e)=>this.onClick(e)}>
                        Close
                    </button>

               </div>

               <div className="card-img">
                    <img className=" response-img img" src= {this.props.imgPath + this.state.events.photo}
                    alt = {this.state.events.title + "photo"}
                    />
               </div>

               <div className="card-body">

                 <div className="description-card">
                     <label for="comment"><h3>Description:</h3></label>
                        <p className= "card-text">
                            {this.state.events.description}
                        </p>
                 </div>

                 <div className="comments-card">
                    <p><h3>Comments:</h3></p>
                    <div className= "comments"></div>
                 </div>

               </div>

             </div>
        )

    }


}

export default EventsMarker;