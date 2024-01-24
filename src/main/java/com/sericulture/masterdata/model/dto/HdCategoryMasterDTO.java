package com.sericulture.masterdata.model.dto;


import lombok.Data;

@Data
public class HdCategoryMasterDTO {
    private Long hdCategoryId;
    private Long hdBoardCategoryId;
    private String hdCategoryName;
    private String hdBoardCategoryName;

    public HdCategoryMasterDTO(Long hdCategoryId,
                               Long hdBoardCategoryId,
                               String hdCategoryName,
                               String hdBoardCategoryName){
        this.hdCategoryId=hdCategoryId;
        this.hdBoardCategoryId=hdBoardCategoryId;
        this.hdCategoryName=hdCategoryName;
        this.hdBoardCategoryName=hdBoardCategoryName;
    }

}
