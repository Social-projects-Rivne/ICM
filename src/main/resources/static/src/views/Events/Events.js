import React, {Component} from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import EventsContainer from "./EventsContainer";

class Events extends Component {
    constructor(props) {
        super(props);

        this.state = {
            events: []
        };
    }

    componentWillMount() {
        var _this = this;
        axios.get("/api/events")
            .then(function(response) {
                _this.setState({
                    events: response.data
                })
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            })
    }

    render() {
        return (
            <EventsContainer data={this.state.events}/>
        )
    }
}
export default Events;
