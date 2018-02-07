import React, {Component} from 'react';
import {ButtonGroup, Button, Card, CardBody, CardFooter, CardHeader, Col, Input, Pagination, PaginationItem, PaginationLink, Row} from "reactstrap";
import DownloadPDF from "../DownloadPDF/DownloadPDF";

class PageContainer extends Component {
    constructor(props) {
        super(props);

        this.state = {
            pagesNum: this.props.pagesNum,
            page: this.props.page,
            sortColumn: this.props.sortColumn,
            sortDirection: this.props.sortDirection,
            title: this.props.title,
            button: this.props.button,
            children: this.props.children
        };
        this.handleFirstPage = this.handleFirstPage.bind(this);
        this.handleLastPage = this.handleLastPage.bind(this);
        this.handlePage = this.handlePage.bind(this);
        this.changeSortDirection = this.changeSortDirection.bind(this);
        this.changeSortColumn = this.changeSortColumn.bind(this);
    }

    componentWillReceiveProps(props){
        this.setState({
            pagesNum: props.pagesNum,
            page: props.page,
            sortColumn: props.sortColumn,
            sortDirection: props.sortDirection,
            title: props.title,
            children: props.children,
            button: props.button
        });
    }

    changeSortDirection(e) {
        this.setState({sortDirection: e.target.value}, function() {
            this.props.onSortChangeDirection(this.state.sortDirection);
        });
    }

    changeSortColumn(e) {
        this.setState({sortColumn: e.target.value}, function() {
            this.props.onSortChangeColumn(this.state.sortColumn)
        });
    }

    handlePage(i) {
        this.changePage(i);
    }

    handleFirstPage() {
        this.changePage(1);
    }

    handleLastPage() {
        this.changePage(this.state.pagesNum);
    }

    changePage(pageNum) {
        if(this.state.page !== pageNum) {
            this.setState({page: pageNum}, function () {
                this.props.onPageChange(this.state.page);
            });
        }
    }

    sorting() {
        return(
            <ButtonGroup className="indent-for-button">
                <Button color="secondary" size="sm" onClick={this.changeSortDirection} value="ASC">ASC</Button>
                <Button color="secondary" size="sm" onClick={this.changeSortDirection} value="DESC">DESC</Button>
                {this.state.title !== "Users list" ?
                    <Input onChange={this.changeSortColumn}
                           type="select" name="sortColumn" placeholder="Sorting">
                        <option value="id">by ID</option>
                        <option value="title">by Title</option>
                        <option value="initialDate">by Initial date</option>
                        <option value="category">by Category</option>
                        <option value="user">by User</option>
                    </Input> :
                    this.state.title === "Users list" ?
                        <Input onChange={this.changeSortColumn}
                               type="select" name="sortColumnForUser" placeholder="Sorting">
                            <option value="id">by ID</option>
                            <option value="userRole">by Role</option>
                            <option value="firstName">by First name</option>
                            <option value="lastName">by Last name</option>
                            <option value="username">by Email</option>
                            <option value="phone">by Phone</option>
                            <option value="userStatus">by User status</option>
                        </Input> :
                        null}
            </ButtonGroup>
        )
    }

    pagination() {
        var items = [];
        for(var i = (this.state.page - 3 > 0) ? (this.state.page - 3) : 1;
            i < ((this.state.pagesNum - this.state.page > 3) ? (this.state.page + 3) : this.state.pagesNum) + 1;
            i++) {
            items.push(i);
        }

        var _this = this;

        return (
            <Pagination size="sm">
                <PaginationItem disabled={this.state.page === 1}>
                    <PaginationLink previous onClick={this.handleFirstPage} />
                </PaginationItem>
                {items.map(function (item, i) {
                    return (
                        <PaginationItem key={i} active={item === _this.state.page}>
                            <PaginationLink key={i} onClick={() => _this.handlePage(item)}>
                                {item}
                            </PaginationLink>
                        </PaginationItem>
                    )
                })}
                <PaginationItem disabled={this.state.pagesNum === this.state.page || this.state.pagesNum === 0}>
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
                        <Card className="page-container-margin">
                            <CardHeader className="indent-for-button">
                                {this.state.title} {this.state.button}
                                <div className="pull-right">
                                    {this.sorting()}
                                </div>
                                <DownloadPDF page={this.props.name}/></CardHeader>
                            <CardBody>
                                {this.state.children}
                            </CardBody>
                            <CardFooter className="flex-align-center">
                                {this.pagination()}
                            </CardFooter>
                        </Card>
                    </Col>
                </Row>
            </div>
        )
    }
}

export default PageContainer;