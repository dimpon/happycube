package com.dimpon.happycube.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.dimpon.happycube.utils.Data3d.*;
import static com.dimpon.happycube.utils.Data3dRealPlanes.*;

import static com.dimpon.happycube.utils.Matrix3dUtils.*;
import static com.dimpon.happycube.utils.MatrixUtils.*;

@Slf4j
public class Matrix3dUtilsTest {

    @Test
    public void testFoldTheCube() throws Exception {

        List<int[][]> unfolded = new ArrayList<>(6);
        unfolded.add(leftPlane);
        unfolded.add(topPlane);
        unfolded.add(rightPlane);

        unfolded.add(frontPlane);
        unfolded.add(bottomPlane);
        unfolded.add(backPlane);


        int[][][] out = Matrix3dUtils.foldTheCube(unfolded);

        //printTheCube(out);

        // Check mark points.
        // Mark points are located on every plane in point (1,3)
        // Good marker to check how 2d unfolded match to 3d cube. See the planes in Data3d
        Assert.assertEquals(11, out[0][3][1]);
        Assert.assertEquals(22, out[3][4][1]);
        Assert.assertEquals(33, out[1][0][1]);
        Assert.assertEquals(44, out[1][3][4]);
        Assert.assertEquals(55, out[3][3][0]);
        Assert.assertEquals(55, out[3][3][0]);
        Assert.assertEquals(66, out[4][1][1]);
    }

    @Test
    public void testFoldTheRealCube() throws Exception {

        List<int[][]> unfolded = new ArrayList<>(6);
        unfolded.add(leftPlaneReal);
        unfolded.add(topPlaneReal);
        unfolded.add(rightPlaneReal);

        unfolded.add(frontPlaneReal);
        unfolded.add(bottomPlaneReal);
        unfolded.add(backPlaneReal);


        int[][][] out = Matrix3dUtils.foldTheCube(unfolded);

        //printTheCube(out);

        Assert.assertTrue(Matrix3dUtils.isCubePerfect(out));

        int[][][] out1 = Matrix3dUtils.foldTheCube(Arrays.stream(realCubeLilac).collect(Collectors.toList()));

        Assert.assertTrue(Matrix3dUtils.isCubePerfect(out1));

    }

    @Test
    public void testIsMakeSenseToCheckFurther() throws Exception {

        List<int[][]> unfolded = new ArrayList<>(6);
        unfolded.add(leftPlaneReal);
        unfolded.add(topPlaneReal);
        unfolded.add(rightPlaneReal);

        unfolded.add(frontPlaneReal);
        unfolded.add(bottomPlaneReal);
        unfolded.add(backPlaneReal);


        boolean isMakeSense = Matrix3dUtils.isMakeSenseToCheckFurther(unfolded);

        //printTheCube(out);

        Assert.assertTrue(isMakeSense);

    }

    @Test
    public void testPaintMatrix() throws Exception {

        //Arrange
        int[][] matrix = new int[][]{
                {0, 1, 0, 1, 0},
                {0, 1, 1, 1, 0},
                {1, 1, 1, 1, 1},
                {0, 1, 1, 1, 0},
                {0, 0, 1, 0, 0}
        };

        int[][] expectedResult = new int[][]{
                {0, 3, 0, 3, 0},
                {0, 3, 3, 3, 0},
                {3, 3, 3, 3, 3},
                {0, 3, 3, 3, 0},
                {0, 0, 3, 0, 0}
        };

        //Act
        int[][] out = paintMatrix(matrix, 3);

        //Assert
        Assert.assertTrue(Arrays.deepEquals(expectedResult, out));

    }


    @Test
    public void testFoldColoredCube() throws Exception {

        List<int[][]> unfolded = new ArrayList<>(6);
        unfolded.add(leftPlaneReal);
        unfolded.add(topPlaneReal);
        unfolded.add(rightPlaneReal);

        unfolded.add(frontPlaneReal);
        unfolded.add(bottomPlaneReal);
        unfolded.add(backPlaneReal);


        int[][][] out = Matrix3dUtils.foldColoredCube(unfolded, new int[]{1, 2, 3, 4, 5, 6});


        Assert.assertTrue(Arrays.deepEquals(coloredCube[0], out[0]));
        Assert.assertTrue(Arrays.deepEquals(coloredCube[1], out[1]));


        //todo need to make test model of folded colored cube

    }

    private void printTheCube(int[][][] cube) {

        for (int z = 0; z < MATRIX_SIZE; z++) {
            for (int y = 0; y < MATRIX_SIZE; y++) {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                for (int x = 0; x < MATRIX_SIZE; x++) {
                    sb.append(cube[z][y][x]).append(",");

                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append("]");
                log.info(sb.toString());
            }
            log.info("***");
        }
    }

    @Test
    public void testIsCubePerfect() throws Exception {
        Assert.assertTrue(Matrix3dUtils.isCubePerfect(perfectCube));
        Assert.assertFalse(Matrix3dUtils.isCubePerfect(defectCube));
    }

    @Test
    public void testIsTwoEdgesMatch() throws Exception {

        Assert.assertTrue(Matrix3dUtils.isTwoEdgesMatch(new int[]{1, 0, 1, 0, 1}, new int[]{1, 1, 0, 1, 1}));

        Assert.assertFalse(Matrix3dUtils.isTwoEdgesMatch(new int[]{1, 1, 1, 0, 0}, new int[]{0, 1, 0, 1, 0}));

        Assert.assertFalse(Matrix3dUtils.isTwoEdgesMatch(new int[]{1, 1, 1, 0, 0}, new int[]{0, 1, 0, 0, 0}));

        Assert.assertFalse(Matrix3dUtils.isTwoEdgesMatch(new int[]{1, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0}));

        Assert.assertFalse(Matrix3dUtils.isTwoEdgesMatch(new int[]{1, 0, 1, 0, 0}, new int[]{0, 0, 1, 1, 1}));

        Assert.assertTrue(Matrix3dUtils.isTwoEdgesMatch(new int[]{5, 1, 1, 1, 0}, new int[]{0, 0, 0, 0, 7}));

    }


}
