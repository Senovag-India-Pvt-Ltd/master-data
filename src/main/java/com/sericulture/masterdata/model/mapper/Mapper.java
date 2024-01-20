package com.sericulture.masterdata.model.mapper;

;
import com.sericulture.masterdata.model.api.binCounterMaster.BinCounterMasterRequest;
import com.sericulture.masterdata.model.api.binMaster.BinMasterRequest;
import com.sericulture.masterdata.model.api.caste.CasteRequest;
import com.sericulture.masterdata.model.api.crateMaster.CrateMasterRequest;
import com.sericulture.masterdata.model.api.deputedInstituteMaster.DeputedInstituteMasterRequest;
import com.sericulture.masterdata.model.api.designation.DesignationRequest;
import com.sericulture.masterdata.model.api.district.DistrictRequest;
import com.sericulture.masterdata.model.api.documentMaster.DocumentMasterRequest;
import com.sericulture.masterdata.model.api.education.EducationRequest;
import com.sericulture.masterdata.model.api.externalUnitType.ExternalUnitTypeRequest;
import com.sericulture.masterdata.model.api.godown.GodownRequest;
import com.sericulture.masterdata.model.api.hobli.HobliRequest;
import com.sericulture.masterdata.model.api.irrigationSource.IrrigationSourceRequest;
import com.sericulture.masterdata.model.api.irrigationType.IrrigationTypeRequest;
import com.sericulture.masterdata.model.api.landOwnership.LandOwnershipRequest;
import com.sericulture.masterdata.model.api.machineTypeMaster.MachineTypeMasterRequest;
import com.sericulture.masterdata.model.api.marketMaster.MarketMasterRequest;
import com.sericulture.masterdata.model.api.marketTypeMaster.MarketTypeMasterRequest;
import com.sericulture.masterdata.model.api.mulberrySource.MulberrySourceRequest;
import com.sericulture.masterdata.model.api.mulberryVariety.MulberryVarietyRequest;
import com.sericulture.masterdata.model.api.plantationType.PlantationTypeRequest;
import com.sericulture.masterdata.model.api.raceMarketMaster.RaceMarketMasterRequest;
import com.sericulture.masterdata.model.api.raceMaster.RaceMasterRequest;
import com.sericulture.masterdata.model.api.reasonBidRejectMaster.ReasonBidRejectMasterRequest;
import com.sericulture.masterdata.model.api.reasonLotRejectMaster.ReasonLotRejectMasterRequest;
import com.sericulture.masterdata.model.api.relationship.RelationshipRequest;
//import com.sericulture.masterdata.model.api.hobli.HobliRequest;
import com.sericulture.masterdata.model.api.landCategory.LandCategoryRequest;
import com.sericulture.masterdata.model.api.reelerTypeMaster.ReelerTypeMasterRequest;
import com.sericulture.masterdata.model.api.role.RoleRequest;
import com.sericulture.masterdata.model.api.roofType.RoofTypeRequest;
import com.sericulture.masterdata.model.api.rpPageRoot.RpPageRootRequest;
import com.sericulture.masterdata.model.api.rpRoleAssociation.RpRoleAssociationRequest;
import com.sericulture.masterdata.model.api.rpRolePermission.RpRolePermissionRequest;
import com.sericulture.masterdata.model.api.scComponent.ScComponentRequest;
import com.sericulture.masterdata.model.api.scHeadAccount.ScHeadAccountRequest;
import com.sericulture.masterdata.model.api.scProgram.ScProgramRequest;
import com.sericulture.masterdata.model.api.rpPagePermission.RpPagePermissionRequest;
import com.sericulture.masterdata.model.api.soilType.SoilTypeRequest;
import com.sericulture.masterdata.model.api.silkwormvariety.SilkWormVarietyRequest;
import com.sericulture.masterdata.model.api.sourceMaster.SourceMasterRequest;
import com.sericulture.masterdata.model.api.state.StateRequest;
import com.sericulture.masterdata.model.api.subsidy.SubsidyRequest;
import com.sericulture.masterdata.model.api.trCourseMaster.TrCourseMasterRequest;
import com.sericulture.masterdata.model.api.trGroupMaster.TrGroupMasterRequest;
import com.sericulture.masterdata.model.api.trInstitutionMaster.TrInstitutionMasterRequest;
import com.sericulture.masterdata.model.api.trModeMaster.TrModeMasterRequest;
import com.sericulture.masterdata.model.api.trOffice.TrOfficeRequest;
import com.sericulture.masterdata.model.api.trProgramMaster.TrProgramMasterRequest;
import com.sericulture.masterdata.model.api.trSchedule.TrScheduleRequest;
import com.sericulture.masterdata.model.api.trTrainee.TrTraineeRequest;
import com.sericulture.masterdata.model.api.trTraining.TrTrainingRequest;
import com.sericulture.masterdata.model.api.traderTypeMaster.TraderTypeMasterRequest;
import com.sericulture.masterdata.model.api.trainingDeputationTracker.TrainingDeputationTrackerRequest;
import com.sericulture.masterdata.model.api.useMaster.UserMasterRequest;
import com.sericulture.masterdata.model.api.userPreference.UserPreferenceRequest;
import com.sericulture.masterdata.model.api.vendorMaster.VendorMasterRequest;
import com.sericulture.masterdata.model.api.village.VillageRequest;
import com.sericulture.masterdata.model.api.workingInstitution.WorkingInstitutionRequest;
import com.sericulture.masterdata.model.dto.*;
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
     * Maps IrrigationType Entity to IrrigationType Response Object
     * @param irrigationTypeEntity
     * @param <T>
     */
    public <T> T irrigationTypeEntityToObject(IrrigationType irrigationTypeEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, irrigationTypeEntity);
        return (T) mapper.map(irrigationTypeEntity, claaz);
    }

    /**
     * Maps IrrigationType Object to IrrigationType Response Entity
     * @param irrigationTypeRequest
     * @param <T>
     */
    public <T> T irrigationTypeObjectToEntity(IrrigationTypeRequest irrigationTypeRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, irrigationTypeRequest);
        return (T) mapper.map(irrigationTypeRequest, claaz);
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
     * Maps Godown DTO to godown Response Object
     * @param godownDTO
     * @param <T>
     */
    public <T> T godownDTOToObject(GodownDTO godownDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, godownDTO);
        return (T) mapper.map(godownDTO, claaz);
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
     * Maps rpRoleAssociation DTO to rpRoleAssociation Response Object
     * @param marketMasterDTO
     * @param <T>
     */
    public <T> T marketMasterDTOToObject(MarketMasterDTO marketMasterDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, marketMasterDTO);
        return (T) mapper.map(marketMasterDTO, claaz);
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
     * Maps RaceMasterDTO to RaceMaster Response Object
     * @param raceMasterDTO
     * @param <T>
     */
    public <T> T raceMasterDTOToObject(RaceMasterDTO raceMasterDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, raceMasterDTO);
        return (T) mapper.map(raceMasterDTO, claaz);
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
     * Maps Rp Page Root Entity to Rp Page Root Response Object
     * @param rpPageRootEntity
     * @param <T>
     */
    public <T> T rpPageRootEntityToObject(RpPageRoot rpPageRootEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, rpPageRootEntity);
        return (T) mapper.map(rpPageRootEntity, claaz);
    }

    /**
     * Maps Rp Page Root Object to Rp Page Root Response Object
     * @param rpPageRootRequest
     * @param <T>
     */
    public <T> T rpPageRootObjectToEntity(RpPageRootRequest rpPageRootRequest, Class<T> claaz) {
        log.info("Value of mapper is:", mapper, rpPageRootRequest);
        return (T) mapper.map(rpPageRootRequest, claaz);
    }

    /**
     * Maps rpRoleAssociation Entity to rpRoleAssociation Response Object
     * @param rpRoleAssociationEntity
     * @param <T>
     */
    public <T> T rpRoleAssociationEntityToObject(RpRoleAssociation rpRoleAssociationEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, rpRoleAssociationEntity);
        return (T) mapper.map(rpRoleAssociationEntity, claaz);
    }

    /**
     * Maps rpRoleAssociation DTO to rpRoleAssociation Response Object
     * @param rpRoleAssociationDTO
     * @param <T>
     */
    public <T> T rpRoleAssociationDTOToObject(RpRoleAssociationDTO rpRoleAssociationDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, rpRoleAssociationDTO);
        return (T) mapper.map(rpRoleAssociationDTO, claaz);
    }

    /**
     * Maps rpRoleAssociation Object to rpRoleAssociation Response Object
     * @param rpRoleAssociationRequest
     * @param <T>
     */
    public <T> T rpRoleAssociationObjectToEntity(RpRoleAssociationRequest rpRoleAssociationRequest, Class<T> claaz) {
        log.info("Value of mapper is:", mapper, rpRoleAssociationRequest);
        return (T) mapper.map(rpRoleAssociationRequest, claaz);
    }

    /**
     * Maps rpRolePermission Entity to rpRolePermission Response Object
     * @param rpRolePermissionEntity
     * @param <T>
     */
    public <T> T rpRolePermissionEntityToObject(RpRolePermission rpRolePermissionEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, rpRolePermissionEntity);
        return (T) mapper.map(rpRolePermissionEntity, claaz);
    }

    /**
     * Maps rpRoleAssociation Object to rpRoleAssociation Response Object
     * @param rpRolePermissionRequest
     * @param <T>
     */
    public <T> T rpRolePermissionObjectToEntity(RpRolePermissionRequest rpRolePermissionRequest, Class<T> claaz) {
        log.info("Value of mapper is:", mapper, rpRolePermissionRequest);
        return (T) mapper.map(rpRolePermissionRequest, claaz);
    }

    /**
     * Maps SC Program Entity SC Program Response Object
     * @param scProgramEntity
     * @param <T>
     */
    public <T> T scProgramEntityToObject(ScProgram scProgramEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scProgramEntity);
        return (T) mapper.map(scProgramEntity, claaz);
    }

    /**
     * Maps SC Program Object to SC Program Response Object
     * @param scProgramRequest
     * @param <T>
     */
    public <T> T scProgramObjectToEntity(ScProgramRequest scProgramRequest, Class<T> claaz) {
        log.info("Value of mapper is:", mapper, scProgramRequest);
        return (T) mapper.map(scProgramRequest, claaz);
    }
    /**
     * Maps SC Component Entity to SC Component Response Object
     * @param scComponentEntity
     * @param <T>
     */
    public <T> T scComponentEntityToObject(ScComponent scComponentEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scComponentEntity);
        return (T) mapper.map(scComponentEntity, claaz);
    }

    /**
     * Maps SC Component Object to SC Component Response Object
     * @param scComponentRequest
     * @param <T>
     */
    public <T> T scComponentObjectToEntity(ScComponentRequest scComponentRequest, Class<T> claaz) {
        log.info("Value of mapper is:", mapper, scComponentRequest);
        return (T) mapper.map(scComponentRequest, claaz);
    }
    /**
     * Maps SC Head Account Entity to SC Head Account Response Object
     * @param scHeadAccountEntity
     * @param <T>
     */
    public <T> T scHeadAccountEntityToObject(ScHeadAccount scHeadAccountEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scHeadAccountEntity);
        return (T) mapper.map(scHeadAccountEntity, claaz);
    }

    /**
     * Maps SC Head Account Object to SC Head Account Response Object
     * @param scHeadAccountRequest
     * @param <T>
     */
    public <T> T scHeadAccountObjectToEntity(ScHeadAccountRequest scHeadAccountRequest, Class<T> claaz) {
        log.info("Value of mapper is:", mapper, scHeadAccountRequest);
        return (T) mapper.map(scHeadAccountRequest, claaz);
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
    /**
     * Maps VendorMaster Entity to VendorMaster  Response Object
     * @param vendorMasterEntity
     * @param <T>
     */
    public <T> T vendorMasterEntityToObject(VendorMaster vendorMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,vendorMasterEntity );
        return (T) mapper.map(vendorMasterEntity, claaz);
    }

    /**
     * Maps VendorMaster  Object to VendorMaster  Response Object
     * @param vendorMasterRequest
     * @param <T>
     */
    public <T> T vendorMasterObjectToEntity(VendorMasterRequest vendorMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, vendorMasterRequest);
        return (T) mapper.map(vendorMasterRequest, claaz);
    }

    /**
     * Maps userMaster Entity to userMaster  Response Object
     * @param userMasterEntity
     * @param <T>
     */
    public <T> T userMasterEntityToObject(UserMaster userMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,userMasterEntity );
        return (T) mapper.map(userMasterEntity, claaz);
    }

    /**
     * Maps UserMasterDTO to UserMaster Response Object
     * @param userMasterDTO
     * @param <T>
     */
    public <T> T userMasterDTOToObject(UserMasterDTO userMasterDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, userMasterDTO);
        return (T) mapper.map(userMasterDTO, claaz);
    }

    /**
     * Maps userMaster  Object to userMaster  Response Object
     * @param userMasterRequest
     * @param <T>
     */
    public <T> T userMasterObjectToEntity(UserMasterRequest userMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, userMasterRequest);
        return (T) mapper.map(userMasterRequest, claaz);
    }


    /**
     * Maps Designation Entity to Designation  Response Object
     * @param designationEntity
     * @param <T>
     */
    public <T> T designationEntityToObject(Designation designationEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,designationEntity );
        return (T) mapper.map(designationEntity, claaz);
    }

    /**
     * Maps Designation  Object to Designation  Response Object
     * @param designationRequest
     * @param <T>
     */
    public <T> T designationObjectToEntity(DesignationRequest designationRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, designationRequest);
        return (T) mapper.map(designationRequest, claaz);
    }

    /**
     * Maps Document Entity to Document  Response Object
     * @param documentMasterEntity
     * @param <T>
     */
    public <T> T documentMasterEntityToObject(DocumentMaster documentMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,documentMasterEntity );
        return (T) mapper.map(documentMasterEntity, claaz);
    }

    /**
     * Maps Document  Object to Document  Response Object
     * @param documentMasterRequest
     * @param <T>
     */
    public <T> T documentMasterObjectToEntity(DocumentMasterRequest documentMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, documentMasterRequest);
        return (T) mapper.map(documentMasterRequest, claaz);
    }
    /**
     * Maps MarketType Entity to Document  Response Object
     * @param marketTypeMasterEntity
     * @param <T>
     */
    public <T> T marketTypeMasterEntityToObject(MarketTypeMaster marketTypeMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,marketTypeMasterEntity );
        return (T) mapper.map(marketTypeMasterEntity, claaz);
    }

    /**
     * Maps MarketType  Object to Document  Response Object
     * @param marketTypeMasterRequest
     * @param <T>
     */
    public <T> T marketTypeMasterObjectToEntity(MarketTypeMasterRequest marketTypeMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, marketTypeMasterRequest);
        return (T) mapper.map(marketTypeMasterRequest, claaz);
    }
    /**
     * Maps CrateMaster Entity to Document  Response Object
     * @param crateMasterEntity
     * @param <T>
     */
    public <T> T crateMasterEntityToObject(CrateMaster crateMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,crateMasterEntity );
        return (T) mapper.map(crateMasterEntity, claaz);
    }

    /**
     * Maps CrateMasterDTO to CrateMaster Response Object
     * @param crateMasterDTO
     * @param <T>
     */
    public <T> T crateMasterDTOToObject(CrateMasterDTO crateMasterDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, crateMasterDTO);
        return (T) mapper.map(crateMasterDTO, claaz);
    }

    /**
     * Maps MarketType  Object to Document  Response Object
     * @param crateMasterRequest
     * @param <T>
     */
    public <T> T crateMasterObjectToEntity(CrateMasterRequest crateMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, crateMasterRequest);
        return (T) mapper.map(crateMasterRequest, claaz);
    }

    /**
     * Maps UserPreference Entity to UserPreference  Response Object
     * @param userPreferenceEntity
     * @param <T>
     */
    public <T> T userPreferenceEntityToObject(UserPreference userPreferenceEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,userPreferenceEntity );
        return (T) mapper.map(userPreferenceEntity, claaz);
    }

    /**
     * Maps rpRoleAssociation DTO to rpRoleAssociation Response Object
     * @param userPreferenceDTO
     * @param <T>
     */
    public <T> T userPreferenceDTOToObject(UserPreferenceDTO userPreferenceDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, userPreferenceDTO);
        return (T) mapper.map(userPreferenceDTO, claaz);
    }


    /**
     * Maps userPreference  Object to userPreference Response Object
     * @param userPreferenceRequest
     * @param <T>
     */
    public <T> T userPreferenceObjectToEntity(UserPreferenceRequest userPreferenceRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, userPreferenceRequest);
        return (T) mapper.map(userPreferenceRequest, claaz);
    }

    /**
     * Maps Working Institution Entity to Working Institution Response Object
     * @param workingInstitutionEntity
     * @param <T>
     */
    public <T> T workingInstitutionEntityToObject(WorkingInstitution workingInstitutionEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, workingInstitutionEntity);
        return (T) mapper.map(workingInstitutionEntity, claaz);
    }

    /**
     * Maps Working Institution Entity to Working Institution Response Object
     * @param workingInstitutionRequest
     * @param <T>
     */
    public <T> T workingInstitutionObjectToEntity(WorkingInstitutionRequest workingInstitutionRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, workingInstitutionRequest);
        return (T) mapper.map(workingInstitutionRequest, claaz);
    }
    /**
     * Maps trCourseMaster Entity to trCourseMaster  Response Object
     * @param trCourseMasterEntity
     * @param <T>
     */
    public <T> T trCourseMasterEntityToObject(TrCourseMaster trCourseMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,trCourseMasterEntity);
        return (T) mapper.map(trCourseMasterEntity, claaz);
    }


    /**
     * Maps trCourseMaster Object to trCourseMaster  Response Object
     * @param trCourseMasterRequest
     * @param <T>
     */
    public <T> T trCourseMasterObjectToEntity(TrCourseMasterRequest trCourseMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, trCourseMasterRequest);
        return (T) mapper.map(trCourseMasterRequest, claaz);
    }
    /**
     * Maps trProgramMaster Entity to trProgramMaster Response Object
     * @param trProgramMasterEntity
     * @param <T>
     */
    public <T> T trProgramMasterEntityToObject(TrProgramMaster trProgramMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,trProgramMasterEntity);
        return (T) mapper.map(trProgramMasterEntity, claaz);
    }


    /**
     * Maps trProgramMaster Object to trProgramMaster  Response Object
     * @param trProgramMasterRequest
     * @param <T>
     */
    public <T> T trProgramMasterObjectToEntity(TrProgramMasterRequest trProgramMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, trProgramMasterRequest);
        return (T) mapper.map(trProgramMasterRequest, claaz);
    }

    /**
     * Maps reelerTypeMaster Entity to reelerTypeMaster Response Object
     * @param reelerTypeMasterEntity
     * @param <T>
     */
    public <T> T reelerTypeMasterEntityToObject(ReelerTypeMaster reelerTypeMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,reelerTypeMasterEntity);
        return (T) mapper.map(reelerTypeMasterEntity, claaz);
    }


    /**
     * Maps reelerTypeMaster Object to releerTypeMaster  Response Object
     * @param reelerTypeMasterRequest
     * @param <T>
     */
    public <T> T reelerTypeMasterObjectToEntity(ReelerTypeMasterRequest reelerTypeMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, reelerTypeMasterRequest);
        return (T) mapper.map(reelerTypeMasterRequest, claaz);
    }

    /**
     * Maps raceMarketMaster Entity to raceMarketMaster Response Object
     * @param raceMarketMasterEntity
     * @param <T>
     */
    public <T> T raceMarketMasterEntityToObject(RaceMarketMaster raceMarketMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,raceMarketMasterEntity);
        return (T) mapper.map(raceMarketMasterEntity, claaz);
    }

    /**
     * Maps raceMarketMasterDTO to raceMarketMaster Response Object
     * @param raceMarketMasterDTO
     * @param <T>
     */
    public <T> T raceMarketMasterDTOToObject(RaceMarketMasterDTO raceMarketMasterDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, raceMarketMasterDTO);
        return (T) mapper.map(raceMarketMasterDTO, claaz);
    }



    /**
     * Maps raceMarketMaster Object to raceMarketMaster  Response Object
     * @param raceMarketMasterRequest
     * @param <T>
     */
    public <T> T raceMarketMasterObjectToEntity(RaceMarketMasterRequest raceMarketMasterRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, raceMarketMasterRequest);
        return (T) mapper.map(raceMarketMasterRequest, claaz);
    }



    /**
     * Maps trInstitutionMaster Entity to trInstitutionMaster Response Object
     * @param trInstitutionMasterEntity
     * @param <T>
     */
    public <T> T trInstitutionMasterEntityToObject(TrInstitutionMaster trInstitutionMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,trInstitutionMasterEntity);
        return (T) mapper.map(trInstitutionMasterEntity, claaz);
    }


    /**
     * Maps trInstitutionMaster Object to trInstitutionMaster  Response Object
     * @param trInstitutionMasterRequest
     * @param <T>
     */
    public <T> T trInstitutionMasterObjectToEntity(TrInstitutionMasterRequest trInstitutionMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, trInstitutionMasterRequest);
        return (T) mapper.map(trInstitutionMasterRequest, claaz);
    }

    /**
     * Maps trGroupMaster Entity to trGroupMaster Response Object
     * @param trGroupMasterEntity
     * @param <T>
     */
    public <T> T trGroupMasterEntityToObject(TrGroupMaster trGroupMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,trGroupMasterEntity);
        return (T) mapper.map(trGroupMasterEntity, claaz);
    }


    /**
     * Maps trGroupMaster Object to trGroupMaster  Response Object
     * @param trGroupMasterRequest
     * @param <T>
     */
    public <T> T trGroupMasterObjectToEntity(TrGroupMasterRequest trGroupMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, trGroupMasterRequest);
        return (T) mapper.map(trGroupMasterRequest, claaz);
    }

    /**
     * Maps trModeMaster Entity to trModeMaster Response Object
     * @param trModeMasterEntity
     * @param <T>
     */
    public <T> T trModeMasterEntityToObject(TrModeMaster trModeMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,trModeMasterEntity);
        return (T) mapper.map(trModeMasterEntity, claaz);
    }


    /**
     * Maps trModeMaster Object to trModeMaster  Response Object
     * @param trModeMasterRequest
     * @param <T>
     */
    public <T> T trModeMasterObjectToEntity(TrModeMasterRequest trModeMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, trModeMasterRequest);
        return (T) mapper.map(trModeMasterRequest, claaz);
    }

    /**
     * Maps trSchedule Entity to trSchedule Response Object
     * @param trScheduleEntity
     * @param <T>
     */
    public <T> T trScheduleEntityToObject(TrSchedule trScheduleEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,trScheduleEntity);
        return (T) mapper.map(trScheduleEntity, claaz);
    }

    /**
     * Maps trScheduleDTO to trSchedule Response Object
     * @param trScheduleDTO
     * @param <T>
     */
    public <T> T trScheduleDTOToObject(TrScheduleDTO trScheduleDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, trScheduleDTO);
        return (T) mapper.map(trScheduleDTO, claaz);
    }

    /**
     * Maps trSchedule Object to trSchedule  Response Object
     * @param trScheduleRequest
     * @param <T>
     */
    public <T> T trScheduleObjectToEntity(TrScheduleRequest trScheduleRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, trScheduleRequest);
        return (T) mapper.map(trScheduleRequest, claaz);
    }
    /**
     * Maps DeputedInstituteMaster Entity to DeputedInstituteMaster Response Object
     * @param deputedInstituteMasterEntity
     * @param <T>
     */
    public <T> T deputedInstituteMasterEntityToObject(DeputedInstituteMaster deputedInstituteMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,deputedInstituteMasterEntity);
        return (T) mapper.map(deputedInstituteMasterEntity, claaz);
    }


    /**
     * Maps trModeMaster Object to trModeMaster  Response Object
     * @param deputedInstituteMasterRequest
     * @param <T>
     */
    public <T> T deputedInstituteMasterObjectToEntity(DeputedInstituteMasterRequest deputedInstituteMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, deputedInstituteMasterRequest);
        return (T) mapper.map(deputedInstituteMasterRequest, claaz);
    }
    /**
     * Maps trainingDeputationTracker Entity to trainingDeputationTracker Response Object
     * @param trainingDeputationTrackerEntity
     * @param <T>
     */
    public <T> T trainingDeputationTrackerEntityToObject(TrainingDeputationTracker trainingDeputationTrackerEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,trainingDeputationTrackerEntity);
        return (T) mapper.map(trainingDeputationTrackerEntity, claaz);
    }

    /**
     * Maps trainingDeputationTrackerDTO to trainingDeputationTracker Response Object
     * @param trainingDeputationTrackerDTO
     * @param <T>
     */
    public <T> T trainingDeputationTrackerDTOToObject(TrainingDeputationTrackerDTO trainingDeputationTrackerDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, trainingDeputationTrackerDTO);
        return (T) mapper.map(trainingDeputationTrackerDTO, claaz);
    }

    /**
     * Maps trSchedule Object to trSchedule  Response Object
     * @param trainingDeputationTrackerRequest
     * @param <T>
     */
    public <T> T trainingDeputationTrackerObjectToEntity(TrainingDeputationTrackerRequest trainingDeputationTrackerRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, trainingDeputationTrackerRequest);
        return (T) mapper.map(trainingDeputationTrackerRequest, claaz);
    }

    /**
     * Maps trOffice Entity to trOffice Response Object
     * @param trOfficeEntity
     * @param <T>
     */
    public <T> T trOfficeEntityToObject(TrOffice trOfficeEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,trOfficeEntity);
        return (T) mapper.map(trOfficeEntity, claaz);
    }


    /**
     * Maps trOffice Object to trOffice  Response Object
     * @param trOfficeRequest
     * @param <T>
     */
    public <T> T trOfficeObjectToEntity(TrOfficeRequest trOfficeRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, trOfficeRequest);
        return (T) mapper.map(trOfficeRequest, claaz);
    }

    /**
     * Maps trTrainee Entity to trTrainee Response Object
     * @param trTraineeEntity
     * @param <T>
     */
    public <T> T trTraineeEntityToObject(TrTrainee trTraineeEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,trTraineeEntity);
        return (T) mapper.map(trTraineeEntity, claaz);
    }

    /**
     * Maps trTraineeDTO to trTrainee Response Object
     * @param trTraineeDTO
     * @param <T>
     */
    public <T> T trTraineeDTOToObject(TrTraineeDTO trTraineeDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, trTraineeDTO);
        return (T) mapper.map(trTraineeDTO, claaz);
    }



    /**
     * Maps trTrainee Object to trTrainee  Response Object
     * @param trTraineeRequest
     * @param <T>
     */
    public <T> T trTraineeObjectToEntity(TrTraineeRequest trTraineeRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, trTraineeRequest);
        return (T) mapper.map(trTraineeRequest, claaz);
    }

    /**
     * Maps trTraining Entity to trTraining Response Object
     * @param trTrainingEntity
     * @param <T>
     */
    public <T> T trTrainingEntityToObject(TrTraining trTrainingEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,trTrainingEntity);
        return (T) mapper.map(trTrainingEntity, claaz);
    }

    /**
     * Maps trTrainingDTO to trTraining Response Object
     * @param trTrainingDTO
     * @param <T>
     */
    public <T> T trTrainingDTOToObject(TrTrainingDTO trTrainingDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, trTrainingDTO);
        return (T) mapper.map(trTrainingDTO, claaz);
    }



    /**
     * Maps trTraining Object to trTraining Response Object
     * @param trTrainingRequest
     * @param <T>
     */
    public <T> T trTrainingObjectToEntity(TrTrainingRequest trTrainingRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, trTrainingRequest);
        return (T) mapper.map(trTrainingRequest, claaz);
    }



}
