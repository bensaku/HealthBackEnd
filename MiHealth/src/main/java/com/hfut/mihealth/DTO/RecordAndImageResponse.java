package com.hfut.mihealth.DTO;

import com.hfut.mihealth.entity.Image;

import java.util.List;
import java.util.Map;

public class RecordAndImageResponse {
    Map<String, List<RecordResponse>> record;
    List<Image> image;

    public void setRecord(Map<String, List<RecordResponse>> dietRecords) {
        this.record = dietRecords;
    }
    public void setImage(List<Image> imageList) {
        this.image = imageList;
    }

    public Map<String, List<RecordResponse>> getRecord() {
        return record;
    }

    public List<Image> getImage() {
        return image;
    }
}
