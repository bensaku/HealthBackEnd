package com.hfut.mihealth.service.serviceImpl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hfut.mihealth.entity.Image;
import com.hfut.mihealth.mapper.ImageMapper;
import com.hfut.mihealth.service.ImageService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class ImageServiceImpl implements ImageService {
    public static class BaiduContent {
        static String APPID = "20250421002339174";
        static String SECRET = "iFeTHYZe3JqjiH6tmv0G";
    }

    @Autowired
    private ImageMapper imageMapper;

    @Override
    public Image insertImage(Image image) {
        imageMapper.insert(image);
        return image;
    }

    @Override
    public Image updateImage(int imageID, String name, int amount) {
        // 从数据库中获取当前记录
        Image existingImage = imageMapper.selectById(imageID);

        if (existingImage == null) {
            throw new RuntimeException("Image not found");
        }

        // 更新需要修改的字段
        existingImage.setFoodName(name);   // 更新食品名称
        existingImage.setAmount(amount);   // 更新数量
        existingImage.setCompleted(true);  // 标记为已完成

        try {
            // 执行更新操作
            int updateResult = imageMapper.updateById(existingImage);

            if (updateResult > 0) {
                return existingImage;
            } else {
                return null;
            }
        } catch (Exception e) {
            // 处理异常
            e.printStackTrace();
            throw new RuntimeException("Failed to update image");
        }
    }

    @Override
    public List<Image> getAllImage(int userId, LocalDate date) {
        // 获取给定日期的开始时间和结束时间
        Date startOfDay = getStartOfDay(date);
        Date endOfDay = getEndOfDay(date);

        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .ge("date", startOfDay)  // 'date' 是数据库中存储日期的字段名
                .lt("date", endOfDay);
        // 执行查询并返回结果
        return imageMapper.selectList(queryWrapper);
    }

    // 辅助方法：获取给定LocalDate当天的开始时间，基于系统默认时区
    private Date getStartOfDay(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // 辅助方法：获取给定LocalDate次日的开始时间，基于系统默认时区
    private Date getEndOfDay(LocalDate localDate) {
        return Date.from(localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


    // 示例：通过ID获取Image对象的方法
    private Image getImageById(int imageId) {
        return imageMapper.selectById(imageId);
    }

    @Override
    public String doTranslate(String q) {
        //获取请求参数
        String from = "en";
        String to = "zh";
        //随机数
        Random random = new Random(10);
        String salt = Integer.toString(random.nextInt());
        //MD5加密
        //自定义的全局变量 appid和密钥
        String appid = BaiduContent.APPID + q + salt + BaiduContent.SECRET;
        String sign = SecureUtil.md5(appid);
        //封装请求参数
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("q", q);
        paramMap.add("from", from);
        paramMap.add("to", to);
        paramMap.add("appid", BaiduContent.APPID);
        paramMap.add("salt", salt);
        paramMap.add("sign", sign);
        String url = "http://api.fanyi.baidu.com/api/trans/vip/translate";
        //封装请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(paramMap, headers);
        //调用百度翻译API，发送请求，得到结果
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, httpEntity, String.class);
        return jsonToSplit(response.getBody()).get(0);
    }

    public static List<String> jsonToSplit(String transResult) {
        JSONObject jsonObject = JSONObject.fromObject(transResult);
        Object msg = jsonObject.get("trans_result");
        JSONArray array = JSONArray.fromObject(msg);
        List<String> res=new ArrayList<>();
        for (int i=0;i<array.size();i++) {
            JSONObject ob = (JSONObject)array.get(i);
            res.add(ob.getString("dst"));
        }
        return res;
    }
}
