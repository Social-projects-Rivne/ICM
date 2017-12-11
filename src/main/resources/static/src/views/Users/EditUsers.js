import React, { Component } from 'react';
import axios from 'axios';
import swal from 'sweetalert';
import {Link} from "react-router-dom";


class EditUsers extends Component{
   constructor(props) {
           super(props);
           this.state = {users:
           id: "",
           userRole: "",
           registrationDate: "",
           firstName:"",
           lastName:"",
           password:"",
           email:"",
           phone:"",
           userStatus:"",
           avatarUrl:"",
           };


           this.handleChange = this.handleChange.bind(this);
   }


    handleChange(){
        this.setState({




        }
        )



    }

    componentWillMount(){
        var _this = this;
               axios.get("/api/users" + this.props.match.params.id)
                    .then(function(response) {
                        _this.setState({
                            users: response.data
                        })
                    })
                    .catch(function (error) {
                        swal({title: "Something went wrong!", text: error, icon: "error"});
                    })



    }








   render() {
           return (
               <div className="animated fadeIn">
                   <Row>
                       <Col xs="24" md="12">
                           <Card>
                               <Form className="form-horizontal">
                                   <CardHeader>
                                       <strong>1</strong>
                                   </CardHeader>
                                   <CardBody>

                                       <FormGroup row>
                                           <Col md="2">
                                               <Label>Title</Label>
                                           </Col>
                                           <Col xs="12" md="10">
                                               <h1>2</h1>
                                           </Col>
                                       </FormGroup>

                                       <FormGroup row>
                                           <Col md="2">
                                               <Label>Description</Label>
                                           </Col>
                                           <Col xs="12" md="10">
                                               <h1>3</h1>
                                           </Col>
                                       </FormGroup>

                                       <FormGroup row>
                                           <Col md="2">
                                               <Label>Initial Date</Label>
                                           </Col>
                                           <Col xs="12" md="4">
                                               <h1>3</h1>
                                           </Col>
                                           <Col md="2">
                                               <Label>End Date</Label>
                                           </Col>
                                           <Col xs="12" md="4">
                                               <InputGroup>
//                                                   <Input value={this.state.event.endDate} type="text"
//                                                          name="endDate" placeholder="DD/MM/YYYY hh:mm"
//                                                          onChange={this.handleDateChange}/>
//                                                   <InputGroupAddon className={this.state.endDate ?
//                                                       "fa fa-calendar-check-o" : "fa fa-calendar-times-o"}/>
                                               </InputGroup>
                                           </Col>
                                       </FormGroup>

                                       <FormGroup row>
                                           <Col md="2">
                                               <Label>Category</Label>
                                           </Col>
                                           <Col xs="12" md="4">
//                                               <Input value={this.state.event.category} onChange={this.handleChange}
//                                                      type="select" name="category"
//                                                      placeholder="Category">
//                                                   <option>CAT1</option>
//                                                   <option>CAT2</option>
//                                                   <option>CAT3</option>
                                               </Input>
                                           </Col>
                                       </FormGroup>

                                   </CardBody>
//                                   <CardFooter className="text-right">
//                                       {(this.state.endDate && this.state.initialDate) ?
//                                           <Button color="success" onClick={this.handleSave}>
//                                               <i className="fa fa-dot-circle-o"/> Save</Button> :
//                                           <Button disabled color="success" onClick={this.handleSave}>
//                                               <i className="fa fa-dot-circle-o"/> Save</Button>}
//                                       <Link to="/events"><Button color="primary">
//                                           <i className="fa fa-ban"/> Back</Button>
//                                       </Link>
//                                   </CardFooter>
                               </Form>
                           </Card>
                       </Col>
                   </Row>
               </div>
           )
       }
   }



}
