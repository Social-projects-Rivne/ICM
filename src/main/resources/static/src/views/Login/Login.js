import React, { Component } from 'react';

class Login extends Component {


    render() {
        return (
            <div className="container" style={this.marginTop()}>
                <form className="form-horizontal" role="form" method="POST" action="/login">
                    <div className="row">
                        <div className="col-md-3"/>
                        <div className="col-md-6">
                            <h2>Please Login</h2>
                            <hr/>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-3"/>
                        <div className="col-md-6">
                            <div className="form-group">
                                <label className="sr-only" htmlFor="email">E-Mail Address</label>
                                <div className="input-group mb-2 mr-sm-2 mb-sm-0">
                                    <div className="input-group-addon" style={this.divStyle()}><i className="fa fa-at"/></div>
                                    <input type="text" name="email" className="form-control" id="email"
                                           placeholder="you@example.com" required autoFocus/>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div className="row">
                        <div className="col-md-3"/>
                        <div className="col-md-6">
                            <div className="form-group has-error">
                                <label className="sr-only" htmlFor="password">Password</label>
                                <div className="input-group mb-2 mr-sm-2 mb-sm-0">
                                    <div className="input-group-addon" style={this.divStyle()}><i className="fa fa-key"/></div>
                                    <input type="password" name="password" className="form-control" id="password"
                                           placeholder="Password" required/>
                                </div>
                                <div className="form-control-feedback" style={this.hasError()}>
                                    <span className="text-danger align-middle">
                                        <i className="fa fa-close"/> Incorrect email or password
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-3">
                            <div className="form-control-feedback">
                        <span className="text-danger align-middle">

                        </span>
                            </div>
                        </div>
                    </div>

                    <div className="row" style={this.divStyle1()}>
                        <div className="col-md-3"/>
                        <div className="col-md-6">
                            <button type="submit" className="btn btn-success"><i className="fa fa-sign-in"/> Login</button>
                        </div>
                    </div>
                </form>
            </div>
        )
    }


    marginTop () {
        return {marginTop : '10%'}
    }
    divStyle () {
        return {width: '2.6rem'};
    };

    divStyle1 (){
        return {paddingTop: '1rem'};
    };


    hasError() {
        if (this.props.history.location.search !== ""){
            console.log(this.props);
            console.log(this.props.history.location.search);
            console.log("ERROR");
            return {display:'inline'}
        } else
            return {display:'none'}
    }
}

export default Login;
