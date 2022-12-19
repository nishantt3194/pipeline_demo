/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.utils.classes;

import com.futurealgos.micro.testing.dto.response.PayloadError;
import com.futurealgos.micro.testing.exceptions.InvalidPayloadFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;

@Service
public class PayloadErrorResolver {

    public ResponseEntity<PayloadError> resolve(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        for (Object object : result.getAllErrors()) {

            if (object instanceof FieldError) {
                FieldError error = (FieldError) object;
                errors.put(error.getField(), error.getDefaultMessage());
            } else if (object instanceof ObjectError) {
                ObjectError error = (ObjectError) object;
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
        }


        throw new InvalidPayloadFormatException("Invalid Data Format. Please check values again before submitting", errors);

    }
}
