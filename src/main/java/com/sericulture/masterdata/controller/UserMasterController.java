package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.useMaster.*;
import com.sericulture.masterdata.model.dto.UserMasterDTO;
import com.sericulture.masterdata.service.UserMasterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/userMaster")
public class UserMasterController {

    @Autowired
    UserMasterService userMasterService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        response.put("validationErrors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @Operation(summary = "Insert UserMaster Details", description = "Creates UserMaster Details in to DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"UserMaster name should be more than 1 characters.\",\"label\":\"name\",\"locale\":null}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/add")
    public ResponseEntity<?> addUserMasterDetails(@Valid @RequestBody UserMasterRequest userMasterRequest){
        ResponseWrapper rw = ResponseWrapper.createWrapper(UserMasterResponse.class);

        rw.setContent(userMasterService.insertUserMasterDetails(userMasterRequest));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/get-all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":6,\"userMaster\":[{\"id\":10,\"userMasterId\":\"\"},{\"id\":11,\"userMasterId\":\" 1\"},{\"id\":13,\"userMasterId\":\"2\"}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    public ResponseEntity<?> getAllByActive(
            @RequestParam(defaultValue = "true") boolean isActive
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(userMasterService.getAllByActive(isActive));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":1,\"userMaster\":[{\"id\":1,\"userMasterId\":\"\"},{\"id\":2,\"userMasterId\":\" 1\"}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    public ResponseEntity<?> getPaginatedList(
            @RequestParam(defaultValue = "0") final Integer pageNumber,
            @RequestParam(defaultValue = "5") final Integer size
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(userMasterService.getPaginatedUserMasterDetails(PageRequest.of(pageNumber, size)));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserMasterDetails(
            @PathVariable final Integer id
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(userMasterService.deleteUserMasterDetails(id));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object saved details"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/edit")
    public ResponseEntity<?> editUserMasterDetails(
            @Valid @RequestBody final EditUserMasterRequest editUserMasterRequest
    ) {
        ResponseWrapper<UserMasterResponse> rw = ResponseWrapper.createWrapper(UserMasterResponse.class);
        rw.setContent(userMasterService.updateUserMasterDetails(editUserMasterRequest));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(
            @PathVariable final Integer id
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(UserMasterResponse.class);

        rw.setContent(userMasterService.getById(id));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @GetMapping("/get-join/{id}")
    public ResponseEntity<?> getByIdJoin(
            @PathVariable final Integer id
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(UserMasterResponse.class);

        rw.setContent(userMasterService.getByIdJoin(id));
        return ResponseEntity.ok(rw);
    }
    @GetMapping("/list-with-join")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":6,\"userMaster\":[{\"id\":10,\"userMasterName\":\"\",\"userMasterId\":1,},{\"id\":11,\"userMasterName\":\"Shimoga\",\"userMasterId\":1,},{\"id\":13,\"userMasterName\":\"Hubli\",\"userMasterId\":1,}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    public ResponseEntity<?> getPaginatedUserMasterDetailsWithJoin(
            @RequestParam(defaultValue = "0") final Integer pageNumber,
            @RequestParam(defaultValue = "5") final Integer size
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(userMasterService.getPaginatedUserMasterDetailsWithJoin(PageRequest.of(pageNumber, size)));
        return ResponseEntity.ok(rw);
    }

    /*@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/get-by-user-name-and-password")
    public ResponseEntity<?> getByUserNameAndPassword(
            @RequestBody final GetUserNamePasswordRequest getUserNamePasswordRequest
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(UserMasterResponse.class);

        rw.setContent(userMasterService.getByUserNameAndPassword(getUserNamePasswordRequest.getUsername(), getUserNamePasswordRequest.getPassword()));
        return ResponseEntity.ok(rw);
    }*/

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/get-by-user-name-and-password")
    public ResponseEntity<?> getByUserNameAndPassword(
            @Valid @RequestBody final UserMasterDTO userMasterDTO
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(UserMasterResponse.class);

        rw.setContent(userMasterService.getByUserNameAndPassword(userMasterDTO.getUsername(), userMasterDTO.getPassword()));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/get-by-roleId-and-talukId")
    public ResponseEntity<?> getByRoleIdAndTalukId(
            @Valid @RequestBody final UserMasterDTO userMasterDTO
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(UserMasterResponse.class);

        rw.setContent(userMasterService.getByRoleIdAndTalukId(userMasterDTO.getRoleId(), userMasterDTO.getTalukId()));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/get-by-designationId-and-districtId")
    public ResponseEntity<?> getByDesignationIdAndDistrictId(
            @Valid @RequestBody final UserMasterDTO userMasterDTO
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(UserMasterResponse.class);

        rw.setContent(userMasterService.getByDesignationIdAndDistrictId(userMasterDTO.getDesignationId(), userMasterDTO.getDistrictId()));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/get-by-designationId-and-districtId-and-talukId")
    public ResponseEntity<?> getByDesignationIdAndDistrictIdAndTalukId(
            @Valid @RequestBody final UserMasterDTO userMasterDTO
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(UserMasterResponse.class);

        rw.setContent(userMasterService.getByDesignationIdAndDistrictIdAndTalukId(userMasterDTO.getDesignationId(), userMasterDTO.getDistrictId(), userMasterDTO.getTalukId()));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/generate-otp-by-user-name")
    public ResponseEntity<?> generateOtpByUserName(
            @Valid @RequestBody final UserMasterDTO userMasterDTO
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(UserMasterResponse.class);

        rw.setContent(userMasterService.generateOtpByUserName(userMasterDTO));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/generate-otp-by-user-name-and-password")
    public ResponseEntity<?> generateOtpByUserNameAndPassword(
            @Valid @RequestBody final UserMasterDTO userMasterDTO
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(UserMasterResponse.class);

        rw.setContent(userMasterService.generateOtpByUserNameAndPassword(userMasterDTO));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/login-without-otp")
    public ResponseEntity<?> loginWithoutOtp(
            @Valid @RequestBody final UserMasterDTO userMasterDTO
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(UserMasterResponse.class);

        rw.setContent(userMasterService.loginWithoutOtp(userMasterDTO));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/verify-otp-by-user-name")
    public ResponseEntity<?> verifyOtp(
            @Valid @RequestBody final UserMasterDTO userMasterDTO
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(UserMasterResponse.class);

        rw.setContent(userMasterService.verifyOtp(userMasterDTO));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/login-by-user-name-and-password")
    public ResponseEntity<?> loginByUserNameAndPassword(
            @Valid @RequestBody final UserMasterDTO userMasterDTO
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(UserMasterResponse.class);

        rw.setContent(userMasterService.getLoginDetails(userMasterDTO.getUsername(), userMasterDTO.getPassword()));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object saved details"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/save-reeler-user")
    public ResponseEntity<?> saveReelerUser(
            @Valid @RequestBody final SaveReelerUserRequest saveReelerUserRequest
    ) {
        ResponseWrapper<UserMasterResponse> rw = ResponseWrapper.createWrapper(UserMasterResponse.class);
        rw.setContent(userMasterService.saveReelerUser(saveReelerUserRequest));
        return ResponseEntity.ok(rw);
    }

    @PostMapping("/save-trader-user")
    public ResponseEntity<?> saveTraderUser(
            @Valid @RequestBody final SaveReelerUserRequest saveReelerUserRequest
    ) {
        ResponseWrapper<UserMasterResponse> rw = ResponseWrapper.createWrapper(UserMasterResponse.class);
        rw.setContent(userMasterService.saveTraderUser(saveReelerUserRequest));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object saved details"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/dummy-save-reeler-user")
    public ResponseEntity<?> dummyReelerUser(
            @Valid @RequestBody final SaveReelerUserRequest saveReelerUserRequest
    ) {
        ResponseWrapper<UserMasterResponse> rw = ResponseWrapper.createWrapper(UserMasterResponse.class);
        rw.setContent(userMasterService.saveForReelerUser(saveReelerUserRequest));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object saved details"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/edit-reeler-user")
    public ResponseEntity<?> editReelerUser(
            @Valid @RequestBody final EditReelerUserRequest saveReelerUserRequest
    ) {
        ResponseWrapper<UserMasterResponse> rw = ResponseWrapper.createWrapper(UserMasterResponse.class);
        rw.setContent(userMasterService.editReelerUser(saveReelerUserRequest));
        return ResponseEntity.ok(rw);
    }

    @PostMapping("/edit-trader-user")
    public ResponseEntity<?> editTraderUser(
            @Valid @RequestBody final EditReelerUserRequest saveReelerUserRequest
    ) {
        ResponseWrapper<UserMasterResponse> rw = ResponseWrapper.createWrapper(UserMasterResponse.class);
        rw.setContent(userMasterService.editTraderUser(saveReelerUserRequest));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object saved details"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/save-external-user")
    public ResponseEntity<?> saveExternalUser(
            @Valid @RequestBody final SaveReelerUserRequest saveReelerUserRequest
    ) {
        ResponseWrapper<UserMasterResponse> rw = ResponseWrapper.createWrapper(UserMasterResponse.class);
        rw.setContent(userMasterService.saveExternalUnitRegistration(saveReelerUserRequest));
        return ResponseEntity.ok(rw);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object saved details"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/search")
    public ResponseEntity<?> search(
            @Valid @RequestBody final SearchWithSortRequest searchWithSortRequest
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(userMasterService.searchByColumnAndSort(searchWithSortRequest));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object saved details"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody final UserMasterChangePasswordRequest userMasterChangePasswordRequest
    ) {
        ResponseWrapper<UserMasterResponse> rw = ResponseWrapper.createWrapper(UserMasterResponse.class);
        rw.setContent(userMasterService.changePassword(userMasterChangePasswordRequest));
        return ResponseEntity.ok(rw);
    }

    @PostMapping("/get-reeler-users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":6,\"userMaster\":[{\"id\":10,\"userMasterId\":\"\"},{\"id\":11,\"userMasterId\":\" 1\"},{\"id\":13,\"userMasterId\":\"2\"}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    public ResponseEntity<?> getReelerUsers(
            @Valid @RequestBody final UserMasterDTO userMasterDTO
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(userMasterService.getAllReelerUsers(true, userMasterDTO.getUserTypeId()));
        return ResponseEntity.ok(rw);
    }
    @PostMapping("/get-trader-users")
    public ResponseEntity<?> getTraderUsers(
            @Valid @RequestBody final UserMasterDTO userMasterDTO
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(userMasterService.getAllTraderUsers(true, userMasterDTO.getUserTypeId()));
        return ResponseEntity.ok(rw);
    }

    @PostMapping("/get-escalate-users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":6,\"userMaster\":[{\"id\":10,\"userMasterId\":\"\"},{\"id\":11,\"userMasterId\":\" 1\"},{\"id\":13,\"userMasterId\":\"2\"}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    public ResponseEntity<?> getEscalateRoleUsers( @Valid @RequestBody final UserMasterDTO userMasterDTO) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(userMasterService.getEscalateRoleUsers(userMasterDTO.getRoleName()));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object saved details"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/get-configure-user-details-for-reeler")
    public ResponseEntity<?> getConfigureUserDetailsForReeler(
            @Valid @RequestBody final UserMasterDTO userMasterDTO
    ) {
        ResponseWrapper<UserMasterResponse> rw = ResponseWrapper.createWrapper(UserMasterResponse.class);
        rw.setContent(userMasterService.getConfigureUserDetailsForReeler( true, userMasterDTO.getUserTypeId()));
        return ResponseEntity.ok(rw);
    }

    @PostMapping("/get-configure-user-details-for-trader")
    public ResponseEntity<?> getConfigureUserDetailsForTrader(
            @Valid @RequestBody final UserMasterDTO userMasterDTO
    ) {
        ResponseWrapper<UserMasterResponse> rw = ResponseWrapper.createWrapper(UserMasterResponse.class);
        rw.setContent(userMasterService.getConfigureUserDetailsForTrader( true, userMasterDTO.getUserTypeId()));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/get-by-designationId-and-districtId-and-talukId-and-workingInstitutionId")
    public ResponseEntity<?> getByDesignationIdAndDistrictIdAndTalukIdAndWorkingInstitutionId(
            @Valid @RequestBody final UserMasterDTO userMasterDTO
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(UserMasterResponse.class);

        rw.setContent(userMasterService.getByDesignationIdAndDistrictIdAndTalukIdAndWorkingInstitutionId(userMasterDTO.getDesignationId(), userMasterDTO.getDistrictId(), userMasterDTO.getTalukId(), userMasterDTO.getWorkingInstitutionId()));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/get-by-tsc-master-id/{tscMasterId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    public ResponseEntity<?> getUserByTscMasterId(
            @PathVariable final Long tscMasterId
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(userMasterService.getUserByTscMasterId(tscMasterId));
        return ResponseEntity.ok(rw);
    }

}
