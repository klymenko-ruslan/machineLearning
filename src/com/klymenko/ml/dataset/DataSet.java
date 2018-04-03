package com.klymenko.ml.dataset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.klymenko.ml.dataset.exception.InexistingCategoryException;
import com.klymenko.ml.dataset.exception.WrongVectorException;

public abstract class DataSet<C extends Enum<C>, V> {

    protected List<Class<? extends Enum>> featureNames;
    protected int amountOfVectors;
    protected Map<C, List<List<V>>> categoryMap = new HashMap<>();

    public List<Class<? extends Enum>> getFeatureNames() {
        return featureNames;
    }

    public void setFeatureNames(final List<Class<? extends Enum>> featureNames) {
        this.featureNames = featureNames;
    }

    public int getAmountOfVectors() {
        return amountOfVectors;
    }

    public void setAmountOfVectors(final int amountOfVectors) {
        this.amountOfVectors = amountOfVectors;
    }

    public Map<C, List<List<V>>> getCategoryMap() {
        return categoryMap;
    }

    public void setCategoryMap(final Map<C, List<List<V>>> categoryMap) {
        this.categoryMap = categoryMap;
    }

    public void addCategory(C categoryName, List<List<V>> vectors) {
        categoryMap.put(categoryName, vectors);
    }

    public void addToCategory(C categoryName, List<V> vector) {
        if(!categoryMap.containsKey(categoryName)) throw new InexistingCategoryException();
        categoryMap.get(categoryName).add(vector);
    }

    public void merge(DataSet<C,V> discreteDataSet) {
        if(featureNames.size() != discreteDataSet.featureNames.size()) throw new WrongVectorException();
        for(int i = 0; i < featureNames.size(); i++) {
            if(!featureNames.get(i).equals(discreteDataSet.featureNames.get(i))) throw new WrongVectorException();
        }
        for(C categoryName: discreteDataSet.categoryMap.keySet()) {
            if(!categoryMap.containsKey(categoryName)) categoryMap.put(categoryName, discreteDataSet.categoryMap.get(categoryName));
            else categoryMap.get(categoryName).addAll(discreteDataSet.categoryMap.get(categoryName));
            amountOfVectors += discreteDataSet.categoryMap.get(categoryName).size();
        }

    }

    public void print() {
        System.out.println(featureNames.stream().map(it -> it.getSimpleName()).collect(Collectors.toList()));
        System.out.println(categoryMap.toString());
    }
}
