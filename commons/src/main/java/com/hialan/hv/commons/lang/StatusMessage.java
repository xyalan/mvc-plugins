package com.hialan.hv.commons.lang;

/**
 * Auth: alanlhy@gmail.com
 * Date Time: 2013-05-03 15:02
 * description
 */
public class StatusMessage {

    public StatusMessage fail(String message) {
        this.success = false;
        this.message = message;
        return this;
    }

    public StatusMessage success(String message) {
        this.message = message;
        return this;
    }

    private boolean success = true;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StatusMessage(boolean success) {
        this.success = success;
    }

    public StatusMessage() {
    }
}
