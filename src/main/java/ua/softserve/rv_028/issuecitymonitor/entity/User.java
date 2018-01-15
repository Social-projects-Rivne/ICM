package ua.softserve.rv_028.issuecitymonitor.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET user_status = '0' WHERE id = ?")
public class User implements UserDetails{

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true)
	private long id;

	@Column(name = "userRole")
	@Enumerated(EnumType.ORDINAL)
	private UserRole userRole;

	@Column(name = "reg_date")
	private LocalDateTime registrationDate;

	@NotEmpty
	@Column(name = "first_name")
	private String firstName;

	@NotEmpty
	@Column(name = "password")
	@Pattern(regexp = "^.{3,}$")
	private String password;

	@NotEmpty
	@Column(name = "email", unique = true)
	@Pattern(regexp = "^([\\w.%+-]+)@([\\w-]+\\.)+([\\w]{2,})$")
	private String username;

	@NotEmpty
	@Column(name = "last_name")
	private String lastName;

	@Column(name = "phone")
	private String phone;

	@Column(name = "user_status")
	@Enumerated(EnumType.ORDINAL)
	private UserStatus userStatus;

	@Column(name = "delete_date")
	private LocalDateTime deleteDate;

	@Column(name = "avatar_url")
	private String avatarUrl;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", targetEntity = Issue.class)
	private Set<Issue> issues = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", targetEntity = Event.class)
	private Set<Event> events = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", targetEntity = Petition.class)
	private Set<Petition> petitions = new HashSet<>();

	public User() {}

	public User(String firstName, String lastName, String password, String username,
				String phone, UserStatus userStatus, UserRole userRole,
				String avatarUrl) {
		this.username = username;
		this.password = password;
		this.userRole = userRole;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.userStatus = userStatus;
		this.avatarUrl = avatarUrl;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
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

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public LocalDateTime getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(LocalDateTime deleteDate) {
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return UserRole.collectionForRole(this.userRole);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return checkUserStatus(this.getUserStatus());
	}

	@PreRemove
	public void delete() {
		this.deleteDate = LocalDateTime.now();
		this.userStatus = UserStatus.DELETED;
	}

	@PrePersist
	private void insert() {
		this.registrationDate = LocalDateTime.now();
	}

	private static boolean checkUserStatus(UserStatus status) {
		return status == UserStatus.ACTIVE || status == UserStatus.UNCONFIRMED;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", userRole=" + userRole +
				", registrationDate='" + registrationDate + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", username='" + username + '\'' +
				", phone='" + phone + '\'' +
				", userStatus=" + userStatus +
				", deleteDate='" + deleteDate + '\'' +
				", avatarUrl='" + avatarUrl + '\'' +
				'}';
	}
}