package com.klymenko.machinelearning.classifier.knn;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

import com.klymenko.machinelearning.classifier.knn.distance.DistanceAlgorithm;
import com.klymenko.machinelearning.model.DataSet;
import com.klymenko.machinelearning.model.Row;

/**
 * Created by klymenko.ruslan on 28.06.2017.
 */
public class KNNClassifier {
    private final int NN;
    private final static int DETAULT_NN = 3;

    private DistanceAlgorithm distanceAlgorithm;
    public KNNClassifier(DistanceAlgorithm distanceAlgorithm) {
        if(distanceAlgorithm == null) throw new IllegalArgumentException();
        this.distanceAlgorithm = distanceAlgorithm;
        this.NN = DETAULT_NN;
    }
    public KNNClassifier(DistanceAlgorithm distanceAlgorithm, int nearestNeighboursAmount) {
        if(nearestNeighboursAmount <= 0 || distanceAlgorithm == null) throw new IllegalArgumentException();
        this.distanceAlgorithm = distanceAlgorithm;
        this.NN = nearestNeighboursAmount;
    }

    public String classify(DataSet dataSet, Row modelForPrediction) {
        return dataSet.getRows()
                      .stream()
                      .map(row -> new RowDistancePair(distanceAlgorithm.calculateDistance(row, modelForPrediction), row))
                      .sorted()
                      .limit(NN)
                      .collect(Collectors.groupingBy(pair -> pair.getRow().getCategory(),
                               Collectors.counting()))
                      .entrySet()
                      .stream()
                      .max(Comparator.comparing(Map.Entry::getValue))
                      .map(pair -> pair.getKey())
                      .get();
    }
    class RowDistancePair implements Comparable<RowDistancePair>{
        private long distance;
        private Row row;

        public RowDistancePair(long distance, Row row) {
            this.distance = distance;
            this.row = row;
        }
        public Row getRow() {
            return row;
        }

        @Override
        public int compareTo(final RowDistancePair o) {
            return this.distance < o.distance ? -1 : this.distance == o.distance ? 0 : 1;
        }
    }
}
