package com.gvb.glodon;

public class BodySubDel {
    private String id;
    private String collectionName;

    public BodySubDel() {
    }

    public BodySubDel(String id, String collectionName) {
        this.id = id;
        this.collectionName = collectionName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null){
            this.id = "null";
        }else {
            this.id = id;
        }
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        if (collectionName == null){
            this.collectionName = "null";
        }else {
            this.collectionName = collectionName;
        }
    }
}
