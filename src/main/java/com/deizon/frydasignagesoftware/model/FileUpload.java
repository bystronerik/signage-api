package com.deizon.frydasignagesoftware.model;

import java.io.InputStream;

public class FileUpload {

    private final String contentType;
    private final InputStream content;

    public FileUpload(String contentType, InputStream content) {
        this.contentType = contentType;
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getContent() {
        return content;
    }
}
