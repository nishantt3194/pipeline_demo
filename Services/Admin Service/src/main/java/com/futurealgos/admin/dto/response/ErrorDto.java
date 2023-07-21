package com.futurealgos.admin.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorDto {

    String timeStamp;
    String status;
    String error;
}
