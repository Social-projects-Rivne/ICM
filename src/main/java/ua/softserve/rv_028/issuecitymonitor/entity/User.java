package ua.softserve.rv_028.issuecitymonitor.entity;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = 'true' WHERE id = ?")
@Where(clause = "deleted <> 'true'")

public class User implements UserDetails{

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true)
	private long id;

	@Column(name = "userRole")
	@Enumerated(EnumType.ORDINAL)
	private UserRole userRole;

	@Column(name = "reg_date")
	private String registrationDate;

	@NotEmpty
	@Column(name = "first_name")
	private String firstName;

	@NotEmpty
	@Column(name = "password")
	private String password;

	@NotEmpty
	@NaturalId
	@Column(name = "email", unique = true)
	private String email;

	@NotEmpty
	@Column(name = "last_name")
	private String lastName;

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
	private boolean isDeleted = false;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", targetEntity = Issue.class)
	private Set<Issue> issues = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", targetEntity = Event.class)
	private Set<Event> events = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", targetEntity = Petition.class)
	private Set<Petition> petitions = new HashSet<>();

	public User() {
		super();
	}

	public User(UserDto userDto) {
		this.userRole = userDto.getUserRole();
		this.registrationDate = userDto.getRegistrationDate();
		this.firstName = userDto.getFirstName();
		this.lastName = userDto.getLastName();
		this.password = userDto.getPassword();
		this.email = userDto.getEmail();;
		this.phone = userDto.getPhone();
		this.userAgreement = userDto.isUserAgreement();
		this.userStatus = userDto.getUserStatus();
		this.deleteDate = userDto.getDeleteDate();
		this.avatarUrl = userDto.getAvatarUrl();
		this.isDeleted = userDto.isDeleted();
	}

	public User(String registrationDate, String firstName, String lastName, String password, String email,
                String phone, boolean userAgreement, UserStatus userStatus, UserRole userRole, String deleteDate,
                String avatarUrl, boolean isDeleted) {
		this.registrationDate = registrationDate;
		this.email = email;
		this.password = password;
		this.userRole = userRole;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.userAgreement = userAgreement;
		this.userStatus = userStatus;
		this.avatarUrl = avatarUrl;
		this.isDeleted = isDeleted;
	}

	public User(String firstName, String lastName, String email, String password){
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
        this.userRole = UserRole.USER;
        this.userStatus = UserStatus.UNCONFIRMED;
        this.registrationDate = new Date().toString();
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

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	public void setUsername(String email) {
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

	public boolean getIsDeleted() {
		return isDeleted;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return prepareCollection(this.userRole);
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
		this.isDeleted = true;
	}

	private static boolean checkUserStatus(UserStatus status) {
		return status == UserStatus.ACTIVE || status == UserStatus.UNCONFIRMED;
	}

	private Collection<GrantedAuthority> prepareCollection(UserRole userRole) {
		HashSet<GrantedAuthority> roles = new HashSet<>();
		if (userRole == UserRole.USER) {
			roles.add(new SimpleGrantedAuthority(UserRole.USER.name()));
		} else if (userRole == UserRole.MODERATOR){
			roles.add(new SimpleGrantedAuthority(UserRole.USER.name()));
			roles.add(new SimpleGrantedAuthority(UserRole.MODERATOR.name()));
		} else if (userRole == UserRole.ADMIN){
			roles.add(new SimpleGrantedAuthority(UserRole.USER.name()));
			roles.add(new SimpleGrantedAuthority(UserRole.MODERATOR.name()));
			roles.add(new SimpleGrantedAuthority(UserRole.ADMIN.name()));
		}
		return roles;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", userRole=" + userRole +
				", registrationDate='" + registrationDate + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", username='" + email + '\'' +
				", phone='" + phone + '\'' +
				", userAgreement=" + userAgreement +
				", userStatus=" + userStatus +
				", deleteDate='" + deleteDate + '\'' +
				", avatarUrl='" + avatarUrl + '\'' +
				'}';
	}

}
