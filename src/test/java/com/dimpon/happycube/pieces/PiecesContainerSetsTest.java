package com.dimpon.happycube.pieces;

import com.dimpon.happycube.loader.DataLoader;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static com.dimpon.happycube.utils.Data3dRealPlanes.*;
import static org.mockito.Mockito.mock;

public class PiecesContainerSetsTest {

    @Test
    public void testBuildOneVariantPotentialPiece() throws Exception {

        int[][] expectedVariant = new int[][]{
                {1, 2, 3, 4, 5},
                {16, 1, 1, 1, 6},
                {15, 1, 1, 1, 7},
                {14, 1, 1, 1, 8},
                {13, 12, 11, 10, 9}
        };


        int[] edge = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        PiecesContainerSets.PotentialPiece pp = new PiecesContainerSets.PotentialPiece(0);

        int[][] variant = pp.buildPieceVariant(edge);

        Assert.assertTrue(Arrays.deepEquals(expectedVariant, variant));

    }


    @Test
    public void testCreateOnePotentialPiece() throws Exception {

        //Act
        PiecesContainerSets.PotentialPiece pp = new PiecesContainerSets.PotentialPiece(0);

        //Assert
        Assert.assertEquals(0, pp.getOrderNumber());
        Assert.assertEquals(27218, pp.getPositions().size());

        checkPieceAvailability(pp, leftPlaneReal);
        checkPieceAvailability(pp, rightPlaneReal);
        checkPieceAvailability(pp, topPlaneReal);
        checkPieceAvailability(pp, frontPlaneReal);
        checkPieceAvailability(pp, bottomPlaneReal);
        checkPieceAvailability(pp, backPlaneReal);

    }

    private void checkPieceAvailability(PiecesContainerSets.PotentialPiece pp, int[][] expectedPiece) {

        Optional<int[][]> find = pp.getPositions().stream().filter(ints ->
                Arrays.deepEquals(ints, expectedPiece)
        ).findFirst();

        Assert.assertTrue(find.isPresent());
        Assert.assertTrue(Arrays.deepEquals(expectedPiece, find.get()));


    }

    @Test
    public void testCreateContainerAndFillIt() throws Exception {

        //Act
        PiecesContainer<int[][][]> pc = new PiecesContainerSets();

        pc.createInitialPieces(DataLoader.STUB);

        //Assert
        Assert.assertEquals(6, pc.getObjectsForCombinations().count());

        pc.getObjectsForCombinations().forEach(ints -> {
            Assert.assertEquals(27218, ints.length);
        });

    }

}
