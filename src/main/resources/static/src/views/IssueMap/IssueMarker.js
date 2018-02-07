import React, { Component } from 'react';
import {withGoogleMap, GoogleMap, withScriptjs, Marker} from "react-google-maps"
import axios from 'axios';
import swal from 'sweetalert';

class IssueMarker extends Component{

    constructor(props) {
        super(props);

        this.state = {
            ID: this.props.ID,
            display: "issue_marker",
            image: null,
            issue: {
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
                    issue: response.data
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
                    issue: response.data,
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
               <div className = {this.state.display + " col-md-5 col-lg-5"}>
               <div className="card-header">
                    <div className="title-card">
                        <h1>{this.state.issue.title}</h1>
                    </div>

                    <button className="btn btn-primary close-button" onClick = {(e)=>this.onClick(e)}>
                        Close
                    </button>

               </div>

               <div className="card-img">
                   <img className="img-responsive img" src={"http://localhost:8080/api/map/img/" + this.state.issue.id}
                        alt={this.state.issue.title + " image"}/>
               </div>

               <div className="card-body">

                        <div className="row justify-content-around">
                           <div className="col-6">
                               <div className="description-card">
                                   <h3>Description:</h3>
                                   <p className= "card-text">
                                       {this.state.issue.description}
                                   </p>
                               </div>
                           </div>
                           <div className="col-6">
                               <div className="comments-card">
                                   <p><h3>Comments:</h3></p>
                                   <div className= "comments"></div>
                               </div>
                           </div>
                        </div>

               </div>
             </div>
        )

    }


}

export default IssueMarker;