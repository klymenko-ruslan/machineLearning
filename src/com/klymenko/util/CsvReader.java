package com.klymenko.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.klymenko.machinelearning.model.DataSet;
import com.klymenko.machinelearning.model.Row;
import jdk.nashorn.api.scripting.URLReader;

/**
 * Created by klymenko.ruslan on 19.06.2017.
 */
public class CsvReader {

    private static final String SEPARATOR = ",";

    public static List<String> readHeader(Reader source) {
        try (BufferedReader reader = new BufferedReader(source)) {
            return reader.lines()
                         .findFirst()
                         .map(line -> Arrays.asList(line.split(SEPARATOR)))
                         .get();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static List<List<String>> readRecords(Reader source, boolean withHeaders) {
        try (BufferedReader reader = new BufferedReader(source)) {
            return reader.lines()
                         .skip(withHeaders ? 1 : 0)
                         .map(line -> Arrays.asList(line.split(SEPARATOR)))
                         .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static List<List<String>> readRecords(String source, boolean withHeaders) throws MalformedURLException {
        return readRecords(new URLReader(new URL(source)), withHeaders);
    }

    public static DataSet readToDataset(String source, List<String> headers) throws MalformedURLException {
        List<List<String>> records = readRecords(source, headers == null || headers.isEmpty());
        if(headers == null || headers.isEmpty()) {
            headers = records.get(0);
            records = records.subList(1, records.size());
        }
        return new DataSet(headers, records.stream()
                                           .map(CsvReader::toRow)
                                           .filter(row -> row.getAttributes().size() > 1)
                                           .collect(Collectors.toList()));
    }

    private static Row toRow(List<String> attributes) {
        return new Row(trim(attributes));
    }

    private static List<String> trim(List<String> attributes) {
        return attributes.stream()
                         .map(String::trim)
                         .collect(Collectors.toList());
    }
}
