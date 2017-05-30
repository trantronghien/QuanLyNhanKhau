package com.it.hientran.tracuunhankhau.internet;

import android.util.Log;

/**
 * Created by admin on 5/19/2017.
 */

public final class Config {

    public static final String XML_FORMAT = "xml";
    public static final String JSON_FORMAT = "json";

    public static final String SEARCH_METHOD_END_WITH = "EndWith";
    public static final String SEARCH_METHOD_START_WITH = "StartWith";
    public static final String SEARCH_METHOD_CONTAINS_WITH = "ContainsWith";
    public static final String LIST_METHOD = "getNguoiDan";

    public static final String HOST_NAME_SEARCH = "http://quanlynhankhau.000webhostapp.com/search.php";
    public static final String HOST_NAME_SERVER = "http://quanlynhankhau.000webhostapp.com/server.php";
    // http://tangchi83.16mb.com/
    public static final String HOST_TEST = "http://quanlynhankhau.000webhostapp.com/server.php?format=json&methodName=getNguoiDan&page=1";
    public static final String LOCAL_HOST_SEARCH = "http://192.168.158.141:86/nhankhau/search.php";
    public static final String LOCAL_HOST_SERVER = "http://192.168.158.141:86/nhankhau/server.php";

    public static final String INTENT_KEY_DETAIL_ITEM = "objectItem";
    public static final String INTENT_KEY_TAG_ID = "idTag";

    public static final String POST_VALUE = "";
    public static final String POST_KEY = "info" ;

    // http://quanlynhankhau.000webhostapp.com/search.php??format=json&search=hiens&method=EndWith&page=3
    public static final String convertToRequestSearch(String format, String searchRequest, String methodName, int page) {
        String formatter = new StringBuilder(HOST_NAME_SEARCH).append("?format=%s&search=%s&method=%s&page=%d").toString();
        return String.format(formatter, format, searchRequest, methodName, page);
    }

    public static final String convertToRequestGetList(String format, String methodName, int page ,final String TAG) {
        page = page <= 0 ? 1 : page;
        String formatter = new StringBuilder(HOST_NAME_SERVER).append("?format=%s&methodName=%s&page=%d").toString();
        String result = String.format(formatter, format, methodName, page);
        Log.i(TAG , result);
        return result;
    }

    public static final String convertToRequestGetList(final String HOST_NAME, String format, String methodName, int page ,final String TAG) {
        page = page <= 0 ? 1 : page;
        String formatter = new StringBuilder(HOST_NAME).append("?format=%s&methodName=%s&page=%d").toString();
        String result = String.format(formatter, format, methodName, page);
        Log.i(TAG , result);
        return result;
    }
}
