package com.dimpon.happycube.utils;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * This util class contains basic methods for matrix NxN size manipulating (in our case N=5)
 * The methods must have good performance. I was going to use boolean[][] but at the end decide to use int[][] for better visibility.
 * <p>
 * todo: now the most methods rotate all matrix, but central area 3x3 can be intact.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MatrixUtils {


    public static final int MATRIX_SIZE = 5;

    /**
     * The method rotates one piece of puzzle to the right on 90 grad.
     *
     * @param in input matrix int[5][5]
     * @return result matrix the same size with moved elements
     */
    public static int[][] rotateToRight(int[][] in) {

        @SuppressWarnings("unchecked")
        int[][] out = (int[][]) Array.newInstance(Integer.TYPE, MATRIX_SIZE, MATRIX_SIZE);

        for (int x = 0, i = MATRIX_SIZE - 1; x < MATRIX_SIZE; x++, i--) {
            for (int y = 0; y < MATRIX_SIZE; y++) {
                out[y][i] = in[x][y];
            }
        }
        return out;
    }

    /**
     * The method rotates one piece of puzzle to the left on 90 grad.
     *
     * @param in input matrix int[5][5]
     * @return result matrix the same size with moved elements
     */
    public static int[][] rotateToLeft(int[][] in) {
        @SuppressWarnings("unchecked")
        int[][] out = (int[][]) Array.newInstance(Integer.TYPE, MATRIX_SIZE, MATRIX_SIZE);

        for (int x = 0, i = MATRIX_SIZE - 1; x < MATRIX_SIZE; x++, i--) {
            for (int y = 0; y < MATRIX_SIZE; y++) {
                out[x][y] = in[y][i];
            }
        }
        return out;
    }

    /**
     * The method rotates one piece of puzzle on 180 grad.
     * The {@link MatrixUtils#rotateToRight(int[][])} could be called twice but it saves few milliseconds.
     *
     * @param in input matrix int[5][5]
     * @return result matrix the same size with moved elements
     */
    public static int[][] rotate2times(int[][] in) {
        @SuppressWarnings("unchecked")
        int[][] out = (int[][]) Array.newInstance(Integer.TYPE, MATRIX_SIZE, MATRIX_SIZE);

        for (int x = 0, a = MATRIX_SIZE - 1; x < MATRIX_SIZE; x++, a--) {
            for (int i = 0, y = MATRIX_SIZE - 1; i < MATRIX_SIZE; i++, y--) {
                out[x][i] = in[a][y];
            }
        }
        return out;
    }

    /**
     * The method flip one piece of puzzle.
     * todo: now the method rotates all matrix, but central area 3x3 can be intact.
     *
     * @param in input matrix int[5][5]
     * @return result matrix the same size with moved elements
     */
    public static int[][] mirror(int[][] in) {
        @SuppressWarnings("unchecked")
        int[][] out = (int[][]) Array.newInstance(Integer.TYPE, MATRIX_SIZE, MATRIX_SIZE);

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int x = 0, a = MATRIX_SIZE - 1; x < MATRIX_SIZE; x++, a--) {
                out[i][a] = in[i][x];
            }
        }
        return out;
    }

    /**
     * @param one int[5][5] array
     * @param two int[5][5] array
     * @return true or false
     */
    public static boolean isTwoArraysEqualUsingEnumeration(int[][] one, int[][] two) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (two[i][j] != one[i][j])
                    return false;
            }
        }
        return true;
    }

    /**
     * @param one int[5][5] array
     * @param two int[5][5] array
     * @return true or false
     */
    public static boolean isTwoArraysEqualUsingDeepEquals(int[][] one, int[][] two) {
        return Arrays.deepEquals(one, two);
    }


    private static final int[][] pattern = new int[][]{
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 1, 1, 1, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0}
    };


    /**
     * Method checks whether central part 3x3 is filled
     *
     * @return true or false
     */
    public static boolean isCentralPartOk(int[][] in) {
        @SuppressWarnings("unchecked")
        int[][] out = (int[][]) Array.newInstance(Integer.TYPE, MATRIX_SIZE, MATRIX_SIZE);

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                out[i][j] = in[i][j] & pattern[i][j];
            }
        }
        return isTwoArraysEqualUsingDeepEquals(out, pattern);
    }

    /**
     * Is generated piece meets basic requirements
     *
     * @return true or false
     */
    public static boolean isGeneratedPieceOk(int[][] in) {

        if (!isCornersOk(in)) {
            return false;
        }
        if (!isCentralPartOk(in)) {
            return false;
        }
        if (hasFlatSide(in)) {
            return false;
        }
        if(hasEmptySide(in)){
            return false;
        }

        return true;
    }

    /**
     * Method checks whether corners are not hanging in air.
     * Might be used in Challenge #2
     *
     * @return true or false
     */
    public static boolean isCornersOk(int[][] in) {

        if (in[0][0] == 1 && (in[0][1] == 0 && in[1][0] == 0)) return false;

        if (in[0][MATRIX_SIZE - 1] == 1 && (in[0][MATRIX_SIZE - 2] == 0 && in[1][MATRIX_SIZE - 1] == 0)) return false;

        if (in[MATRIX_SIZE - 1][0] == 1 && (in[MATRIX_SIZE - 2][0] == 0 && in[MATRIX_SIZE - 1][1] == 0)) return false;

        if (in[MATRIX_SIZE - 1][MATRIX_SIZE - 1] == 1 && (in[MATRIX_SIZE - 1][MATRIX_SIZE - 2] == 0 && in[MATRIX_SIZE - 2][MATRIX_SIZE - 1] == 0))
            return false;

        return true;

    }

    @FunctionalInterface
    interface FindEdgePixel {
        int find(int index);
    }

    /**
     * Method checks whether piece has a flat side. Flat side is bad.
     * Might be used in Challenge #2
     *
     * @return true or false
     */
    public static boolean hasFlatSide(int[][] in) {


        if (isOneEdgeHasFlatSide(i -> in[0][i])) {
            return true;
        }

        if (isOneEdgeHasFlatSide(i -> in[i][0])) {
            return true;
        }

        if (isOneEdgeHasFlatSide(i -> in[MATRIX_SIZE - 1][i])) {
            return true;
        }

        if (isOneEdgeHasFlatSide(i -> in[i][MATRIX_SIZE - 1])) {
            return true;
        }

        return false;

    }

    /**
     * Method checks whether piece has a empty side (00000). Empty side is bad.
     * Might be used in Challenge #2
     *
     * @return true or false
     */
    public static boolean hasEmptySide(int[][] in) {


        if (isOneEdgeHasEmptySide(i -> in[0][i])) {
            return true;
        }

        if (isOneEdgeHasEmptySide(i -> in[i][0])) {
            return true;
        }

        if (isOneEdgeHasEmptySide(i -> in[MATRIX_SIZE - 1][i])) {
            return true;
        }

        if (isOneEdgeHasEmptySide(i -> in[i][MATRIX_SIZE - 1])) {
            return true;
        }

        return false;

    }

    private static boolean isOneEdgeHasFlatSide(FindEdgePixel func) {
        int sum = 0;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            sum = sum + func.find(i);
        }
        return (sum == MATRIX_SIZE);
    }

    private static boolean isOneEdgeHasEmptySide(FindEdgePixel func) {
        int sum = 0;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            sum = sum + func.find(i);
        }
        return (sum == 0);
    }


}
