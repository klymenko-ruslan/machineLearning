package com.klymenko.ml.classifier;

import java.util.List;
import java.util.Map;

import com.klymenko.ml.dataset.DataSet;

public abstract class Classifier<C extends Enum<C>, V> {

    protected DataSet<C, V> dataSet;

    public abstract Map<C, Double> classifyDetailed(List<V> vector);

    public C classify(final List<V> vector) {
        return classifyDetailed(vector).entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }

    public void learn(DataSet<C, V> dataSet) {
        if(this.dataSet == null)
            this.dataSet = dataSet;
        else this.dataSet.merge(dataSet);
    }

    protected abstract void validate(List<V> vector);

}
