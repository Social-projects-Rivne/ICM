import React, {Component} from 'react';
import {Table} from "reactstrap";
import Event from "./Event";
import PageContainer from "../../components/PageContainer/PageContainer";

class EventsContainer extends Component {
    constructor(props) {
        super(props);

        this.state = {
            data: this.props.data
        };

        this.handlePageChange = this.handlePageChange.bind(this);
    }

    handlePageChange(page) {
        this.props.onPageChange(page);
    }

    componentWillReceiveProps(props) {
        this.setState({data: props.data});
    }

    table() {
        if(this.state.data.content.length !== 0) {
            return (
                <Table responsive bordered>
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Title</th>
                        <th>Initial Date</th>
                        <th>End Date</th>
                        <th>Category</th>
                        <th>User ID</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.data.content.map(function (event, i) {
                        return (
                            <Event key={i} event={event}/>
                        );
                    })}
                    </tbody>
                </Table>
            );
        } else {
            return (<div className="text-center">The list is empty</div>)
        }
    }

    render() {
        return (
            <PageContainer onPageChange={this.handlePageChange} title="Events list"
                           page={this.state.data.number + 1} pagesNum={this.state.data.totalPages}>
                {this.table()}
            </PageContainer>
        )
    }
}

export default EventsContainer;
