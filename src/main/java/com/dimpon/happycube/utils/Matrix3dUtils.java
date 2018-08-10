package com.dimpon.happycube.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.*;

import static com.dimpon.happycube.utils.MatrixUtils.MATRIX_SIZE;

/**
 * The calss contains util methods for 3d matrices, e.g. int[][][]
 * Of course these methods can be placed in {@link MatrixUtils} but I decided that 200 lines is enough for one class
 */
@Slf4j
@UtilityClass
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
    public static int[][][] foldColoredCube(List<int[][]> unfolded, int[] colors) {
        List<int[][]> res = new ArrayList<>(6);
        for (int i = 0; i < unfolded.size(); i++) {
            int[][] matrix = unfolded.get(i);
            res.add(paintMatrix(matrix, colors[i]));
        }

        return foldTheCube(res);
    }

    /**
     * Rotate 3d cube 90 grad clockwise over Z axis
     * <p>
     * top, bottom planes are't move
     * facade > left, left > back, back > right, right > facade
     *
     * @param in inout cube
     * @return rotated cube
     */
    public static int[][][] rotateCubeZ(int[][][] in) {
        int[][][] out = (int[][][]) Array.newInstance(Integer.TYPE, MATRIX_SIZE, MATRIX_SIZE, MATRIX_SIZE);

        for (int i = 0; i < MATRIX_SIZE; i++) {
            out[i] = MatrixUtils.rotateToRight(in[i]);
        }

        return out;
    }

    /**
     * Rotate 3d cube 90 grad over Y axis
     * <p>
     * right, left planes are't move
     * top > facade, facade > bottom, bottom > back, back > top
     *
     * @param in inout cube
     * @return rotated cube
     */
    public static int[][][] rotateCubeY(int[][][] in) {
        int[][][] out = (int[][][]) Array.newInstance(Integer.TYPE, MATRIX_SIZE, MATRIX_SIZE, MATRIX_SIZE);


        //top > facade
        for (int i = 0; i < MATRIX_SIZE; i++) {
            out[i][MATRIX_SIZE - 1] = in[0][i];
        }

        //facade > bottom
        for (int i = 0; i < MATRIX_SIZE; i++) {
            out[MATRIX_SIZE - 1][MATRIX_SIZE - 1 - i] = in[i][MATRIX_SIZE - 1];
        }

        //bottom > back
        for (int i = 0; i < MATRIX_SIZE; i++) {
            out[i][0] = in[MATRIX_SIZE - 1][i];
        }

        //back > top
        for (int i = 0; i < MATRIX_SIZE; i++) {
            out[0][i] = in[MATRIX_SIZE - 1 - i][0];
        }

        //central part
        for (int i = 1; i < MATRIX_SIZE - 1; i++) {
            for (int u = 1; u < MATRIX_SIZE - 1; u++) {
                out[i][u] = in[i][u];
            }
        }

        return out;
    }

    public static int[][][] mirrorCube(int[][][] in) {
        int[][][] out = (int[][][]) Array.newInstance(Integer.TYPE, MATRIX_SIZE, MATRIX_SIZE, MATRIX_SIZE);

        for (int i = 0; i < MATRIX_SIZE; i++) {
            out[MATRIX_SIZE - 1 - i] = in[i];
        }

        return out;
    }


    /**
     * Rotate the 3d cube in order that plane with 1 pixels will b eon top
     *
     * @param cube colored cube
     * @return colored cobe with 1 on top
     */
    public static int[][][] planeOneToTop(int[][][] cube) {

        int top = cube[0][2][2];
        if (top == 1) {
            return Arrays.copyOf(cube,MATRIX_SIZE);
        }

        int front = cube[2][4][2];
        if (front == 1) {
            return rotateCubeY(rotateCubeY(rotateCubeY(cube)));
        }

        int back = cube[2][0][2];
        if (back == 1) {
            return rotateCubeY(cube);
        }

        int left = cube[2][2][0];
        if (left == 1) {
            return rotateCubeY(rotateCubeZ(cube));
        }

        int right = cube[2][2][4];
        if (right == 1) {
            return rotateCubeY(rotateCubeY(rotateCubeY(rotateCubeZ(cube))));
        }


        int bottom = cube[4][2][2];
        if (bottom == 1) {
            return rotateCubeY(rotateCubeY(cube));
        }

        return Arrays.copyOf(cube,MATRIX_SIZE);

    }

    /**
     * Replaces all 1 to color (int).
     *
     * @param matrix initial matrix
     * @param color  int number, imitate color
     * @return "colored matrix"
     */
     static int[][] paintMatrix(int[][] matrix, int color) {
        @SuppressWarnings("unchecked")
        int[][] out = (int[][]) Array.newInstance(Integer.TYPE, MATRIX_SIZE, MATRIX_SIZE);

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                out[i][j] = matrix[i][j] * color;
            }
        }
        return out;
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


    static boolean isMakeSenseToCheckFurtherUsingMagicNumbers(List<Map<MatrixUtils.Edge, Integer>> edges) {


        //0-1
        if (!isTwoEdgesMatch(
                edges.get(0).get(MatrixUtils.Edge.RIGHT),
                edges.get(1).get(MatrixUtils.Edge.LEFT))) {
            return false;
        }

        //1-2
        if (!isTwoEdgesMatch(
                edges.get(1).get(MatrixUtils.Edge.RIGHT),
                edges.get(2).get(MatrixUtils.Edge.LEFT))) {
            return false;
        }

        //1-3
        if (!isTwoEdgesMatch(
                edges.get(1).get(MatrixUtils.Edge.BOTTOM),
                edges.get(3).get(MatrixUtils.Edge.TOP))) {
            return false;
        }

        //1-5
        if (!isTwoEdgesMatch(
                edges.get(1).get(MatrixUtils.Edge.TOP),
                edges.get(5).get(MatrixUtils.Edge.BOTTOM))) {
            return false;
        }

        //4-3
        if (!isTwoEdgesMatch(
                edges.get(4).get(MatrixUtils.Edge.TOP),
                edges.get(3).get(MatrixUtils.Edge.BOTTOM))) {
            return false;
        }

        //4-5
        if (!isTwoEdgesMatch(
                edges.get(4).get(MatrixUtils.Edge.BOTTOM),
                edges.get(5).get(MatrixUtils.Edge.TOP))) {
            return false;
        }

        //4-0
        if (!isTwoEdgesMatch(
                edges.get(4).get(MatrixUtils.Edge.LEFT),
                edges.get(0).get(MatrixUtils.Edge.LEFT_REVERSE))) {
            return false;
        }

        //4-2
        if (!isTwoEdgesMatch(
                edges.get(4).get(MatrixUtils.Edge.RIGHT),
                edges.get(2).get(MatrixUtils.Edge.RIGHT_REVERSE))) {
            return false;
        }

        return true;

    }

    public static boolean isCubePerfectUsingEdges(List<Map<MatrixUtils.Edge, Integer>> unfolded) {


        //1-3
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(0).get(MatrixUtils.Edge.BOTTOM_REVERSE),
                unfolded.get(1).get(MatrixUtils.Edge.BOTTOM),
                unfolded.get(3).get(MatrixUtils.Edge.TOP),
                unfolded.get(2).get(MatrixUtils.Edge.BOTTOM_REVERSE)
        )) {
            log.debug("1-3");
            return false;
        }

        //0-1
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(5).get(MatrixUtils.Edge.BOTTOM),
                unfolded.get(0).get(MatrixUtils.Edge.RIGHT),
                unfolded.get(1).get(MatrixUtils.Edge.LEFT),
                unfolded.get(3).get(MatrixUtils.Edge.TOP_REVERSE)
        )) {
            log.debug("0-1");
            return false;
        }

        //1-2
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(5).get(MatrixUtils.Edge.BOTTOM_REVERSE),
                unfolded.get(2).get(MatrixUtils.Edge.LEFT),
                unfolded.get(1).get(MatrixUtils.Edge.RIGHT),
                unfolded.get(3).get(MatrixUtils.Edge.TOP)
        )) {
            log.debug("1-2");
            return false;
        }

        //1-5
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(0).get(MatrixUtils.Edge.TOP_REVERSE),
                unfolded.get(1).get(MatrixUtils.Edge.TOP),
                unfolded.get(5).get(MatrixUtils.Edge.BOTTOM),
                unfolded.get(2).get(MatrixUtils.Edge.TOP_REVERSE)
        )) {
            log.debug("1-5");
            return false;
        }

        //3-4
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(0).get(MatrixUtils.Edge.BOTTOM),
                unfolded.get(3).get(MatrixUtils.Edge.BOTTOM),
                unfolded.get(4).get(MatrixUtils.Edge.TOP),
                unfolded.get(2).get(MatrixUtils.Edge.BOTTOM)
        )) {
            log.debug("4-3");
            return false;
        }


        //4-5
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(0).get(MatrixUtils.Edge.TOP),
                unfolded.get(4).get(MatrixUtils.Edge.BOTTOM),
                unfolded.get(5).get(MatrixUtils.Edge.TOP),
                unfolded.get(2).get(MatrixUtils.Edge.TOP)

        )) {
            log.debug("4-5");
            return false;
        }

        //4-0
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(3).get(MatrixUtils.Edge.LEFT_REVERSE),
                unfolded.get(4).get(MatrixUtils.Edge.LEFT),
                unfolded.get(0).get(MatrixUtils.Edge.LEFT_REVERSE),
                unfolded.get(5).get(MatrixUtils.Edge.LEFT_REVERSE)

        )) {
            log.debug("4-0");
            return false;
        }

        //4-2
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(3).get(MatrixUtils.Edge.RIGHT_REVERSE),
                unfolded.get(4).get(MatrixUtils.Edge.RIGHT),
                unfolded.get(2).get(MatrixUtils.Edge.RIGHT_REVERSE),
                unfolded.get(5).get(MatrixUtils.Edge.RIGHT_REVERSE)

        )) {
            log.debug("4-2");
            return false;
        }


        //3-0
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(1).get(MatrixUtils.Edge.BOTTOM),
                unfolded.get(3).get(MatrixUtils.Edge.LEFT),
                unfolded.get(0).get(MatrixUtils.Edge.BOTTOM_REVERSE),
                unfolded.get(4).get(MatrixUtils.Edge.TOP_REVERSE)

        )) {
            log.debug("3-0");
            return false;
        }

        //3-2
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(1).get(MatrixUtils.Edge.BOTTOM_REVERSE),
                unfolded.get(3).get(MatrixUtils.Edge.RIGHT),
                unfolded.get(2).get(MatrixUtils.Edge.BOTTOM),
                unfolded.get(4).get(MatrixUtils.Edge.TOP)

        )) {
            log.debug("3-2");
            return false;
        }

        //5-0
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(4).get(MatrixUtils.Edge.LEFT_REVERSE),
                unfolded.get(5).get(MatrixUtils.Edge.LEFT),
                unfolded.get(0).get(MatrixUtils.Edge.TOP),
                unfolded.get(1).get(MatrixUtils.Edge.LEFT_REVERSE)

        )) {
            log.debug("5-0");
            return false;
        }

        //5-2
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(4).get(MatrixUtils.Edge.RIGHT_REVERSE),
                unfolded.get(5).get(MatrixUtils.Edge.RIGHT),
                unfolded.get(2).get(MatrixUtils.Edge.TOP_REVERSE),
                unfolded.get(1).get(MatrixUtils.Edge.RIGHT_REVERSE)

        )) {
            log.debug("5-2");
            return false;
        }


        return true;
    }


    static boolean isTwoEdgesMatch(int one, int two) {
        log.debug(String.format("%5s", Integer.toBinaryString(one)).replace(' ', '0'));
        log.debug(String.format("%5s", Integer.toBinaryString(two)).replace(' ', '0'));
        int res = (one & 0b01110) ^ (two & 0b01110);
        return res == 0b01110;
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
