import React, { Component } from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import {Button} from "reactstrap";

class DownloadPDF extends Component {
    constructor(props) {
        super(props);

    }


    render() {
        return <Button className="pull-right mr-10" color="info" size="sm" type="button" onClick={(e) => this.onClick(e, "petitions")}>PDF</Button>
    }


    onClick(e, pageName) {

        axios.get('/api/pdf/' + pageName)
            .then(function () {
                swal({title: pageName + " is ready to download", icon: "success"})
            })
            .catch(function () {
                swal({title: "Something went wrong !!!", text: error, icon: "error"});
            });
    }

}


export default DownloadPDF;
