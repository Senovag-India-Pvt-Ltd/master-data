package com.sericulture.masterdata.model.mapper;

;
import com.sericulture.masterdata.model.api.caste.CasteRequest;
import com.sericulture.masterdata.model.api.district.DistrictRequest;
import com.sericulture.masterdata.model.api.education.EducationRequest;
import com.sericulture.masterdata.model.api.irrigation_source.IrrigationSourceRequest;
import com.sericulture.masterdata.model.api.relationship.RelationshipRequest;
//import com.sericulture.masterdata.model.api.hobli.HobliRequest;
import com.sericulture.masterdata.model.api.landCategory.LandCategoryRequest;
import com.sericulture.masterdata.model.api.state.StateRequest;
import com.sericulture.masterdata.model.entity.*;
import com.sericulture.masterdata.model.api.taluk.TalukRequest;
//import com.sericulture.masterdata.model.api.village.VillageRequest;
import com.sericulture.masterdata.model.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class Mapper {

    @Autowired
    ModelMapper mapper;

    /**
     * Maps Education Entity to Education Response Object
     * @param educationEntity
     * @param <T>
     */
    public <T> T educationEntityToObject(Education educationEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, educationEntity);
        return (T) mapper.map(educationEntity, claaz);
    }

    /**
     * Maps Education Entity to Education Response Object
     * @param educationRequest
     * @param <T>
     */
    public <T> T educationObjectToEntity(EducationRequest educationRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, educationRequest);
        return (T) mapper.map(educationRequest, claaz);
    }

    /**
     * Maps Caste Entity to Caste Response Object
     * @param casteEntity
     * @param <T>
     */
    public <T> T casteEntityToObject(Caste casteEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, casteEntity);
        return (T) mapper.map(casteEntity, claaz);
    }

    /**
     * Maps Education Entity to Education Response Object
     * @param casteRequest
     * @param <T>
     */
    public <T> T casteObjectToEntity(CasteRequest casteRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, casteRequest);
        return (T) mapper.map(casteRequest, claaz);
    }

    /**
     * Maps State Entity to State Response Object
     * @param stateEntity
     * @param <T>
     */
    public <T> T stateEntityToObject(State stateEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, stateEntity);
        return (T) mapper.map(stateEntity, claaz);
    }

    /**
     * Maps State Entity to State Response Object
     * @param stateRequest
     * @param <T>
     */
    public <T> T stateObjectToEntity(StateRequest stateRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, stateRequest);
        return (T) mapper.map(stateRequest, claaz);
    }

    /**
     * Maps District Entity to District Response Object
     * @param districtEntity
     * @param <T>
     */
    public <T> T districtEntityToObject(District districtEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, districtEntity);
        return (T) mapper.map(districtEntity, claaz);
    }

    /**
     * Maps District Entity to District Response Object
     * @param districtRequest
     * @param <T>
     */
    public <T> T districtObjectToEntity(DistrictRequest districtRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, districtRequest);
        return (T) mapper.map(districtRequest, claaz);
    }
    /**
     * Maps IrrigationSource Entity to IrrigationSource Response Object
     * @param irrigationSourceEntity
     * @param <T>
     */
    public <T> T irrigationSourceEntityToObject(IrrigationSource irrigationSourceEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, irrigationSourceEntity);
        return (T) mapper.map(irrigationSourceEntity, claaz);
    }

    /**
     * Maps IrrigationSource Object to IrrigationSource Response Entity
     * @param irrigationSourceRequest
     * @param <T>
     */
    public <T> T irrigationSourceObjectToEntity(IrrigationSourceRequest irrigationSourceRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, irrigationSourceRequest);
        return (T) mapper.map(irrigationSourceRequest, claaz);
    }



    /**
     * Maps Taluk Entity to Taluk Response Object
     * @param talukEntity
     * @param <T>
     */
    public <T> T talukEntityToObject(Taluk talukEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, talukEntity);
        return (T) mapper.map(talukEntity, claaz);
    }

    /**
     * Maps Taluk Entity to Taluk Response Object
     * @param talukRequest
     * @param <T>
     */
    public <T> T talukObjectToEntity(TalukRequest talukRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, talukRequest);
        return (T) mapper.map(talukRequest, claaz);
    }

    /**
     * Maps Hobli Entity to Hobli Response Object
     * @param hobliEntity
     * @param <T>
     */
   /* public <T> T hobliEntityToObject(Hobli hobliEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, hobliEntity);
        return (T) mapper.map(hobliEntity, claaz);
    }*/

    /**
     * Maps Hobli Entity to Hobli Response Object
     * @param hobliRequest
     * @param <T>
     */
  /*  public <T> T hobliObjectToEntity(HobliRequest hobliRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, hobliRequest);
        return (T) mapper.map(hobliRequest, claaz);
    }*/

    /**
     * Maps Village Entity to Village Response Object
     * @param villageEntity
     * @param <T>
     */
    /*public <T> T villageEntityToObject(Village villageEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, villageEntity);
        return (T) mapper.map(villageEntity, claaz);
    }*/

    /**
     * Maps Village Entity to Village Response Object
     * @param villageRequest
     * @param <T>
     */
   /* public <T> T villageObjectToEntity(VillageRequest villageRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, villageRequest);
        return (T) mapper.map(villageRequest, claaz);
    }*/



    /**
     * Maps LandCategory Entity to LandCategory Response Object
     * @param landCategoryEntity
     * @param <T>
     */
    public <T> T landCategoryEntityToObject(LandCategory landCategoryEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, landCategoryEntity);
        return (T) mapper.map(landCategoryEntity, claaz);
    }

    /**
     * Maps State Entity to State Response Object
     * @param landCategoryRequest
     * @param <T>
     */
    public <T> T landCategoryObjectToEntity(LandCategoryRequest landCategoryRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, landCategoryRequest);
        return (T) mapper.map(landCategoryRequest, claaz);
    }
}
