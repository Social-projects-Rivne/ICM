package ua.softserve.rv_028.issuecitymonitor.entity;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.Role;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = '1' WHERE id = ?")
@Where(clause = "deleted <> '1'")
public class User {

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true)
	private long id;

	@Column(name = "role")
	@Enumerated(EnumType.ORDINAL)
	private Role role;

	@Column(name = "reg_date")
	private String registrationDate;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "password")
	private String password;

	@NaturalId
	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "user_agreement")
	private boolean userAgreement;

	@Column(name = "user_status")
	@Enumerated(EnumType.ORDINAL)
	private UserStatus userStatus;

	@Column(name = "delete_date")
	private String deleteDate;

	@Column(name = "avatar_url")
	private String avatarUrl;

	@Column(name = "deleted")
	private int isDeleted = 0;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", targetEntity = Issue.class)
	private Set<Issue> issues = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", targetEntity = Event.class)
	private Set<Event> events = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", targetEntity = Petition.class)
	private Set<Petition> petitions = new HashSet<>();
	
	public User() {}
	
	public User(String registrationDate, String firstName, String lastName, String password, String email,
				String phone, boolean userAgreement, UserStatus userStatus, Role role, String deleteDate,
				String avatarUrl) {
		this.role = role;
		this.registrationDate = registrationDate;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.userAgreement = userAgreement;
		this.userStatus = userStatus;
		this.deleteDate = deleteDate;
		this.avatarUrl = avatarUrl;
	}

	public long getId() {
		return id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isUserAgreement() {
		return userAgreement;
	}

	public void setUserAgreement(boolean userAgreement) {
		this.userAgreement = userAgreement;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public String getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(String deleteDate) {
		this.deleteDate = deleteDate;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public Set<Issue> getIssues(){
		return issues;
	}

	public Set<Event> getEvents(){
		 return events;
	}

	public Set<Petition> getPetitions(){
		return petitions;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	@PreRemove
	public void delete() {
		this.isDeleted = 1;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", role=" + role +
				", registrationDate='" + registrationDate + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				", userAgreement=" + userAgreement +
				", userStatus=" + userStatus +
				", deleteDate='" + deleteDate + '\'' +
				", avatarUrl='" + avatarUrl + '\'' +
				'}';
	}
}
