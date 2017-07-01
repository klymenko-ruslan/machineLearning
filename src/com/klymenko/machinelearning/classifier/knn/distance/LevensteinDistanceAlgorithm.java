package com.klymenko.machinelearning.classifier.knn.distance;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import com.klymenko.machinelearning.model.Row;

/**
 * Created by klymenko.ruslan on 28.06.2017.
 */
public class LevensteinDistanceAlgorithm extends DistanceAlgorithm {
    @Override
    public long calculateDistance(final Row row, final Row rowForPrediction) {
        validate(row, rowForPrediction);
        return LongStream.range(0, rowForPrediction.getAttributes().size())
                .map(val -> calculateLevensteinDistance(row.getAttribute((int)val), rowForPrediction.getAttribute((int)val)))
                .sum();
    }
    private long calculateLevensteinDistance(String str, String str2) {
        int str2Length = str2.length();
        int[] tmp2 = IntStream.rangeClosed(0, str2Length).toArray();
        int[] tmp;
        for(int i = 1; i <= str.length(); i ++) {
            tmp = tmp2;
            tmp2 = new int[str2Length + 1];
            for(int j = 0; j <= str2Length; j ++) {
                if(j == 0) tmp2[0] = i;
                else {
                    int cost = str.charAt(i - 1) != str2.charAt(j - 1) ? 1 : 0;
                    tmp2[j] = getDist(tmp, tmp2, j, cost);
                }
            }
        }
        return tmp2[str2Length];
    }
    private int getDist(int []tmp, int []tmp2, int j, int cost) {
        if(tmp2[j - 1] < tmp[j] && tmp2[j - 1] < tmp[j - 1] + cost) return tmp2[j - 1] + 1;
        else if(tmp[j] < tmp[j - 1] + cost) return tmp[j] + 1;
        else return tmp[j - 1] + cost;
    }
}
