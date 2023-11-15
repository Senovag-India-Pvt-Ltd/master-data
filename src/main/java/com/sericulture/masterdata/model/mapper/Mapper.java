package com.sericulture.masterdata.model.mapper;

;
import com.sericulture.masterdata.model.api.binCounterMaster.BinCounterMasterRequest;
import com.sericulture.masterdata.model.api.binMaster.BinMasterRequest;
import com.sericulture.masterdata.model.api.caste.CasteRequest;
import com.sericulture.masterdata.model.api.district.DistrictRequest;
import com.sericulture.masterdata.model.api.education.EducationRequest;
import com.sericulture.masterdata.model.api.externalUnitType.ExternalUnitTypeRequest;
import com.sericulture.masterdata.model.api.godown.GodownRequest;
import com.sericulture.masterdata.model.api.hobli.HobliRequest;
import com.sericulture.masterdata.model.api.irrigationSource.IrrigationSourceRequest;
import com.sericulture.masterdata.model.api.landOwnership.LandOwnershipRequest;
import com.sericulture.masterdata.model.api.machineTypeMaster.MachineTypeMasterRequest;
import com.sericulture.masterdata.model.api.marketMaster.MarketMasterRequest;
import com.sericulture.masterdata.model.api.mulberrySource.MulberrySourceRequest;
import com.sericulture.masterdata.model.api.mulberryVariety.MulberryVarietyRequest;
import com.sericulture.masterdata.model.api.plantationType.PlantationTypeRequest;
import com.sericulture.masterdata.model.api.raceMaster.RaceMasterRequest;
import com.sericulture.masterdata.model.api.reasonBidRejectMaster.ReasonBidRejectMasterRequest;
import com.sericulture.masterdata.model.api.reasonLotRejectMaster.ReasonLotRejectMasterRequest;
import com.sericulture.masterdata.model.api.relationship.RelationshipRequest;
//import com.sericulture.masterdata.model.api.hobli.HobliRequest;
import com.sericulture.masterdata.model.api.landCategory.LandCategoryRequest;
import com.sericulture.masterdata.model.api.role.RoleRequest;
import com.sericulture.masterdata.model.api.roofType.RoofTypeRequest;
import com.sericulture.masterdata.model.api.rpPagePermission.RpPagePermissionRequest;
import com.sericulture.masterdata.model.api.soilType.SoilTypeRequest;
import com.sericulture.masterdata.model.api.silkwormvariety.SilkWormVarietyRequest;
import com.sericulture.masterdata.model.api.sourceMaster.SourceMasterRequest;
import com.sericulture.masterdata.model.api.state.StateRequest;
import com.sericulture.masterdata.model.api.subsidy.SubsidyRequest;
import com.sericulture.masterdata.model.api.traderTypeMaster.TraderTypeMasterRequest;
import com.sericulture.masterdata.model.api.village.VillageRequest;
import com.sericulture.masterdata.model.dto.DistrictDTO;
import com.sericulture.masterdata.model.dto.HobliDTO;
import com.sericulture.masterdata.model.dto.TalukDTO;
import com.sericulture.masterdata.model.dto.VillageDTO;
import com.sericulture.masterdata.model.entity.*;
import com.sericulture.masterdata.model.api.taluk.TalukRequest;
//import com.sericulture.masterdata.model.api.village.VillageRequest;
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
     * Maps DistrictDTO to District Response Object
     * @param districtDTO
     * @param <T>
     */
    public <T> T districtDTOToObject(DistrictDTO districtDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, districtDTO);
        return (T) mapper.map(districtDTO, claaz);
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
     * Maps TalukDTO to Taluk Response Object
     * @param talukDTO
     * @param <T>
     */
    public <T> T talukDTOToObject(TalukDTO talukDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, talukDTO);
        return (T) mapper.map(talukDTO, claaz);
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
    public <T> T hobliEntityToObject(Hobli hobliEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, hobliEntity);
        return (T) mapper.map(hobliEntity, claaz);

    }

    /**
     * Maps HobliDTO to Hobli Response Object
     * @param hobliDTO
     * @param <T>
     */
    public <T> T hobliDTOToObject(HobliDTO hobliDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, hobliDTO);
        return (T) mapper.map(hobliDTO, claaz);
    }

    /**
     * Maps Hobli Entity to Hobli Response Object
     * @param hobliRequest
     * @param <T>
     */
    public <T> T hobliObjectToEntity(HobliRequest hobliRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, hobliRequest);
        return (T) mapper.map(hobliRequest, claaz);
    }

    /**
     * Maps Village Entity to Village Response Object
     * @param villageEntity
     * @param <T>
     */
    public <T> T villageEntityToObject(Village villageEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, villageEntity);
        return (T) mapper.map(villageEntity, claaz);
    }
    /**
     * Maps VillageDTO to Village Response Object
     * @param villageDTO
     * @param <T>
     */
    public <T> T villageDTOToObject(VillageDTO villageDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, villageDTO);
        return (T) mapper.map(villageDTO, claaz);
    }

    /**
     * Maps Village Entity to Village Response Object
     * @param villageRequest
     * @param <T>
     */
    public <T> T villageObjectToEntity(VillageRequest villageRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, villageRequest);
        return (T) mapper.map(villageRequest, claaz);
    }



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
    /**
     * Maps LandOwnership Entity to LandOwnership Response Object
     * @param landOwnershipEntity
     * @param <T>
     */
    public <T> T landOwnershipEntityToObject(LandOwnership landOwnershipEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, landOwnershipEntity);
        return (T) mapper.map(landOwnershipEntity, claaz);
    }

    /**
     * Maps LandOwnership Object to LandOwnership Response Entity
     * @param landOwnershipRequest
     * @param <T>
     */
    public <T> T landOwnershipObjectToEntity(LandOwnershipRequest landOwnershipRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, landOwnershipRequest);
        return (T) mapper.map(landOwnershipRequest, claaz);
    }
    /**
     * Maps Relationship Entity to Relationship Response Object
     * @param relationshipEntity
     * @param <T>
     */
    public <T> T relationshipEntityToObject(Relationship relationshipEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, relationshipEntity);
        return (T) mapper.map(relationshipEntity, claaz);
    }

    /**
     * Maps Relationship Object to Relationship Response Entity
     * @param relationshipRequest
     * @param <T>
     */
    public <T> T relationshipObjectToEntity(RelationshipRequest relationshipRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, relationshipRequest);
        return (T) mapper.map(relationshipRequest, claaz);
    }
    /**
     * Maps Soil Type Entity to Soil Type Response Object
     * @param soilTypeEntity
     * @param <T>
     */
    public <T> T soilTypeEntityToObject(SoilType soilTypeEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, soilTypeEntity);
        return (T) mapper.map(soilTypeEntity, claaz);
    }

    /**
     * Maps Soil Type Object to Soil Type Response Entity
     * @param soilTypeRequest
     * @param <T>
     */
    public <T> T soilTypeObjectToEntity(SoilTypeRequest soilTypeRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, soilTypeRequest);
        return (T) mapper.map(soilTypeRequest, claaz);
    }

    /**
     * Maps SilkWormVariety Entity to SilkWormVariety Response Object
     * @param silkWormVarietyEntity
     * @param <T>
     */
    public <T> T silkWormVarietyEntityToObject(SilkWormVariety silkWormVarietyEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, silkWormVarietyEntity);
        return (T) mapper.map(silkWormVarietyEntity, claaz);
    }

    /**
     * Maps SilkWormVariety Object to SilkWormVariety Response Entity
     * @param silkWormVarietyRequest
     * @param <T>
     */
    public <T> T silkWormVarietyObjectToEntity(SilkWormVarietyRequest silkWormVarietyRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, silkWormVarietyRequest);
        return (T) mapper.map(silkWormVarietyRequest, claaz);
    }

    /**
     * Maps MulberrySource Entity to MulberrySource Response Object
     * @param mulberrySourceEntity
     * @param <T>
     */
    public <T> T mulberrySourceEntityToObject(MulberrySource mulberrySourceEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, mulberrySourceEntity);
        return (T) mapper.map(mulberrySourceEntity, claaz);
    }

    /**
     * Maps MulberrySource Object to MulberrySource Response Entity
     * @param mulberrySourceRequest
     * @param <T>
     */
    public <T> T mulberrySourceObjectToEntity(MulberrySourceRequest mulberrySourceRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, mulberrySourceRequest);
        return (T) mapper.map(mulberrySourceRequest, claaz);
    }
    /**
     * Maps RoofType Entity to RoofType Response Object
     * @param roofTypeEntity
     * @param <T>
     */
    public <T> T roofTypeEntityToObject(RoofType roofTypeEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, roofTypeEntity);
        return (T) mapper.map(roofTypeEntity, claaz);
    }

    /**
     * Maps RoofType Object to RoofType Response Entity
     * @param roofTypeRequest
     * @param <T>
     */
    public <T> T roofTypeObjectToEntity(RoofTypeRequest roofTypeRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, roofTypeRequest);
        return (T) mapper.map(roofTypeRequest, claaz);
    }

    /**
     * Maps MulberryVariety Entity to MulberryVariety Response Object
     * @param mulberryVarietyEntity
     * @param <T>
     */
    public <T> T mulberryVarietyEntityToObject(MulberryVariety mulberryVarietyEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, mulberryVarietyEntity);
        return (T) mapper.map(mulberryVarietyEntity, claaz);
    }

    /**
     * Maps MulberryVariety Object to MulberryVariety Response Entity
     * @param mulberryVarietyRequest
     * @param <T>
     */
    public <T> T mulberryVarietyObjectToEntity(MulberryVarietyRequest mulberryVarietyRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, mulberryVarietyRequest);
        return (T) mapper.map(mulberryVarietyRequest, claaz);
    }

    /**
     * Maps Godown Entity to Godown Response Object
     * @param godownEntity
     * @param <T>
     */
    public <T> T godownEntityToObject(Godown godownEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, godownEntity);
        return (T) mapper.map(godownEntity, claaz);
    }

    /**
     * Maps Godown Object to Godown Response Entity
     * @param godownRequest
     * @param <T>
     */
    public <T> T godownObjectToEntity(GodownRequest godownRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, godownRequest);
        return (T) mapper.map(godownRequest, claaz);
    }

    /**
     * Maps MachineType Entity to MachineType Response Object
     * @param machineMasterEntity
     * @param <T>
     */
    public <T> T machineTypeEntityToObject(MachineTypeMaster machineMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, machineMasterEntity);
        return (T) mapper.map(machineMasterEntity, claaz);
    }

    /**
     * Maps MachineMaster Object to MachineMaster Response Entity
     * @param machineTypeRequest
     * @param <T>
     */
    public <T> T machineTypeObjectToEntity(MachineTypeMasterRequest machineTypeRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, machineTypeRequest);
        return (T) mapper.map(machineTypeRequest, claaz);
    }
    /**
     * Maps PlantationType Entity to PlantationType Response Object
     * @param plantationTypeEntity
     * @param <T>
     */
    public <T> T plantationTypeEntityToObject(PlantationType plantationTypeEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, plantationTypeEntity);
        return (T) mapper.map(plantationTypeEntity, claaz);
    }

    /**
     * Maps PlantationType Object to PlantationType Response Entity
     * @param plantationTypeRequest
     * @param <T>
     */
    public <T> T plantationTypeObjectToEntity(PlantationTypeRequest plantationTypeRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, plantationTypeRequest);
        return (T) mapper.map(plantationTypeRequest, claaz);
    }

    /**
     * Maps ReasonLotReject Entity to ReasonLotReject Response Object
     * @param reasonLotRejectMasterEntity
     * @param <T>
     */
    public <T> T reasonLotRejectEntityToObject(ReasonLotRejectMaster reasonLotRejectMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, reasonLotRejectMasterEntity);
        return (T) mapper.map(reasonLotRejectMasterEntity, claaz);
    }

    /**
     * Maps ReasonLotReject Object to ReasonLotReject Response Entity
     * @param reasonLotRejectMasterRequest
     * @param <T>
     */
    public <T> T reasonLotRejectObjectToEntity(ReasonLotRejectMasterRequest reasonLotRejectMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, reasonLotRejectMasterRequest);
        return (T) mapper.map(reasonLotRejectMasterRequest, claaz);
    }
    /**
     * Maps ReasonBidReject Entity to ReasonBidReject Response Object
     * @param reasonBidRejectMasterEntity
     * @param <T>
     */
    public <T> T reasonBidRejectEntityToObject(ReasonBidRejectMaster reasonBidRejectMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, reasonBidRejectMasterEntity);
        return (T) mapper.map(reasonBidRejectMasterEntity, claaz);
    }

    /**
     * Maps ReasonBidReject Object to ReasonBidReject Response Entity
     * @param reasonBidRejectMasterRequest
     * @param <T>
     */
    public <T> T reasonBidRejectObjectToEntity(ReasonBidRejectMasterRequest reasonBidRejectMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, reasonBidRejectMasterRequest);
        return (T) mapper.map(reasonBidRejectMasterRequest, claaz);
    }

    /**
     * Maps Subsidy Entity to Subsidy Response Object
     * @param subsidyEntity
     * @param <T>
     */
    public <T> T subsidyEntityToObject(Subsidy subsidyEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, subsidyEntity);
        return (T) mapper.map(subsidyEntity, claaz);
    }

    /**
     * Maps Subsidy Object to Subsidy Response Object
     * @param subsidyRequest
     * @param <T>
     */
    public <T> T subsidyObjectToEntity(SubsidyRequest subsidyRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, subsidyRequest);
        return (T) mapper.map(subsidyRequest, claaz);
    }

    /**
     * Maps Role Entity to Role Response Object
     * @param roleEntity
     * @param <T>
     */
    public <T> T roleEntityToObject(Role roleEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, roleEntity);
        return (T) mapper.map(roleEntity, claaz);
    }

    /**
     * Maps Role Object to Role Response Object
     * @param roleRequest
     * @param <T>
     */
    public <T> T roleObjectToEntity(RoleRequest roleRequest, Class<T> claaz) {
        log.info("Value of mapper is:", mapper, roleRequest);
        return (T) mapper.map(roleRequest, claaz);
    }

        /**
         * Maps MarketMaster Entity to MarketMaster  Response Object
         * @param marketMasterEntity
         * @param <T>
         */
        public <T> T marketMasterEntityToObject(MarketMaster marketMasterEntity, Class<T> claaz) {
            log.info("Value of mapper is:",mapper,marketMasterEntity );
            return (T) mapper.map(marketMasterEntity, claaz);
        }

        /**
         * Maps MarketMaster  Object to MarketMaster  Response Object
         * @param marketMasterRequest
         * @param <T>
         */
        public <T> T marketMasterObjectToEntity(MarketMasterRequest marketMasterRequest, Class<T> claaz) {
            log.info("Value of mapper is:",mapper, marketMasterRequest);
            return (T) mapper.map(marketMasterRequest, claaz);
    }
    /**
     * Maps BinCounterMaster Entity to BinCounterMaster  Response Object
     * @param binCounterMasterEntity
     * @param <T>
     */
    public <T> T binCounterMasterEntityToObject(BinCounterMaster binCounterMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,binCounterMasterEntity );
        return (T) mapper.map(binCounterMasterEntity, claaz);
    }

    /**
     * Maps BinCounterMaster  Object to BinCounterMaster   Response Object
     * @param binCounterMasterRequest
     * @param <T>
     */
    public <T> T binCounterMasterObjectToEntity(BinCounterMasterRequest binCounterMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, binCounterMasterRequest);
        return (T) mapper.map(binCounterMasterRequest, claaz);
    }
    /**
     * Maps BinMaster Entity to BinMaster  Response Object
     * @param binMasterEntity
     * @param <T>
     */
    public <T> T binMasterEntityToObject(BinMaster binMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,binMasterEntity );
        return (T) mapper.map(binMasterEntity, claaz);
    }

    /**
     * Maps BinMaster  Object to BinMaster   Response Object
     * @param binMasterRequest
     * @param <T>
     */
    public <T> T binMasterObjectToEntity(BinMasterRequest binMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, binMasterRequest);
        return (T) mapper.map(binMasterRequest, claaz);
    }
    /**
     * Maps TraderType Entity to TraderType  Response Object
     * @param traderTypeMasterEntity
     * @param <T>
     */
    public <T> T traderTypeMasterEntityToObject(TraderTypeMaster traderTypeMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,traderTypeMasterEntity );
        return (T) mapper.map(traderTypeMasterEntity, claaz);
    }

    /**
     * Maps TraderType  Object to TraderType Response Object
     * @param traderTypeMasterRequest
     * @param <T>
     */
    public <T> T traderTypeMasterObjectToEntity(TraderTypeMasterRequest traderTypeMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, traderTypeMasterRequest);
        return (T) mapper.map(traderTypeMasterRequest, claaz);
    }
    /**
     * Maps ExternalUnitType Entity to ExternalUnitType  Response Object
     * @param externalUnitTypeEntity
     * @param <T>
     */
    public <T> T externalUnitTypeEntityToObject(ExternalUnitType externalUnitTypeEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,externalUnitTypeEntity );
        return (T) mapper.map(externalUnitTypeEntity, claaz);
    }

    /**
     * Maps ExternalUnitType Object to ExternalUnitType Response Object
     * @param externalUnitTypeRequest
     * @param <T>
     */
    public <T> T externalUnitTypeObjectToEntity(ExternalUnitTypeRequest externalUnitTypeRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, externalUnitTypeRequest);
        return (T) mapper.map(externalUnitTypeRequest, claaz);
    }
    /**
     * Maps Race Entity to Race Response Object
     * @param raceMasterEntity
     * @param <T>
     */
    public <T> T raceMasterEntityToObject(RaceMaster raceMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, raceMasterEntity);
        return (T) mapper.map(raceMasterEntity, claaz);
    }

    /**
     * Maps Race Object to Race Response Object
     * @param raceMasterRequest
     * @param <T>
     */
    public <T> T raceMasterObjectToEntity(RaceMasterRequest raceMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:", mapper, raceMasterRequest);
        return (T) mapper.map(raceMasterRequest, claaz);
    }
    /**
     * Maps Source Entity to Source Response Object
     * @param sourceMasterEntity
     * @param <T>
     */
    public <T> T sourceMasterEntityToObject(SourceMaster sourceMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, sourceMasterEntity);
        return (T) mapper.map(sourceMasterEntity, claaz);
    }

    /**
     * Maps Source Object to Source Response Object
     * @param sourceMasterRequest
     * @param <T>
     */
    public <T> T sourceMasterObjectToEntity(SourceMasterRequest sourceMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:", mapper, sourceMasterRequest);
        return (T) mapper.map(sourceMasterRequest, claaz);
    }

    /**
     * Maps RpPagePermission Entity to RpPagePermission Response Object
     * @param rpPagePermissionEntity
     * @param <T>
     */
    public <T> T rpPagePermissionEntityToObject(RpPagePermission rpPagePermissionEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, rpPagePermissionEntity);
        return (T) mapper.map(rpPagePermissionEntity, claaz);
    }

    /**
     * Maps RpPagePermission Object to RpPagePermission Response Object
     * @param rpPagePermissionRequest
     * @param <T>
     */
    public <T> T rpPagePermissionObjectToEntity(RpPagePermissionRequest rpPagePermissionRequest, Class<T> claaz) {
        log.info("Value of mapper is:", mapper, rpPagePermissionRequest);
        return (T) mapper.map(rpPagePermissionRequest, claaz);
    }
}
