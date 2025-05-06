package com.hfut.mihealth.service;

import com.hfut.mihealth.entity.Image;

import java.time.LocalDate;
import java.util.List;

public interface ImageService {

    Image insertImage(Image image);

    List<Image> getAllImage(int userId, LocalDate date);

    String doTranslate(String q);

    Image updateImage(int imageId, String foodName, String amount, String calories);
}
