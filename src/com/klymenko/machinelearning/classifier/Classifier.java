package com.klymenko.machinelearning.classifier;

import com.klymenko.machinelearning.model.DataSet;
import com.klymenko.machinelearning.model.Row;

/**
 * Created by klymenko.ruslan on 28.06.2017.
 */
public abstract class Classifier {
    public abstract String classify(DataSet dataSet, Row modelForPrediction);
}
