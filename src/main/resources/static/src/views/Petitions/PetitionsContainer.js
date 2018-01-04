import React, {Component} from 'react';
import {Table, Button} from "reactstrap";
import Petition from "./Petition";
import PageContainer from "../../components/PageContainer/PageContainer";
import {Link} from "react-router-dom";

class PetitionsContainer extends Component {
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

    addNewPetition() {
        return(
        <Link to={"/admin/petitions/add"}>
            <Button className="pull-right" color="success" size="sm">Add new</Button>
        </Link>
        )
    }

    componentWillReceiveProps(props) {
        this.setState({data: props.data});
    }

    table() {
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
                    {this.state.data.content.map(function(petition, i) {
                        return (
                            <Petition key={i} petition={petition}/>);
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
            <PageContainer onPageChange={this.handlePageChange} title="Petitions list"
                           page={this.state.data.number + 1} pagesNum={this.state.data.totalPages}>
                {this.table()}
            </PageContainer>
        )
    }

}
export default PetitionsContainer;