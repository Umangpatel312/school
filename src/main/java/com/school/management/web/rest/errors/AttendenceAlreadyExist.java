package com.school.management.web.rest.errors;

public class AttendenceAlreadyExist extends BadRequestAlertException{
    public AttendenceAlreadyExist(){
        super(ErrorConstants.USER_ALREADY_TAKEN_ATTENDENCE,"user already taken attendence given date",
            "AttendenceStudent","attendence exist");
    }
}
