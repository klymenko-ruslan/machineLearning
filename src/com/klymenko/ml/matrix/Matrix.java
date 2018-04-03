package com.klymenko.ml.matrix;

import java.util.Arrays;
import java.util.function.BiFunction;

public class Matrix {

    private double [][] data;

    public Matrix(double [][] data) {
        if(data == null || data.length == 0 || data[0].length == 0) throw new IllegalArgumentException();
        this.data = data;
    }

    public Matrix(int height, int width) {
        if(height < 1 || width < 1) throw new IllegalArgumentException();
        this.data = new double[height][width];
    }

    public int getRowsNum() {
        return data.length;
    }

    public int getColumnsNum() {
        return data[0].length;
    }

    public double getCell(int rowNum, int columnNum) {
        return data[rowNum][columnNum];
    }
    public void setCell(int rowNum, int columnNum, double value) {
        data[rowNum][columnNum] = value;
    }

    private Matrix apply(Matrix matrix, BiFunction<Double, Double, Double> function) {
        if(!sameSize(matrix)) throw new IllegalArgumentException();
        Matrix resultMatrix = new Matrix(data.length, data[0].length);
        for(int i = 0; i < data.length; i++) {
            for(int j = 0; j < data[0].length; j++) {
                resultMatrix.data[i][j] = function.apply(data[i][j], matrix.data[i][j]);
            }
        }
        return resultMatrix;
    }
    private Matrix apply(double value, BiFunction<Double, Double, Double> function) {
        Matrix resultMatrix = new Matrix(data.length, data[0].length);
        for(int i = 0; i < data.length; i++) {
            for(int j = 0; j < data[0].length; j++) {
                resultMatrix.data[i][j] = function.apply(data[i][j], value);
            }
        }
        return resultMatrix;
    }

    public Matrix add(Matrix matrix) {
        return apply(matrix, (a,b) -> a + b);
    }

    public Matrix substract(Matrix matrix) {
        return apply(matrix, (a,b) -> a - b);
    }

    public Matrix multiplyHadamard(Matrix matrix) {
        return apply(matrix, (a,b) -> a * b);
    }

    public Matrix multiplyScalar(double value) {
        return apply(value, (a,b) -> a * b);
    }

    public Matrix divideHadamard(Matrix matrix) {
        return apply(matrix, (a,b) -> a / b);
    }

    public static Matrix transpose(Matrix matrix) {
        Matrix resultMatrix = new Matrix(matrix.data[0].length, matrix.data.length);
        for(int i = 0; i < matrix.data.length; i++) {
            for(int j = 0; j < matrix.data[0].length; j++) {
                resultMatrix.data[j][i] = matrix.data[i][j];
            }
        }
        return resultMatrix;
    }

    public Matrix multiply(Matrix matrix) {
        if(!validMultiplicationSize(matrix)) throw new IllegalArgumentException();
        Matrix resultMatrix = new Matrix(data.length, matrix.data[0].length);
        for(int i = 0; i < data.length; i++) {
            for(int j = 0; j < matrix.data[0].length; j++) {
                for(int k = 0; k < data[0].length; k++) {
                    resultMatrix.data[i][j] += data[i][k] * matrix.data[k][j];
                }
            }
        }
        return resultMatrix;
    }

    public static Matrix multiply(Matrix matrix, Matrix matrix2) {
        if(!validMultiplicationSize(matrix, matrix2)) throw new IllegalArgumentException();
        Matrix resultMatrix = new Matrix(matrix2.data.length, matrix.data[0].length);
        for(int i = 0; i < matrix2.data.length; i++) {
            for(int j = 0; j < matrix.data[0].length; j++) {
                for(int k = 0; k < matrix2.data[0].length; k++) {
                    resultMatrix.data[i][j] += matrix2.data[i][k] * matrix.data[k][j];
                }
            }
        }
        return resultMatrix;
    }

    private boolean validMultiplicationSize(Matrix matrix) {
        return validMultiplicationSize(matrix, this);
    }

    private static boolean validMultiplicationSize(Matrix matrix, Matrix matrix2) {
        return matrix != null && matrix2.data.length == matrix.data[0].length && matrix2.data[0].length == matrix.data.length;
    }

    public boolean sameSize(Matrix matrix) {
        return matrix != null && matrix.data.length != 0 && data.length == matrix.data.length && data[0].length == matrix.data[0].length;
    }

    public boolean isSquare() {
        return data.length == data[0].length;
    }

    public double determinant() {
        return determinant(this);
    }
    private double determinant(Matrix matrix) {
        if(!matrix.isSquare()) throw new IllegalArgumentException();
        if(matrix.data.length == 1) return matrix.data[0][0];
        if(matrix.data.length == 2) return matrix.data[0][0] * matrix.data[1][1] - matrix.data[0][1] * matrix.data[1][0];
        double result = 0;
        for(int i = 0; i < matrix.data.length; i++) {
            result += changeSign(i) * matrix.data[0][i] * determinant(createSubMatrix(matrix, 0, i));
        }
        return result;
    }
    private int changeSign(int value) {
        return value % 2 == 0 ? 1 : -1;
    }
    private Matrix createSubMatrix(Matrix matrix, int excludingRow, int excludingCol) {
        Matrix mat = new Matrix(matrix.data.length - 1, matrix.data[0].length - 1);
        int r = 0;
        for (int i=0;i<matrix.data.length;i++) {
            if (i==excludingRow) continue;
            int c = 0;
            for (int j=0;j<matrix.data[0].length;j++) {
                if (j==excludingCol) continue;
                mat.data[r][c++] = data[i][j];
            }
            r++;
        }
        return mat;
    }

    public Matrix cofactor(Matrix matrix) {
        Matrix mat = new Matrix(matrix.data.length, matrix.data[0].length);
        for (int i = 0;i < matrix.data.length; i++) {
            for (int j = 0; j < matrix.data[0].length; j++) {
                mat.data[i][j] = changeSign(i) * changeSign(j) *
                        determinant(createSubMatrix(matrix, i, j));
            }
        }

        return mat;
    }

    public Matrix inverse(Matrix matrix) {
        return transpose(cofactor(matrix)).multiplyScalar(1.0/determinant(matrix));
    }

    public Matrix calculate(Matrix Y) {
        Matrix Xtr = transpose(this);
        Matrix XXtr = multiply(Xtr,this);
        Matrix inverse_of_XXtr = inverse(XXtr);
        if (inverse_of_XXtr == null) {
            System.out.println("Matrix X'X does not have any inverse. So MLR failed to create the model for these data.");
            return null;
        }
        Matrix XtrY = multiply(Xtr,Y);
        return multiply(inverse_of_XXtr,XtrY);
    }
}
