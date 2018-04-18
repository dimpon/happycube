package com.dimpon.happycube.pieces;

import com.dimpon.happycube.pieces.helpers.CartesianProductFinderLight;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Builder
public class MainProcessorSetsCount implements MainProcessor {

    private long $total = 0;

    private List<int[]> cubePixels;




    private static final List<int[]> testCube = new ArrayList<int[]>(20) {
        {
            add(new int[]{1, 5, 4});//0
            add(new int[]{1, 5});
            add(new int[]{1, 5, 2});//2
            add(new int[]{2, 5});
            add(new int[]{2, 5, 3});//4
            add(new int[]{3, 5});
            add(new int[]{3, 5, 4});//6
            add(new int[]{4, 5});

            add(new int[]{1, 4, 6});//8
            add(new int[]{1, 6});
            /*add(new int[]{1, 2, 6});//10
            add(new int[]{2, 6});
            add(new int[]{2, 3, 6});//12
            add(new int[]{3, 6});
            add(new int[]{3, 4, 6});//14
            add(new int[]{4, 6});

            add(new int[]{1, 4});//16

            add(new int[]{1, 2});

            add(new int[]{2, 3});

            add(new int[]{3, 4});//19*/
        }
    };







    @Override
    public void letsRoll() {


        //$total number of combinations
        long count = combinationsCount(testCube);

        log.info("count:" + count);


        IntStream.rangeClosed(0, 11)
                .forEach(i -> {
                });

        final AtomicLong good = new AtomicLong(0);
        final AtomicLong bad = new AtomicLong(0);


        CartesianProductFinderLight cfl = CartesianProductFinderLight.builder()
                .input(testCube)
                .task(ints -> {
                    if (check(ints,testEdgesRules)) {
                        good.incrementAndGet();
                        //log.info(Arrays.stream(ints).boxed().map(String::valueOf).collect(Collectors.joining(",")));
                    } else {
                        bad.incrementAndGet();
                        //log.info(Arrays.stream(ints).boxed().map(String::valueOf).collect(Collectors.joining(",")) + " *");
                    }
                })
                .build();

        cfl.combinations();

        log.info("good:" + good.toString());
        log.info("bad:" + bad.toString());


        newApproach();

    }



    private void newApproach() {

        addOrSubtract = true;
        oneCalculationRound(testEdgesRules);

        log.info("$total:" + $total);


    }



    boolean addOrSubtract = true;//add

    private void oneCalculationRound(List<CubeRule> rules) {

        List<CubeRule> newRules = new ArrayList<>();

        for (int i = 0; i < rules.size(); i++) {
            CubeRule cubeRule = rules.get(i);

            long comb = countCombinationsWithRules(cubeRule);

            if (addOrSubtract)
                $total = $total + comb;
            else
                $total = $total - comb;

            for (int u = 0; u < testEdgesRules.size(); u++) {
                CubeRule cubeRule1 = testEdgesRules.get(u);

                if (cubeRule.canExistTogether(cubeRule1) && !cubeRule.isEqual(cubeRule1)) {
                    CubeRule newRule = new CubeRule(cubeRule, cubeRule1);

                    if (!cubeRule.isEqual(newRule)) {

                        if (newRules.stream().noneMatch(newRule::isEqual)) {
                            newRules.add(newRule);
                        }
                    }
                }
            }
        }


        addOrSubtract = !addOrSubtract;

        if (newRules.size() == 0)
            return;

        oneCalculationRound(newRules);
    }


    private long countCombinationsWithRules(CubeRule rules) {
        List<int[]> toCheck = new ArrayList<>(testCube);
        for (CubeRule.Pixel pixel : rules.pixels) {
            toCheck.set(pixel.cell, new int[]{pixel.color});
        }
        return combinationsCount(toCheck);
    }


    private static final List<CubeRule> testEdgesRules = new ArrayList<CubeRule>(8) {
        {
            add(new CubeRule().add(1, 0).add(1, 1).add(1, 2)); //1, new int[]{0, 1, 2}));
            add(new CubeRule().add(5, 0).add(5, 1).add(5, 2));//5, new int[]{0, 1, 2}));

            add(new CubeRule().add(2, 2).add(2, 3).add(2, 4)); //2, new int[]{2, 3, 4}));
            add(new CubeRule().add(5, 2).add(5, 3).add(5, 4)); //5, new int[]{2, 3, 4}));

            add(new CubeRule().add(5, 2).add(1, 1).add(2, 3));//vertex

            add(new CubeRule().add(3, 4).add(3, 5).add(3, 6));//3, new int[]{4, 5, 6}));
            add(new CubeRule().add(5, 4).add(5, 5).add(5, 6));// 5, new int[]{4, 5, 6}));

            add(new CubeRule().add(4, 6).add(4, 7).add(4, 0));// 4, new int[]{6, 7, 0}));
            add(new CubeRule().add(5, 6).add(5, 7).add(5, 0));//5, new int[]{6, 7, 0}));
        }
    };

    private boolean check(int[] arr, List<CubeRule> rules) {
        for (CubeRule rule : rules) {
            boolean r = true;
            for (CubeRule.Pixel pixel : rule.pixels) {
                r = r & (arr[pixel.cell] == pixel.color);
            }
            if (r) return false;
        }

        return true;
    }


    private long combinationsCount(List<int[]> pixels) {
        return pixels.stream().mapToLong(v -> v.length)
                .reduce((left, right) -> left * right).orElse(0);
    }

    @Value
    @NoArgsConstructor
    static class CubeRule {

        final List<Pixel> pixels = new ArrayList<>();

        boolean canExistTogether(CubeRule rule) {
            return (rule.pixels.stream()
                    .noneMatch(v -> this.pixels.stream().anyMatch(v1 -> (v1.cell == v.cell && v1.color != v.color))));
        }

        boolean isEqual(CubeRule r) {

            if (this.pixels.size() != r.pixels.size())
                return false;

            for (Pixel p : r.pixels) {
                if (!this.pixels.contains(p))
                    return false;
            }
            return true;
        }


        CubeRule(CubeRule a, CubeRule b) {
            Set<Pixel> se = new HashSet<>();
            se.addAll(a.pixels);
            se.addAll(b.pixels);
            pixels.addAll(se);
        }


        CubeRule add(int color, int cell) {
            Pixel p = new Pixel(color, cell);
            if (!pixels.contains(p))
                pixels.add(p);
            return this;
        }


        @Override
        public String toString() {
            return this.getPixels().stream().map(p -> "[" + p.color + "," + p.cell + "]").collect(Collectors.joining(","));
        }

        @Value
        static class Pixel {
            final int color;
            final int cell;
        }
    }
}
