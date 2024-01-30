package com.sericulture.masterdata.model.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HdQuestionMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hd_question_master_seq")
    @SequenceGenerator(name = "hd_question_master_seq", sequenceName = "hd_question_master_seq", allocationSize = 1)
    @Column(name = "hd_question_id")
    private Long hdQuestionId;

    @Size(min = 2, max = 250, message = "Hd Question name should be more than 1 characters.")
    @Column(name = "hd_question_name", unique = true)
    private String hdQuestionName;

    @Column(name = "hd_question_answer_name", unique = true)
    private String hdQuestionAnswerName;

    @Column(name = "hd_faq_upload_path", unique = true )
    private String hdFaqUploadPath;
}
