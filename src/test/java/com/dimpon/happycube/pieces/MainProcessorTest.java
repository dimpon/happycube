package com.dimpon.happycube.pieces;

import com.dimpon.happycube.loader.DataLoaderImpl;
import com.dimpon.happycube.utils.MatrixUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainProcessorTest {

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
                .path("src/test/resources")
                .prefix("blue")
                .extension("piece")
                .build();

        Stream<OnePiece> sets = IntStream.range(0,6).mapToObj(OnePieceImpl::new);


        MainProcessorImpl processor = MainProcessorImpl.builder()
                //.checkSolutionUnique((a, a1) -> true)//right now don't check solution Uniqueness. print all solutions.
                .loader(loader)
                //.writer(writer)
                .positionsSets(sets.collect(Collectors.toList()))
                .build();

        processor.prepareData();

        Assert.assertTrue(Arrays.deepEquals(plane1,processor.getPiecePositionByKey(10)));

        Assert.assertTrue(Arrays.deepEquals(plane3,processor.getPiecePositionByKey(30)));
        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.rotateToRight(plane3),processor.getPiecePositionByKey(31)));
        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.rotateToLeft(plane3),processor.getPiecePositionByKey(32)));
        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.rotate2times(plane3),processor.getPiecePositionByKey(33)));

        Assert.assertTrue(Arrays.deepEquals(plane6,processor.getPiecePositionByKey(60)));
        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.rotateToRight(plane6),processor.getPiecePositionByKey(61)));
        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.rotateToLeft(plane6),processor.getPiecePositionByKey(62)));
        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.rotate2times(plane6),processor.getPiecePositionByKey(63)));
        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.mirror(plane6),processor.getPiecePositionByKey(64)));

        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.rotateToRight(MatrixUtils.mirror(plane6)),processor.getPiecePositionByKey(65)));
        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.rotateToLeft(MatrixUtils.mirror(plane6)),processor.getPiecePositionByKey(66)));
        Assert.assertTrue(Arrays.deepEquals(MatrixUtils.rotate2times(MatrixUtils.mirror(plane6)),processor.getPiecePositionByKey(67)));

    }

}
