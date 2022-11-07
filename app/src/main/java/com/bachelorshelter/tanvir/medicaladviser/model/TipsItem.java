package com.bachelorshelter.tanvir.medicaladviser.model;

/**
 * Created by anikc on 2017-05-20.
 */

public class TipsItem {
    private String headline;
    private String description;
    private String tipsId;

    public String getTipsId() {
        return tipsId;
    }

    public void setTipsId(String tipsId) {
        this.tipsId = tipsId;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
