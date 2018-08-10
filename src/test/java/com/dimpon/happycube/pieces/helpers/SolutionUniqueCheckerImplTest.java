package com.dimpon.happycube.pieces.helpers;

import org.junit.Assert;
import org.junit.Test;

import static com.dimpon.happycube.utils.Data3dRealPlanes.coloredCubeRotatedY;

public class SolutionUniqueCheckerImplTest {

    @Test
    public void testCheck(){

        SolutionUniqueChecker checker = new SolutionUniqueCheckerImpl();

        boolean check = checker.check(coloredCubeRotatedY);

        Assert.assertTrue(check);
    }

}
