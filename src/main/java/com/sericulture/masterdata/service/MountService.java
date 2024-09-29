package com.sericulture.masterdata.service;


import com.sericulture.masterdata.model.api.mountMaster.EditMountRequest;
import com.sericulture.masterdata.model.api.mountMaster.MountRequest;
import com.sericulture.masterdata.model.api.mountMaster.MountResponse;
import com.sericulture.masterdata.model.entity.Mount;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.MountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MountService {
    @Autowired
    MountRepository mountRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    public MountResponse getMountDetails(String name){
        MountResponse mountResponse = new MountResponse();
        Mount mount = mountRepository.findByNameAndActive(name,true);
        if(mount==null){
            mountResponse.setError(true);
            mountResponse.setError_description("Mulberry Variety not found");
        }else{
            mountResponse = mapper.mountEntityToObject(mount, MountResponse.class);
            mountResponse.setError(false);
        }
        log.info("Entity is ",mount);
        return mountResponse;
    }

    @Transactional
    public MountResponse insertMountDetails(MountRequest mountRequest){
        MountResponse mountResponse = new MountResponse();
        Mount mount = mapper.mountObjectToEntity(mountRequest,Mount.class);
        validator.validate(mount);
        List<Mount> mountList = mountRepository.findByName(mountRequest.getName());
        if(!mountList.isEmpty() && mountList.stream().filter(Mount::getActive).findAny().isPresent()){
            mountResponse.setError(true);
            mountResponse.setError_description("Mount name already exist");
//        }
//        else if(!mountList.isEmpty() && mountList.stream().filter(Predicate.not(Mount::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            mountResponse.setError(true);
//            mountResponse.setError_description("Mount name already exist with inactive state");
        }else {
            mountResponse = mapper.mountEntityToObject(mountRepository.save(mount), MountResponse.class);
            mountResponse.setError(false);
        }
        return mountResponse;
    }

    public Map<String,Object> getPaginatedMountDetails(final Pageable pageable){
        return convertToMapResponse(mountRepository.findByActiveOrderByNameAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(mountRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<Mount> activeMounts) {
        Map<String, Object> response = new HashMap<>();

        List<MountResponse> mountResponses = activeMounts.getContent().stream()
                .map(mount -> mapper.mountEntityToObject(mount,MountResponse.class)).collect(Collectors.toList());
        response.put("mount",mountResponses);
        response.put("currentPage", activeMounts.getNumber());
        response.put("totalItems", activeMounts.getTotalElements());
        response.put("totalPages", activeMounts.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<Mount> activeMounts) {
        Map<String, Object> response = new HashMap<>();

        List<MountResponse> mountResponses = activeMounts.stream()
                .map(mount -> mapper.mountEntityToObject(mount,MountResponse.class)).collect(Collectors.toList());
        response.put("mount",mountResponses);
        return response;
    }

    @Transactional
    public MountResponse deleteMountDetails(long id) {
        MountResponse mountResponse = new MountResponse();
        Mount mount = mountRepository.findByMountIdAndActive(id, true);
        if (Objects.nonNull(mount)) {
            mount.setActive(false);
            mountResponse = mapper.mountEntityToObject(mountRepository.save(mount), MountResponse.class);
            mountResponse.setError(false);
        } else {
            mountResponse.setError(true);
            mountResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return mountResponse;
    }

    public MountResponse getById(int id){
        MountResponse mountResponse = new MountResponse();
        Mount mount = mountRepository.findByMountIdAndActive(id,true);
        if(mount == null){
            mountResponse.setError(true);
            mountResponse.setError_description("Invalid id");
        }else{
            mountResponse =  mapper.mountEntityToObject(mount,MountResponse.class);
            mountResponse.setError(false);
        }
        log.info("Entity is ",mount);
        return mountResponse;
    }

    @Transactional
    public MountResponse updateMountDetails(EditMountRequest mountRequest){
        MountResponse mountResponse = new MountResponse();
        List<Mount> mountList = mountRepository.findByActiveAndName(true,mountRequest.getName());
        if(mountList.size()>0){
            mountResponse.setError(true);
            mountResponse.setError_description("Mount already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {

            Mount mount = mountRepository.findByMountIdAndActiveIn(mountRequest.getMountId(), Set.of(true,false));
            if(Objects.nonNull(mount)){
                mount.setName(mountRequest.getName());
                mount.setActive(true);
                Mount mount1 = mountRepository.save(mount);
                mountResponse = mapper.mountEntityToObject(mount1, MountResponse.class);
                mountResponse.setError(false);
            } else {
                mountResponse.setError(true);
                mountResponse.setError_description("Error occurred while fetching mount");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return mountResponse;
    }
}
