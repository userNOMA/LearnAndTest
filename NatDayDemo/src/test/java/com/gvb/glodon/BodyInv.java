package com.gvb.glodon;

public class BodyInv {
    private String fileId;
    private String fileType;
    private String fileName;
    private String fileSize;
    private String configType;
    private String  bidSegId ;
    private String isTender;
    public BodyInv() {
    }

    public BodyInv(String fileId, String fileType, String fileName, String fileSize, String configType, String bidSegId, String isTender) {
        this.fileId = fileId;
        this.fileType = fileType;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.configType = configType;
        this.bidSegId = bidSegId;
        this.isTender = isTender;
    }

    public String getFileId() {
        return fileId;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getBidSegId() {
        return bidSegId;
    }

    public String getIsTender() {
        return isTender;
    }

    public void setFileId(String fileId) {
        if (fileId == null){
            this.fileId = "null";
        } else {
            this.fileId = fileId;
        }
    }

    public void setFileType(String fileType) {
        if (fileType == null){
            this.fileType = "null";
        } else {
            this.fileType = fileType;
        }
    }

    public void setFileName(String fileName) {
        if (fileName == null){
            this.fileName = "null";
        } else {
            this.fileName = fileName;
        }
    }

    public void setFileSize(String fileSize) {
        if (fileSize == null){
            this.fileSize = "null";
        } else {
            this.fileSize = fileSize;
        }
    }

    public void setBidSegId(String bidSegId) {
        if (bidSegId == null){
            this.bidSegId = "null";
        }else {
            this.bidSegId = bidSegId;
        }
    }

    public void setIsTender(String isTender) {
        if (isTender == null){
            this.isTender = "null";
        }else {
            this.isTender = isTender;
        }
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        if (configType == null){
            this.configType = "null";
        }else {
            this.configType = configType;
        }
    }
}
