package com.dimpon.happycube.pieces;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dimpon.happycube.pieces.MainProcessorSetsCount.*;

public class MainProcessorSetsCountTestC extends MainProcessorSetsCountTest {

    @Test
    public void testRunProcessor() throws Exception {

        long countCart = countUsingCartesianProduct(testCubePixels2, testRules2);

        long count = builder()
                .cubePixels(testCubePixels2)
                .rules(testRules2)
                .build().letsRoll();

        Assert.assertEquals(countCart, count);
    }


    private static final List<int[]> testCubePixels2 = new ArrayList<int[]>() {
        {
            add(new int[]{1, 5});//0
            add(new int[]{1, 5});
            add(new int[]{1, 5});
            add(new int[]{1, 5});
            add(new int[]{1, 5});
            add(new int[]{1, 5});//5
            //add(new int[]{1, 5});
        }
    };

    private static final List<MainProcessorSetsCount.CubeRule> testRules2 = new ArrayList<MainProcessorSetsCount.CubeRule>() {
        {
            /*add(new CubeRule().add(5, 0).add(5, 1));
            add(new CubeRule().add(5, 2).add(5, 3));
            add(new CubeRule().add(5, 0).add(5, 3));
            add(new CubeRule().add(5, 1).add(5, 2));*/



            add(new CubeRule().add(5, 0).add(5, 1));
            add(new CubeRule().add(5, 1).add(5, 2));
            add(new CubeRule().add(5, 2).add(5, 3));
            add(new CubeRule().add(5, 3).add(5, 4));

            //add(new CubeRule().add(1, 3).add(5, 4));

            /*add(new CubeRule().add(5, 0).add(5, 1).add(5, 2));
            add(new CubeRule().add(5, 2).add(5, 3).add(5, 4));
            add(new CubeRule().add(5, 4).add(5, 5).add(5, 0));*/




        }
    };

}
