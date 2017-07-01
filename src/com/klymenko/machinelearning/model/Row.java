package com.klymenko.machinelearning.model;

import java.util.List;

/**
 * Created by klymenko.ruslan on 19.06.2017.
 */
public class Row {
    private List<String> attributes;

    public Row(List<String> attributes) {
        this.attributes = attributes;
    }

    public String getAttribute(int index) {
        return attributes.get(index);
    }
    public List<String> getAttributes() {
        return attributes;
    }

    public String getCategory() {
        return attributes.get(attributes.size() - 1);
    }
}
