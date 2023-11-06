package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.role.EditRoleRequest;
import com.sericulture.masterdata.model.api.role.RoleRequest;
import com.sericulture.masterdata.model.api.role.RoleResponse;
import com.sericulture.masterdata.model.entity.Role;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public RoleResponse getRoleDetails(String roleName){
        Role role = null;
        if(role==null){
            role = roleRepository.findByRoleNameAndActive(roleName,true);
        }
        log.info("Entity is ",role);
        return mapper.roleEntityToObject(role,RoleResponse.class);
    }

    @Transactional
    public RoleResponse insertRoleDetails(RoleRequest roleRequest){
        Role role = mapper.roleObjectToEntity(roleRequest,Role.class);
        validator.validate(role);
        List<Role> roleList = roleRepository.findByRoleName(roleRequest.getRoleName());
        if(!roleList.isEmpty() && roleList.stream().filter(Role::getActive).findAny().isPresent()){
            throw new ValidationException("Role name already exist");
        }
        if(!roleList.isEmpty() && roleList.stream().filter(Predicate.not(Role::getActive)).findAny().isPresent()){
            throw new ValidationException("State name already exist with inactive state");
        }
        return mapper.roleEntityToObject(roleRepository.save(role),RoleResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedRoleDetails(final Pageable pageable){
        return convertToMapResponse(roleRepository.findByActiveOrderByRoleIdAsc( true, pageable));
    }

    private Map<String, Object> convertToMapResponse(final Page<Role> activeRoles) {
        Map<String, Object> response = new HashMap<>();

        List<RoleResponse> roleResponses = activeRoles.getContent().stream()
                .map(role -> mapper.roleEntityToObject(role,RoleResponse.class)).collect(Collectors.toList());
        response.put("role",roleResponses);
        response.put("currentPage", activeRoles.getNumber());
        response.put("totalItems", activeRoles.getTotalElements());
        response.put("totalPages", activeRoles.getTotalPages());

        return response;
    }

    @Transactional
    public void deleteRoleDetails(long id) {
        Role role = roleRepository.findByRoleIdAndActive(id, true);
        if (Objects.nonNull(role)) {
            role.setActive(false);
            roleRepository.save(role);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public RoleResponse getById(int id){
        Role role = roleRepository.findByRoleIdAndActive(id,true);
        if(role == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",role);
        return mapper.roleEntityToObject(role,RoleResponse.class);
    }

    @Transactional
    public RoleResponse updateRoleDetails(EditRoleRequest roleRequest){
        List<Role> roleList = roleRepository.findByRoleName(roleRequest.getRoleName());
        if(roleList.size()>0){
            throw new ValidationException("role already exists with this name, duplicates are not allowed.");
        }

        Role role = roleRepository.findByRoleIdAndActiveIn(roleRequest.getRoleId(), Set.of(true,false));
        if(Objects.nonNull(role)){
            role.setRoleName(roleRequest.getRoleName());
            role.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching state");
        }
        return mapper.roleEntityToObject(roleRepository.save(role),RoleResponse.class);
    }

}
