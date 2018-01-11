import React, {Component} from 'react';
import {ButtonGroup, Button, Card, CardBody, CardFooter, CardHeader, Col, Input, Pagination, PaginationItem, PaginationLink, Row} from "reactstrap";

class PageContainer extends Component {
    constructor(props) {
        super(props);

        this.state = {
            pagesNum: this.props.pagesNum,
            page: this.props.page,
            sortColumn: this.props.sortColumn,
            sortDirection: this.props.sortDirection,
            title: this.props.title,
            children: this.props.children
        };
        this.handleFirstPage = this.handleFirstPage.bind(this);
        this.handleLastPage = this.handleLastPage.bind(this);
        this.handlePage = this.handlePage.bind(this);
        this.changeSortAsc = this.changeSortAsc.bind(this);
        this.changeSortDesc = this.changeSortDesc.bind(this);
        /*this.changeSortByIdAsc = this.changeSortByIdAsc.bind(this);
        this.changeSortByIdDesc = this.changeSortByIdDesc.bind(this);
        this.changeSortByTitleAsc = this.changeSortByTitleAsc.bind(this);
        this.changeSortByTitleDesc = this.changeSortByTitleDesc.bind(this);
        this.changeSortByInitialDateAsc = this.changeSortByInitialDateAsc.bind(this);
        this.changeSortByInitialDateDesc = this.changeSortByInitialDateDesc.bind(this);*/
    }

    componentWillReceiveProps(props){
        this.setState({
            pagesNum: props.pagesNum,
            page: props.page,
            sortColumn: props.sortColumn,
            sortDirection: props.sortDirection,
            title: props.title,
            children: props.children
        });
    }

    changeSortAsc() {
        this.setState({sortDirection: "ASC"}, function() {
            this.props.onSortChange(this.state.sortDirection);
        });
    }

    changeSortDesc() {
        this.setState({sortDirection: "DESC"}, function() {
            this.props.onSortChange(this.state.sortDirection);
        });
    }

    /*changeSortByIdAsc() {
        this.setState({sortColumn: "id", sortDirection: "ASC"}, function() {
            this.props.onSortChange(this.state.sortColumn, this.state.sortDirection);
        });
    }

    changeSortByIdDesc() {
        this.setState({sortColumn: "id", sortDirection: "DESC"}, function() {
            this.props.onSortChange(this.state.sortColumn, this.state.sortDirection);
        });
    }

    changeSortByTitleAsc() {
        this.setState({sortColumn: "title", sortDirection: "ASC"}, function() {
            this.props.onSortChange(this.state.sortColumn, this.state.sortDirection);
        });
    }

    changeSortByTitleDesc() {
        this.setState({sortColumn: "title", sortDirection: "DESC"}, function() {
            this.props.onSortChange(this.state.sortColumn, this.state.sortDirection);
        });
    }

    changeSortByInitialDateAsc() {
        this.setState({sortColumn: "initialDate", sortDirection: "ASC"}, function() {
            this.props.onSortChange(this.state.sortColumn, this.state.sortDirection);
        });
    }

    changeSortByInitialDateDesc() {
        this.setState({sortColumn: "initialDate", sortDirection: "DESC"}, function() {
            this.props.onSortChange(this.state.sortColumn, this.state.sortDirection);
        });
    }*/

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

    handleChange(e) {
        const name = e.target.name;
        const value = e.target.value;
        this.setState(function(prev) {
            return {
                children: {
                    ...prev.children,
                    [name]: value
                }
            }
        })
    }

    sorting() {
        return(
            <ButtonGroup>
                <Button color="secondary" size="sm" onClick={this.changeSortAsc}>ASC</Button>
                <Button color="secondary" size="sm" onClick={this.changeSortDesc}>DESC</Button>
                <Input value={this.state.children} onChange={this.handleChange}
                       type="select" name="sorting"
                       placeholder="Sorting">
                    <option>ID</option>
                    <option>Title</option>
                    <option>Initial date</option>
                </Input>
            </ButtonGroup>
            /*<ButtonGroup>
                <Button color="info" size="sm" onClick={this.changeSortByIdAsc}>Ascending by Id</Button>
                <Button color="info" size="sm" onClick={this.changeSortByIdDesc}>Descending by Id</Button>
                <Button color="info" size="sm" onClick={this.changeSortByTitleAsc}>Ascending by Title</Button>
                <Button color="info" size="sm" onClick={this.changeSortByTitleDesc}>Descending by Title</Button>
                <Button color="info" size="sm" onClick={this.changeSortByInitialDateAsc}>Ascending by Initial date</Button>
                <Button color="info" size="sm" onClick={this.changeSortByInitialDateDesc}>Descending by Initial date</Button>
            </ButtonGroup>*/
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
