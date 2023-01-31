package com.jmn.logman.api.rest.mapper;

import com.jmn.logman.api.rest.dto.request.RegisterUserRequestDTO;
import com.jmn.logman.service.bpm.model.user.RegisterUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class UserDTOMapper {

    @Mapping(target = "plainTextPassword", source = "password")
    @Mapping(target = "username", source = "email")
    @Mapping(target = "processInstanceId", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "requestingUser", ignore = true)
    @Mapping(target = "candidateGroups", ignore = true)
    @Mapping(target = "lastAction", ignore = true)
    @Mapping(target = "comment", ignore = true)
    public abstract RegisterUserRequest toRegisterUserRequest(RegisterUserRequestDTO source);
}
