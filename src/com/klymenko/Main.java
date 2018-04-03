package com.klymenko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.klymenko.ml.classifier.KNNContinuousClassifier;
import com.klymenko.ml.dataset.ContinuousDataSet;

enum Height {
    low, middle, high
}
enum Width {
    low, middle, high
}
enum Gender {
    man, woman
}

public class Main {

    public static void main(String[] args) {
        KNNContinuousClassifier<Gender> b =  new KNNContinuousClassifier<>(8);
        b.learn(loadDataset());

        System.out.println(b.classifyDetailed(new ArrayList<Double>() {
            {
                add(180d);
                add(75d);
            }
        }));
    }

    private static ContinuousDataSet<Gender> loadDataset() {
        ContinuousDataSet<Gender> dataSet = new ContinuousDataSet<>(new ArrayList<Class<? extends Enum>>() {
            {
                add(Height.class);
                add(Width.class);
            }
        }, new HashMap<Gender, List<List<Double>>>() {
            {
                put(Gender.man, new ArrayList<List<Double>>() {
                    {
                        add(new ArrayList<Double>() {
                            {
                                add(180d);
                                add(80d);
                            }
                        });
                        add(new ArrayList<Double>() {
                            {
                                add(175d);
                                add(85d);
                            }
                        });
                        add(new ArrayList<Double>() {
                            {
                                add(200d);
                                add(80d);
                            }
                        });
                    }
                });
                put(Gender.woman, new ArrayList<List<Double>>() {
                    {
                        add(new ArrayList<Double>() {
                            {
                                add(160d);
                                add(60d);
                            }
                        });
                        add(new ArrayList<Double>() {
                            {
                                add(155d);
                                add(55d);
                            }
                        });
                        add(new ArrayList<Double>() {
                            {
                                add(163d);
                                add(62d);
                            }
                        });
                    }
                });
            }
        });
        return dataSet;
    }
}
