import React, { Component } from 'react';

class Search extends Component {
    constructor(props) {
        super(props);

        this.state = {
            keyword: "",
        };
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange() {
        const name = e.target.name;
        const value = e.target.value;
        this.setState({[name]: value});
    }

    render() {
        return(
            <div className="animated fadeIn">
                <Row>
                    <Col xs="12" lg="12">
                        <Card>
                            <CardHeader>Extended search</CardHeader>
                            <CardBody>
                                <FormGroup row>
                                    <Col md="2">
                                        <Label>Keyword</Label>
                                    </Col>
                                    <Col xs="12" md="10">
                                        <Input value={this.state.keyword} onChange={this.handleChange}
                                            type="text" name="keyword" placeholder="Search..."/>
                                    </Col>
                                </FormGroup>
                                <FormGroup row>
                                    <Col md="2">
                                        <Label>Filters</Label>
                                    </Col>
                                    <Col xs="12" md="10">
                                        <Input type="text" name="keyword" placeholder="Search..."/>
                                    </Col>
                                </FormGroup>
                            </CardBody>
                        </Card>
                    </Col>
                </Row>
            </div>
        );
    }
}

export default Search