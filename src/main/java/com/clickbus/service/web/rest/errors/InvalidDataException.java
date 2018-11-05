package com.clickbus.service.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class InvalidDataException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public InvalidDataException(String message, String entity) {
        super(ErrorConstants.EMAIL_ALREADY_USED_TYPE, message, entity, "invaliddata");
    }
}
