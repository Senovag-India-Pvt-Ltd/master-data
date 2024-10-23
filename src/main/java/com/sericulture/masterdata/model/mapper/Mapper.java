package com.sericulture.masterdata.model.mapper;


import com.sericulture.masterdata.model.api.agency.AgencyRequest;
import com.sericulture.masterdata.model.api.bankMaster.BankMasterRequest;
import com.sericulture.masterdata.model.api.binCounterMaster.BinCounterMasterRequest;
import com.sericulture.masterdata.model.api.binMaster.BinMasterRequest;
import com.sericulture.masterdata.model.api.caste.CasteRequest;
import com.sericulture.masterdata.model.api.cropInspectionType.CropInspectionTypeRequest;
import com.sericulture.masterdata.model.api.cropStatus.CropStatusRequest;
import com.sericulture.masterdata.model.api.departmentMaster.DepartmentMasterRequest;
import com.sericulture.masterdata.model.api.diseaseStatus.DiseaseStatusRequest;
import com.sericulture.masterdata.model.api.disinfectantMaster.DisinfectantMasterRequest;
import com.sericulture.masterdata.model.api.divisionMaster.DivisionMasterRequest;
import com.sericulture.masterdata.model.api.farmMaster.FarmMasterRequest;
import com.sericulture.masterdata.model.api.farmerBankAccountReason.FarmerBankAccountReasonRequest;
import com.sericulture.masterdata.model.api.financialYearMaster.FinancialYearMasterRequest;
import com.sericulture.masterdata.model.api.generationNumberMaster.GenerationNumberMasterRequest;
import com.sericulture.masterdata.model.api.grainageMaster.GrainageMasterRequest;
import com.sericulture.masterdata.model.api.hdAnswerMaster.HdAnswerMasterRequest;
import com.sericulture.masterdata.model.api.hdBoardCategoryMaster.HdBoardCategoryMasterRequest;
import com.sericulture.masterdata.model.api.hdCategoryMaster.HdCategoryMasterRequest;
import com.sericulture.masterdata.model.api.crateMaster.CrateMasterRequest;
import com.sericulture.masterdata.model.api.deputedInstituteMaster.DeputedInstituteMasterRequest;
import com.sericulture.masterdata.model.api.designation.DesignationRequest;
import com.sericulture.masterdata.model.api.district.DistrictRequest;
import com.sericulture.masterdata.model.api.documentMaster.DocumentMasterRequest;
import com.sericulture.masterdata.model.api.education.EducationRequest;
import com.sericulture.masterdata.model.api.externalUnitType.ExternalUnitTypeRequest;
import com.sericulture.masterdata.model.api.godown.GodownRequest;
import com.sericulture.masterdata.model.api.hdFeatureMaster.HdFeatureMasterRequest;
import com.sericulture.masterdata.model.api.hdModuleMaster.HdModuleMasterRequest;
import com.sericulture.masterdata.model.api.hdQuestionMaster.HdQuestionMasterRequest;
import com.sericulture.masterdata.model.api.hdSeverityMaster.HdSeverityMasterRequest;
import com.sericulture.masterdata.model.api.hdStatusMaster.HdStatusMasterRequest;
import com.sericulture.masterdata.model.api.hdSubCategoryMaster.HdSubCategoryMasterRequest;
import com.sericulture.masterdata.model.api.hectareMaster.HectareMasterRequest;
import com.sericulture.masterdata.model.api.hobli.HobliRequest;
import com.sericulture.masterdata.model.api.inspectionType.InspectionTypeRequest;
import com.sericulture.masterdata.model.api.irrigationSource.IrrigationSourceRequest;
import com.sericulture.masterdata.model.api.irrigationType.IrrigationTypeRequest;
import com.sericulture.masterdata.model.api.landOwnership.LandOwnershipRequest;
import com.sericulture.masterdata.model.api.lineNameMaster.LineNameMasterRequest;
import com.sericulture.masterdata.model.api.loginHistory.LoginHistoryRequest;
import com.sericulture.masterdata.model.api.machineTypeMaster.MachineTypeMasterRequest;
import com.sericulture.masterdata.model.api.marketMaster.MarketMasterRequest;
import com.sericulture.masterdata.model.api.marketTypeMaster.MarketTypeMasterRequest;
import com.sericulture.masterdata.model.api.mountMaster.MountRequest;
import com.sericulture.masterdata.model.api.mulberrySource.MulberrySourceRequest;
import com.sericulture.masterdata.model.api.mulberryVariety.MulberryVarietyRequest;
import com.sericulture.masterdata.model.api.plantationType.PlantationTypeRequest;
import com.sericulture.masterdata.model.api.raceMarketMaster.RaceMarketMasterRequest;
import com.sericulture.masterdata.model.api.raceMaster.RaceMasterRequest;
import com.sericulture.masterdata.model.api.reasonBidRejectMaster.ReasonBidRejectMasterRequest;
import com.sericulture.masterdata.model.api.reasonLotRejectMaster.ReasonLotRejectMasterRequest;
import com.sericulture.masterdata.model.api.reasonMaster.ReasonRequest;
import com.sericulture.masterdata.model.api.rejectReasonWorkflowMaster.RejectReasonWorkFlowMasterRequest;
import com.sericulture.masterdata.model.api.relationship.RelationshipRequest;
//import com.sericulture.masterdata.model.api.hobli.HobliRequest;
import com.sericulture.masterdata.model.api.landCategory.LandCategoryRequest;
import com.sericulture.masterdata.model.api.reelerTypeMaster.ReelerTypeMasterRequest;
import com.sericulture.masterdata.model.api.rendittaMaster.RendittaMasterRequest;
import com.sericulture.masterdata.model.api.role.RoleRequest;
import com.sericulture.masterdata.model.api.roofType.RoofTypeRequest;
import com.sericulture.masterdata.model.api.rpPageRoot.RpPageRootRequest;
import com.sericulture.masterdata.model.api.rpRoleAssociation.RpRoleAssociationRequest;
import com.sericulture.masterdata.model.api.rpRolePermission.RpRolePermissionRequest;
import com.sericulture.masterdata.model.api.scApprovalStage.ScApprovalStageRequest;
import com.sericulture.masterdata.model.api.scApprovingAuthority.ScApprovingAuthorityRequest;
import com.sericulture.masterdata.model.api.scCategory.ScCategoryRequest;
import com.sericulture.masterdata.model.api.scComponent.ScComponentRequest;
import com.sericulture.masterdata.model.api.scHeadAccount.ScHeadAccountRequest;
import com.sericulture.masterdata.model.api.scHeadAccountCategory.ScHeadAccountCategoryRequest;
import com.sericulture.masterdata.model.api.scProgram.ScProgramRequest;
import com.sericulture.masterdata.model.api.rpPagePermission.RpPagePermissionRequest;
import com.sericulture.masterdata.model.api.scProgramAccountMapping.ScProgramAccountMappingRequest;
import com.sericulture.masterdata.model.api.scProgramApprovalMapping.ScProgramApprovalMappingRequest;
import com.sericulture.masterdata.model.api.scSchemeDetails.ScSchemeDetailsRequest;
import com.sericulture.masterdata.model.api.scSubSchemeDetails.ScSubSchemeDetailsRequest;
import com.sericulture.masterdata.model.api.scSubSchemeMapping.ScSubSchemeMappingRequest;
import com.sericulture.masterdata.model.api.scUnitCost.ScUnitCostRequest;
import com.sericulture.masterdata.model.api.scVendor.ScVendorRequest;
import com.sericulture.masterdata.model.api.scVendorBank.ScVendorBankRequest;
import com.sericulture.masterdata.model.api.scVendorContact.ScVendorContactRequest;
import com.sericulture.masterdata.model.api.schemeQuota.SchemeQuotaRequest;
import com.sericulture.masterdata.model.api.soilType.SoilTypeRequest;
import com.sericulture.masterdata.model.api.silkwormvariety.SilkWormVarietyRequest;
import com.sericulture.masterdata.model.api.sourceMaster.SourceMasterRequest;
import com.sericulture.masterdata.model.api.spacing.SpacingMasterRequest;
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
import com.sericulture.masterdata.model.api.tsActivityMaster.TsActivityMasterRequest;
import com.sericulture.masterdata.model.api.tsBudget.TsBudgetRequest;
import com.sericulture.masterdata.model.api.tsBudgetDistrict.TsBudgetDistrictRequest;
import com.sericulture.masterdata.model.api.tsBudgetHoa.TsBudgetHoaRequest;
import com.sericulture.masterdata.model.api.tsBudgetInstitution.TsBudgetInstitutionRequest;
import com.sericulture.masterdata.model.api.tsBudgetTaluk.TsBudgetTalukRequest;
import com.sericulture.masterdata.model.api.tsBudgetTsc.TsBudgetTscRequest;
import com.sericulture.masterdata.model.api.tsReleaseBudgetDistrict.TsReleaseBudgetDistrictRequest;
import com.sericulture.masterdata.model.api.tsReleaseBudgetInstitution.TsReleaseBudgetInstitutionRequest;
import com.sericulture.masterdata.model.api.tsReleaseBudgetTaluk.TsReleaseBudgetTalukRequest;
import com.sericulture.masterdata.model.api.tscMaster.TscMasterRequest;
import com.sericulture.masterdata.model.api.useMaster.UserMasterRequest;
import com.sericulture.masterdata.model.api.userHierarchyMapping.UserHierarchyMappingRequest;
import com.sericulture.masterdata.model.api.userPreference.UserPreferenceRequest;
import com.sericulture.masterdata.model.api.vendorMaster.VendorMasterRequest;
import com.sericulture.masterdata.model.api.village.VillageRequest;
import com.sericulture.masterdata.model.api.workingInstitution.WorkingInstitutionRequest;
import com.sericulture.masterdata.model.api.wormStageMaster.WormStageMasterRequest;
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
     * Maps Reason Entity to Reason Response Object
     * @param reasonEntity
     * @param <T>
     */
    public <T> T reasonEntityToObject(Reason reasonEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, reasonEntity);
        return (T) mapper.map(reasonEntity, claaz);
    }

    /**
     * Maps Reason Object to Reason Response Entity
     * @param reasonRequest
     * @param <T>
     */
    public <T> T reasonObjectToEntity(ReasonRequest reasonRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, reasonRequest);
        return (T) mapper.map(reasonRequest, claaz);
    }

    /**
     * Maps CropInspectionType Entity to CropInspectionType Response Object
     * @param  cropInspectionTypeEntity
     * @param <T>
     */
    public <T> T cropInspectionTypeEntityToObject(CropInspectionType cropInspectionTypeEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, cropInspectionTypeEntity);
        return (T) mapper.map(cropInspectionTypeEntity, claaz);
    }

    /**
     * Maps CropInspectionType Object to CropInspectionType Response Entity
     * @param cropInspectionTypeRequest
     * @param <T>
     */
    public <T> T cropInspectionTypeObjectToEntity(CropInspectionTypeRequest cropInspectionTypeRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, cropInspectionTypeRequest);
        return (T) mapper.map(cropInspectionTypeRequest, claaz);
    }
    /**
     * Maps DiseaseStatus Entity to DiseaseStatus Response Object
     * @param diseaseStatusEntity
     * @param <T>
     */
    public <T> T diseaseStatusEntityToObject(DiseaseStatus diseaseStatusEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, diseaseStatusEntity);
        return (T) mapper.map(diseaseStatusEntity, claaz);
    }

    /**
     * Maps DiseaseStatus Object to DiseaseStatus Response Entity
     * @param diseaseStatusRequest
     * @param <T>
     */
    public <T> T diseaseStatusObjectToEntity(DiseaseStatusRequest diseaseStatusRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, diseaseStatusRequest);
        return (T) mapper.map(diseaseStatusRequest, claaz);
    }

    /**
     * Maps Mount Entity to Mount Response Object
     * @param mountEntity
     * @param <T>
     */
    public <T> T mountEntityToObject(Mount mountEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, mountEntity);
        return (T) mapper.map(mountEntity, claaz);
    }

    /**
     * Maps Mount Object to Mount Response Entity
     * @param mountRequest
     * @param <T>
     */
    public <T> T mountObjectToEntity(MountRequest mountRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, mountRequest);
        return (T) mapper.map(mountRequest, claaz);
    }

    /**
     * Maps CropStatus Entity to CropStatus Response Object
     * @param cropStatusEntity
     * @param <T>
     */
    public <T> T cropStatusEntityToObject(CropStatus cropStatusEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, cropStatusEntity);
        return (T) mapper.map(cropStatusEntity, claaz);
    }

    /**
     * Maps CropStatus Object to CropStatus Response Entity
     * @param cropStatusRequest
     * @param <T>
     */
    public <T> T cropStatusObjectToEntity(CropStatusRequest cropStatusRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, cropStatusRequest);
        return (T) mapper.map(cropStatusRequest, claaz);
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
     * Maps scComponentDTO to scComponent Response Object
     * @param scComponentDTO
     * @param <T>
     */
    public <T> T scComponentDTOToObject(ScComponentDTO scComponentDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scComponentDTO);
        return (T) mapper.map(scComponentDTO, claaz);
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
     * Maps scHead Account Entity to scHeadAccount Response Object
     * @param scHeadAccountEntity
     * @param <T>
     */
    public <T> T scHeadAccountEntityToObject(ScHeadAccount scHeadAccountEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scHeadAccountEntity);
        return (T) mapper.map(scHeadAccountEntity, claaz);

    }
    /**
     * Maps scHeadAccountDTO to scHeadAccount Response Object
     * @param scHeadAccountDTO
     * @param <T>
     */
    public <T> T scHeadAccountDTOToObject(ScHeadAccountDTO scHeadAccountDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scHeadAccountDTO);
        return (T) mapper.map(scHeadAccountDTO, claaz);
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
    /**
     * Maps hdCategoryMaster Entity to hdCategoryMaster Response Object
     * @param hdCategoryMasterEntity
     * @param <T>
     */
    public <T> T hdCategoryMasterEntityToObject(HdCategoryMaster hdCategoryMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,hdCategoryMasterEntity);
        return (T) mapper.map(hdCategoryMasterEntity, claaz);
    }

    /**
     * Maps hdCategoryMasterDTO to hdCategoryMaster Response Object
     * @param hdCategoryMasterDTO
     * @param <T>
     */
    public <T> T hdCategoryMasterDTOToObject(HdCategoryMasterDTO hdCategoryMasterDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, hdCategoryMasterDTO);
        return (T) mapper.map(hdCategoryMasterDTO, claaz);
    }



    /**
     * Maps hdCategoryMaster Object to hdCategoryMaster  Response Object
     * @param hdCategoryMasterRequest
     * @param <T>
     */
    public <T> T hdCategoryMasterObjectToEntity(HdCategoryMasterRequest hdCategoryMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, hdCategoryMasterRequest);
        return (T) mapper.map(hdCategoryMasterRequest, claaz);
    }
    /**
     * Maps hdSubCategoryMaster Entity to hdSubCategoryMaster Response Object
     * @param hdSubCategoryMasterEntity
     * @param <T>
     */
    public <T> T hdSubCategoryMasterEntityToObject(HdSubCategoryMaster hdSubCategoryMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,hdSubCategoryMasterEntity);
        return (T) mapper.map(hdSubCategoryMasterEntity, claaz);
    }
    /**
     * Maps hdSubCategoryMasterDTO to hdSubCategoryMaster Response Object
     * @param hdSubCategoryMasterDTO
     * @param <T>
     */
    public <T> T hdSubCategoryMasterDTOToObject(HdSubCategoryMasterDTO hdSubCategoryMasterDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, hdSubCategoryMasterDTO);
        return (T) mapper.map(hdSubCategoryMasterDTO, claaz);
    }


    /**
     * Maps hdSubCategoryMaster Object to hdSubCategoryMaster  Response Object
     * @param hdSubCategoryMasterRequest
     * @param <T>
     */
    public <T> T hdSubCategoryMasterObjectToEntity(HdSubCategoryMasterRequest hdSubCategoryMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, hdSubCategoryMasterRequest);
        return (T) mapper.map(hdSubCategoryMasterRequest, claaz);
    }
    /**
     * Maps hdBoardCategoryMaster Entity to hdBoardCategoryMaster Response Object
     * @param hdBoardCategoryMasterEntity
     * @param <T>
     */
    public <T> T hdBoardCategoryMasterEntityToObject(HdBoardCategoryMaster hdBoardCategoryMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,hdBoardCategoryMasterEntity);
        return (T) mapper.map(hdBoardCategoryMasterEntity, claaz);
    }


    /**
     * Maps hdBoardCategoryMaster Object to hdBoardCategoryMaster  Response Object
     * @param hdBoardCategoryMasterRequest
     * @param <T>
     */
    public <T> T hdBoardCategoryMasterObjectToEntity(HdBoardCategoryMasterRequest hdBoardCategoryMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, hdBoardCategoryMasterRequest);
        return (T) mapper.map(hdBoardCategoryMasterRequest, claaz);
    }
    /**
     * Maps hdModuleMaster Entity to hdModuleMaster Response Object
     * @param hdModuleMasterEntity
     * @param <T>
     */
    public <T> T hdModuleMasterEntityToObject(HdModuleMaster hdModuleMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,hdModuleMasterEntity);
        return (T) mapper.map(hdModuleMasterEntity, claaz);
    }


    /**
     * Maps hdModuleMaster Object to hdModuleMaster  Response Object
     * @param hdModuleMasterRequest
     * @param <T>
     */
    public <T> T hdModuleMasterObjectToEntity(HdModuleMasterRequest hdModuleMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, hdModuleMasterRequest);
        return (T) mapper.map(hdModuleMasterRequest, claaz);
    }
    /**
     * Maps hdFeatureMaster Entity to hdFeatureMaster Response Object
     * @param hdFeatureMasterEntity
     * @param <T>
     */
    public <T> T hdFeatureMasterEntityToObject(HdFeatureMaster hdFeatureMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,hdFeatureMasterEntity);
        return (T) mapper.map(hdFeatureMasterEntity, claaz);
    }

    /**
     * Maps hdFeatureMasterDTO to hdFeatureMaster Response Object
     * @param hdFeatureMasterDTO
     * @param <T>
     */
    public <T> T hdFeatureMasterDTOToObject(HdFeatureMasterDTO hdFeatureMasterDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, hdFeatureMasterDTO);
        return (T) mapper.map(hdFeatureMasterDTO, claaz);
    }



    /**
     * Maps hdFeatureMaster Object to hdFeatureMaster  Response Object
     * @param hdFeatureMasterRequest
     * @param <T>
     */
    public <T> T hdFeatureMasterObjectToEntity(HdFeatureMasterRequest hdFeatureMasterRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, hdFeatureMasterRequest);
        return (T) mapper.map(hdFeatureMasterRequest, claaz);
    }
    /**
     * Maps hdStatusMaster Entity to hdStatusMaster Response Object
     * @param hdStatusMasterEntity
     * @param <T>
     */
    public <T> T hdStatusMasterEntityToObject(HdStatusMaster hdStatusMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,hdStatusMasterEntity);
        return (T) mapper.map(hdStatusMasterEntity, claaz);
    }


    /**
     * Maps hdStatusMaster Object to hdStatusMaster  Response Object
     * @param hdStatusMasterRequest
     * @param <T>
     */
    public <T> T hdStatusMasterObjectToEntity(HdStatusMasterRequest hdStatusMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, hdStatusMasterRequest);
        return (T) mapper.map(hdStatusMasterRequest, claaz);
    }
    /**
     * Maps hdSeverityMaster Entity to hdSeverityMaster Response Object
     * @param hdSeverityMasterEntity
     * @param <T>
     */
    public <T> T hdSeverityMasterEntityToObject(HdSeverityMaster hdSeverityMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,hdSeverityMasterEntity);
        return (T) mapper.map(hdSeverityMasterEntity, claaz);
    }


    /**
     * Maps hdSeverityMaster Object to hdSeverityMaster  Response Object
     * @param hdSeverityMasterRequest
     * @param <T>
     */
    public <T> T hdSeverityMasterObjectToEntity(HdSeverityMasterRequest hdSeverityMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, hdSeverityMasterRequest);
        return (T) mapper.map(hdSeverityMasterRequest, claaz);
    }
    /**
     * Maps hdQuestionMaster Entity to hdQuestionMaster Response Object
     * @param hdQuestionMasterEntity
     * @param <T>
     */
    public <T> T hdQuestionMasterEntityToObject(HdQuestionMaster hdQuestionMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,hdQuestionMasterEntity);
        return (T) mapper.map(hdQuestionMasterEntity, claaz);
    }
    /**
     * Maps hdFeatureMasterDTO to hdFeatureMaster Response Object
     * @param hdQuestionMasterDTO
     * @param <T>
     */
    public <T> T hdQuestionMasterDTOToObject(HdQuestionMasterDTO hdQuestionMasterDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, hdQuestionMasterDTO);
        return (T) mapper.map(hdQuestionMasterDTO, claaz);
    }

    /**
     * Maps hdQuestionMaster Object to hdQuestionMaster  Response Object
     * @param hdQuestionMasterRequest
     * @param <T>
     */
    public <T> T hdQuestionMasterObjectToEntity(HdQuestionMasterRequest hdQuestionMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, hdQuestionMasterRequest);
        return (T) mapper.map(hdQuestionMasterRequest, claaz);
    }

    /**
     * Maps hdAnswerMaster Entity to hdAnswerMaster Response Object
     * @param hdAnswerMasterEntity
     * @param <T>
     */
    public <T> T hdAnswerMasterEntityToObject(HdAnswerMaster hdAnswerMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,hdAnswerMasterEntity);
        return (T) mapper.map(hdAnswerMasterEntity, claaz);
    }


    /**
     * Maps hdAnswerMaster Object to hdAnswerMaster  Response Object
     * @param hdAnswerMasterRequest
     * @param <T>
     */
    public <T> T hdAnswerMasterObjectToEntity(HdAnswerMasterRequest hdAnswerMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, hdAnswerMasterRequest);
        return (T) mapper.map(hdAnswerMasterRequest, claaz);
    }

    /**
     * Maps disinfectantMaster Entity to disinfectantMaster Response Object
     * @param disinfectantMasterEntity
     * @param <T>
     */
    public <T> T disinfectantMasterEntityToObject(DisinfectantMaster disinfectantMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,disinfectantMasterEntity);
        return (T) mapper.map(disinfectantMasterEntity, claaz);
    }


    /**
     * Maps disinfectantMaster Object to disinfectantMaster  Response Object
     * @param disinfectantMasterRequest
     * @param <T>
     */
    public <T> T disinfectantMasterObjectToEntity(DisinfectantMasterRequest disinfectantMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, disinfectantMasterRequest);
        return (T) mapper.map(disinfectantMasterRequest, claaz);
    }
    /**
     * Maps grainageMaster Entity to grainageMaster Response Object
     * @param grainageMasterEntity
     * @param <T>
     */
    public <T> T grainageMasterEntityToObject(GrainageMaster grainageMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,grainageMasterEntity);
        return (T) mapper.map(grainageMasterEntity, claaz);
    }

    /**
     * Maps grainageMasterDTO to grainageMaster Response Object
     * @param grainageMasterDTO
     * @param <T>
     */
    public <T> T grainageMasterDTOToObject(GrainageMasterDTO grainageMasterDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, grainageMasterDTO);
        return (T) mapper.map(grainageMasterDTO, claaz);
    }


    /**
     * Maps grainageMaster Object to grainageMaster  Response Object
     * @param grainageMasterRequest
     * @param <T>
     */
    public <T> T grainageMasterObjectToEntity(GrainageMasterRequest grainageMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, grainageMasterRequest);
        return (T) mapper.map(grainageMasterRequest, claaz);
    }
    /**
     * Maps lineNameMaster Entity to lineNameMaster Response Object
     * @param lineNameMasterEntity
     * @param <T>
     */
    public <T> T lineNameMasterEntityToObject(LineNameMaster lineNameMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,lineNameMasterEntity);
        return (T) mapper.map(lineNameMasterEntity, claaz);
    }


    /**
     * Maps lineNameMaster Object to lineNameMaster  Response Object
     * @param lineNameMasterRequest
     * @param <T>
     */
    public <T> T lineNameMasterObjectToEntity(LineNameMasterRequest lineNameMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, lineNameMasterRequest);
        return (T) mapper.map(lineNameMasterRequest, claaz);
    }
    /**
     * Maps farmMaster Entity to farmMaster Response Object
     * @param farmMasterEntity
     * @param <T>
     */
    public <T> T farmMasterEntityToObject(FarmMaster farmMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,farmMasterEntity);
        return (T) mapper.map(farmMasterEntity, claaz);
    }

    /**
     * Maps farmMasterDTO to farmMaster Response Object
     * @param farmMasterDTO
     * @param <T>
     */
    public <T> T farmMasterDTOToObject(FarmMasterDTO farmMasterDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, farmMasterDTO);
        return (T) mapper.map(farmMasterDTO, claaz);
    }


    /**
     * Maps farmMaster Object to farmMaster  Response Object
     * @param farmMasterRequest
     * @param <T>
     */
    public <T> T farmMasterObjectToEntity(FarmMasterRequest farmMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, farmMasterRequest);
        return (T) mapper.map(farmMasterRequest, claaz);
    }

    /**
     * Maps generationNumberMaster Entity to generationNumberMaster Response Object
     * @param generationNumberMasterEntity
     * @param <T>
     */
    public <T> T generationNumberMasterEntityToObject(GenerationNumberMaster generationNumberMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,generationNumberMasterEntity);
        return (T) mapper.map(generationNumberMasterEntity, claaz);
    }


    /**
     * Maps generationNumberMaster Object to generationNumberMaster  Response Object
     * @param generationNumberMasterRequest
     * @param <T>
     */
    public <T> T generationNumberObjectToEntity(GenerationNumberMasterRequest generationNumberMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, generationNumberMasterRequest);
        return (T) mapper.map(generationNumberMasterRequest, claaz);
    }

    /**
     * Maps loginHistory Entity to loginHistory Response Object
     * @param loginHistoryEntity
     * @param <T>
     */
    public <T> T loginHistoryEntityToObject(LoginHistory loginHistoryEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,loginHistoryEntity);
        return (T) mapper.map(loginHistoryEntity, claaz);
    }


    /**
     * Maps loginHistory Object to loginHistory  Response Object
     * @param loginHistoryRequest
     * @param <T>
     */
    public <T> T loginHistoryObjectToEntity(LoginHistoryRequest loginHistoryRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, loginHistoryRequest);
        return (T) mapper.map(loginHistoryRequest, claaz);
    }


    /**
     * Maps tscMaster Entity to tscMaster Response Object
     * @param tscMasterEntity
     * @param <T>
     */
    public <T> T tscMasterEntityToObject(TscMaster tscMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,tscMasterEntity);
        return (T) mapper.map(tscMasterEntity, claaz);
    }

    /**
     * Maps tscMasterDTO to tscMaster Response Object
     * @param tscMasterDTO
     * @param <T>
     */
    public <T> T tscMasterDTOToObject(TscMasterDTO tscMasterDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tscMasterDTO);
        return (T) mapper.map(tscMasterDTO, claaz);
    }


    /**
     * Maps tscMaster Object to tscMaster  Response Object
     * @param tscMasterRequest
     * @param <T>
     */
    public <T> T tscMasterObjectToEntity(TscMasterRequest tscMasterRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tscMasterRequest);
        return (T) mapper.map(tscMasterRequest, claaz);
    }
    /**
     * Maps scCategory Entity to scCategory Response Object
     * @param scCategoryEntity
     * @param <T>
     */
    public <T> T scCategoryEntityToObject(ScCategory scCategoryEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,scCategoryEntity);
        return (T) mapper.map(scCategoryEntity, claaz);
    }


    /**
     * Maps scCategory Object to scCategory  Response Object
     * @param scCategoryRequest
     * @param <T>
     */
    public <T> T scCategoryObjectToEntity(ScCategoryRequest scCategoryRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scCategoryRequest);
        return (T) mapper.map(scCategoryRequest, claaz);
    }

    /**
     * Maps scApprovalStage Entity to scApprovalStage Response Object
     * @param scApprovalStageEntity
     * @param <T>
     */
    public <T> T scApprovalStageEntityToObject(ScApprovalStage scApprovalStageEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,scApprovalStageEntity);
        return (T) mapper.map(scApprovalStageEntity, claaz);
    }


    /**
     * Maps scApprovalStage Object to scApprovalStage  Response Object
     * @param scApprovalStageRequest
     * @param <T>
     */
    public <T> T scApprovalStageObjectToEntity(ScApprovalStageRequest scApprovalStageRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scApprovalStageRequest);
        return (T) mapper.map(scApprovalStageRequest, claaz);
    }

    /**
     * Maps scProgramApprovalMapping Entity to scProgramApprovalMapping Response Object
     * @param scProgramApprovalMappingEntity
     * @param <T>
     */
    public <T> T scProgramApprovalMappingEntityToObject(ScProgramApprovalMapping scProgramApprovalMappingEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,scProgramApprovalMappingEntity);
        return (T) mapper.map(scProgramApprovalMappingEntity, claaz);
    }

    /**
     * Maps scProgramApprovalMappingDTO to scProgramApprovalMapping Response Object
     * @param scProgramApprovalMappingDTO
     * @param <T>
     */
    public <T> T scProgramApprovalMappingDTOToObject(ScProgramApprovalMappingDTO scProgramApprovalMappingDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scProgramApprovalMappingDTO);
        return (T) mapper.map(scProgramApprovalMappingDTO, claaz);
    }



    /**
     * Maps scProgramApprovalMapping Object to scProgramApprovalMapping  Response Object
     * @param scProgramApprovalMappingRequest
     * @param <T>
     */
    public <T> T scProgramApprovalMappingObjectToEntity(ScProgramApprovalMappingRequest scProgramApprovalMappingRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scProgramApprovalMappingRequest);
        return (T) mapper.map(scProgramApprovalMappingRequest, claaz);
    }

    /**
     * Maps bankMaster Entity to bankMaster Response Object
     * @param bankMasterEntity
     * @param <T>
     */
    public <T> T bankMasterEntityToObject(BankMaster bankMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,bankMasterEntity);
        return (T) mapper.map(bankMasterEntity, claaz);
    }


    /**
     * Maps bankMaster Object to bankMaster  Response Object
     * @param bankMasterRequest
     * @param <T>
     */
    public <T> T bankMasterObjectToEntity(BankMasterRequest bankMasterRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, bankMasterRequest);
        return (T) mapper.map(bankMasterRequest, claaz);
    }

    /**
     * Maps scHeadAccountCategory Entity to scHeadAccountCategory Response Object
     * @param scHeadAccountCategoryEntity
     * @param <T>
     */
    public <T> T scHeadAccountCategoryEntityToObject(ScHeadAccountCategory scHeadAccountCategoryEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,scHeadAccountCategoryEntity);
        return (T) mapper.map(scHeadAccountCategoryEntity, claaz);
    }

    /**
     * Maps scHeadAccountCategoryDTO to scHeadAccountCategory Response Object
     * @param scHeadAccountCategoryDTO
     * @param <T>
     */
    public <T> T scHeadAccountCategoryDTOToObject(ScHeadAccountCategoryDTO scHeadAccountCategoryDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scHeadAccountCategoryDTO);
        return (T) mapper.map(scHeadAccountCategoryDTO, claaz);
    }



    /**
     * Maps scHeadAccountCategory Object to scHeadAccountCategory  Response Object
     * @param scHeadAccountCategoryRequest
     * @param <T>
     */
    public <T> T scHeadAccountCategoryObjectToEntity(ScHeadAccountCategoryRequest scHeadAccountCategoryRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scHeadAccountCategoryRequest);
        return (T) mapper.map(scHeadAccountCategoryRequest, claaz);
    }


    /**
     * Maps scUnitCost Entity to scUnitCost Response Object
     * @param scUnitCostEntity
     * @param <T>
     */
    public <T> T scUnitCostEntityToObject(ScUnitCost scUnitCostEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,scUnitCostEntity);
        return (T) mapper.map(scUnitCostEntity, claaz);
    }

    /**
     * Maps scUnitCostDTO to scUnitCost Response Object
     * @param scUnitCostDTO
     * @param <T>
     */
    public <T> T scUnitCostDTOToObject(ScUnitCostDTO scUnitCostDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scUnitCostDTO);
        return (T) mapper.map(scUnitCostDTO, claaz);
    }



    /**
     * Maps scUnitCost Object to scUnitCost  Response Object
     * @param scUnitCostRequest
     * @param <T>
     */
    public <T> T scUnitCostObjectToEntity(ScUnitCostRequest scUnitCostRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scUnitCostRequest);
        return (T) mapper.map(scUnitCostRequest, claaz);
    }


    /**
     * Maps scApprovingAuthority Entity to scApprovingAuthority Response Object
     * @param scApprovingAuthorityEntity
     * @param <T>
     */
    public <T> T scApprovingAuthorityEntityToObject(ScApprovingAuthority scApprovingAuthorityEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,scApprovingAuthorityEntity);
        return (T) mapper.map(scApprovingAuthorityEntity, claaz);
    }

    /**
     * Maps scApprovingAuthorityDTO to scApprovingAuthority Response Object
     * @param scApprovingAuthorityDTO
     * @param <T>
     */
    public <T> T scApprovingAuthorityDTOToObject(ScApprovingAuthorityDTO scApprovingAuthorityDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scApprovingAuthorityDTO);
        return (T) mapper.map(scApprovingAuthorityDTO, claaz);
    }



    /**
     * Maps scApprovingAuthority Object to scApprovingAuthority  Response Object
     * @param scApprovingAuthorityRequest
     * @param <T>
     */
    public <T> T scApprovingAuthorityObjectToEntity(ScApprovingAuthorityRequest scApprovingAuthorityRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scApprovingAuthorityRequest);
        return (T) mapper.map(scApprovingAuthorityRequest, claaz);
    }
    /**
     * Maps scVendor Entity to scVendor Response Object
     * @param scVendorEntity
     * @param <T>
     */
    public <T> T scVendorEntityToObject(ScVendor scVendorEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,scVendorEntity);
        return (T) mapper.map(scVendorEntity, claaz);
    }


    /**
     * Maps scVendor Object to scVendor  Response Object
     * @param scVendorRequest
     * @param <T>
     */
    public <T> T scVendorObjectToEntity(ScVendorRequest scVendorRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scVendorRequest);
        return (T) mapper.map(scVendorRequest, claaz);
    }

    /**
     * Maps scSchemeDetails Entity to scSchemeDetails Response Object
     * @param scSchemeDetailsEntity
     * @param <T>
     */
    public <T> T scSchemeDetailsEntityToObject(ScSchemeDetails scSchemeDetailsEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,scSchemeDetailsEntity);
        return (T) mapper.map(scSchemeDetailsEntity, claaz);
    }


    /**
     * Maps scSchemeDetails Object to scSchemeDetails  Response Object
     * @param scSchemeDetailsRequest
     * @param <T>
     */
    public <T> T scSchemeDetailsObjectToEntity(ScSchemeDetailsRequest scSchemeDetailsRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scSchemeDetailsRequest);
        return (T) mapper.map(scSchemeDetailsRequest, claaz);
    }

    /**
     * Maps scSubSchemeDetails Entity to scSubSchemeDetails Response Object
     * @param scSubSchemeDetailsEntity
     * @param <T>
     */
    public <T> T scSubSchemeDetailsEntityToObject(ScSubSchemeDetails scSubSchemeDetailsEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,scSubSchemeDetailsEntity);
        return (T) mapper.map(scSubSchemeDetailsEntity, claaz);
    }

    /**
     * Maps scSubSchemeDetailsDTO to scSubSchemeDetails Response Object
     * @param scSubSchemeDetailsDTO
     * @param <T>
     */
    public <T> T scSubSchemeDetailsDTOToObject(ScSubSchemeDetailsDTO scSubSchemeDetailsDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scSubSchemeDetailsDTO);
        return (T) mapper.map(scSubSchemeDetailsDTO, claaz);
    }



    /**
     * Maps scSubSchemeDetails Object to scSubSchemeDetails  Response Object
     * @param scSubSchemeDetailsRequest
     * @param <T>
     */
    public <T> T scSubSchemeDetailsObjectToEntity(ScSubSchemeDetailsRequest scSubSchemeDetailsRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scSubSchemeDetailsRequest);
        return (T) mapper.map(scSubSchemeDetailsRequest, claaz);
    }

    /**
     * Maps scVendorContact Entity to scVendorContact Response Object
     * @param scVendorContactEntity
     * @param <T>
     */
    public <T> T scVendorContactEntityToObject(ScVendorContact scVendorContactEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,scVendorContactEntity);
        return (T) mapper.map(scVendorContactEntity, claaz);
    }

    /**
     * Maps scVendorContactDTO to scVendorContact Response Object
     * @param scVendorContactDTO
     * @param <T>
     */
    public <T> T scVendorContactDTOToObject(ScVendorContactDTO scVendorContactDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scVendorContactDTO);
        return (T) mapper.map(scVendorContactDTO, claaz);
    }



    /**
     * Maps scVendorContact Object to scVendorContact  Response Object
     * @param scVendorContactRequest
     * @param <T>
     */
    public <T> T scVendorContactObjectToEntity(ScVendorContactRequest scVendorContactRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scVendorContactRequest);
        return (T) mapper.map(scVendorContactRequest, claaz);
    }

    /**
     * Maps scVendorBank Entity to scVendorBank Response Object
     * @param scVendorBankEntity
     * @param <T>
     */
    public <T> T scVendorBankEntityToObject(ScVendorBank scVendorBankEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,scVendorBankEntity);
        return (T) mapper.map(scVendorBankEntity, claaz);
    }

    /**
     * Maps scVendorBankDTO to scVendorBank Response Object
     * @param scVendorBankDTO
     * @param <T>
     */
    public <T> T scVendorBankDTOToObject(ScVendorBankDTO scVendorBankDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scVendorBankDTO);
        return (T) mapper.map(scVendorBankDTO, claaz);
    }



    /**
     * Maps scVendorBank Object to scVendorBank  Response Object
     * @param scVendorBankRequest
     * @param <T>
     */
    public <T> T scVendorBankObjectToEntity(ScVendorBankRequest scVendorBankRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scVendorBankRequest);
        return (T) mapper.map(scVendorBankRequest, claaz);
    }


    /**
     * Maps Worm Stage Master Entity to Worm Stage Master Response Object
     * @param wormStageMasterEntity
     * @param <T>
     */
    public <T> T wormStageMasterEntityToObject(WormStageMaster wormStageMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, wormStageMasterEntity);
        return (T) mapper.map(wormStageMasterEntity, claaz);
    }

    /**
     * Maps Worm Stage Master Entity to Worm Stage Master Response Object
     * @param wormStageMasterRequest
     * @param <T>
     */
    public <T> T wormStageMasterObjectToEntity(WormStageMasterRequest wormStageMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, wormStageMasterRequest);
        return (T) mapper.map(wormStageMasterRequest, claaz);
    }

    /**
     * Maps divisionMaster Entity to divisionMaster Response Object
     * @param divisionMasterEntity
     * @param <T>
     */
    public <T> T divisionMasterEntityToObject(DivisionMaster divisionMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,divisionMasterEntity);
        return (T) mapper.map(divisionMasterEntity, claaz);
    }


    /**
     * Maps divisionMaster Object to divisionMaster  Response Object
     * @param divisionMasterRequest
     * @param <T>
     */
    public <T> T divisionMasterObjectToEntity(DivisionMasterRequest divisionMasterRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, divisionMasterRequest);
        return (T) mapper.map(divisionMasterRequest, claaz);
    }

    /**
     * Maps financialYearMaster Entity to financialYearMaster Response Object
     * @param financialYearMasterEntity
     * @param <T>
     */
    public <T> T financialYearMasterEntityToObject(FinancialYearMaster financialYearMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,financialYearMasterEntity);
        return (T) mapper.map(financialYearMasterEntity, claaz);
    }


    /**
     * Maps financialYearMaster Object to financialYearMaster  Response Object
     * @param financialYearMasterRequest
     * @param <T>
     */
    public <T> T financialYearMasterObjectToEntity(FinancialYearMasterRequest financialYearMasterRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, financialYearMasterRequest);
        return (T) mapper.map(financialYearMasterRequest, claaz);
    }

    /**
     * Maps tsActivityMaster Entity to tsActivityMaster Response Object
     * @param tsActivityMasterEntity
     * @param <T>
     */
    public <T> T tsActivityMasterEntityToObject(TsActivityMaster tsActivityMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,tsActivityMasterEntity);
        return (T) mapper.map(tsActivityMasterEntity, claaz);
    }


    /**
     * Maps tsActivityMaster Object to tsActivityMaster  Response Object
     * @param tsActivityMasterRequest
     * @param <T>
     */
    public <T> T tsActivityMasterObjectToEntity(TsActivityMasterRequest tsActivityMasterRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tsActivityMasterRequest);
        return (T) mapper.map(tsActivityMasterRequest, claaz);
    }

    /**
     * Maps tsBudget Entity to tsBudget Response Object
     * @param tsBudgetEntity
     * @param <T>
     */
    public <T> T tsBudgetEntityToObject(TsBudget tsBudgetEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,tsBudgetEntity);
        return (T) mapper.map(tsBudgetEntity, claaz);
    }

    /**
     * Maps tsBudgetDTO to tsBudget Response Object
     * @param tsBudgetDTO
     * @param <T>
     */
    public <T> T tsBudgetDTOToObject(TsBudgetDTO tsBudgetDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tsBudgetDTO);
        return (T) mapper.map(tsBudgetDTO, claaz);
    }



    /**
     * Maps tsBudget Object to tsBudget  Response Object
     * @param tsBudgetRequest
     * @param <T>
     */
    public <T> T tsBudgetObjectToEntity(TsBudgetRequest tsBudgetRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tsBudgetRequest);
        return (T) mapper.map(tsBudgetRequest, claaz);
    }


    /**
     * Maps tsBudgetHoa Entity to tsBudgetHoa Response Object
     * @param tsBudgetHoaEntity
     * @param <T>
     */
    public <T> T tsBudgetHoaEntityToObject(TsBudgetHoa tsBudgetHoaEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,tsBudgetHoaEntity);
        return (T) mapper.map(tsBudgetHoaEntity, claaz);
    }

    /**
     * Maps tsBudgetHoaDTO to tsBudgetHoa Response Object
     * @param tsBudgetHoaDTO
     * @param <T>
     */
    public <T> T tsBudgetHoaDTOToObject(TsBudgetHoaDTO tsBudgetHoaDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tsBudgetHoaDTO);
        return (T) mapper.map(tsBudgetHoaDTO, claaz);
    }



    /**
     * Maps tsBudgetHoa Object to tsBudgetHoa  Response Object
     * @param tsBudgetHoaRequest
     * @param <T>
     */
    public <T> T tsBudgetHoaObjectToEntity(TsBudgetHoaRequest tsBudgetHoaRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tsBudgetHoaRequest);
        return (T) mapper.map(tsBudgetHoaRequest, claaz);
    }

    /**
     * Maps tsBudgetDistrict Entity to tsBudgetDistrict Response Object
     * @param tsBudgetDistrictEntity
     * @param <T>
     */
    public <T> T tsBudgetDistrictEntityToObject(TsBudgetDistrict tsBudgetDistrictEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,tsBudgetDistrictEntity);
        return (T) mapper.map(tsBudgetDistrictEntity, claaz);
    }

    /**
     * Maps tsBudgetDistrictDTO to tsBudgetDistrict Response Object
     * @param tsBudgetDistrictDTO
     * @param <T>
     */
    public <T> T tsBudgetDistrictDTOToObject(TsBudgetDistrictDTO tsBudgetDistrictDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tsBudgetDistrictDTO);
        return (T) mapper.map(tsBudgetDistrictDTO, claaz);
    }



    /**
     * Maps tsBudgetDistrict Object to tsBudgetDistrict  Response Object
     * @param tsBudgetDistrictRequest
     * @param <T>
     */
    public <T> T tsBudgetDistrictObjectToEntity(TsBudgetDistrictRequest tsBudgetDistrictRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tsBudgetDistrictRequest);
        return (T) mapper.map(tsBudgetDistrictRequest, claaz);
    }

    /**
     * Maps tsBudgetTaluk Entity to tsBudgetTaluk Response Object
     * @param tsBudgetTalukEntity
     * @param <T>
     */
    public <T> T tsBudgetTalukEntityToObject(TsBudgetTaluk tsBudgetTalukEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,tsBudgetTalukEntity);
        return (T) mapper.map(tsBudgetTalukEntity, claaz);
    }

    /**
     * Maps tsBudgetTalukDTO to tsBudgetTaluk Response Object
     * @param tsBudgetTalukDTO
     * @param <T>
     */
    public <T> T tsBudgetTalukDTOToObject(TsBudgetTalukDTO tsBudgetTalukDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tsBudgetTalukDTO);
        return (T) mapper.map(tsBudgetTalukDTO, claaz);
    }



    /**
     * Maps tsBudgetTaluk Object to tsBudgetTaluk  Response Object
     * @param tsBudgetTalukRequest
     * @param <T>
     */
    public <T> T tsBudgetTalukObjectToEntity(TsBudgetTalukRequest tsBudgetTalukRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tsBudgetTalukRequest);
        return (T) mapper.map(tsBudgetTalukRequest, claaz);
    }

    /**
     * Maps tsBudgetTsc Entity to tsBudgetTsc Response Object
     * @param tsBudgetTscEntity
     * @param <T>
     */
    public <T> T tsBudgetTscEntityToObject(TsBudgetTsc tsBudgetTscEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,tsBudgetTscEntity);
        return (T) mapper.map(tsBudgetTscEntity, claaz);
    }

    /**
     * Maps tsBudgetTscDTO to tsBudgetTsc Response Object
     * @param tsBudgetTscDTO
     * @param <T>
     */
    public <T> T tsBudgetTscDTOToObject(TsBudgetTscDTO tsBudgetTscDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tsBudgetTscDTO);
        return (T) mapper.map(tsBudgetTscDTO, claaz);
    }



    /**
     * Maps tsBudgetTsc Object to tsBudgetTsc   Response Object
     * @param tsBudgetTscRequest
     * @param <T>
     */
    public <T> T tsBudgetTscObjectToEntity(TsBudgetTscRequest tsBudgetTscRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tsBudgetTscRequest);
        return (T) mapper.map(tsBudgetTscRequest, claaz);
    }


    /**
     * Maps tsBudgetInstitution Entity to tsBudgetInstitution Response Object
     * @param tsBudgetInstitutionEntity
     * @param <T>
     */
    public <T> T tsBudgetInstitutionEntityToObject(TsBudgetInstitution tsBudgetInstitutionEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,tsBudgetInstitutionEntity);
        return (T) mapper.map(tsBudgetInstitutionEntity, claaz);
    }

    /**
     * Maps tsBudgetInstitutionDTO to tsBudgetInstitution Response Object
     * @param tsBudgetInstitutionDTO
     * @param <T>
     */
    public <T> T tsBudgetInstitutionDTOToObject(TsBudgetInstitutionDTO tsBudgetInstitutionDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tsBudgetInstitutionDTO);
        return (T) mapper.map(tsBudgetInstitutionDTO, claaz);
    }



    /**
     * Maps tsBudgetInstitution Object to tsBudgetInstitution   Response Object
     * @param tsBudgetInstitutionRequest
     * @param <T>
     */
    public <T> T tsBudgetInstitutionObjectToEntity(TsBudgetInstitutionRequest tsBudgetInstitutionRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tsBudgetInstitutionRequest);
        return (T) mapper.map(tsBudgetInstitutionRequest, claaz);
    }


    /**
     * Maps tsReleaseBudgetTaluk Entity to tsReleaseBudgetTaluk Response Object
     * @param tsReleaseBudgetTalukEntity
     * @param <T>
     */
    public <T> T tsReleaseBudgetTalukEntityToObject(TsReleaseBudgetTaluk tsReleaseBudgetTalukEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,tsReleaseBudgetTalukEntity);
        return (T) mapper.map(tsReleaseBudgetTalukEntity, claaz);
    }

    /**
     * Maps tsReleaseBudgetTalukDTO to tsReleaseBudgetTaluk Response Object
     * @param tsReleaseBudgetTalukDTO
     * @param <T>
     */
    public <T> T tsReleaseBudgetTalukDTOToObject(TsReleaseBudgetTalukDTO tsReleaseBudgetTalukDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tsReleaseBudgetTalukDTO);
        return (T) mapper.map(tsReleaseBudgetTalukDTO, claaz);
    }



    /**
     * Maps tsReleaseBudgetTaluk Object to tsReleaseBudgetTaluk  Response Object
     * @param tsReleaseBudgetTalukRequest
     * @param <T>
     */
    public <T> T tsReleaseBudgetTalukObjectToEntity(TsReleaseBudgetTalukRequest tsReleaseBudgetTalukRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tsReleaseBudgetTalukRequest);
        return (T) mapper.map(tsReleaseBudgetTalukRequest, claaz);
    }



    /**
     * Maps tsReleaseBudgetInstitution Entity to tsReleaseBudgetInstitution Response Object
     * @param tsReleaseBudgetInstitutionEntity
     * @param <T>
     */
    public <T> T tsReleaseBudgetInstitutionEntityToObject(TsReleaseBudgetInstitution tsReleaseBudgetInstitutionEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,tsReleaseBudgetInstitutionEntity);
        return (T) mapper.map(tsReleaseBudgetInstitutionEntity, claaz);
    }

    /**
     * Maps tsReleaseBudgetInstitutionDTO to tsReleaseBudgetInstitution Response Object
     * @param tsReleaseBudgetInstitutionDTO
     * @param <T>
     */
    public <T> T tsReleaseBudgetInstitutionDTOToObject(TsReleaseBudgetInstitutionDTO tsReleaseBudgetInstitutionDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tsReleaseBudgetInstitutionDTO);
        return (T) mapper.map(tsReleaseBudgetInstitutionDTO, claaz);
    }



    /**
     * Maps tsReleaseBudgetInstitution Object to tsReleaseBudgetInstitution   Response Object
     * @param tsReleaseBudgetInstitutionRequest
     * @param <T>
     */
    public <T> T tsReleaseBudgetInstitutionObjectToEntity(TsReleaseBudgetInstitutionRequest tsReleaseBudgetInstitutionRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tsReleaseBudgetInstitutionRequest);
        return (T) mapper.map(tsReleaseBudgetInstitutionRequest, claaz);
    }


    /**
     * Maps tsReleaseBudgetDistrict Entity to tsReleaseBudgetDistrict Response Object
     * @param tsReleaseBudgetDistrictEntity
     * @param <T>
     */
    public <T> T tsReleaseBudgetDistrictEntityToObject(TsReleaseBudgetDistrict tsReleaseBudgetDistrictEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,tsReleaseBudgetDistrictEntity);
        return (T) mapper.map(tsReleaseBudgetDistrictEntity, claaz);
    }

    /**
     * Maps tsReleaseBudgetDistrictDTO to tsReleaseBudgetDistrict Response Object
     * @param tsReleaseBudgetDistrictDTO
     * @param <T>
     */
    public <T> T tsReleaseBudgetDistrictDTOToObject(TsReleaseBudgetDistrictDTO tsReleaseBudgetDistrictDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tsReleaseBudgetDistrictDTO);
        return (T) mapper.map(tsReleaseBudgetDistrictDTO, claaz);
    }



    /**
     * Maps tsReleaseBudgetDistrict Object to tsReleaseBudgetDistrict  Response Object
     * @param tsReleaseBudgetDistrictRequest
     * @param <T>
     */
    public <T> T tsReleaseBudgetDistrictObjectToEntity(TsReleaseBudgetDistrictRequest tsReleaseBudgetDistrictRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, tsReleaseBudgetDistrictRequest);
        return (T) mapper.map(tsReleaseBudgetDistrictRequest, claaz);
    }
    /**
     * Maps agency Entity to agency Response Object
     * @param agencyEntity
     * @param <T>
     */
    public <T> T agencyEntityToObject(Agency agencyEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,agencyEntity);
        return (T) mapper.map(agencyEntity, claaz);
    }


    /**
     * Maps agency Object to agency  Response Object
     * @param agencyRequest
     * @param <T>
     */
    public <T> T agencyObjectToEntity(AgencyRequest agencyRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, agencyRequest);
        return (T) mapper.map(agencyRequest, claaz);
    }

    /**
     * Maps schemeQuota Entity to schemeQuota Response Object
     * @param schemeQuotaEntity
     * @param <T>
     */
    public <T> T schemeQuotaEntityToObject(SchemeQuota schemeQuotaEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,schemeQuotaEntity);
        return (T) mapper.map(schemeQuotaEntity, claaz);
    }

    /**
     * Maps schemeQuotaDTO to schemeQuota Response Object
     * @param schemeQuotaDTO
     * @param <T>
     */
    public <T> T schemeQuotaDTOToObject(SchemeQuotaDTO schemeQuotaDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, schemeQuotaDTO);
        return (T) mapper.map(schemeQuotaDTO, claaz);
    }


    /**
     * Maps schemeQuota Object to schemeQuota  Response Object
     * @param schemeQuotaRequest
     * @param <T>
     */
    public <T> T schemeQuotaObjectToEntity(SchemeQuotaRequest schemeQuotaRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, schemeQuotaRequest);
        return (T) mapper.map(schemeQuotaRequest, claaz);
    }



    /**
     * Maps scSubSchemeMapping Entity to scSubSchemeMapping Response Object
     * @param scSubSchemeMappingEntity
     * @param <T>
     */
    public <T> T scSubSchemeMappingEntityToObject(ScSubSchemeMapping scSubSchemeMappingEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,scSubSchemeMappingEntity);
        return (T) mapper.map(scSubSchemeMappingEntity, claaz);
    }

    /**
     * Maps scSubSchemeMappingDTO to scSubSchemeMapping Response Object
     * @param scSubSchemeMappingDTO
     * @param <T>
     */
    public <T> T scSubSchemeMappingDTOToObject(ScSubSchemeMappingDTO scSubSchemeMappingDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scSubSchemeMappingDTO);
        return (T) mapper.map(scSubSchemeMappingDTO, claaz);
    }

    /**
     * Maps departmentMaster Entity to departmentMaster Response Object
     * @param departmentMasterEntity
     * @param <T>
     */
    public <T> T departmentMasterEntityToObject(DepartmentMaster departmentMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,departmentMasterEntity);
        return (T) mapper.map(departmentMasterEntity, claaz);
    }


    /**
     * Maps departmentMaster Object to departmentMaster  Response Object
     * @param departmentMasterRequest
     * @param <T>
     */
    public <T> T departmentMasterObjectToEntity(DepartmentMasterRequest departmentMasterRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, departmentMasterRequest);
        return (T) mapper.map(departmentMasterRequest, claaz);
    }


    /**
     * Maps rendittaMaster Entity to rendittaMaster Response Object
     * @param rendittaMasterEntity
     * @param <T>
     */
    public <T> T rendittaMasterEntityToObject(RendittaMaster rendittaMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,rendittaMasterEntity);
        return (T) mapper.map(rendittaMasterEntity, claaz);
    }

    /**
     * Maps rendittaMasterDTO to rendittaMaster Response Object
     * @param rendittaMasterDTO
     * @param <T>
     */
    public <T> T rendittaMasterDTOToObject(RendittaMasterDTO rendittaMasterDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, rendittaMasterDTO);
        return (T) mapper.map(rendittaMasterDTO, claaz);
    }



    /**
     * Maps rendittaMaster Object to rendittaMaster  Response Object
     * @param rendittaMasterRequest
     * @param <T>
     */
    public <T> T rendittaMasterObjectToEntity(RendittaMasterRequest rendittaMasterRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, rendittaMasterRequest);
        return (T) mapper.map(rendittaMasterRequest, claaz);
    }

    /**
     * Maps inspectionType Entity to inspectionType Response Object
     * @param inspectionTypeEntity
     * @param <T>
     */
    public <T> T inspectionTypeEntityToObject(InspectionType inspectionTypeEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,inspectionTypeEntity);
        return (T) mapper.map(inspectionTypeEntity, claaz);
    }


    /**
     * Maps inspectionType Object to inspectionType  Response Object
     * @param inspectionTypeRequest
     * @param <T>
     */
    public <T> T inspectionTypeObjectToEntity(InspectionTypeRequest inspectionTypeRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, inspectionTypeRequest);
        return (T) mapper.map(inspectionTypeRequest, claaz);
    }

    /**
     * Maps userHierarchyMapping Entity to userHierarchyMapping Response Object
     * @param userHierarchyMappingEntity
     * @param <T>
     */
    public <T> T userHierarchyMappingEntityToObject(UserHierarchyMapping userHierarchyMappingEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,userHierarchyMappingEntity);
        return (T) mapper.map(userHierarchyMappingEntity, claaz);
    }


    /**
     * Maps inspectionType Object to inspectionType  Response Object
     * @param userHierarchyMappingRequest
     * @param <T>
     */
    public <T> T userHierarchyMappingObjectToEntity(UserHierarchyMappingRequest userHierarchyMappingRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, userHierarchyMappingRequest);
        return (T) mapper.map(userHierarchyMappingRequest, claaz);
    }

    /**
     * Maps rejectReasonWorkflowMaster Entity to rejectReasonWorkflowMaster Response Object
     * @param rejectReasonWorkflowMasterEntity
     * @param <T>
     */
    public <T> T rejectReasonWorkflowMasterEntityToObject(RejectReasonWorkFlowMaster rejectReasonWorkflowMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,rejectReasonWorkflowMasterEntity);
        return (T) mapper.map(rejectReasonWorkflowMasterEntity, claaz);
    }


    /**
     * Maps rejectReasonWorkflowMaster Object to rejectReasonWorkflowMaster  Response Object
     * @param rejectReasonWorkflowMasterRequest
     * @param <T>
     */
    public <T> T rejectReasonWorkflowMasterObjectToEntity(RejectReasonWorkFlowMasterRequest rejectReasonWorkflowMasterRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, rejectReasonWorkflowMasterRequest);
        return (T) mapper.map(rejectReasonWorkflowMasterRequest, claaz);
    }

    /**
     * Maps scSubSchemeMapping Object to scSubSchemeMapping  Response Object
     * @param scSubSchemeMappingRequest
     * @param <T>
     */
    public <T> T scSubSchemeMappingObjectToEntity(ScSubSchemeMappingRequest scSubSchemeMappingRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scSubSchemeMappingRequest);
        return (T) mapper.map(scSubSchemeMappingRequest, claaz);
    }


    /**
     * Maps scProgramAccountMapping Entity to scProgramAccountMapping Response Object
     * @param scProgramAccountMappingEntity
     * @param <T>
     */
    public <T> T scProgramAccountMappingEntityToObject(ScProgramAccountMapping scProgramAccountMappingEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,scProgramAccountMappingEntity);
        return (T) mapper.map(scProgramAccountMappingEntity, claaz);
    }

    /**
     * Maps scProgramAccountMappingDTO to scProgramAccountMapping Response Object
     * @param scProgramAccountMappingDTO
     * @param <T>
     */
    public <T> T scProgramAccountMappingDTOToObject(ScProgramAccountMappingDTO scProgramAccountMappingDTO, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scProgramAccountMappingDTO);
        return (T) mapper.map(scProgramAccountMappingDTO, claaz);
    }



    /**
     * Maps scProgramAccountMapping Object to scProgramAccountMapping  Response Object
     * @param scProgramAccountMappingRequest
     * @param <T>
     */
    public <T> T scProgramAccountMappingObjectToEntity(ScProgramAccountMappingRequest scProgramAccountMappingRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, scProgramAccountMappingRequest);
        return (T) mapper.map(scProgramAccountMappingRequest, claaz);
    }

    /**
     * Maps hectareMaster Entity to hectareMaster Response Object
     * @param hectareMasterEntity
     * @param <T>
     */
    public <T> T hectareMasterEntityToObject(HectareMaster hectareMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,hectareMasterEntity);
        return (T) mapper.map(hectareMasterEntity, claaz);
    }

    /**
     * Maps hectareMaster Object to hectareMaster  Response Object
     * @param hectareMasterRequest
     * @param <T>
     */
    public <T> T hectareMasterObjectToEntity(HectareMasterRequest hectareMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, hectareMasterRequest);
        return (T) mapper.map(hectareMasterRequest, claaz);
    }

    /**
     * Maps spacingMaster Entity to spacingMaster Response Object
     * @param spacingMasterEntity
     * @param <T>
     */
    public <T> T spacingMasterEntityToObject(SpacingMaster spacingMasterEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,spacingMasterEntity);
        return (T) mapper.map(spacingMasterEntity, claaz);
    }

    /**
     * Maps spacing Object to spacingMaster  Response Object
     * @param spacingMasterRequest
     * @param <T>
     */
    public <T> T spacingMasterObjectToEntity(SpacingMasterRequest spacingMasterRequest, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, spacingMasterRequest);
        return (T) mapper.map(spacingMasterRequest, claaz);
    }
    /**
     * Maps farmerBankAccountReasonEntity to farmerBankAccountReason Response Object
     * @param farmerBankAccountReasonEntity
     * @param <T>
     */
    public <T> T farmerBankAccountReasonEntityToObject(FarmerBankAccountReason farmerBankAccountReasonEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper,farmerBankAccountReasonEntity);
        return (T) mapper.map(farmerBankAccountReasonEntity, claaz);
    }


    /**
     * Maps farmerBankAccountReason Object to farmerBankAccountReason  Response Object
     * @param farmerBankAccountReasonRequest
     * @param <T>
     */
    public <T> T farmerBankAccountReasonObjectToEntity(FarmerBankAccountReasonRequest farmerBankAccountReasonRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, farmerBankAccountReasonRequest);
        return (T) mapper.map(farmerBankAccountReasonRequest, claaz);
    }

}
