package com.dimpon.happycube.pieces;

import com.dimpon.happycube.exception.HappyCubeException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

import static com.dimpon.happycube.exception.HappyCubeException.ExceptionsType.RULES_CANNOT_EXIST_TOGETHER;
import static com.dimpon.happycube.exception.HappyCubeException.ExceptionsType.WRONG_INITIAL_RULES;


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

        //check
        checkTheRules();

        //calculate total number of combinations
        long allCombinations = combinationsCount(cubePixels);

        log.info("Total combinations: " + allCombinations);

        //calculate "bad" combinations - with flat edges and hanging corners
        $plus = true;
        oneCalculationRoundA(rules);
        log.info("Bad combinations: " + $total);
        long rez = (allCombinations - $total);
        log.info("(Total-Bad): " + rez);
        return rez;
    }

    private void checkTheRules() {
        for (final CubeRule one : rules) {
            for (final CubeRule two : rules) {

                if (one.equals(two))
                    continue;

                if (one.contains(two)) {
                    throw new HappyCubeException(WRONG_INITIAL_RULES, one, two);
                }
                if (two.contains(one)) {
                    throw new HappyCubeException(WRONG_INITIAL_RULES, two, one);
                }
            }
        }
    }

    private boolean $firstTry = true;

    private void oneCalculationRoundA(List<CubeRule> initRules) {

        final Map<CubeRule, Integer> howManyEqualsRules = new HashMap<>();

        List<CubeRule> rulesForTheNextRound = new ArrayList<>();


        for (int i = 0; i < initRules.size(); i++) {

            CubeRule firstRule = initRules.get(i);
            long comb = countCombinationsWithRules(firstRule);

            if ($plus) {
                $total = $total + comb;
                log.trace("plus:" + firstRule + " > " + comb);
            } else {
                $total = $total - comb;
                log.trace("minus:" + firstRule + " > " + comb);
            }



                for (int u = 0; u < rules.size(); u++) {
                    CubeRule secondRule = rules.get(u);

                    if (firstRule.canExistTogether(secondRule)
                            //&& !firstRule.equals(secondRule)
                            && !firstRule.contains(secondRule)
                            ) {
                        CubeRule joinedRule = new CubeRule(firstRule, secondRule);

                        howManyEqualsRules.compute(joinedRule, (s, integer) -> (integer == null) ? 1 : integer + 1);

                        if (!rulesForTheNextRound.contains(joinedRule))
                            rulesForTheNextRound.add(joinedRule);

                    }

            }
        }

        log.trace("total:" + $total);
        log.trace("rulesForTheNextRound:" + rulesForTheNextRound.size());

        log.trace("\n" + howManyEqualsRules.entrySet().stream().map(e -> e.getKey() + " #" + e.getValue()).collect(Collectors.joining("\n")));

        if (rulesForTheNextRound.size() == 0) {
            return;
        }

        $plus = !$plus;

        $firstTry = false;

        ArrayList<CubeRule> rulesForTheNextRoundLi = new ArrayList<>(rulesForTheNextRound);


        oneCalculationRoundA(rulesForTheNextRoundLi);
    }


    private void oneCalculationRound(List<CubeRule> initRules) {

        List<CubeRule> newRules = new ArrayList<>();

        for (int i = 0; i < initRules.size(); i++) {
            CubeRule firstRule = initRules.get(i);

            long comb = countCombinationsWithRules(firstRule);

            if ($plus)
                $total = $total + comb;
            else
                $total = $total - comb;

            for (int u = 0; u < rules.size(); u++) {
                CubeRule secondRule = rules.get(u);

                if (firstRule.canExistTogether(secondRule) && !firstRule.equals(secondRule)) {
                    CubeRule newRule = new CubeRule(firstRule, secondRule);

                    if (!firstRule.equals(newRule)) {

                        if (newRules.stream().noneMatch(newRule::equals)) {
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

        /*boolean isEqual(CubeRule r) {

            if (this.pixels.size() != r.pixels.size())
                return false;

            for (Pixel p : r.pixels) {
                if (!this.pixels.contains(p))
                    return false;
            }
            return true;
        }*/

        boolean contains(CubeRule r) {
            //this contains all rules from r and may be more.
            for (Pixel p : r.pixels) {
                if (!this.pixels.contains(p))
                    return false;
            }
            return true;
        }


        CubeRule(CubeRule... rules) {
            Set<Pixel> se = new HashSet<>();
            for (CubeRule rule : rules) {
                if ((se.stream()
                        .noneMatch(v -> rule.pixels.stream().anyMatch(v1 -> (v1.cell == v.cell && v1.color != v.color))))) {
                    se.addAll(rule.pixels);
                } else {
                    throw new HappyCubeException(RULES_CANNOT_EXIST_TOGETHER);
                }
            }
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;


            CubeRule cubeRule = (CubeRule) o;

            if (this.pixels.size() != cubeRule.pixels.size())
                return false;

            for (Pixel p : cubeRule.pixels) {
                if (!this.pixels.contains(p))
                    return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            return pixels.stream().mapToInt(p -> (int) p.color * p.cell).sum();
        }

        @Value
        static class Pixel {
            final int color;
            final int cell;
        }
    }
}
