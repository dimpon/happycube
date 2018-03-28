package com.dimpon.happycube.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dimpon.happycube.utils.Data3dRealPlanes.*;
import static com.dimpon.happycube.utils.Data3dRealPlanes.backPlaneReal;
import static com.dimpon.happycube.utils.Data3dRealPlanes.bottomPlaneReal;
import static com.dimpon.happycube.utils.MatrixUtils.*;

@Slf4j
public class MatrixUtilsTest {

    private int[][] in = new int[][]{
            {1, 2, 3, 4, 5},
            {0, 0, 0, 0, 4},
            {0, 0, 0, 0, 3},
            {1, 1, 0, 0, 2},
            {1, 1, 0, 0, 1}
    };

    @Test
    public void testRotateToRight() throws Exception {

        //Arrange
        int[][] expectedResult = new int[][]{
                {1, 1, 0, 0, 1},
                {1, 1, 0, 0, 2},
                {0, 0, 0, 0, 3},
                {0, 0, 0, 0, 4},
                {1, 2, 3, 4, 5}
        };

        int[][] copy = Arrays.copyOf(in,in.length);

        //Act
        int[][] out = MatrixUtils.rotateToRight(in);

        //Assert
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingDeepEquals(expectedResult, out));
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingEnumeration(expectedResult, out));
        Assert.assertTrue(Arrays.deepEquals(copy,in));//check that initial matrix wasn't changed
    }

    @Test
    public void testRotateToLeft() throws Exception {

        //Arrange
        int[][] expectedResult = new int[][]{
                {5, 4, 3, 2, 1},
                {4, 0, 0, 0, 0},
                {3, 0, 0, 0, 0},
                {2, 0, 0, 1, 1},
                {1, 0, 0, 1, 1}
        };

        int[][] copy = Arrays.copyOf(in,in.length);

        //Act
        int[][] out = MatrixUtils.rotateToLeft(in);

        //Assert
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingDeepEquals(expectedResult, out));
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingEnumeration(expectedResult, out));
        Assert.assertTrue(Arrays.deepEquals(copy,in));
    }

    @Test
    public void testRotate2Times() throws Exception {

        //Arrange
        int[][] expectedResult = new int[][]{
                {1, 0, 0, 1, 1},
                {2, 0, 0, 1, 1},
                {3, 0, 0, 0, 0},
                {4, 0, 0, 0, 0},
                {5, 4, 3, 2, 1}
        };

        int[][] copy = Arrays.copyOf(in,in.length);

        //Act
        int[][] out = MatrixUtils.rotate2times(in);

        //Assert
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingDeepEquals(expectedResult, out));
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingEnumeration(expectedResult, out));
        Assert.assertTrue(Arrays.deepEquals(copy,in));
    }

    @Test
    public void testMirror() throws Exception {

        //Arrange
        int[][] expectedResult = new int[][]{
                {5, 4, 3, 2, 1},
                {4, 0, 0, 0, 0},
                {3, 0, 0, 0, 0},
                {2, 0, 0, 1, 1},
                {1, 0, 0, 1, 1}
        };

        int[][] copy = Arrays.copyOf(in,in.length);

        //Act
        int[][] out = MatrixUtils.mirror(in);

        //Assert
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingDeepEquals(expectedResult, out));
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingEnumeration(expectedResult, out));
        Assert.assertTrue(Arrays.deepEquals(copy,in));
    }

    @Test
    public void testArraysNotEqual() throws Exception {
        //Arrange
        int[][] wrongArray = new int[][]{
                {5, 4, 3, 2, 1},
                {4, 0, 0, 0, 0},
                {3, 0, 0, 0, 0},
                {2, 0, 0, 0, 0},
                {1, 0, 0, 0, 0}
        };

        //Act & Assert
        Assert.assertFalse(MatrixUtils.isTwoArraysEqualUsingDeepEquals(wrongArray, in));
        Assert.assertFalse(MatrixUtils.isTwoArraysEqualUsingEnumeration(wrongArray, in));
    }

    @Test
    public void testIsCentralPartOk() throws Exception {
        //Arrange
        int[][] wrongMatrix = new int[][]{
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 0},
                {1, 1, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0}
        };

        int[][] rightMatrix = new int[][]{
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 0},
                {1, 1, 1, 1, 0},
                {1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0}
        };

        //Act & Assert
        Assert.assertFalse(MatrixUtils.isCentralPartOk(wrongMatrix));
        Assert.assertTrue(MatrixUtils.isCentralPartOk(rightMatrix));

    }

    @Test
    public void testHashCodes() throws Exception {

        int[][] matrix = new int[][]{
                {1, 0, 1, 0, 1},
                {1, 1, 1, 1, 1},
                {0, 1, 1, 1, 0},
                {1, 1, 1, 1, 1},
                {1, 0, 1, 0, 1}
        };

        log.info("" + Arrays.deepEquals(matrix, mirror(matrix)));
        log.info("" + Arrays.deepHashCode(matrix));
        log.info("" + Arrays.deepHashCode(mirror(matrix)));
        log.info("***");

        log.info("" + Arrays.deepEquals(matrix, rotateToRight(matrix)));
        log.info("" + Arrays.deepHashCode(matrix));
        log.info("" + Arrays.deepHashCode(rotateToRight(matrix)));


    }


    @Test
    public void testIsCornersOk() throws Exception {
        //Arrange
        int[][] good = new int[][]{
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 0},
                {1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1}
        };


        int[][] bad1 = new int[][]{
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 0},
                {1, 1, 1, 1, 0},
                {0, 1, 1, 1, 0},
                {1, 0, 0, 0, 0}
        };

        int[][] bad2 = new int[][]{
                {1, 1, 1, 0, 1},
                {1, 1, 1, 1, 0},
                {1, 1, 1, 1, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0}
        };

        int[][] bad3 = new int[][]{
                {1, 1, 1, 0, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 0, 1}
        };

        int[][] bad4 = new int[][]{
                {1, 0, 1, 0, 1},
                {0, 1, 1, 1, 1},
                {1, 1, 1, 1, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0}
        };

        //Act & Assert
        Assert.assertTrue(MatrixUtils.isCornersOk(good));
        Assert.assertFalse(MatrixUtils.isCornersOk(bad1));
        Assert.assertFalse(MatrixUtils.isCornersOk(bad2));
        Assert.assertFalse(MatrixUtils.isCornersOk(bad3));
        Assert.assertFalse(MatrixUtils.isCornersOk(bad4));

    }

    @Test
    public void testIsCubePerfect() throws Exception {

        List<int[][]> unfolded = new ArrayList<>(6);
        unfolded.add(leftPlaneReal);
        unfolded.add(topPlaneReal);
        unfolded.add(rightPlaneReal);

        unfolded.add(frontPlaneReal);
        unfolded.add(bottomPlaneReal);
        unfolded.add(backPlaneReal);

        boolean cubePerfect = MatrixUtils.isCubePerfect(unfolded);

        Assert.assertTrue(cubePerfect);
    }





    }
