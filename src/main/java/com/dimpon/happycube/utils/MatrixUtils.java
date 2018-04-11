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



    /*
    The methods below are used for algorithm B, based on edges checking.
    It turned out that performance is worse.
     */


    private static int one(int a, int b, int c, int d) {
        return (a & ~b & ~c & ~d) | (~a & b & ~c & ~d) | (~a & ~b & c & ~d) | (~a & ~b & ~c & d);
    }

    public static boolean checkOneEdge(int... edges) {

        log.debug(String.format("%5s", Integer.toBinaryString(edges[0])).replace(' ', '0'));
        log.debug(String.format("%5s", Integer.toBinaryString(edges[1])).replace(' ', '0'));
        log.debug(String.format("%5s", Integer.toBinaryString(edges[2])).replace(' ', '0'));
        log.debug(String.format("%5s", Integer.toBinaryString(edges[3])).replace(' ', '0'));
        log.debug("");


        int r = one((edges[0] & 0b10000),
                edges[1], edges[2],
                (edges[3] & 0b00001));

        return 0b11111 == r;
    }

    public enum Edge {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        TOP_REVERSE,
        BOTTOM_REVERSE,
        LEFT_REVERSE,
        RIGHT_REVERSE
    }


    /**
     * Returns int representation of matrix edges
     *
     * @param matrix initial matrix
     * @return map of int
     */
    public static Map<Edge, Integer> getMagicNumbersOfOneMatrix(int[][] matrix) {
        Map<Edge, Integer> res = new EnumMap<>(Edge.class);

        for (Edge e : Edge.values()) {
            res.put(e, edge(matrix, e));
        }
        return res;
    }

    /**
     * Calculates the magic number for piece edge (basically the int representation of binary edge)
     *
     * @param matrix piece
     * @param edge   wich edge to calculate
     * @return int representation of binary edge
     */
    static int edge(int[][] matrix, Edge edge) {

        int res = 0;

        switch (edge) {
            case TOP_REVERSE:
                res = IntStream.range(1, MATRIX_SIZE + 1).map(i -> matrix[0][MATRIX_SIZE - i]).reduce((left, right) -> (left << 1) + right).orElse(0);
                break;
            case LEFT_REVERSE:
                res = IntStream.range(1, MATRIX_SIZE + 1).map(i -> matrix[MATRIX_SIZE - i][0]).reduce((left, right) -> (left << 1) + right).orElse(0);
                break;
            case RIGHT_REVERSE:
                res = IntStream.range(1, MATRIX_SIZE + 1).map(i -> matrix[MATRIX_SIZE - i][MATRIX_SIZE - 1]).reduce((left, right) -> (left << 1) + right).orElse(0);
                break;
            case BOTTOM_REVERSE:
                res = IntStream.range(1, MATRIX_SIZE + 1).map(i -> matrix[MATRIX_SIZE - 1][MATRIX_SIZE - i]).reduce((left, right) -> (left << 1) + right).orElse(0);
                break;
            case TOP:
                res = IntStream.range(0, MATRIX_SIZE).map(i -> matrix[0][i]).reduce((left, right) -> (left << 1) + right).orElse(0);
                break;
            case LEFT:
                res = IntStream.range(0, MATRIX_SIZE).map(i -> matrix[i][0]).reduce((left, right) -> (left << 1) + right).orElse(0);
                break;
            case RIGHT:
                res = IntStream.range(0, MATRIX_SIZE).map(i -> matrix[i][MATRIX_SIZE - 1]).reduce((left, right) -> (left << 1) + right).orElse(0);
                break;
            case BOTTOM:
                res = IntStream.range(0, MATRIX_SIZE).map(i -> matrix[MATRIX_SIZE - 1][i]).reduce((left, right) -> (left << 1) + right).orElse(0);
                break;
        }

        return res;
    }

}
