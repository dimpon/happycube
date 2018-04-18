package com.dimpon.happycube.pieces;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Builder
public class MainProcessorSetsCount {

    private long $total = 0;

    private boolean $plus = true;//plus or minus found combinations

    @NonNull
    private List<int[]> cubePixels;

    @NonNull
    private List<CubeRule> rules;


    public long letsRoll() {


        //calculate total number of combinations
        long allCombinations = combinationsCount(cubePixels);

        log.info("Total combinations:" + allCombinations);

        //calculate "bad" combinations - with flat edges and hanging corner
        $plus = true;
        oneCalculationRound(rules);
        log.info("Bad combinations:" + $total);

        long rez = (allCombinations - $total);

        log.info("Total - bad:" + rez);

        return rez;

    }


    private void oneCalculationRound(List<CubeRule> cRules) {

        List<CubeRule> newRules = new ArrayList<>();

        for (int i = 0; i < cRules.size(); i++) {
            CubeRule cubeRule = cRules.get(i);

            long comb = countCombinationsWithRules(cubeRule);

            if ($plus)
                $total = $total + comb;
            else
                $total = $total - comb;

            for (int u = 0; u < rules.size(); u++) {
                CubeRule cubeRule1 = rules.get(u);

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

        $plus = !$plus;

        if (newRules.size() == 0)
            return;

        oneCalculationRound(newRules);
    }


    private long countCombinationsWithRules(CubeRule rules) {
        List<int[]> toCheck = new ArrayList<>(cubePixels);
        for (CubeRule.Pixel pixel : rules.pixels) {
            toCheck.set(pixel.cell, new int[]{pixel.color});
        }
        return combinationsCount(toCheck);
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


        @Value
        static class Pixel {
            final int color;
            final int cell;
        }
    }
}
