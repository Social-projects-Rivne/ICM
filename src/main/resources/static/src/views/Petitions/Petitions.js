import React, {Component} from 'react';
import {
    Badge,
    Row,
    Col,
    Card,
    CardHeader,
    CardBody,
    Table,
    Pagination,
    PaginationItem,
    PaginationLink
} from 'reactstrap';
import {Link} from "react-router-dom";


class Tables extends Component {
    render() {
        return (
            <div className="animated fadeIn">


              <Row>

                <Col xs="12" lg="12">
                  <Card>
                    <CardHeader>
                      <i className="fa fa-align-justify"></i> Bordered Table
                    </CardHeader>
                    <CardBody>
                      <Table responsive bordered>
                        <thead>
                        <tr>
                          <th>Username</th>
                          <th>Date registered</th>
                          <th>Role</th>
                          <th>Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                          <td>Pompeius RenГ©</td>
                          <td>2012/01/01</td>
                          <td>Member</td>
                          <td>
                            <Badge color="success">Active</Badge>
                              <Link to={"/petitions-edit"}>Edit</Link>
                          </td>
                        </tr>
                        </tbody>
                      </Table>
                      <Pagination>
                        <PaginationItem><PaginationLink previous href="#">Prev</PaginationLink></PaginationItem>
                        <PaginationItem active>
                          <PaginationLink href="#">1</PaginationLink>
                        </PaginationItem>
                        <PaginationItem className="page-item"><PaginationLink href="#">2</PaginationLink></PaginationItem>
                        <PaginationItem><PaginationLink href="#">3</PaginationLink></PaginationItem>
                        <PaginationItem><PaginationLink href="#">4</PaginationLink></PaginationItem>
                        <PaginationItem><PaginationLink next href="#">Next</PaginationLink></PaginationItem>
                      </Pagination>
                    </CardBody>
                  </Card>
                </Col>

              </Row>


            </div>

        )
    }
}

export default Tables;