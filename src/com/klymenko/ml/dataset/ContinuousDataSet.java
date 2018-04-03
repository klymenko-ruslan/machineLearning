package com.klymenko.ml.dataset;

import java.util.List;
import java.util.Map;

import com.klymenko.ml.dataset.exception.WrongVectorException;

public class ContinuousDataSet<C extends Enum<C>> extends DataSet<C, Double> {
    public ContinuousDataSet(List<Class<? extends Enum>> featureNames, Map<C, List<List<Double>>> categoryMap) {
        this.featureNames = featureNames;
        for(Map.Entry<C,List<List<Double>>> categoryEntries: categoryMap.entrySet()) {
            for(List<Double> itemValues: categoryEntries.getValue()) {
                if(itemValues.size() != featureNames.size()) throw new WrongVectorException();
            }
            amountOfVectors += categoryEntries.getValue().size();
            this.categoryMap.put(categoryEntries.getKey(), categoryEntries.getValue());
        }
    }
}
