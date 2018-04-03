package com.klymenko.ml.dataset;

import java.util.List;
import java.util.Map;
import com.klymenko.ml.dataset.exception.WrongVectorException;

public class DiscreteDataSet<C extends Enum<C>> extends DataSet<C, Enum> {

    public DiscreteDataSet(List<Class<? extends Enum>> featureNames, Map<C, List<List<Enum>>> categoryMap) {
        this.featureNames = featureNames;
        for(Map.Entry<C,List<List<Enum>>> categoryEntries: categoryMap.entrySet()) {
            for(List<Enum> itemValues: categoryEntries.getValue()) {
                if(itemValues.size() != featureNames.size()) throw new WrongVectorException();
                for(int i = 0; i < itemValues.size(); i++) {
                    if(itemValues.get(i).getDeclaringClass() != featureNames.get(i)) throw new WrongVectorException();
                }
            }
            amountOfVectors += categoryEntries.getValue().size();
            this.categoryMap.put(categoryEntries.getKey(), categoryEntries.getValue());
        }
    }
}
