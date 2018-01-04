import React, { Component } from 'react';
import { Alert, Label, Col, Button, Row, FormGroup, Input} from 'reactstrap';
import {Container} from 'reactstrap';
import axios from 'axios';

export default class EditProfile extends Component{
    constructor(props){
        super(props);

        this.state = {
            id: null,
            email: "",
            avatar: "http://www.teequilla.com/images/tq/empty-avatar.png",
            firstName: "",
            firstNameValid: false,
            lastName: "",
            lastNameValid: false,
            phone: "",
            phoneValid: null,
            oldPassword: "",
            newPassword: "",
            newPasswordValid: false,
            confirmNewPassword: "",
            confirmNewPasswordValid: false,
            responseIsSuccess : null,
            contactInfoResponseIsSuccess: null
         };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.updatePasswordHandlerBtn = this.updatePasswordHandlerBtn.bind(this);
        this.updateContactsHandlerBtn = this.updateContactsHandlerBtn.bind(this);
        this.uploadAvatar = this.uploadAvatar.bind(this);
    }

    componentWillMount(){
        this.setState({id: this.props.user.id, email: this.props.user.email, firstName: this.props.user.firstName,
            lastName: this.props.user.lastName, phone: this.props.user.phone});

        let _this = this;
        const userID = this.props.user.id;
        if(userID != null) {
            if (!isNaN(userID)) {
                axios.get('/api/avatar/' + userID).then(function () {
                    _this.setState({avatar: "http://localhost:8080/api/avatar/" + userID});
                }).catch(function () {
                    _this.setState({avatar: "http://www.teequilla.com/images/tq/empty-avatar.png"});
                });
            }
        } else {
            this.setState({avatar: "http://www.teequilla.com/images/tq/empty-avatar.png"});
        }
    }

    handleInputChange(event){
        const name = event.target.name;
        const value = event.target.value;

        this.setState({[name]: value},
            () => { this.validateField(name, value) });
    }

    validateField(fieldName, value){
        let firstNameValid = this.state.firstNameValid;
        let lastNameValid = this.state.lastNameValid;
        let phoneValid = this.state.phoneValid;
        let newPasswordValid = this.state.newPasswordValid;
        let confirmNewPasswordValid = this.state.confirmNewPasswordValid;

        switch (fieldName){
            case 'firstName':
                firstNameValid = !EditProfile.isEmpty(value);
                break;
            case 'lastName':
                lastNameValid = !EditProfile.isEmpty(value);
                break;
            case 'phone':
                phoneValid = (/^(\+)+([\d]{1,4})+([\d]{10})$/i).test(value);
                break;
            case 'newPassword':
                newPasswordValid = value.length >= 3;
                confirmNewPasswordValid = false;
                break;
            case 'confirmNewPassword':
                confirmNewPasswordValid = (value === this.state.newPassword);
                break;
            default:
                break;
        }

        this.setState({
            firstNameValid: firstNameValid,
            lastNameValid : lastNameValid,
            phoneValid: phoneValid,
            newPasswordValid: newPasswordValid,
            confirmNewPasswordValid: confirmNewPasswordValid
        });
    }

    updatePasswordHandlerBtn(){
        let data = new FormData();
        data.append('email', this.state.email);
        data.append('oldPassword', this.state.oldPassword);
        data.append('newPassword', this.state.newPassword);

        let _this = this;
        axios.post('/api/userSetting/updatePassword', data)
            .then(function (response) {
                _this.updateAlertState(true);
            })
            .catch(function (error) {
                _this.updateAlertState(false);
            });
    }

    updateContactsHandlerBtn(){
        let data = new FormData();
        data.append('email', this.state.email);
        data.append('firstName', this.state.firstName);
        data.append('lastName', this.state.lastName);
        data.append('phone', this.state.phone);

        let _this = this;
        axios.post('/api/userSetting/updateContacts', data)
            .then(function(response){
                _this.setState({contactInfoResponseIsSuccess: true});
            })
            .catch(function(error){
                _this.setState({contactInfoResponseIsSuccess: false});
            });
    }

    uploadAvatar(evt){
        let file = evt.target.files[0];
        let formData = new FormData();
        formData.append('photo', file);

        axios.post('/api/userSettings/updateLogo', formData);
    }

    render(){
        return(
            <Container className="page-content">
                <Col sm={10}>
                    <h2 className="subhead-heading">Profile Settings</h2>
                    <hr className="col-12"/>
                    <Row style={{marginBottom: '40px'}}>

                        <Col sm={8}>
                            <FormGroup>
                                <Label htmlFor='firstName'>First name</Label>
                                <Input type="text" name="firstName" id="firstName"
                                       bsSize="lg"
                                       className="border-radius"
                                       onChange={this.handleInputChange}
                                       value={this.state.firstName}/>
                            </FormGroup>

                            <FormGroup>
                                <Label htmlFor='lastName'>Last name</Label>
                                <Input type="text" name="lastName" id="lastName"
                                       bsSize="lg"
                                       className="border-radius"
                                       onChange={this.handleInputChange}
                                       value={this.state.lastName}/>
                            </FormGroup>

                            <FormGroup>
                                <Label htmlFor='phone'>Phone number</Label>
                                <Input type="text" name="phone" id="phone"
                                       bsSize="lg"
                                       className="border-radius"
                                       onChange={this.handleInputChange}
                                       value={this.state.phone}
                                       valid={this.checkPhoneValid()}/>
                            </FormGroup>

                            <Button size='lg' onClick={this.updateContactsHandlerBtn} color={this.buttonColorContacts()}>Update contacts form</Button>
                            {EditProfile.showContactAlert(this.state.contactInfoResponseIsSuccess)}

                        </Col>
                        <Col sm={4} style={{paddingLeft: '60px'}}>
                            <div className="edit-profile-avatar">
                            <Label>Profile picture</Label>
                            <img src={this.state.avatar} alt="avatar" className='border-radius' height="200" width="200"/>
                            <Label className="btn btn-primary avatar-upload ">
                                Upload new picture <Input type="file" onChange={this.uploadAvatar} hidden/>
                            </Label>
                            </div>
                        </Col>
                    </Row>

                    <h2 className="subhead-heading">Change password</h2>
                    <hr className="col-12"/>
                    <Row>
                        <Col sm={8}>
                            <FormGroup>
                                <Label htmlFor='password'>Old password</Label>
                                <Input type="password" name="oldPassword" id="oldPassword"
                                       bsSize="lg"
                                       className="border-radius"
                                       onChange={this.handleInputChange}
                                       value={this.state.oldPassword}/>
                            </FormGroup>

                            <FormGroup>
                                <Label htmlFor='password'>Password</Label>
                                <Input type="password" name="newPassword" id="newPassword"
                                       bsSize="lg"
                                       className="border-radius"
                                       onChange={this.handleInputChange}
                                       value={this.state.newPassword}
                                       valid={this.checkNewPassword()}/>
                            </FormGroup>

                            <FormGroup>
                                <Label htmlFor='password2'>Password</Label>
                                <Input type="password" name="confirmNewPassword" id="confirmNewPassword"
                                       bsSize="lg"
                                       className="border-radius"
                                       onChange={this.handleInputChange}
                                       value={this.state.confirmNewPassword}
                                       valid={this.checkConfirmPassword()}/>
                            </FormGroup>

                            <Button size='lg' onClick={this.updatePasswordHandlerBtn} color={this.buttonColorPass()}>Set the new password</Button>
                            {EditProfile.showPassAlert(this.state.responseIsSuccess)}
                        </Col>
                    </Row>
                </Col>
            </Container>
        )
    }

    updateAlertState(responseIsSuccess){
        this.setState({
            responseIsSuccess : responseIsSuccess,
            oldPassword: "",
            newPassword: "",
            confirmNewPassword: ""
        });
    }

    static successAlert(){
        return  <Alert color="success" className="alert-form">
            Password changed successfully !
        </Alert>;
    }

    static successContactAlert(){
        return  <Alert color="success" className="alert-form">
            Contacts information changed successfully !
        </Alert>;
    }

    static errorAlert(){
        return  <Alert color="danger" className="alert-form">
            Error
        </Alert>
    }

    static showPassAlert(response){
        if(response != null){
            return response ? EditProfile.successAlert() : EditProfile.errorAlert()
        } else {
            return null;
        }
    }

    static showContactAlert(response){
        if(response != null){
            return response ? EditProfile.successContactAlert() : EditProfile.errorAlert()
        } else {
            return null;
        }
    }

    checkPhoneValid(){
        return this.state.phone === '' ? null : this.state.phoneValid;
    }

    checkNewPassword(){
        return this.state.newPassword === '' ? null : this.state.newPasswordValid;
    }

    checkConfirmPassword(){
        return this.state.confirmNewPassword === '' ? null : this.state.confirmNewPasswordValid;
    }

    checkPasswordFormValid(){
        return (this.state.oldPassword.length >= 3) && this.state.newPasswordValid && this.state.confirmNewPasswordValid;
    }

    checkContactsFormValid(){
        return (this.state.firstName.length >= 2) && (this.state.lastName.length >= 2) && (this.state.phone.length >= 5);
    }

    buttonColorPass(){
        if (this.checkPasswordFormValid())
            return "success";
        else
            return "secondary";
    }

    buttonColorContacts(){
        if (this.state.firstNameValid && this.state.lastNameValid && this.state.phoneValid)
            return "success";
        else
            return "secondary";
    }

    static visible(isTrue){
        if (isTrue) {
            return {display : 'block'};
        } else {
            return {display : 'none'};
        }
    }

    static isEmpty(obj){
        if(obj === null)
            return true;
        return obj.length === 0;

    }
}
