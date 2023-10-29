package com.sericulture.masterdata.handlers;

import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.ErrorResponse;
import com.sericulture.masterdata.model.api.ErrorType;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.List;


@ControllerAdvice
public class GlobalAPIExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleEmployeeNotFound(
            ValidationException exception
    ) {
        List<ErrorResponse> errorResponses = Arrays.asList(new ErrorResponse(exception.getErrorMessages(), ErrorType.VALIDATION));
        ResponseWrapper wr = new ResponseWrapper();
        wr.setErrorMessages(errorResponses);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(wr);
    }
}