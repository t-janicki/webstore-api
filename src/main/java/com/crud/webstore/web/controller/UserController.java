package com.crud.webstore.web.controller;

import com.crud.webstore.dto.AddressDto;
import com.crud.webstore.dto.UserDto;
import com.crud.webstore.mapper.AddressMapper;
import com.crud.webstore.web.request.RequestOperationNames;
import com.crud.webstore.web.respone.*;
import com.crud.webstore.exception.UserServiceException;
import com.crud.webstore.mapper.UserMapper;
import com.crud.webstore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/v1/users")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService service;
    @Autowired
    private AddressMapper addressMapper;

    //Change to PathVariable?
    @GetMapping(value = "id", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse getUser(@RequestParam String id) {
        return userMapper.mapToUserResponse(
                 userMapper.mapToUserDto(service.getUserByUserId(id)));
    }

    @PostMapping(value = "createUser",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
            )
    public @ResponseBody UserResponse createUser(@RequestBody UserDto userDto) {
        if (userDto.getFirstName().isEmpty())
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        return userMapper.mapToUserResponse(userMapper.mapToUserDto(service.createUser(userDto)));
    }

    @PutMapping(value = "updateUserDetails",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
            )
    public UserResponse updateUser(@RequestBody UserDto userDto, @RequestParam String id) throws Exception {
        return userMapper.mapToUserResponse(service.updateUserDetails(id, userDto));
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public OperationStatus deleteUser(@RequestParam String id) {
        OperationStatus result = new OperationStatus();

        result.setOperationName(RequestOperationNames.DELETE.name());

        service.deleteUser(id);

        result.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return result;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserResponse> getUsers(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "25") int limit) {
        return userMapper.mapToUserListResponse(service.findAll(page, limit));
    }
}

