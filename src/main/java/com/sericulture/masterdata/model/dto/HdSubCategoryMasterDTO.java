package com.sericulture.masterdata.model.dto;


import lombok.Data;

@Data
public class HdSubCategoryMasterDTO {
    private Long hdSubCategoryId;
    private Long hdCategoryId;
    private Long hdBoardCategoryId;
    private String hdSubCategoryName;
    private String hdCategoryName;
    private String hdBoardCategoryName;

    public HdSubCategoryMasterDTO() {
    }

    public HdSubCategoryMasterDTO(Long hdSubCategoryId,
                                  Long hdCategoryId,
                                  Long hdBoardCategoryId,
                                  String hdSubCategoryName,
                                  String hdCategoryName,
                                  String hdBoardCategoryName){
        this.hdSubCategoryId=hdSubCategoryId;
        this.hdCategoryId=hdCategoryId;
        this.hdBoardCategoryId=hdBoardCategoryId;
        this.hdSubCategoryName=hdSubCategoryName;
        this.hdCategoryName=hdCategoryName;
        this.hdBoardCategoryName=hdBoardCategoryName;
    }

}


