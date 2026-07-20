package by.mchs.e112.auth.service;

import by.mchs.e112.auth.dto.RoleAssignRequest;
import by.mchs.e112.auth.dto.UserCreateRequest;
import by.mchs.e112.auth.dto.UserResponse;
import by.mchs.e112.auth.dto.UserUpdateRequest;
import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponse create(UserCreateRequest request, String actor);

    UserResponse update(UUID id, UserUpdateRequest request, String actor);

    UserResponse getById(UUID id);

    List<UserResponse> getAll();

    void assignRoles(UUID id, RoleAssignRequest request, String actor);

    void block(UUID id, String actor);

    void activate(UUID id, String actor);
}
