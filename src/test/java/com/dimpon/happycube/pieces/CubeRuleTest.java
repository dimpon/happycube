package com.dimpon.happycube.pieces;

import com.dimpon.happycube.exception.HappyCubeException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dimpon.happycube.exception.HappyCubeException.ExceptionsType.RULES_CANNOT_EXIST_TOGETHER;
import static com.dimpon.happycube.pieces.MainProcessorSetsCount.*;
import static com.dimpon.happycube.pieces.MainProcessorSetsCount.CubeRule.*;

public class CubeRuleTest {

    @Test
    public void testRulesAuxMethods() throws Exception {





        CubeRule r1 = new CubeRule().add(5, 1).add(5, 2).add(5, 3);
        CubeRule r1a = new CubeRule().add(5, 2).add(5, 3).add(5, 1);
        CubeRule r1b = new CubeRule().add(5, 3).add(5, 2).add(5, 1);
        CubeRule r1bb = new CubeRule().add(5, 2).add(5, 1);

        CubeRule r2 = new CubeRule().add(1, 3).add(1, 4).add(1, 5);
        CubeRule r3 = new CubeRule().add(5, 3).add(5, 4).add(5, 5);
        CubeRule r4 = new CubeRule().add(1, 4).add(1, 5).add(1, 6);

        CubeRule r5a = new CubeRule().add(5, 0).add(5, 1).add(5, 2).add(5, 4).add(5, 5).add(5, 6);
        CubeRule r5b = new CubeRule().add(5, 1).add(5, 2).add(5, 4).add(5, 5).add(5, 6).add(5, 0);

        List<MainProcessorSetsCount.CubeRule> rulesForMinus = new ArrayList<>();
        rulesForMinus.add(r1);
        rulesForMinus.add(r2);
        rulesForMinus.add(r3);
        rulesForMinus.add(r4);


        //Assert
        Assert.assertTrue(rulesForMinus.contains(r1));
        Assert.assertTrue(rulesForMinus.contains(r1a));

        Assert.assertFalse(rulesForMinus.stream().noneMatch(r1a::equals));
        Assert.assertFalse(rulesForMinus.stream().noneMatch(r1b::equals));
        Assert.assertTrue(rulesForMinus.stream().noneMatch(r5a::equals));
        Assert.assertTrue(rulesForMinus.stream().noneMatch(r5b::equals));

        List<MainProcessorSetsCount.CubeRule> cr = new ArrayList<>();
        cr.add(r5a);

        Assert.assertTrue(cr.stream().allMatch(r5b::equals));
        Assert.assertFalse(cr.stream().allMatch(r4::equals));

        Assert.assertTrue(r1.contains(r1bb));
        Assert.assertTrue(r1a.contains(r1bb));
        Assert.assertTrue(r1b.contains(r1bb));
        Assert.assertFalse(r1bb.contains(r1b));
        Assert.assertFalse(r2.contains(r1b));
        Assert.assertTrue(r2.contains(r2));


        Assert.assertTrue(r1.equals(r1a));
        Assert.assertTrue(r1.equals(r1b));
        Assert.assertFalse(r1.equals(r2));
        Assert.assertTrue(r1.canExistTogether(r3));
        Assert.assertTrue(r1.canExistTogether(r4));
        Assert.assertTrue(r1.canExistTogether(r1));
        Assert.assertFalse(r1.canExistTogether(r2));
    }

    @Test
    public void testConstructor() throws Exception {


        CubeRule r1 = new CubeRule().add(5, 1).add(5, 2).add(5, 3);
        CubeRule r1a = new CubeRule().add(5, 2).add(5, 3).add(5, 1);
        CubeRule r1b = new CubeRule().add(5, 3).add(5, 2).add(5, 1);
        CubeRule r1bb = new CubeRule().add(5, 2).add(5, 1).add(5, 4);

        CubeRule joined = new CubeRule(r1, r1a, r1b, r1bb);

        List<CubeRule.Pixel> pixels = joined.getPixels();

        Assert.assertEquals(4, pixels.size());
        Assert.assertTrue(pixels.contains(new Pixel(5, 1)));
        Assert.assertTrue(pixels.contains(new Pixel(5, 2)));
        Assert.assertTrue(pixels.contains(new Pixel(5, 3)));
        Assert.assertTrue(pixels.contains(new Pixel(5, 4)));

        CubeRule r2 = new CubeRule().add(1, 2).add(5, 1).add(5, 4);

        try {
            new CubeRule(joined, r2);
            Assert.fail();
        } catch (HappyCubeException e) {
            Assert.assertEquals(RULES_CANNOT_EXIST_TOGETHER, e.getType());

        }


    }

}
