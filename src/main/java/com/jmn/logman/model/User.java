package com.jmn.logman.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "profile_image_uri")
    private String profileImageUri;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "activation_serial_number")
    private String activationSerialNumber;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userId")
    @Cascade({CascadeType.ALL})
    private Set<UserRole> roles = new HashSet<>();

    @Column(name = "deactivated_date")
    private Instant deactivatedDate;

    @Column(name = "user_deactivate_reason_id")
    private Integer deactivatedReasonId;

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

    public Instant getDeactivatedDate() {
        return deactivatedDate;
    }

    public void setDeactivatedDate(Instant deactivatedDate) {
        this.deactivatedDate = deactivatedDate;
    }

    public Integer getDeactivatedReasonId() {
        return deactivatedReasonId;
    }

    public void setDeactivatedReasonId(Integer userDeactivationReasonId) {
        this.deactivatedReasonId = userDeactivationReasonId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null) {
            this.username = null;
        } else {
            this.username = username.toLowerCase();
        }
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
        if (email == null) {
            this.email = null;
        } else {
            this.email = email.toLowerCase();
        }
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public String getProfileImageUri() {
        return profileImageUri;
    }

    public void setProfileImageUri(String profileImageUri) {
        this.profileImageUri = profileImageUri;
    }


    public void setActivationSerialNumber(String activationSerialNumber) {
        this.activationSerialNumber = activationSerialNumber;
    }

    public String getActivationSerialNumber() {
        return activationSerialNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return username.equals(user.username) &&
                email.equals(user.email);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profileImageUri=" + profileImageUri +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", activationSerialNumber='" + activationSerialNumber + '\'' +
                ", roles=" + roles +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
