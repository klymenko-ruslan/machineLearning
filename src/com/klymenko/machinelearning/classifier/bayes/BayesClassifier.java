package com.klymenko.machinelearning.classifier.bayes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import com.klymenko.machinelearning.classifier.Classifier;
import com.klymenko.machinelearning.model.DataSet;
import com.klymenko.machinelearning.model.Row;

/**
 * Created by Unicorn on 19.06.2017.
 */
public class BayesClassifier extends Classifier {
  class Pair {
        public Pair(String category, double probability) {
            this.category = category;
            this.probability = probability;
        }
        private double probability;
        private String category;
    }

    public String classify(DataSet dataSet, Row modelForPrediction) {
        double matchedInDataset = matchingProbability(dataSet.getRows(), modelForPrediction);
        return dataSet.getCategories()
                      .stream()
                      .map(category -> toCategoryPair(dataSet, modelForPrediction, category, matchedInDataset))
                      .max((category, category2) -> Double.valueOf(category.probability).compareTo(category2.probability))
                      .get()
                      .category;
    }
    private Pair toCategoryPair(DataSet dataSet, Row modelForPrediction, String category, double matchedInDataset) {
        List<Row> rowsInCategory = filterByCategory(dataSet, category);
        double givenProbabilityOfCategory = rowsInCategory.size() / Double.valueOf(dataSet.size());
        double matchedInCategory = matchingProbability(rowsInCategory, modelForPrediction);
        double probabilityThatThisCategory = givenProbabilityOfCategory * matchedInCategory / matchedInDataset;
        category = Double.isNaN(probabilityThatThisCategory) ? "UNKNOWN" : category;
        System.out.println(category + " and " + givenProbabilityOfCategory + " and " + probabilityThatThisCategory + " and" + matchedInCategory + " abd " + matchedInDataset);
        return new Pair(category, probabilityThatThisCategory);
    }
    private List<Row> filterByCategory(DataSet dataSet, String category) {
        return dataSet.getRows()
                      .stream()
                      .filter(row -> row.getCategory().equals(category))
                      .collect(Collectors.toList());
    }
    private double matchingProbability(List<Row> rows, Row modelForPrediction) {
        return rows.stream()
                   .mapToDouble(row -> weightOfRowMatch(row, modelForPrediction))
                   .sum() / rows.size();
    }
    private double weightOfRowMatch(Row row, Row modelForPrediction) {
      return IntStream.range(0, modelForPrediction.getAttributes().size())
                .filter(indx -> modelForPrediction.getAttributes().get(indx).equals(row.getAttribute(indx)))
                .count()  / Double.valueOf(modelForPrediction.getAttributes().size());
    }
}
