package com.dimpon.happycube.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.dimpon.happycube.utils.MatrixUtils.MATRIX_SIZE;

/**
 * The calss contains util methods for 3d matrices, e.g. int[][][]
 * Of course these methods can be placed in {@link MatrixUtils} but I decided that 200 lines is enough for one class
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Matrix3dUtils {


    /**
     * Builds the cube (int[][][]) based on unfolded form.
     * <p>
     * The sequence of plane surfaces:
     * <blockquote><pre>
     * 0 1 2
     *   3
     *   4
     *   5
     * </pre></blockquote>
     *
     * @param unfolded unfolded plane layout
     * @return cube in 3d array
     */
    public static int[][][] foldTheCube(List<int[][]> unfolded) {
        @SuppressWarnings("unchecked")
        int[][][] out = (int[][][]) Array.newInstance(Integer.TYPE, MATRIX_SIZE, MATRIX_SIZE, MATRIX_SIZE);


        int[][] leftPlane = unfolded.get(0);

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0, z = MATRIX_SIZE - 1; j < MATRIX_SIZE; j++, z--) {
                out[z][i][0] = out[z][i][0] + leftPlane[i][j];
            }
        }

        int[][] topPlane = unfolded.get(1);

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                out[0][i][j] = out[0][i][j] + topPlane[i][j];
            }
        }

        int[][] rightPlane = unfolded.get(2);

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0, z = 0; j < MATRIX_SIZE; j++, z++) {
                out[z][i][MATRIX_SIZE - 1] = out[z][i][MATRIX_SIZE - 1] + rightPlane[i][j];
            }
        }

        int[][] frontPlane = unfolded.get(3);

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                out[i][MATRIX_SIZE - 1][j] = out[i][MATRIX_SIZE - 1][j] + frontPlane[i][j];
            }
        }

        int[][] bottomPlane = unfolded.get(4);

        for (int i = 0, y = MATRIX_SIZE - 1; i < MATRIX_SIZE; i++, y--) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                out[MATRIX_SIZE - 1][y][j] = out[MATRIX_SIZE - 1][y][j] + bottomPlane[i][j];
            }
        }

        int[][] backPlane = unfolded.get(5);

        for (int i = 0, z = MATRIX_SIZE - 1; i < MATRIX_SIZE; i++, z--) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                out[z][0][j] = out[z][0][j] + backPlane[i][j];
            }
        }

        return out;
    }


    /**
     * The methods builds the "colored" cube. Each pieces matrix uses the number (1-6) instead of 1
     * e.g. piece 1 use 1, piece 2 use 2 etc.
     * <p>
     * The sequence of plane surfaces:
     * <blockquote><pre>
     * 0 1 2
     *   3
     *   4
     *   5
     * </pre></blockquote>
     *
     * @param unfolded unfolded plane layout
     * @return cube in 3d array
     */
    public static int[][][] foldColoredCube(List<int[][]> unfolded) {
        int color = 1;
        for (int[][] matrix : unfolded) {
            paintMatrix(matrix, color);
            color++;
        }

        return foldTheCube(unfolded);
    }

    /**
     * Replaces all 1 to color (int). Method does not create the new array, it changes initial!
     *
     * @param matrix initial matrix
     * @param color  int number, imitate color
     * @return "colored matrix"
     */
    public static void paintMatrix(int[][] matrix, int color) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                matrix[i][j] = matrix[i][j] * color;
            }
        }
    }


    /**
     * Performs preliminary check based on xor of "horizontal" edges, which does not require looping.
     * There are edges between 1-3, 3-4, 4-5, 1-5, 0-1, 1-2
     * Generally it speed up the whole checking case reject most permutations early. It gain 3 sec on my laptop for blue pieces.
     * <p>
     * todo might be the checking the others edges will give some more sec ? ))
     * <p>
     * <p>
     * <p>
     * The sequence of plane surfaces:
     * <blockquote><pre>
     * 0 1 2
     *   3
     *   4
     *   5
     * </pre></blockquote>
     *
     * @param unfolded unfolded form
     * @return true or false
     */
    static boolean isMakeSenseToCheckFurther(List<int[][]> unfolded) {


        //1-5
        if (!isTwoEdgesMatch(unfolded.get(1)[0], unfolded.get(5)[MATRIX_SIZE - 1])) {
            return false;
        }

        //1-3
        if (!isTwoEdgesMatch(unfolded.get(1)[MATRIX_SIZE - 1], unfolded.get(3)[0])) {
            return false;
        }

        //3-4
        if (!isTwoEdgesMatch(unfolded.get(3)[MATRIX_SIZE - 1], unfolded.get(4)[0])) {
            return false;
        }

        //4-5
        if (!isTwoEdgesMatch(unfolded.get(4)[MATRIX_SIZE - 1], unfolded.get(5)[0])) {
            return false;
        }

        //the checks below gives less performance. Hardcoding is ugly too.

        //1-2
        int[][] pl0 = unfolded.get(0);
        int[][] pl1 = unfolded.get(1);
        if (!isTwoEdgesMatch(new int[]{
                        pl0[0][MATRIX_SIZE - 1],
                        pl0[1][MATRIX_SIZE - 1],
                        pl0[2][MATRIX_SIZE - 1],
                        pl0[3][MATRIX_SIZE - 1],
                        pl0[4][MATRIX_SIZE - 1]},
                new int[]{
                        pl1[0][0],
                        pl1[1][0],
                        pl1[2][0],
                        pl1[3][0],
                        pl1[4][0]})) {

            return false;
        }

        //0-1
        int[][] pl11 = unfolded.get(1);
        int[][] pl22 = unfolded.get(2);
        if (!isTwoEdgesMatch(new int[]{
                        pl11[0][MATRIX_SIZE - 1],
                        pl11[1][MATRIX_SIZE - 1],
                        pl11[2][MATRIX_SIZE - 1],
                        pl11[3][MATRIX_SIZE - 1],
                        pl11[4][MATRIX_SIZE - 1]},
                new int[]{
                        pl22[0][0],
                        pl22[1][0],
                        pl22[2][0],
                        pl22[3][0],
                        pl22[4][0]})) {

            return false;
        }

        return true;
    }

    static boolean isTwoEdgesMatch(int[] one, int[] two) {
        int res = 0;
        for (int i = 1; i < MATRIX_SIZE - 1; i++) {
            res = res + (one[i] ^ two[i]);
        }
        return res == 3;
    }


    /**
     * Method checks whether cube is perfect.
     *
     * @param in cube for check
     * @return true or false
     */
    public static boolean isCubePerfect(int[][][] in) {
        return Arrays.deepEquals(in, perfectCube);
    }

    private static final int[][][] perfectCube = new int[][][]{
            {
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1}
            },
            {
                    {1, 1, 1, 1, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 1, 1, 1, 1}
            },
            {
                    {1, 1, 1, 1, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 1, 1, 1, 1}
            },
            {
                    {1, 1, 1, 1, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 1, 1, 1, 1}
            },
            {
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1}
            }
    };


}
