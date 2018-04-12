package com.dimpon.happycube.pieces;

import com.dimpon.happycube.loader.DataLoader;
import org.junit.Assert;
import org.junit.Test;
import sun.management.snmp.util.SnmpLoadedClassData;

import static org.mockito.Mockito.mock;

public class PotentialSetsPiecesContainerImplTest {

    @Test
    public void testCreateOnePotentialPiece() throws Exception {

        //Act
        PotentialSetsPiecesContainerImpl.PotentialPiece pp = new PotentialSetsPiecesContainerImpl.PotentialPiece(0);

        //Assert
        Assert.assertEquals(0,pp.getOrderNumber());
        Assert.assertEquals(22594,pp.positionsSetKeys().count());

    }

    @Test
    public void testCreateContainerAndFillIt() throws Exception {

        //Act
        PiecesContainer pc = new PotentialSetsPiecesContainerImpl();

        pc.createInitialPieces(DataLoader.STUB);

        //Assert
        Assert.assertEquals(6,pc.getPiecesCodesGroupedByOrigins().count());

        pc.getPiecesCodesGroupedByOrigins().forEach(ints -> {
            Assert.assertEquals(22594,ints.length);
        });

    }

}
