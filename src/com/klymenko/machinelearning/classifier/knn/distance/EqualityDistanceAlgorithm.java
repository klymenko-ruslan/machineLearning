package com.klymenko.machinelearning.classifier.knn.distance;

import java.util.stream.IntStream;

import com.klymenko.machinelearning.model.Row;

/**
 * Created by klymenko.ruslan on 28.06.2017.
 */
public class EqualityDistanceAlgorithm extends DistanceAlgorithm {
    @Override
    public long calculateDistance(final Row row, final Row rowForPrediction) {
        validate(row, rowForPrediction);
        return IntStream.range(0, rowForPrediction.getAttributes().size())
                        .filter(val -> !row.getAttribute(val).equals(rowForPrediction.getAttribute(val)))
                        .count();
    }
}
