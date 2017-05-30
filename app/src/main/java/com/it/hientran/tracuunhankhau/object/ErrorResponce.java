package com.it.hientran.tracuunhankhau.object;

/**
 * Created by admin on 5/22/2017.
 */

public class ErrorResponce {


    /**
     * errorcode : 0
     * contentError : methodName có thể không đúng
     */

    private int errorcode;
    private String contentError;

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getContentError() {
        return contentError;
    }

    public void setContentError(String contentError) {
        this.contentError = contentError;
    }
}
