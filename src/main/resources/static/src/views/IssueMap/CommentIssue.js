import React, {Component} from 'react';
import swal from 'sweetalert';

class CommentIssue extends Component {

    constructor(props) {
        super(props);

        this.state = {
            comment: this.props.comment
        };
    }

    componentWillReceiveProps(props) {
        this.setState({comment: props.comment});
    }

    render() {
        const row = (
            <tr>
               <td>{this.state.comment.userDto.id}</td>
               <td>{this.state.comment.body}</td>
               <td>{this.state.comment.initialDate}</td>
            </tr>
        );
        return (row);
    }
}

export default CommentIssue;