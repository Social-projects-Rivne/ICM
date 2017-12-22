import React, {Component} from 'react';
import {
    Card, CardBody, CardFooter, CardHeader, Col, Pagination, PaginationItem, PaginationLink, Row,
    Table
} from "reactstrap";
import Event from "./Event";

class EventsContainer extends Component {
    constructor(props) {
        super(props);

        this.state = {
            data: this.props.data,
            page: this.props.data.number + 1
        };

        this.handleFirstPage = this.handleFirstPage.bind(this);
        this.handleLastPage = this.handleLastPage.bind(this);
        this.handlePage = this.handlePage.bind(this);
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
            )
        } else {
            return (<div className="text-center">The list is empty</div>)
        }
    }

    handlePage(i) {
        this.setState({page: i});
        this.props.onPageChange(this.state.page);
    }

    handleFirstPage() {
        this.setState({page: 1});
        this.props.onPageChange(this.state.page);
    }

    handleLastPage() {
        this.setState({page: this.state.data.totalPages});
        this.props.onPageChange(this.state.page);
    }

    pagination() {
        var items = [];
        for(var i = (this.state.page - 3 > 0) ? (this.state.page - 3) : 1;
            i < ((this.state.data.totalPages - this.state.page > 3) ? (this.state.page + 3) : this.state.data.totalPages) + 1;
            i++) {
            items.push(i);
        }

        var _this = this;

        return (
            <Pagination size="sm">
                <PaginationItem disabled={this.state.data.first}>
                    <PaginationLink previous onClick={this.handleFirstPage} />
                </PaginationItem>
                {items.map(function (item, i) {
                    return (
                        <PaginationItem key={i} active={item === _this.state.page}>
                            <PaginationLink onClick={() => _this.handlePage(item)}>
                                {item}
                            </PaginationLink>
                        </PaginationItem>
                    )
                })}
                <PaginationItem disabled={this.state.data.last}>
                    <PaginationLink next onClick={this.handleLastPage} />
                </PaginationItem>
            </Pagination>
        )
    }

    render() {
        return (
            <div className="animated fadeIn">
                <Row>
                    <Col xs="12" lg="12">
                        <Card>
                            <CardHeader>Event list</CardHeader>
                            <CardBody>
                                {this.table()}
                            </CardBody>
                            <CardFooter>
                                {this.pagination()}
                            </CardFooter>
                        </Card>
                    </Col>
                </Row>
            </div>
        )
    }
}

export default EventsContainer;
