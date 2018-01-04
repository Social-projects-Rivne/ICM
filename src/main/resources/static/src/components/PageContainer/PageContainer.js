import React, {Component} from 'react';
import {Card, CardBody, CardFooter, CardHeader, Col, Pagination, PaginationItem, PaginationLink, Row} from "reactstrap";
import DownloadPDF from "../DownloadPDF/DownloadPDF";


class PageContainer extends Component {
    constructor(props) {
        super(props);

        this.state = {
            pagesNum: this.props.pagesNum,
            page: this.props.page,
            title: this.props.title,
            button: this.props.button,
            children: this.props.children
        };

        this.handleFirstPage = this.handleFirstPage.bind(this);
        this.handleLastPage = this.handleLastPage.bind(this);
        this.handlePage = this.handlePage.bind(this);
    }

    componentWillReceiveProps(props){
        this.setState({
            pagesNum: props.pagesNum,
            page: props.page,
            title: props.title,
            button: props.button,
            children: props.children
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
                            <CardHeader>{this.state.title}, {this.state.button} <DownloadPDF/> </CardHeader>
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