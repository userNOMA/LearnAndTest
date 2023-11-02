package com.gvb.glodon;

public class BodySubFile {
    private String fileId;
    private String fileName;
    private String fileSize;
    private String filePath;

    public BodySubFile() {
    }

    public BodySubFile(String fileId, String fileName, String fileSize, String filePath) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.filePath = filePath;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        if (fileId == null){
            this.fileId = "null";
        }else {
            this.fileId = fileId;
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        if (fileName == null){
            this.fileName = "null";
        }else {
            this.fileName = fileName;
        }
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        if (fileSize == null){
            this.fileSize = "null";
        }else {
            this.fileSize = fileSize;
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        if (filePath == null){
            this.filePath = "null";
        }else {
            this.filePath = filePath;
        }
    }
}
