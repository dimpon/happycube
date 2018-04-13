package com.dimpon.happycube.loader;

import com.dimpon.happycube.exception.HappyCubeException;

import com.dimpon.happycube.pieces.PiecesContainerSolutions;
import com.dimpon.happycube.utils.MatrixUtils;
import org.junit.Assert;
import org.junit.Test;


public class DataLoaderTest {

    @Test
    public void loadInitialFiles() throws Exception {
        //Arrange
        int[][] expectedResult = new int[][]{
                {1, 0, 1, 0, 1},
                {1, 1, 1, 1, 1},
                {0, 1, 1, 1, 0},
                {1, 1, 1, 1, 1},
                {1, 0, 1, 0, 1}
        };


        //Act
        DataLoaderImpl loader = DataLoaderImpl
                .builder()
                .path("./src/test/resources")
                .prefix("blue")
                .extension("piece")
                .build();
        loader.loadData();

        PiecesContainerSolutions.Piece p = new PiecesContainerSolutions.Piece(1);
        loader.populate(p);


        //Assert
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingDeepEquals(p.getPositionByKey(20), expectedResult));
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingEnumeration(p.getPositionByKey(20), expectedResult));

    }

    @Test
    public void loadWrongFiles() throws Exception {

        DataLoaderImpl loader1 = DataLoaderImpl
                .builder()
                .path("./src/test/resources")
                .prefix("wrong1")
                .extension("piece")
                .build();

        try {
            loader1.loadData();
            Assert.fail();
        } catch (HappyCubeException e) {
            Assert.assertEquals(e.getType(), HappyCubeException.ExceptionsType.WRONG_INIT_DATA);
        }

        DataLoaderImpl loader2 = DataLoaderImpl
                .builder()
                .path("./src/test/resources")
                .prefix("wrong2")
                .extension("piece")
                .build();

        try {
            loader2.loadData();
            Assert.fail();
        } catch (HappyCubeException e) {
            Assert.assertEquals(e.getType(), HappyCubeException.ExceptionsType.WRONG_INIT_DATA);
        }

    }

}
