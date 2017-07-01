package com.klymenko.machinelearning.classifier.knn.distance;

import com.klymenko.machinelearning.model.Row;

/**
 * Created by klymenko.ruslan on 28.06.2017.
 */
public abstract class DistanceAlgorithm {
    public abstract long calculateDistance(Row row, Row rowForPrediction);
    protected void validate(Row row, Row rowForPrediction) {
        if(rowForPrediction.getAttributes().size() >= row.getAttributes().size()) throw new IllegalArgumentException();
    }
}
