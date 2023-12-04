package module6.backend.service;

import module6.backend.entity.account.Role;
import module6.backend.entity.employee.Position;

import java.util.List;

public interface IRoleService {
    Role findRoleByRoleName(String name);
    List<Role> findAllRole();

}
