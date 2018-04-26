package com.dimpon.happycube.pieces;

import com.dimpon.happycube.pieces.helpers.CartesianProductFinderLight;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.dimpon.happycube.pieces.MainProcessorSetsCount.*;


@Slf4j
public abstract class MainProcessorSetsCountTest {


    public abstract void testRunProcessor() throws Exception;


     long countUsingCartesianProduct(List<int[]> pixels, List<CubeRule> rules) {
        final AtomicLong good = new AtomicLong(0);//no big reason use Atomic, just easy to increment.
        final AtomicLong total = new AtomicLong(0);

        CartesianProductFinderLight cfl = CartesianProductFinderLight.builder()
                .input(pixels)
                .task(ints -> {
                    total.incrementAndGet();
                    if (check(ints, rules)) {
                        good.incrementAndGet();
                        log.trace(Arrays.stream(ints).boxed().map(String::valueOf).collect(Collectors.joining(",")));
                    }else{
                        log.trace(Arrays.stream(ints).boxed().map(String::valueOf).collect(Collectors.joining(","))+" *");
                    }
                })
                .build();

        cfl.combinations();

        log.debug("*Total combinations: " + total.toString());

        log.debug("*Good combinations: " + good.toString());

         log.debug("*Bad combinations: " + (total.get() - good.get()));

        return good.get();
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









}
