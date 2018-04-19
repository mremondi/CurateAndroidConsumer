package curatetechnologies.com.curate_consumer.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mremondi on 4/17/18.
 */

public class CurateAPISimpleWrapper {

    @SerializedName("ID")
    @Expose
    private Integer iD;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

}
