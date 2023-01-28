
package com.jmn.logman.service.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jmn.logman.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ApplicationUserDetails implements UserDetails {

    @Serial
    private static final long serialVersionUID = 2201255793842239980L;

    private final Long id;
    private final String username;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String displayName;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    private final Set<String> groupIdentities = new HashSet<>();
    private final Set<UserRole> roles;
    private final String profileImageUri;
    private final String mobileNo;
    private final Instant deactivatedDate;
    private final Integer deactivatedReasonId;

    ApplicationUserDetails(Long id
            , String username
            , String password
            , String email
            , String firstName
            , String lastName
            , String displayName
            , Collection<? extends GrantedAuthority> authorities
            , Set<UserRole> roles
            , String profileImageUri
            , String mobileNo
            , Instant deactivatedDate
            , Integer deactivatedReasonId
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.deactivatedDate = deactivatedDate;
        this.profileImageUri = profileImageUri;
        this.mobileNo = mobileNo;
        this.deactivatedReasonId = deactivatedReasonId;
        this.authorities = authorities;
        this.roles = roles;
    }


    public Set<UserRole> getRoles() {
        return roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return true;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Set<String> getGroupIdentities() {
        return groupIdentities;
    }

    @JsonIgnore
    public void addGroupIdentity(String groupIdentity) {
        this.groupIdentities.add(groupIdentity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ApplicationUserDetails user = (ApplicationUserDetails) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public Instant getDeactivatedDate() {
        return deactivatedDate;
    }

    public String getProfileImageUri() {
        return profileImageUri;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public Integer getDeactivatedReasonId() {
        return deactivatedReasonId;
    }
}
