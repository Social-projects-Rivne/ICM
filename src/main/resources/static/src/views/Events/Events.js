import React, {Component} from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import EventsContainer from "./EventsContainer";

class Events extends Component {
    constructor(props) {
        super(props);

        this.state = {
            events: "",
            page: 1
        };

        this.handlePageChange = this.handlePageChange.bind(this);
    }

    componentWillMount() {
        this.makeQuery();
    }

    handlePageChange(pageNum) {
        this.setState({page: pageNum}, function() {
            this.makeQuery()
        });
    }

    makeQuery() {
        var _this = this;
        axios.get(["/api/events?page=",this.state.page].join(""))
            .then(function(response) {
                _this.setState({
                    events: response.data
                });
            })
            .catch(function (error) {
                swal({title: "Something went wrong!", text: error, icon: "error"});
            });
    }

    render() {
        return (
            <EventsContainer data={this.state.events} onPageChange={this.handlePageChange}/>
        )
    }
}
export default Events;
