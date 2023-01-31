package com.jmn.logman.service.bpm.model.user;

import com.jmn.logman.model.Roles;
import com.jmn.logman.service.bpm.common.ProcessTypes;
import com.jmn.logman.service.bpm.model.ProcessRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

public class RegisterUserRequest extends ProcessRequest {

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    private Long partyId;

    private String mobileNo;

    private String profileImageUri;

    private Set<Roles> roles;

//    private RequestSource source;

    private String plainTextPassword;

    @Override
    public ProcessTypes getType() {
        return ProcessTypes.USER_REGISTRATION;
    }

    @Override
    public String getBusinessKey() {
        return StringUtils.joinWith("::", getType(), username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Long getPartyId() {
        return partyId;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getProfileImageUri() {
        return profileImageUri;
    }

    public void setProfileImageUri(String profileImageUri) {
        this.profileImageUri = profileImageUri;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public String getPlainTextPassword() {
        return plainTextPassword;
    }

    public void setPlainTextPassword(String plainTextPassword) {
        this.plainTextPassword = plainTextPassword;
    }
}
