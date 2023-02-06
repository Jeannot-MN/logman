package com.jmn.logman.api.rest.user;

import com.jmn.logman.api.rest.ApiPaths;
import com.jmn.logman.api.rest.BaseController;
import com.jmn.logman.api.rest.dto.request.RegisterUserRequestDTO;
import com.jmn.logman.api.rest.dto.response.RegisterUserResponseDTO;
import com.jmn.logman.api.rest.mapper.UserDTOMapper;
import com.jmn.logman.service.bpm.ProcessService;
import com.jmn.logman.service.bpm.model.user.RegisterUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.USERS_PATH)
public class UserController extends BaseController {

    private final ProcessService processService;
    private final UserDTOMapper userDTOMapper;

    @Autowired
    public UserController(ProcessService processService, UserDTOMapper userDTOMapper) {
        this.processService = processService;
        this.userDTOMapper = userDTOMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDTO> registerUser(@RequestBody RegisterUserRequestDTO request) {
        RegisterUserResponse registerUserResponse = processService.registerUser(userDTOMapper.toRegisterUserRequest(request));

        return ResponseEntity.ok(new RegisterUserResponseDTO(
                registerUserResponse.getUser().getId()
                , registerUserResponse.getUser().getUsername()));
    }
}
