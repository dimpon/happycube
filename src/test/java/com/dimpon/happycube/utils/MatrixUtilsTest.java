package com.dimpon.happycube.utils;

import com.dimpon.happycube.pieces.OnePiece;
import com.dimpon.happycube.pieces.OnePieceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

        int[][] copy = Arrays.copyOf(in, in.length);

        //Act
        int[][] out = MatrixUtils.rotateToRight(in);

        //Assert
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingDeepEquals(expectedResult, out));
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingEnumeration(expectedResult, out));
        Assert.assertTrue(Arrays.deepEquals(copy, in));//check that initial matrix wasn't changed
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

        int[][] copy = Arrays.copyOf(in, in.length);

        //Act
        int[][] out = MatrixUtils.rotateToLeft(in);

        //Assert
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingDeepEquals(expectedResult, out));
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingEnumeration(expectedResult, out));
        Assert.assertTrue(Arrays.deepEquals(copy, in));
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

        int[][] copy = Arrays.copyOf(in, in.length);

        //Act
        int[][] out = MatrixUtils.rotate2times(in);

        //Assert
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingDeepEquals(expectedResult, out));
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingEnumeration(expectedResult, out));
        Assert.assertTrue(Arrays.deepEquals(copy, in));
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

        int[][] copy = Arrays.copyOf(in, in.length);

        //Act
        int[][] out = MatrixUtils.mirror(in);

        //Assert
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingDeepEquals(expectedResult, out));
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingEnumeration(expectedResult, out));
        Assert.assertTrue(Arrays.deepEquals(copy, in));
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
    public void testCheckOneEdge() throws Exception {

        Assert.assertTrue(MatrixUtils
                .checkOneEdge(
                        0b10000,
                        0b01010,
                        0b00100,
                        0b00001));

        Assert.assertTrue(MatrixUtils
                .checkOneEdge(
                        0b11111,
                        0b01010,
                        0b00100,
                        0b11111));

        Assert.assertTrue(MatrixUtils
                .checkOneEdge(
                        0b00100,
                        0b01000,
                        0b10110,
                        0b00001));


        Assert.assertTrue(MatrixUtils
                .checkOneEdge(
                        0b00100,
                        0b01010,
                        0b10100,
                        0b11011));

    }

    @Test
    public void testCalculateEdgesMagicNumbers() throws Exception {

        int[][] plane = new int[][]{
                {0, 0, 1, 0, 1},
                {1, 1, 1, 1, 1},
                {0, 1, 1, 1, 0},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 0, 1}
        };

        OnePiece p1 = new OnePieceImpl(0);
        p1.populate(plane);

        Map<Edge, Integer> mn = p1.getEdgeMagicNumbers(10);

        Assert.assertEquals(new Integer(5), mn.get(Edge.TOP));
        Assert.assertEquals(new Integer(20), mn.get(Edge.TOP_REVERSE));

        Assert.assertEquals(new Integer(11), mn.get(Edge.LEFT));
        Assert.assertEquals(new Integer(26), mn.get(Edge.LEFT_REVERSE));

        Assert.assertEquals(new Integer(27), mn.get(Edge.RIGHT));
        Assert.assertEquals(new Integer(27), mn.get(Edge.RIGHT_REVERSE));

        Assert.assertEquals(new Integer(29), mn.get(Edge.BOTTOM));
        Assert.assertEquals(new Integer(23), mn.get(Edge.BOTTOM_REVERSE));

    }

    @Test
    public void testIsCubePerfectUsingEdges() throws Exception {

        OnePiece p0 = new OnePieceImpl(0);
        p0.populate(leftPlaneReal);

        OnePiece p1 = new OnePieceImpl(1);
        p1.populate(topPlaneReal);

        OnePiece p2 = new OnePieceImpl(2);
        p2.populate(rightPlaneReal);

        OnePiece p3 = new OnePieceImpl(3);
        p3.populate(frontPlaneReal);

        OnePiece p4 = new OnePieceImpl(4);
        p4.populate(bottomPlaneReal);

        OnePiece p5 = new OnePieceImpl(5);
        p5.populate(backPlaneReal);


        List<Map<Edge, Integer>> input = new ArrayList<>(6);
        input.add(p0.getEdgeMagicNumbers(10));
        input.add(p1.getEdgeMagicNumbers(20));
        input.add(p2.getEdgeMagicNumbers(30));
        input.add(p3.getEdgeMagicNumbers(40));
        input.add(p4.getEdgeMagicNumbers(50));
        input.add(p5.getEdgeMagicNumbers(60));


        boolean b = PerfectCubeChecker.isCubePerfectUsingEdges(input);

        Assert.assertTrue(b);
    }


}
