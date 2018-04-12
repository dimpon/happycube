package com.dimpon.happycube.pieces;

import com.dimpon.happycube.loader.DataLoaderImpl;
import com.dimpon.happycube.utils.MatrixUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PiecesContainerImplTest {

    @Test
    public void testPopulateAndFindAllPositions() throws Exception {

        //Arrange
        int[][] initData1 = new int[][]{
                {1, 0, 1, 0, 1},
                {1, 1, 1, 1, 1},
                {0, 1, 1, 1, 0},
                {1, 1, 1, 1, 1},
                {1, 0, 1, 0, 1}
        };
        PiecesContainerImpl.Piece p1 = new PiecesContainerImpl.Piece(0);

        //Act
        p1.populate(initData1);

        //Assert
        Assert.assertEquals(2, p1.positionsSetKeys().toArray().length);

        //Arrange
        int[][] initData2 = new int[][]{
                {1, 0, 0, 0, 0},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {0, 0, 0, 0, 1}
        };
        PiecesContainerImpl.Piece p2 = new PiecesContainerImpl.Piece(1);

        //Act
        p2.populate(initData2);

        //Assert
        Assert.assertEquals(4, p2.positionsSetKeys().toArray().length);

        //Arrange
        int[][] initData3 = new int[][]{
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 1, 1, 1, 0},
                {0, 1, 1, 1, 1},
                {0, 0, 0, 0, 0}
        };
        PiecesContainerImpl.Piece p3 = new PiecesContainerImpl.Piece(2);

        //Act
        p3.populate(initData3);

        //Assert
        Assert.assertEquals(8, p3.positionsSetKeys().toArray().length);

        //Arrange
        int[][] initData4 = new int[][]{
                {0, 0, 1, 0, 0},
                {0, 1, 1, 1, 0},
                {1, 1, 1, 1, 1},
                {0, 1, 1, 1, 0},
                {0, 0, 1, 0, 0}
        };
        PiecesContainerImpl.Piece p4 = new PiecesContainerImpl.Piece(3);

        //Act
        p4.populate(initData4);

        //Assert
        Assert.assertEquals(1, p4.positionsSetKeys().toArray().length);

    }


    private static final int[][] plane1 = new int[][]{
            {0, 0, 1, 0, 0},
            {0, 1, 1, 1, 0},
            {1, 1, 1, 1, 1},
            {0, 1, 1, 1, 0},
            {0, 0, 1, 0, 0}
    };

    private static final int[][] plane3 = new int[][]{
            {0, 0, 1, 0, 0},
            {0, 1, 1, 1, 1},
            {1, 1, 1, 1, 0},
            {0, 1, 1, 1, 1},
            {0, 0, 1, 0, 0}
    };

    private static final int[][] plane6 = new int[][]{
            {0, 1, 0, 1, 0},
            {0, 1, 1, 1, 1},
            {1, 1, 1, 1, 0},
            {0, 1, 1, 1, 1},
            {1, 1, 0, 1, 1}
    };

    @Test
    public void testDataPreparation() throws Exception {


        DataLoaderImpl loader = DataLoaderImpl
                .builder()
                .path("./src/test/resources")
                .prefix("blue")
                .extension("piece")
                .build();


        PiecesContainer container = new PiecesContainerImpl();
        container.createInitialPieces(loader);

        Assert.assertTrue(Arrays.deepEquals(plane1, container.getPiecePositionByKey(10)));

        Assert.assertTrue(Arrays.deepEquals(plane3, container.getPiecePositionByKey(30)));
        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.rotateToRight(plane3), container.getPiecePositionByKey(31)));
        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.rotateToLeft(plane3), container.getPiecePositionByKey(32)));
        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.rotate2times(plane3), container.getPiecePositionByKey(33)));

        Assert.assertTrue(Arrays.deepEquals(plane6, container.getPiecePositionByKey(60)));
        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.rotateToRight(plane6), container.getPiecePositionByKey(61)));
        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.rotateToLeft(plane6), container.getPiecePositionByKey(62)));
        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.rotate2times(plane6), container.getPiecePositionByKey(63)));
        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.mirror(plane6), container.getPiecePositionByKey(64)));

        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.rotateToRight(MatrixUtils.mirror(plane6)), container.getPiecePositionByKey(65)));
        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.rotateToLeft(MatrixUtils.mirror(plane6)), container.getPiecePositionByKey(66)));
        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.rotate2times(MatrixUtils.mirror(plane6)), container.getPiecePositionByKey(67)));

    }

}
