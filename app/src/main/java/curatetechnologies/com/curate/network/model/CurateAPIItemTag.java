package curatetechnologies.com.curate.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CurateAPIItemTag {
    @SerializedName("TagType_ID")
    @Expose
    private Integer tagTypeID;

    @SerializedName("TagType_Type")
    @Expose
    private String tagTypeType;

    public Integer getTagTypeID() {
        return tagTypeID;
    }

    public void setTagTypeID(Integer tagTypeID) {
        this.tagTypeID = tagTypeID;
    }

    public String getTagTypeType() {
        return tagTypeType;
    }

    public void setTagTypeType(String tagTypeType) {
        this.tagTypeType = tagTypeType;
    }

}