import React, { Component } from 'react';

import { Map, Marker, Popup, TileLayer } from 'react-leaflet'

const position = [51.505, -0.09]



export default class Petitions extends Component {

    render() {

        return (
            <Map center={position} zoom={13}>
              <TileLayer
                  url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                  attribution="&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors"
              />
              <Marker position={position}>
                <Popup>
       <span>
         A pretty CSS3 popup.<br />Easily customizable.
       </span>
                </Popup>
              </Marker>
            </Map>


        )
    }



}



