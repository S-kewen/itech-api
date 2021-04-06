package com.boot.yuntechlife.util;

import org.apache.tomcat.util.http.fileupload.FileItem;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

public interface TencentCloudApiUtil {
    boolean codeResponse(String userIp, String ticket, String randstr);

    Map<String, Object> cosUpload(File fileItem, String key) throws Exception;

    boolean deleteTempFile(File... files);
}
