package com.dimpon.happycube.utils;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * This util class contains basic methods for matrix NxN size manipulating (in our case N=5)
 * The methods must have good performance. I was going to use boolean[][] but at the end decide to use int[][] for better visibility.
 * <p>
 * todo: now the most methods rotate all matrix, but central area 3x3 can be intact.
 */
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
        return  Arrays.deepEquals(one,two);
    }


   /* private static final int[][] patternToCheck = new int[][]{
            {1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1},
            {1, 0, 0, 0, 1},
            {1, 0, 0, 0, 1},
            {1, 1, 1, 1, 1}
    };*/

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
     * Method checks whether corners are not hanging in air.
     * Might be used in Challenge #2
     *
     * @return true or false
     */
    public static boolean isCornersOk(int[][] in) {

        if (in[0][0] == 1 && (in[0][1] == 0 && in[1][0] == 0)) return false;

        if (in[0][MATRIX_SIZE-1] == 1 && (in[0][MATRIX_SIZE-2] == 0 && in[1][MATRIX_SIZE-1] == 0)) return false;

        if (in[MATRIX_SIZE-1][0] == 1 && (in[MATRIX_SIZE-2][0] == 0 && in[MATRIX_SIZE-1][1] == 0)) return false;

        if (in[MATRIX_SIZE-1][MATRIX_SIZE-1] == 1 && (in[MATRIX_SIZE-1][MATRIX_SIZE-2] == 0 && in[MATRIX_SIZE-2][MATRIX_SIZE-1] == 0)) return false;

        return true;


    }


}
