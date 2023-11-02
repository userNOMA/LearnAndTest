package com.gvb.glodon;

import java.util.List;

public class BodySub {
    private String bidSegId;
    private List<BodySubFile> fileId;
    private String fileType;
    private String StringerfaceCode;

    public BodySub() {
    }

    public BodySub(String bidSegId, List<BodySubFile> fileId, String fileType, String StringerfaceCode) {
        this.bidSegId = bidSegId;
        this.fileId = fileId;
        this.fileType = fileType;
        this.StringerfaceCode = StringerfaceCode;
    }

    public String getBidSegId() {
        return bidSegId;
    }

    public void setBidSegId(String bidSegId) {
        if (bidSegId == null){
            this.bidSegId = "null";
        }else {
            this.bidSegId = bidSegId;
        }
    }

    public List<BodySubFile> getFileId() {
        return fileId;
    }

    public void setFileId(List<BodySubFile> fileId) {
        this.fileId = fileId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        if (fileType == null){
            this.fileType = "null";
        }else {
            this.fileType = fileType;
        }
    }

    public String getStringerfaceCode() {
        return StringerfaceCode;
    }

    public void setInterfaceCode(String StringerfaceCode) {
        if (StringerfaceCode == null){
            this.StringerfaceCode = "null";
        }else {
            this.StringerfaceCode = StringerfaceCode;
        }
    }
}
