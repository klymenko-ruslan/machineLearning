package com.klymenko.machinelearning.model;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by klymenko.ruslan on 19.06.2017.
 */
public class DataSet {
    private List<String> headers;
    private List<Row> rows;
    private List<String> categories;

    public DataSet(List<String> headers, List<Row> rows) {
        this.headers = headers;
        this.rows = rows;
        this.categories = rows.stream().map(Row::getCategory).distinct().collect(Collectors.toList());
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(final List<String> headers) {
        this.headers = headers;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(final List<Row> rows) {
        this.rows = rows;
    }

    public List<String> getCategories() {
        return categories;
    }

    public int size() {
        return rows.size();
    }
}
