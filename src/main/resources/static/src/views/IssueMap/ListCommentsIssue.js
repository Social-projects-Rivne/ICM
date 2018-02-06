import React, {Component} from 'react';
import {Table} from "reactstrap";
import swal from 'sweetalert';
import CommentIssue from "./CommentIssue";

class ListCommentsIssue extends Component {

    constructor(props) {
         super(props);

         this.state = {
            data: this.props.data
         };
    }

    componentWillReceiveProps(props) {
        this.setState({data: props.data});
    }

    table() {
        let _this = this;
        if(this.state.data !== "") {
             return (
                <Table responsive bordered>
                    <thead>
                       <tr>
                          <th>User</th>
                          <th>Body</th>
                          <th>Initial Date</th>
                       </tr>
                    </thead>
                    <tbody>
                        {this.state.data.map(function(comment, i) {
                            return (
                                <CommentIssue key={i} comment={comment}/>
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
        return(
            <div className="comments-card">
                <p><h3>Comments:</h3></p>
                <div className= "comments">{this.table()}</div>
            </div>
        )
    }
}

export default ListCommentsIssue;
