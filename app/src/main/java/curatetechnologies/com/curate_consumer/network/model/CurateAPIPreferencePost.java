package curatetechnologies.com.curate_consumer.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mremondi on 2/19/18.
 */

public class CurateAPIPreferencePost {
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("tagIds")
    @Expose
    private ArrayList<Integer> tagIds;

    public CurateAPIPreferencePost(Integer userId, ArrayList<Integer> tagIds) {
        this.userId = userId;
        this.tagIds = tagIds;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public ArrayList<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(ArrayList<Integer> tagIds) {
        this.tagIds = tagIds;
    }
}
