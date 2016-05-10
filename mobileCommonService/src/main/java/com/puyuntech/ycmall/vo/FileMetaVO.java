package com.puyuntech.ycmall.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * 文件
 */
//ignore "bytes" when return json format
@JsonIgnoreProperties({"bytes"})
public class FileMetaVO {

    private String fileName;
    private String fileSize;
    private String fileType;
    private String filePath; //real file path
    private byte[] bytes;

    //setters & getters

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String file_path) {
        this.filePath = file_path;
    }
}