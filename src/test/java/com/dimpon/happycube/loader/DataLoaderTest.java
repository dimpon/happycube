package com.dimpon.happycube.loader;

import com.dimpon.happycube.exception.HappyCubeException;
import com.dimpon.happycube.pieces.OnePiece;
import com.dimpon.happycube.utils.MatrixUtils;
import lombok.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;


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
                .path("src/test/resources")
                .prefix("blue")
                .extension("piece")
                .build();
        loader.loadData();

        TestOnePiece p = new TestOnePiece(1);
        loader.populate(p);


        //Assert
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingDeepEquals(p.getInitialData(), expectedResult));
        Assert.assertTrue(MatrixUtils.isTwoArraysEqualUsingEnumeration(p.getInitialData(), expectedResult));

    }

    @Test
    public void loadWrongFiles() throws Exception {

        DataLoaderImpl loader1 = DataLoaderImpl
                .builder()
                .path("src/test/resources")
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
                .path("src/test/resources")
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

    @Getter
    @RequiredArgsConstructor
    private static class TestOnePiece implements OnePiece {

        private int[][] initialData = new int[MatrixUtils.MATRIX_SIZE][MatrixUtils.MATRIX_SIZE];

        private final int orderNumber;

        @Override
        public int getOrderNumber() {
            return orderNumber;
        }

        @Override
        public void populate(int[][] initialData) {
            this.initialData = initialData;
        }

        @Override
        public Stream<Integer> positionsSetKeys() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int[][] getPositionByKey(int key) {
            throw new UnsupportedOperationException();
        }
    }

}
