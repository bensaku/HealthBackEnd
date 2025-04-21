package com.hfut.mihealth.service.serviceImpl;

import com.hfut.mihealth.entity.Image;
import com.hfut.mihealth.mapper.ImageMapper;
import com.hfut.mihealth.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageMapper imageMapper;

    @Override
    public Image insertImage(Image image) {
        imageMapper.insert(image);
        return image;
    }
}
