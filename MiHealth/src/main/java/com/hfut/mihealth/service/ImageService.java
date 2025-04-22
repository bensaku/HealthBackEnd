package com.hfut.mihealth.service;

import com.hfut.mihealth.entity.Image;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ImageService {

    Image insertImage(Image image);

    Image updateImage(int imageID, String name, int amount);

    List<Image> getAllImage(int userId, LocalDate date);

    String doTranslate(String q);
}
