import React, {Component} from 'react';

class Footer extends Component {
  render() {
    return (
      <footer className="app-footer">
        <span><a href="http://coreui.io">ICM</a> &copy; 2017</span>
        <span className="ml-auto">Powered by <a href="/">SoftServe</a></span>
      </footer>
    )
  }
}

export default Footer;
