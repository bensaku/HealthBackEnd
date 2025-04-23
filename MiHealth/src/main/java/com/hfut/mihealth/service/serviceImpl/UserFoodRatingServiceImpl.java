package com.hfut.mihealth.service.serviceImpl;

import com.hfut.mihealth.entity.UserFoodRating;
import com.hfut.mihealth.mapper.UserFoodRatingMapper;
import com.hfut.mihealth.service.UserFoodRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class UserFoodRatingServiceImpl implements UserFoodRatingService {

    @Autowired
    private UserFoodRatingMapper userFoodRatingMapper;

    /**
     * 增加用户对食物的选择次数
     */
    public void incrementCount(Integer userId, Integer foodId) {
        userFoodRatingMapper.insertOrUpdate(userId, foodId);
    }

    /**
     * 获取所有的数据
     *
     * @return
     */
    @Override
    public List<UserFoodRating> getAllRecords() {
        return userFoodRatingMapper.selectList(null);
    }

}
