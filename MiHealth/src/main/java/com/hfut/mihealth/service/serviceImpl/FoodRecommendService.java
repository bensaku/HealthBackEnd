package com.hfut.mihealth.service.serviceImpl;

import com.hfut.mihealth.entity.UserFoodRating;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FoodRecommendService {

    /**
     * 用户-食物矩阵
     * @param records
     * @return
     */
    public Map<Integer, Map<Integer, Double>> buildUserItemMatrix(List<UserFoodRating> records) {
        Map<Integer, Map<Integer, Double>> userItemMatrix = new HashMap<>();

        for (UserFoodRating record : records) {
            userItemMatrix.computeIfAbsent(record.getUserId(), k -> new HashMap<>())
                    .put(record.getFoodId(), record.getCount().doubleValue());
        }

        return userItemMatrix;
    }

    public Map<Integer, Map<Integer, Double>> calculateSimilarity(Map<Integer, Map<Integer, Double>> userItemMatrix) {
        Map<Integer, Map<Integer, Double>> similarityMatrix = new HashMap<>();
        Set<Integer> allItems = userItemMatrix.values().stream()
                .flatMap(map -> map.keySet().stream())
                .collect(Collectors.toSet());

        for (Integer item1 : allItems) {
            similarityMatrix.put(item1, new HashMap<>());
            for (Integer item2 : allItems) {
                if (item1.equals(item2)) continue;
                double similarity = cosineSimilarity(userItemMatrix, item1, item2);
                similarityMatrix.get(item1).put(item2, similarity);
            }
        }

        return similarityMatrix;
    }

    private double cosineSimilarity(Map<Integer, Map<Integer, Double>> matrix, Integer item1, Integer item2) {
        Set<Integer> commonUsers = matrix.entrySet().stream()
                .filter(entry -> entry.getValue().containsKey(item1) && entry.getValue().containsKey(item2))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        double numerator = commonUsers.stream()
                .mapToDouble(userId -> matrix.get(userId).get(item1) * matrix.get(userId).get(item2))
                .sum();

        double norm1 = Math.sqrt(commonUsers.stream()
                .mapToDouble(userId -> Math.pow(matrix.get(userId).get(item1), 2))
                .sum());

        double norm2 = Math.sqrt(commonUsers.stream()
                .mapToDouble(userId -> Math.pow(matrix.get(userId).get(item2), 2))
                .sum());

        return numerator / (norm1 * norm2);
    }

    public List<Integer> recommend(Integer userId, Map<Integer, Map<Integer, Double>> userItemMatrix,
                                   Map<Integer, Map<Integer, Double>> similarityMatrix, int topN) {
        Map<Integer, Double> recommendations = new HashMap<>();
        Map<Integer, Double> userRatings = userItemMatrix.getOrDefault(userId, new HashMap<>());

        for (Map.Entry<Integer, Double> entry : userRatings.entrySet()) {
            Integer item = entry.getKey();
            Double rating = entry.getValue();

            Map<Integer, Double> similarities = similarityMatrix.get(item);
            if (similarities == null) continue;

            for (Map.Entry<Integer, Double> simEntry : similarities.entrySet()) {
                Integer similarItem = simEntry.getKey();
                //if (userRatings.containsKey(similarItem)) continue; // 用户已选择过的食物不推荐

                recommendations.merge(similarItem, rating * simEntry.getValue(), Double::sum);
            }
        }

        return recommendations.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(topN)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
