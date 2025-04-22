package com.hfut.mihealth.DTO;

import com.hfut.mihealth.entity.Image;

import java.util.List;
import java.util.Map;

public class RecordAndImageResponse {
    Map<String, List<RecordResponse>> dietRecords;
    List<Image> imageList;

    public void setDietRecords(Map<String, List<RecordResponse>> dietRecords) {
        this.dietRecords = dietRecords;
    }
    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }
}
