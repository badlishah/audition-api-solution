package com.audition.common.exception;

import java.io.Serial;

public class ServerException extends SystemException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String title = "Server Error";

    public ServerException(final String detail, final Integer errorCode) {
        super(detail, title, errorCode);
    }
}
