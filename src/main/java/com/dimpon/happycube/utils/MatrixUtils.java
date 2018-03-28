package com.dimpon.happycube.utils;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
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

        if (in[0][MATRIX_SIZE - 1] == 1 && (in[0][MATRIX_SIZE - 2] == 0 && in[1][MATRIX_SIZE - 1] == 0)) return false;

        if (in[MATRIX_SIZE - 1][0] == 1 && (in[MATRIX_SIZE - 2][0] == 0 && in[MATRIX_SIZE - 1][1] == 0)) return false;

        if (in[MATRIX_SIZE - 1][MATRIX_SIZE - 1] == 1 && (in[MATRIX_SIZE - 1][MATRIX_SIZE - 2] == 0 && in[MATRIX_SIZE - 2][MATRIX_SIZE - 1] == 0))
            return false;

        return true;

    }

    /**
     * <blockquote><pre>
     * 0 1 2
     *   3
     *   4
     *   5
     * </pre></blockquote>
     *
     * @param unfolded
     * @return
     */

    public static boolean isCubePerfect(List<int[][]> unfolded) {


        //unfolded.forEach(e->log.info(Arrays.deepToString(e)));


        //1-3
        if (!checkOneEdge(

                edge(unfolded.get(0), Edge.BOTTOM, true),
                edge(unfolded.get(3), Edge.LEFT, false),


                edge(unfolded.get(2), Edge.BOTTOM, true),
                edge(unfolded.get(3), Edge.RIGHT, true),


                edge(unfolded.get(1), Edge.BOTTOM, false),
                edge(unfolded.get(3), Edge.TOP, false)

        )) {
            return false;
        }


/*
            log.info(Arrays.deepToString(unfolded.get(1)));


        int[] r = new int[]{
                0b01111,
                0b11111,

                0b01110,
                0b01111,

                0b01010,
                0b00100,
        };
*/


/*
        boolean b = checkOneEdge(r);


        int edgeT = edge(unfolded.get(1), Edge.LEFT, false);
        log.info(Long.toBinaryString(edgeT));


        int edgeTR = edge(unfolded.get(1), Edge.LEFT, true);
        log.info(Long.toBinaryString(edgeTR));


        int edgeB = edge(unfolded.get(1), Edge.RIGHT, false);
        log.info(Long.toBinaryString(edgeB));


        int edgeBR = edge(unfolded.get(1), Edge.RIGHT, true);
        log.info(Long.toBinaryString(edgeBR));
*/

        //int[] a = new int[]{1, 1, 1, 0, 0, 1};



        /*IntStream.range(0, 0).peek(e -> log.info(e + "")).forEach(value -> {
        });*/


       /* int asInt = IntStream.range(0, 6).map(i -> a[i]).reduce((left, right) -> {
            log.info(left + "  " + right);
            return (left << 1) + right;
        }).getAsInt();

        log.info(Long.toBinaryString(asInt));
*/

        /*log.info(Arrays.deepToString(unfolded.get(0)));

        List<int[]> onlyEdgesInInt = unfolded.stream().map(m -> {
            int[] edges = new int[4];

            edges[0] = IntStream.range(0, MATRIX_SIZE).map(i -> m[0][i]).reduce((left, right) -> {
                return (left << 1) + right;
            }).getAsInt();


            edges[3] = IntStream.range(0, MATRIX_SIZE).map(i -> m[MATRIX_SIZE - 1][i]).reduce((left, right) -> {
                return (left << 1) + right;
            }).getAsInt();


            return edges;
        }).collect(Collectors.toList());
*/
        return true;
    }

    private static boolean checkOneEdge(int... edges) {
        int r = (edges[0] & 0b10000) ^ (edges[1] & 0b10000) ^ (edges[2] & 0b00001) ^ (edges[3] & 0b00001) ^ edges[4] ^ edges[5];
        return 15 == r;
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


    private static int edge(int[][] matrix, Edge edge, boolean reverse) {

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

        log.info(Long.toBinaryString(res));
        return res;
    }


/*
    public static IntStream streamInReverse(int[] input) {
        return IntStream.range(1, input.length + 1).map(
                i -> input[input.length - i]);
    }
*/


}
