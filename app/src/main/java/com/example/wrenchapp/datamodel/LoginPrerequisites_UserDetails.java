package com.example.wrenchapp.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginPrerequisites_UserDetails {
    String LOGIN_NAME;
    String USER_NAME;
    String EXT_MAIL_ID;
    Integer USER_ID;
    String ORIGIN_CODE;
    Integer ORIGIN_ID;
    Integer ORIGIN;
    String MULTI_FACTOR_AUTH_OTP_SOURCE;

    @SerializedName("AUTHENTICATION_MODE")
    @Expose
    Integer AUTHENTICATION_MODE;
    public Integer getAuthenticationMode(){return AUTHENTICATION_MODE;}
    public void setAuthenticationMode(Integer val){AUTHENTICATION_MODE=val;}

    Integer CAN_OVERRIDE_MULTI_FACTOR_AUTH_OTP_SOURCE;
    Integer ATTEMPT_COUNT;
    String LOCK_EXPIRY;
    String LAST_LOGIN_FAILURE_ON;
    Integer ACCESS_STATUS;
    Integer LOGIN_CAPTCHA_REQD_ATTEMPT_COUNT;
    Integer LOGIN_LOCK_ATTEMPT_COUNT;
    Double LOGIN_LOCK_DURATION;

}
