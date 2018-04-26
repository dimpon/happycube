package com.dimpon.happycube.pieces;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dimpon.happycube.pieces.MainProcessorSetsCount.*;

public class MainProcessorSetsCountTestA extends MainProcessorSetsCountTest {

    @Test
    public void testRunProcessor() throws Exception {

        long countCart = countUsingCartesianProduct(testCubePixels1, testRules1);

        long count = builder()
                .cubePixels(testCubePixels1)
                .rules(testRules1)
                .build().letsRoll();

        Assert.assertEquals(countCart, count);
    }

    private static final List<int[]> testCubePixels1 = new ArrayList<int[]>() {
        {
            add(new int[]{1, 5});//0
            add(new int[]{1, 5});
            add(new int[]{1, 5});
            add(new int[]{1, 5});//3
            add(new int[]{1, 5});
            add(new int[]{1, 5});
            add(new int[]{1, 5});//6
            add(new int[]{1, 5});
            add(new int[]{1, 5});//8
            add(new int[]{1, 5});
            add(new int[]{1, 5});//10
            add(new int[]{1, 2, 5});
        }
    };

    private static final List<MainProcessorSetsCount.CubeRule> testRules1 = new ArrayList<MainProcessorSetsCount.CubeRule>() {
        {
            add(new CubeRule().add(1, 5).add(1, 6));
            add(new CubeRule().add(5, 6).add(5, 7).add(5, 8).add(5, 9).add(5, 10));
            add(new CubeRule().add(5, 9).add(5, 11).add(5, 0).add(5, 1).add(5, 2));
            add(new CubeRule().add(1, 0).add(1, 1));
            add(new CubeRule().add(5, 6).add(1, 7).add(5, 8).add(1, 9));
        }
    };



}
