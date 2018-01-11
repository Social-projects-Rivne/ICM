import React, {Component} from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import EventsContainer from "./EventsContainer";

class Events extends Component {
    constructor(props) {
        super(props);

        this.state = {
            events: "",
            direction: "ASC",
            sort: "id",
            page: 1
        };
        this.handleSortChange = this.handleSortChange.bind(this);
        this.handlePageChange = this.handlePageChange.bind(this);
    }

    componentWillMount() {
        this.makeQuery();
    }

    handleSortChange(sortDirection) {
        this.setState({direction: sortDirection}, function() {
            this.makeQuery();
        });
    }

    handlePageChange(pageNum) {
        this.setState({page: pageNum}, function() {
            this.makeQuery()
        });
    }

    makeQuery() {
        var _this = this;
        axios.get(["/api/events?page=", this.state.page, "&sort=", this.state.sort,
            "&direction=", this.state.direction].join(""))
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
            <EventsContainer data={this.state.events} onPageChange={this.handlePageChange}
                             onSortChange={this.handleSortChange}/>
        )
    }
}
export default Events;
