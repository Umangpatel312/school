package com.school.management.service;

import com.school.management.web.rest.errors.BadRequestAlertException;
import com.school.management.web.rest.errors.ErrorConstants;

public class AttendenceAlreadyExist extends RuntimeException {
    public AttendenceAlreadyExist(){
        super("user already taken attendence given date");
    }
}
