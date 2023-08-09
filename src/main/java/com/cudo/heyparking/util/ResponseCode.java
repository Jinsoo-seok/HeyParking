package com.cudo.heyparking.util;

public enum ResponseCode {

    SUCCESS(200, "SUCCESS", "SUCCESS"),
    NO_DATA(201, "NO_DATA", "NO_DATA"),
    NO_CONTENT(204, "NO_CONTENT", "No Content"),
    FAIL(500, "FAIL", "FAIL"),
    FAIL_CONNECT(8888, "Fail Connect Server", "Fail Connect Server"),



    // API : 1000
    NO_REQUIRED_PARAM(1000, "NoRequiredParam", "No Required Parameter"),
    NO_REQUIRED_VALUE(1001, "NoRequiredValue", "No Required Parameter Value"),
    INVALID_PARAM_TYPE(1002, "InvalidParamType", "Invalid Parameter Type"),
    INVALID_PARAM_LENGTH(1003, "InvalidParamLength", "Invalid Parameter Length"),
    INVALID_PARAM_VALUE(1004, "InvalidParamValue", "Invalid Parameter Value"),



    // Login, SignUp : 2000
    FAIL_INVALID_USER_ID(2000, "InvalidUserId", "Invalid User ID"),
    FAIL_INVALID_USER_PASSWORD(2001, "InvalidUserPassword", "Invalid User Password"),
    DUPLICATE_ID(2002, "DuplicateId", "Duplicate ID"),
    NOT_EXIST_ID(2003, "NotExistId", "Not Exist ID"),
    FAIL_CHANGE_PW(2004, "FailedChangePW", "Failed Change PW"),
    FAIL_SIGN(2005, "FailedSignUp", "Failed Sign-Up"),


    // Parking : 3000
    // Agent : 3100
    FAIL_INSERT_PARKING(3100, "FailedInsertParkingInfo", "Failed Insert Parking Info"),
    FAIL_UPDATE_PARKING(3101, "FailedUpdateParkingInfo", "Failed Update Parking Info"),
    FAIL_DUPLICATE_PARKING(3102, "FailedDuplicatedParkingInfo", "Failed Duplicated Parking Info"),
    FAIL_DELETE_PARKING(3103, "FailedDeleteParkingInfo", "Failed Delete Parking Info"),
    FAIL_NOT_EXIST_PARKING(3103, "FailedNotExistParkingInfo", "Failed Not Exist Parking Info"),

    FAIL_EXCEED_PARKING_CAPACITY(3104, "FailedExceedParkingCapacity", "Number of empty parking spaces exceeds the parking capacity."),
    FAIL_INVALID_PARKING_CAPACITY(3105, "FailedInvalidParkingCapacity", "Invalid parking capacity due to negative parking space count."),


    // FO : 3200
    FAIL_INSERT_SCREEN(3100, "FailedInsertScreen", "Failed Insert Screen"),
    FAIL_UPDATE_SCREEN(3101, "FailedUpdateScreen", "Failed Update Screen"),
    FAIL_DUPLICATE_SCREEN(3102, "FailedDuplicateScreen", "Failed Duplicate Screen"),
    FAIL_DELETE_SCREEN(3103, "FailedDeleteScreen", "Failed Delete Screen"),
    FAIL_NOT_EXIST_SCREEN(3104, "FailedNotExistScreen", "Failed Not Exist Screen"),
    FAIL_DELETE_SCREEN_ALLOCATE_DISPLAY(3105, "FailedDeleteScreenAllocateDisplays", "Failed Delete Screen Allocate Displays"),
    FAIL_INSERT_SCREEN_ALLOCATE_DISPLAYS(3106, "FailedInsertScreenAllocateDisplays", "Failed Insert Screen Allocate Displays");



    private final int code;
    private final String codeName;
    private final String message;

    ResponseCode(int code, String codeName, String message){
        this.code = code;
        this.codeName = codeName;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }

    public String getMessage() {
        return message;
    }
}