import React, {Component} from 'react';
import {
    Row,
    Col,
    Button,
    ButtonDropdown,
    DropdownToggle,
    DropdownMenu,
    DropdownItem,
    Card,
    CardHeader,
    CardFooter,
    CardBody,
    Form,
    FormGroup,
    FormText,
    Label,
    Input,
    InputGroup,
    InputGroupAddon,
    InputGroupButton
} from 'reactstrap';

class Forms extends Component {
    constructor(props) {
        super(props);

        this.state = {};
    }

    render() {
        return (
            <div className="animated fadeIn">

                <Row>
                    <Col xs="12" md="12">
                        <Card>
                            <Form action="" method="post" encType="multipart/form-data" className="form-horizontal">
                            <CardHeader>
                                <strong>Petition title</strong> (ID 123)
                            </CardHeader>
                            <CardBody>


                                <FormGroup row>
                                    <Col md="3">
                                        <Label>Status</Label>
                                    </Col>
                                    <Col md="9">
                                        <FormGroup check>
                                            <div className="radio">
                                                <Label check htmlFor="radio1">
                                                    <Input type="radio" id="radio1" name="radios" value="1"/> ON
                                                </Label>
                                            </div>
                                            <div className="radio">
                                                <Label check htmlFor="radio2">
                                                    <Input type="radio" id="radio2" name="radios" value="2"/> OFF
                                                </Label>
                                            </div>
                                        </FormGroup>
                                    </Col>
                                </FormGroup>

                                    <FormGroup row>
                                        <Col md="3">
                                            <Label htmlFor="text-input">Text Input</Label>
                                        </Col>
                                        <Col xs="12" md="9">
                                            <Input type="text" id="text-input" name="text-input" placeholder="Text"/>
                                            <FormText color="muted">This is a help text</FormText>
                                        </Col>
                                    </FormGroup>

                                    <FormGroup row>
                                        <Col md="3">
                                            <Label htmlFor="textarea-input">Textarea</Label>
                                        </Col>
                                        <Col xs="12" md="9">
                                            <Input type="textarea" name="textarea-input" id="textarea-input" rows="9"
                                                   placeholder="Content..."/>
                                            <FormText color="muted">This is a help text</FormText>
                                        </Col>
                                    </FormGroup>



                                    <FormGroup row>
                                        <Col md="3">
                                            <Label htmlFor="file-input">File input</Label>
                                        </Col>
                                        <Col xs="12" md="9">
                                            <Input type="file" id="file-input" name="file-input"/>
                                        </Col>
                                    </FormGroup>



                            </CardBody>
                            <CardFooter className="text-right">

                                <Button type="submit" color="primary"><i className="fa fa-ban"></i> Back</Button>
                                <Button type="submit" color="success"><i className="fa fa-dot-circle-o"></i> Save</Button>
                            </CardFooter>
                            </Form>
                        </Card>

                    </Col>

                </Row>


            </div>
        )
    }
}

export default Forms;
