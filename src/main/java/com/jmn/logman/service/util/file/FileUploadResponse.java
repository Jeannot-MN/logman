package com.jmn.logman.service.util.file;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FileUploadResponse {

    private final String key;

    private final String fileUrl;

    @JsonCreator
    public FileUploadResponse(@JsonProperty("key") String key, @JsonProperty("fileUrl") String fileUrl) {
        this.key = key;
        this.fileUrl = fileUrl;
    }

    public String getKey() {
        return key;
    }

    public String getFileUrl() {
        return fileUrl;
    }
}
