import React, { Component } from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import {Button} from "reactstrap";
import * as response from "react/lib/ReactDOMFactories";

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

        axios.get('/api/pdf/' + pageName)
            .then(function () {
                const FileDownload = require('react-file-download');
                FileDownload(response.data, pageName + '.pdf');
                swal({title: pageName + " is ready to download", icon: "success"})
            })
            .catch(function () {
                swal({title: "Something went wrong!", icon: "error"});
            });
    }

}


export default DownloadPDF;
