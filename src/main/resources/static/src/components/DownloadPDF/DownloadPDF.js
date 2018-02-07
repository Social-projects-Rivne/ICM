import React, { Component } from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import {Button} from "reactstrap";


class DownloadPDF extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Button className="pull-right mr-10" color="info" size="sm" type="button" onClick={(e) => this.onClick(e, this.props.page)}>PDF</Button>
    );
    }

    onClick(e, pageName) {

        axios.get('/apis/pdf/' + pageName + '.pdf')
            .then(function () {
                const response = {
                    file: 'http://localhost:8080/api/pdf/' + pageName + '.pdf',
                };
                window.open(response.file);
                swal({title: pageName + " is ready to download", icon: "success"})
            })
            .catch(function () {
                swal({title: "Something went wrong!", icon: "error"});
            });
    }

}


export default DownloadPDF;
