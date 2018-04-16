package com.dimpon.happycube.pieces.helpers;


import lombok.Builder;

import java.util.List;
import java.util.function.Consumer;

@Builder
public class CartesianProductFinderLight {

    private final List<int[]> input;
    private  int[] $temp;

    @Builder.Default
    private Consumer<int[]> task = (i) -> {
    };

    @Builder.Default
    private EarlyCheckFunction earlyReject = (a, b, c) -> false;

    public void combinations() {
        this.$temp = new int[input.size()];
        solution(0);
    }

    private void solution(int index) {

        if (index > $temp.length - 1) {
            task.accept($temp);
            return;
        }

        for (int i = 0; i < input.get(index).length; i++) {
            int s = input.get(index)[i];

            if (earlyReject.apply($temp, index, s))
                continue;

            $temp[index] = s;
            index++;
            solution(index);
            index--;
        }


    }

    @FunctionalInterface
    public interface EarlyCheckFunction {
        boolean apply(int[] temp, int index, int newVariant);
    }

}
