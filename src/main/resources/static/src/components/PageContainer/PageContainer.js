import React, {Component} from 'react';
import {ButtonGroup, Button, Card, CardBody, CardFooter, CardHeader, Col, Input, Pagination, PaginationItem, PaginationLink, Row} from "reactstrap";

class PageContainer extends Component {
    constructor(props) {
        super(props);

        this.state = {
            pagesNum: this.props.pagesNum,
            page: this.props.page,
<<<<<<< HEAD
            sortOrder: this.props.sortOrder,
=======
            sorting: this.props.sorting,
>>>>>>> 30007d19d6d9d03664017e5ae7301307ea759cc0
            title: this.props.title,
            children: this.props.children
        };
        this.changeSortByIdAsc = this.changeSortByIdAsc.bind(this);
        this.changeSortByIdDesc = this.changeSortByIdDesc.bind(this);
        this.handleFirstPage = this.handleFirstPage.bind(this);
        this.handleLastPage = this.handleLastPage.bind(this);
        this.handlePage = this.handlePage.bind(this);
    }

    componentWillReceiveProps(props){
        this.setState({
            pagesNum: props.pagesNum,
            page: props.page,
<<<<<<< HEAD
            sortOrder: props.sortOrder,
=======
            sorting: props.sorting,
>>>>>>> 30007d19d6d9d03664017e5ae7301307ea759cc0
            title: props.title,
            children: props.children
        });
    }

    changeSortByIdAsc() {
        if(this.state.sortOrder.descending === true) {
            this.setState(function() {
                this.props.onPageChange(this.state.sortOrder.ascending === true);
            });
        }
    }

    changeSortByIdDesc() {
        if(this.state.sortOrder.ascending === true) {
            this.setState(function() {
                this.props.onPageChange(this.state.sortOrder.descending === true);
            });
        }
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
            <ButtonGroup>
                <Button color="info" size="sm" onClick={this.changeSortByIdAsc}>ascending order</Button>
                <Button color="info" size="sm" onClick={this.changeSortByIdDesc}>descending order</Button>
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
                        <Card>
                            <CardHeader>
                                {this.state.title}
                                {this.sorting()}
                            </CardHeader>
                            <CardBody>
                                {this.state.children}
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

export default PageContainer;
