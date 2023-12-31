package com.cudo.heyparking.config;

import com.cudo.heyparking.util.ResponseCode;

public class ParamException extends Throwable {

    private static final long serialVersionUID = 1L;

    private final ResponseCode responseCode;
    private final int code;
    private final String codeName;
    private final String message;
    private final String key;

    public ParamException(ResponseCode responseCode) {
        this.responseCode = responseCode;
        this.code = responseCode.getCode();
        this.codeName = responseCode.getCodeName();
        this.message = responseCode.getMessage();
        this.key = "";
    }

    public ParamException(ResponseCode responseCode, String parameterKey) {
        this.responseCode = responseCode;
        this.code = responseCode.getCode();
        this.codeName = responseCode.getCodeName();
        this.message = responseCode.getMessage();
        this.key = parameterKey;
    }

    public ResponseCode getResponseCode() {
        return this.responseCode;
    }

    public int getCode(){
        return this.code;
    }

    public String getCodeName(){
        return this.codeName;
    }

    public String getMessage() {
        String message = this.message;

        if(this.key.length() > 0) {
            message += " (" + this.key + ")";
        }

        return message;
    }

    public String getKey() {
        return this.key;
    }

}
