package com.sericulture.masterdata.model.mapper;

;
import com.sericulture.masterdata.model.api.caste.CasteRequest;
import com.sericulture.masterdata.model.api.education.EducationRequest;
import com.sericulture.masterdata.model.entity.Caste;
import com.sericulture.masterdata.model.entity.Education;
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
}
