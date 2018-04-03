package com.klymenko.ml.classifier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.klymenko.ml.dataset.exception.WrongVectorException;
public class KNNContinuousClassifier<C extends Enum<C>> extends Classifier<C, Double> {

    private final int K;

    public KNNContinuousClassifier() {
        this.K = 3;
    }

    public KNNContinuousClassifier(int K) {
        if(K < 1) throw new RuntimeException();
        this.K = K;
    }

    @Override
    public Map<C, Double> classifyDetailed(final List<Double> vector) {
        TreeMap<Double, List<C>> resultingMap = new TreeMap<>();
        for(Map.Entry<C, List<List<Double>>> category : dataSet.getCategoryMap().entrySet()) {
            for(List<Double> categoryVector: category.getValue()) {
                double distance = 0;
                for(int i = 0; i < vector.size(); i++) {
                    distance += Math.pow(vector.get(i) - categoryVector.get(i), 2);
                }
                distance = Math.sqrt(distance);
                if(!resultingMap.containsKey(distance)) resultingMap.put(distance, new LinkedList<>());
                resultingMap.get(distance).add(category.getKey());
            }
        }
        Iterator<Map.Entry<Double, List<C>>> iterator = resultingMap.entrySet().iterator();
        List<C> resultingList = new LinkedList<>();
        while(resultingList.size() < K && iterator.hasNext()) {
            resultingList.addAll(iterator.next().getValue());
        }
        if(K < resultingList.size())
            resultingList = resultingList.subList(0, K);
        Map<C, Double> countingMap = new HashMap<>();
        for(C value: resultingList) {
            if(!countingMap.containsKey(value)) countingMap.put(value, 1d);
            else countingMap.put(value, countingMap.get(value) + 1);
        }
        return countingMap;
    }

    @Override
    protected void validate(final List<Double> vector) {
        for(Double value: vector) if(value <= 0) throw new WrongVectorException();
    }
}
