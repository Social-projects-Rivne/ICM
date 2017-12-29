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
<<<<<<< HEAD
export default Events;
=======

class Event extends Component {

    constructor(props) {
        super(props);

        this.state = {
            event: this.props.event,
            mounted: true
        };

        this.handleDelete = this.handleDelete.bind(this);
    }

    handleDelete(){
        var _this = this;
        swal({
            title: "Are you sure?",
            text: "Once deleted, you will not be able to recover this data!",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then(function(willDelete) {
                if (willDelete) {
                    axios.delete("/api/events/"+_this.state.event.id)
                        .then(function(response) {
                            _this.setState({
                                mounted: false
                            });
                            swal({title: "Event record deleted", icon: "success"});
                        }).catch(function (error) {
                            swal({title: "Something went wrong!", text: error, icon: "error"});
                        });
                }
            });
    }

    render() {
        const row = (
                <tr>
                    <td>{this.state.event.id}</td>
                    <td>{this.state.event.title}</td>
                    <td>{this.state.event.initialDate}</td>
                    <td>{this.state.event.endDate}</td>
                    <td>{this.state.event.category}</td>
                    <td>{this.state.event.userDto.id}</td>
                    <td>
                        <Link to={"/admin/events/"+this.state.event.id+"/edit"}>
                            <Button color="info" size="sm">Edit</Button></Link>{' '}
                        <Button color="danger" size="sm" onClick={this.handleDelete}>Delete</Button>
                    </td>
                </tr>
            );
        return (this.state.mounted === true ? row : null)
    }
}

export default Events;
>>>>>>> afac4e11bc87c5a7724f3c64bfe5d4e66ba37d0d
