package com.klymenko.ml.classifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BayesDiscreteClassifier<C extends Enum<C>> extends DiscreteClassifier<C> {

    @Override
    public Map<C, Double> classifyDetailed(final List<Enum> vector) {
        validate(vector);
        double vectorOccurences = 0;
        Map<C, Double> vectorInCategoryProbabilityMap = new HashMap<>();
        for(Map.Entry<C, List<List<Enum>>> categoryData : dataSet.getCategoryMap().entrySet()) {
            Long vectorInCategoryProbability = categoryData.getValue().stream().filter(it -> it.equals(vector)).count();
            vectorOccurences += vectorInCategoryProbability;
            vectorInCategoryProbabilityMap.put(categoryData.getKey(), vectorInCategoryProbability.doubleValue() / categoryData.getValue().size());
        }
        double vectorProbability = vectorOccurences / dataSet.getAmountOfVectors();

        return dataSet.getCategoryMap().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                it -> vectorInCategoryProbabilityMap.get(it.getKey()) * (new Double(it.getValue().size()) / dataSet.getAmountOfVectors()) / vectorProbability));
    }
}
