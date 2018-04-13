package com.dimpon.happycube.pieces.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Finds combinations of all positions of every planes.
 */
public class CartesianProductFinder {

    public CartesianProductFinder(List<int[]> input) {
        this.input = input;
        this.temp = new int[input.size()];
    }

    private final List<int[]> input;

    private final List<int[]> combinations = new ArrayList<>();

    private final int[] temp;


    public Stream<int[]> combinations() {
        solution(0);
        return combinations.stream();
    }


    private void solution(int index) {

        if (index > temp.length - 1) {
            combinations.add(Arrays.copyOf(temp, temp.length));
            return;
        }

        for (int i = 0; i < input.get(index).length; i++) {
            int s = input.get(index)[i];

            temp[index] = s;
            index++;
            solution(index);
            index--;
        }


    }
}