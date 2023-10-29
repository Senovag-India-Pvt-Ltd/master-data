package com.sericulture.masterdata.model.api;

import com.sericulture.masterdata.model.exceptions.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse extends ResponseBody {
    List<? extends Message> Message;

    ErrorType errorType;

}

