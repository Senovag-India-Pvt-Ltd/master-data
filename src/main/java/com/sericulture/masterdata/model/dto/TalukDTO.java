package com.sericulture.masterdata.model.dto;

import com.sericulture.masterdata.model.entity.Taluk;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TalukDTO {
    private Long talukId;
    private String talukName;
    private String talukNameInKannada;
    private Long stateId;
    private Long districtId;
    private String lgTaluk;
    private String stateName;
    private String districtName;

    public TalukDTO(){

    }
    public TalukDTO(Long talukId,String talukName,String talukNameInKannada,Long stateId,Long districtId,String lgTaluk,String stateName,String districtName) {
        this.talukId = talukId;
        this.talukName = talukName;
        this.talukNameInKannada = talukNameInKannada;
        this.stateId = stateId;
        this.districtId = districtId;
        this.lgTaluk = lgTaluk;
        this.stateName = stateName;
        this.districtName = districtName;

    }
}
