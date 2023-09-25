package com.example.project_main;

import android.graphics.Bitmap;

public class Alert_Data {

    private String timeline_text;
    private Bitmap timeline_resId;
    private String timeline_cardview;

    public String getTimeline_text() {
        return timeline_text;
    }

    public void setTimeline_text(String timeline_text) {
        this.timeline_text = timeline_text;
    }

    public Bitmap getTimeline_resId() {
        return timeline_resId;
    }

    public void setTimeline_resId(Bitmap timeline_resId) {
        this.timeline_resId = timeline_resId;
    }

    public String getTimeline_cardview() {
        return timeline_cardview;
    }

    public void setTimeline_cardview(String timeline_cardview) { this.timeline_cardview = timeline_cardview; }

}
