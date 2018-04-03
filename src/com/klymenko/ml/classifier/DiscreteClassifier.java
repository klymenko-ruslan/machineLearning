package com.klymenko.ml.classifier;

import java.util.List;
import com.klymenko.ml.dataset.exception.WrongVectorException;

public abstract class DiscreteClassifier<C extends Enum<C>> extends Classifier<C, Enum> {

    @Override
    protected void validate(List<Enum> vector) {
        for(int i = 0; i < vector.size(); i++) {
            if(vector.get(i).getDeclaringClass() != dataSet.getFeatureNames().get(i)) throw new WrongVectorException();
        }
    }
}
