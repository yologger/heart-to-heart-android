package com.yologger.domain.usecase.auth.emailVerificationCode;

public enum EmailVerificationCodeResultError {
    NETWORK_ERROR,
    MAIL_ERROR,
    CLIENT_ERROR,
    MEMBER_ALREADY_EXIST,
    INVALID_PARAMS,
    JSON_PARSE_ERROR
}
