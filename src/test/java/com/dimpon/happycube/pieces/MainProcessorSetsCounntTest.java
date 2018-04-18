package com.dimpon.happycube.pieces;

import com.dimpon.happycube.pieces.helpers.CartesianProductFinderLight;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.dimpon.happycube.pieces.MainProcessorSetsCount.*;

@Slf4j
public class MainProcessorSetsCounntTest {

    @Test
    public void testRunProcessor() throws Exception {


        long start = System.currentTimeMillis();

        final AtomicLong good = new AtomicLong(0);//no big reason use Atomic, just easy to increment.
        //final AtomicLong bad = new AtomicLong(0);


        CartesianProductFinderLight cfl = CartesianProductFinderLight.builder()
                .input(testCubePixels)
                .task(ints -> {
                    if (check(ints, testRules))
                        good.incrementAndGet();
                })
                //.earlyReject(this::reject)
                .build();

        cfl.combinations();

        log.info("good:" + good.toString());
        log.info("Time iteration:" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        long count = MainProcessorSetsCount.builder()
                .cubePixels(testCubePixels)
                .rules(testRules)
                .build().letsRoll();

        log.info("Time smart approach:" + (System.currentTimeMillis() - start));


        Assert.assertEquals(good.get(), count);


    }

    private boolean reject(int[] t, int i, int n) {
        int[] tt = Arrays.copyOf(t, i);
        tt = Arrays.copyOf(tt, t.length);
        tt[i] = n;
        return !check(tt, testRules);
    }


    private boolean check(int[] arr, List<CubeRule> rules) {
        for (CubeRule rule : rules) {
            boolean r = true;
            for (CubeRule.Pixel pixel : rule.getPixels()) {
                r = r & (arr[pixel.getCell()] == pixel.getColor());
            }
            if (r) {
                return false;//combination is found
            }
        }

        return true;
    }

    @Test
    public void testCubeRules() throws Exception {

        CubeRule r1 = new CubeRule().add(5, 1).add(5, 2).add(5, 3);
        CubeRule r1a = new CubeRule().add(5, 2).add(5, 3).add(5, 1);
        CubeRule r1b = new CubeRule().add(5, 3).add(5, 2).add(5, 1);

        CubeRule r2 = new CubeRule().add(1, 3).add(1, 4).add(1, 5);
        CubeRule r3 = new CubeRule().add(5, 3).add(5, 4).add(5, 5);
        CubeRule r4 = new CubeRule().add(1, 4).add(1, 5).add(1, 6);

        CubeRule r5a = new CubeRule().add(5, 0).add(5, 1).add(5, 2).add(5, 4).add(5, 5).add(5, 6);
        CubeRule r5b = new CubeRule().add(5, 1).add(5, 2).add(5, 4).add(5, 5).add(5, 6).add(5, 0);

        List<CubeRule> cr = new ArrayList<>();
        cr.add(r5a);

        Assert.assertTrue(cr.stream().allMatch(r5b::isEqual));
        Assert.assertFalse(cr.stream().allMatch(r4::isEqual));

        Assert.assertTrue(r1.isEqual(r1a));
        Assert.assertTrue(r1.isEqual(r1b));
        Assert.assertFalse(r1.isEqual(r2));
        Assert.assertTrue(r1.canExistTogether(r3));
        Assert.assertTrue(r1.canExistTogether(r4));
        Assert.assertTrue(r1.canExistTogether(r1));
        Assert.assertFalse(r1.canExistTogether(r2));
    }


    //basically it is cube 3x3x3
    /*
    <blockquote><pre>

              5

    |0   1   2  3  4   5   6  7|
[4] |16 [1] 17 [2] 18 [3] 19 [4]
    |8   9  10 11 12  13  14 15|

              6
   __1__
  |0 1 2|
4 |7   3| 2   top
  |6 5 4|
    3

   __1_____
  |8  9  10|
4 |15    11| 2   bottom
  |14 13 12|
      3

    </blockquote></pre>
     */
    private static final List<int[]> testCubePixels = new ArrayList<int[]>(20) {
        {
            add(new int[]{1, 5, 4});//0
            add(new int[]{1, 5});
            add(new int[]{1, 5, 2});//2
            add(new int[]{2, 5});
            add(new int[]{2, 5, 3});//4
            add(new int[]{3, 5});
            add(new int[]{3, 5, 4});//6
            add(new int[]{4, 5});

            add(new int[]{1, 6, 4});//8
            add(new int[]{1, 6});
            add(new int[]{1, 6, 2});//10
            add(new int[]{2, 6});
            add(new int[]{2, 6, 3});//12
            add(new int[]{3, 6});
            add(new int[]{3, 6, 4});//14
            add(new int[]{4, 6});

            add(new int[]{1, 4});//16

            add(new int[]{1, 2});//17

            add(new int[]{2, 3});//18

            add(new int[]{3, 4});//19
        }
    };


    //some rules are commented for saving build time. But unit test works with uncommented rules also :)
    private static final List<CubeRule> testRules = new ArrayList<CubeRule>(8) {
        {

            //vertexes. every vertex have 3 variants. Totally 24 rules

            // color 5  plane
            add(new CubeRule().add(1, 1).add(5, 2).add(2, 3));
            add(new CubeRule().add(4, 7).add(5, 0).add(1, 1));
            add(new CubeRule().add(4, 7).add(5, 6).add(3, 5));
            add(new CubeRule().add(3, 5).add(5, 4).add(2, 3));

            //color 6  plane
            add(new CubeRule().add(4, 15).add(6, 8).add(1, 9));
            add(new CubeRule().add(1, 9).add(6, 10).add(2, 11));
            add(new CubeRule().add(2, 11).add(6, 12).add(3, 13));
            add(new CubeRule().add(3, 13).add(6, 14).add(4, 15));

            //color 1  plane
            //add(new CubeRule().add(4, 16).add(1, 0).add(5, 1));
            //add(new CubeRule().add(5, 1).add(1, 2).add(2, 17));
            //add(new CubeRule().add(6, 9).add(1, 10).add(2, 17));
            //add(new CubeRule().add(6, 9).add(1, 8).add(4, 16));

            //color 2 plane
            //add(new CubeRule().add(1, 17).add(2, 2).add(5, 3));
            //add(new CubeRule().add(5, 3).add(2, 4).add(3, 18));
            //add(new CubeRule().add(3, 18).add(2, 12).add(6, 11));
            //add(new CubeRule().add(6, 11).add(2, 10).add(1, 17));

            //color 3 plane
            //add(new CubeRule().add(2, 18).add(3, 4).add(5, 5));
            //add(new CubeRule().add(5, 5).add(3, 6).add(4, 19));
            //add(new CubeRule().add(4, 19).add(3, 14).add(6, 13));
            //add(new CubeRule().add(6, 13).add(3, 12).add(2, 18));

            //color 4 plane
            //add(new CubeRule().add(3, 19).add(4, 6).add(5, 7));
            //add(new CubeRule().add(5, 7).add(4, 0).add(1, 16));
            //add(new CubeRule().add(1, 16).add(4, 8).add(6, 15));
            //add(new CubeRule().add(6, 15).add(4, 14).add(3, 19));


            //edges. Edge cannot have all pixels of the same color
            //top plane
            add(new CubeRule().add(1, 0).add(1, 1).add(1, 2));
            add(new CubeRule().add(5, 0).add(5, 1).add(5, 2));

            add(new CubeRule().add(2, 2).add(2, 3).add(2, 4));
            add(new CubeRule().add(5, 2).add(5, 3).add(5, 4));

            add(new CubeRule().add(3, 4).add(3, 5).add(3, 6));
            add(new CubeRule().add(5, 4).add(5, 5).add(5, 6));

            add(new CubeRule().add(4, 6).add(4, 7).add(4, 0));
            add(new CubeRule().add(5, 6).add(5, 7).add(5, 0));

            //bottom plane
            add(new CubeRule().add(6, 8).add(6, 9).add(6, 10));
            add(new CubeRule().add(1, 8).add(1, 9).add(1, 10));

            add(new CubeRule().add(6, 10).add(6, 11).add(6, 12));
            add(new CubeRule().add(2, 10).add(2, 11).add(2, 12));

            add(new CubeRule().add(6, 12).add(6, 13).add(6, 14));
            add(new CubeRule().add(3, 12).add(3, 13).add(3, 14));

            add(new CubeRule().add(6, 14).add(6, 15).add(6, 8));
            add(new CubeRule().add(4, 14).add(4, 15).add(4, 8));

            //columns edges
            add(new CubeRule().add(1, 0).add(1, 16).add(1, 8));
            add(new CubeRule().add(4, 0).add(4, 16).add(4, 8));

            add(new CubeRule().add(1, 2).add(1, 17).add(1, 10));
            add(new CubeRule().add(2, 2).add(2, 17).add(2, 10));

            add(new CubeRule().add(2, 4).add(2, 18).add(2, 12));
            add(new CubeRule().add(3, 4).add(3, 18).add(3, 12));

            add(new CubeRule().add(3, 6).add(3, 19).add(3, 14));
            add(new CubeRule().add(4, 6).add(4, 19).add(4, 14));

        }
    };


}
