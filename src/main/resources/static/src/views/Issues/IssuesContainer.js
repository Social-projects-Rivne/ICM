import React, {Component} from 'react';
import {Table} from "reactstrap";
import Issue from "./Issue";
import PageContainer from "../../components/PageContainer/PageContainer";

class IssuesContainer extends Component {
    constructor(props) {
        super(props);

        this.state = {
            data: this.props.data
        };
        this.handleSortChange = this.handleSortChange.bind(this);
        this.handlePageChange = this.handlePageChange.bind(this);
        this.handlePageUpdate = this.handlePageUpdate.bind(this);
    }

    handlePageUpdate() {
        this.props.onPageChange(this.state.data.number + 1);
    }

    handleSortChange(sortDirection, sortColumn) {
        this.props.onSortChange(sortDirection, sortColumn);
    }

    handlePageChange(page) {
        this.props.onPageChange(page);
    }

    componentWillReceiveProps(props) {
        this.setState({data: props.data});
    }

    table() {
        let _this = this;
        if(this.state.data !== "" && this.state.data.content.length !== 0) {
            return (
                <Table responsive bordered>
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Title</th>
                        <th>Initial Date</th>
                        <th>Category</th>
                        <th>User ID</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.data.content.map(function(issue, i) {
                        return (
                            <Issue key={i} issue={issue} onDelete={_this.handlePageUpdate}/>
                        );
                    })}
                    </tbody>
                </Table>
            )
        } else {
            return <div className="text-center">The list is empty</div>
        }
    }

    render() {
        return (
            <PageContainer onPageChange={this.handlePageChange} onSortChange={this.handleSortChange} title="Issues list"
                           sortDirection={this.state.data.sort} sortColumn={this.state.data.sort}
                           page={this.state.data.number + 1} pagesNum={this.state.data.totalPages}>
                {this.table()}
            </PageContainer>
        )
    }

}
export default IssuesContainer;