package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.role.EditRoleRequest;
import com.sericulture.masterdata.model.api.role.RoleRequest;
import com.sericulture.masterdata.model.api.role.RoleResponse;
import com.sericulture.masterdata.model.api.roofType.RoofTypeResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.Role;
import com.sericulture.masterdata.model.entity.RoofType;
import com.sericulture.masterdata.model.entity.Village;
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
        RoleResponse roleResponse = new RoleResponse();
        Role role = roleRepository.findByRoleNameAndActive(roleName,true);
        if(role==null){
            roleResponse.setError(true);
            roleResponse.setError_description("Role not found");
        }else{
            roleResponse = mapper.roleEntityToObject(role, RoleResponse.class);
            roleResponse.setError(false);
        }
        log.info("Entity is ",role);
        return roleResponse;
    }

    @Transactional
    public RoleResponse insertRoleDetails(RoleRequest roleRequest){
        RoleResponse roleResponse = new RoleResponse();
        Role role = mapper.roleObjectToEntity(roleRequest,Role.class);
        validator.validate(role);
        List<Role> roleList = roleRepository.findByRoleName(roleRequest.getRoleName());
        if(!roleList.isEmpty() && roleList.stream().filter(Role::getActive).findAny().isPresent()){
            roleResponse.setError(true);
            roleResponse.setError_description("Role name already exist");
        }
        else if(!roleList.isEmpty() && roleList.stream().filter(Predicate.not(Role::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            roleResponse.setError(true);
            roleResponse.setError_description("Role name already exist with inactive state");
        }else {
            roleResponse = mapper.roleEntityToObject(roleRepository.save(role), RoleResponse.class);
            roleResponse.setError(false);
        }
        return roleResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedRoleDetails(final Pageable pageable){
        return convertToMapResponse(roleRepository.findByActiveOrderByRoleNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(roleRepository.findByActiveOrderByRoleNameAsc(isActive));
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

    private Map<String, Object> convertListEntityToMapResponse(final List<Role> activeRoles) {
        Map<String, Object> response = new HashMap<>();

        List<RoleResponse> roleResponses = activeRoles.stream()
                .map(role -> mapper.roleEntityToObject(role,RoleResponse.class)).collect(Collectors.toList());
        response.put("role",roleResponses);
        return response;
    }

    @Transactional
    public RoleResponse deleteRoleDetails(long id) {
        RoleResponse roleResponse = new RoleResponse();
        Role role = roleRepository.findByRoleIdAndActive(id, true);
        if (Objects.nonNull(role)) {
            role.setActive(false);
            roleResponse = mapper.roleEntityToObject(roleRepository.save(role), RoleResponse.class);
            roleResponse.setError(false);
        } else {
            roleResponse.setError(true);
            roleResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return roleResponse;
    }

    @Transactional
    public RoleResponse getById(int id){
        RoleResponse roleResponse = new RoleResponse();
        Role role = roleRepository.findByRoleIdAndActive(id,true);
        if(role == null){
            roleResponse.setError(true);
            roleResponse.setError_description("Invalid id");
        }else{
            roleResponse =  mapper.roleEntityToObject(role,RoleResponse.class);
            roleResponse.setError(false);
        }
        log.info("Entity is ",role);
        return roleResponse;
    }

    @Transactional
    public RoleResponse updateRoleDetails(EditRoleRequest roleRequest) {
        RoleResponse roleResponse = new RoleResponse();
        List<Role> roleList = roleRepository.findByRoleName(roleRequest.getRoleName());
        if (roleList.size() > 0) {
            roleResponse.setError(true);
            roleResponse.setError_description("Role already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            Role role = roleRepository.findByRoleIdAndActiveIn(roleRequest.getRoleId(), Set.of(true, false));
            if (Objects.nonNull(role)) {
                role.setRoleName(roleRequest.getRoleName());
                role.setActive(true);
                Role role1 = roleRepository.save(role);
                roleResponse = mapper.roleEntityToObject(role1, RoleResponse.class);
                roleResponse.setError(false);
            } else {
                roleResponse.setError(true);
                roleResponse.setError_description("Error occurred while fetching Role");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return roleResponse;
    }
}
