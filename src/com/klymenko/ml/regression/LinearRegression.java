package com.klymenko.ml.regression;

public class LinearRegression {

    public double predict(double [][] matrix, double values[]) {
        if(matrix.length - 1 != values.length) throw new IllegalArgumentException();
        double yMean = 0;
        for(int i = 0; i < matrix[0].length; i++) {
            yMean += matrix[0][i];
        }
        yMean /= matrix[0].length;
        double yMeanDifferences[] = new double[matrix[0].length];
        for(int i = 0; i < matrix[0].length; i++) {
            yMeanDifferences[i] = matrix[0][i] - yMean;
        }

        double means[] = new double[matrix.length - 1];
        for(int i = 0; i < matrix.length - 1; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                means[i] += matrix[i + 1][j];
            }
            means[i] /= matrix[0].length;
        }
        double [][] meanDifferences = new double[matrix.length - 1][matrix[0].length];
        double [] squares = new double[matrix.length - 1];
        for(int i = 0; i < matrix.length - 1; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                meanDifferences[i][j] = matrix[i + 1][j] - means[i];
                squares[i] += meanDifferences[i][j] * meanDifferences[i][j];
            }
        }
        double [] dSums = new double[matrix.length - 1];
        for(int i = 0; i < matrix.length - 1; i++) {
            for(int j = 0; j < yMeanDifferences.length; j++) {
                dSums[i] += meanDifferences[i][j] * yMeanDifferences[j];
            }
        }
        double [] coefficients = new double[matrix.length - 1];
        double slope = 0;
        for(int i = 0; i < dSums.length; i++) {
            coefficients[i] = dSums[i] / squares[i];
            slope += yMean - coefficients[i] * means[i];
        }
        double result = slope;
        for(int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * values[i];
        }
        return result / coefficients.length;
    }
}
