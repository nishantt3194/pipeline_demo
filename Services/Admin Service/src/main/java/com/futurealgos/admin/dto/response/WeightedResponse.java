package com.futurealgos.admin.dto.response;

import java.io.Serializable;

public class WeightedResponse implements Serializable {

    public String message;

    public Object data;

    public WeightedResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}