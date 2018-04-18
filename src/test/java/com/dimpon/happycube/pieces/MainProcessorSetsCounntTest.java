package com.dimpon.happycube.pieces;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dimpon.happycube.pieces.MainProcessorSetsCount.*;

@Slf4j
public class MainProcessorSetsCounntTest {

    @Test
    public void testRunProcessor() throws Exception {


        log.info("");


        MainProcessor mp = MainProcessorSetsCount.builder().build();

        mp.letsRoll();


    }

    @Test
    public void testCubeRules() throws Exception {

        CubeRule r1 = new CubeRule().add(5, 1).add(5, 2).add(5, 3);
        CubeRule r1a = new CubeRule().add(5, 2).add(5, 3).add(5, 1);
        CubeRule r1b = new CubeRule().add(5, 3).add(5, 2).add(5, 1);

        CubeRule r2 = new CubeRule().add(1, 3).add(1, 4).add(1, 5);
        CubeRule r3 = new CubeRule().add(5, 3).add(5, 4).add(5, 5);
        CubeRule r4 = new CubeRule().add(1, 4).add(1, 5).add(1, 6);

        CubeRule r5a = new CubeRule().add(5, 0).add(5, 1).add(5, 2).add(5,4).add(5,5).add(5,6);
        CubeRule r5b = new CubeRule().add(5, 1).add(5, 2).add(5,4).add(5,5).add(5,6).add(5, 0);

        List<CubeRule> cr = new ArrayList<>();
        cr.add(r5a);

        Assert.assertTrue(cr.stream().allMatch(r5b::isEqual));
        Assert.assertFalse(cr.stream().allMatch(r4::isEqual));

        Assert.assertTrue(r1.isEqual(r1a));
        Assert.assertTrue(r1.isEqual(r1b));
        Assert.assertFalse(r1.isEqual(r2));
        Assert.assertTrue(r1.canExistTogether(r3));
        Assert.assertTrue(r1.canExistTogether(r4));
        Assert.assertFalse(r1.canExistTogether(r1));
        Assert.assertFalse(r1.canExistTogether(r2));
    }


}
