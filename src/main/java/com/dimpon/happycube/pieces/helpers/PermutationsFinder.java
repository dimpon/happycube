package com.dimpon.happycube.pieces.helpers;

import com.dimpon.happycube.pieces.PermutationChecker;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;


/**
 * Finds all permutations of input keys array and call {@link PermutationChecker} for each permutation
 */
@Slf4j
public  class PermutationsFinder {

    private PermutationChecker checker;

    public PermutationsFinder(PermutationChecker checker) {
        this.checker = checker;
    }

    public void permutations(int[] input) {
        solution(input, 0);
    }

    private void solution(int[] keys, int index) {

        if(!checker.continueSearch())
            return;

        if (index == keys.length - 1) {
            boolean perfect = this.checker.checkOnePermutation(keys);

            if(perfect){
                checker.perfectPermutationFound();
            }
        }

        for (int i = index; i < keys.length; i++) {
            solution(swap(index, i, Arrays.copyOf(keys, keys.length)), index + 1);
        }

    }

    private int[] swap(int first, int second, int[] arr) {
        int v = arr[first];
        arr[first] = arr[second];
        arr[second] = v;
        return arr;
    }
}
