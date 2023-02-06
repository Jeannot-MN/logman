package com.jmn.logman.api.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterUserResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;


    public RegisterUserResponseDTO() {
    }

    public RegisterUserResponseDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
