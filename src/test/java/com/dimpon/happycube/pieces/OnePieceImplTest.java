package com.dimpon.happycube.pieces;

import org.junit.Assert;
import org.junit.Test;

public class OnePieceImplTest {


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
        OnePiece p1 = new OnePieceImpl(0);

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
        OnePiece p2 = new OnePieceImpl(1);

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
        OnePiece p3 = new OnePieceImpl(2);

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
        OnePiece p4 = new OnePieceImpl(3);

        //Act
        p4.populate(initData4);

        //Assert
        Assert.assertEquals(1, p4.positionsSetKeys().toArray().length);

    }


}
