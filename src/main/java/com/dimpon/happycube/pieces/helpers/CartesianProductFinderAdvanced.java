package com.dimpon.happycube.pieces.helpers;

import com.dimpon.happycube.utils.Matrix3dUtils;
import com.dimpon.happycube.utils.MatrixUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * This class finds combinations of int[][] unlike {@link CartesianProductFinder} which operates with int codes
 * Into algorithm the early-checks are integrated for avoid unnessesary loops.
 */
@Slf4j
public class CartesianProductFinderAdvanced {

    public CartesianProductFinderAdvanced(List<int[][][]> input) {
        this.input = input;
        this.temp = new int[input.size()][][];
    }

    private final List<int[][][]> input;

    private final int[][][] temp;

    private Consumer<int[][][]> task;

    public void combinationsWithoutSaving(Consumer<int[][][]> task) {
        this.task = task;
        solution(0);
    }

    private void solution(int index) {

        if (index > temp.length - 1) {
            task.accept(Arrays.copyOf(temp, temp.length));
            return;
        }

        for (int i = 0; i < input.get(index).length; i++) {
            int[][] s = input.get(index)[i];


            if (earlyReject(index, s))
                continue;


            temp[index] = s;
            index++;
            solution(index);
            index--;
        }
    }

    private boolean earlyReject(int index, int[][] newVariant) {

        //int res = 0;

        switch (index) {
            case 0:

                break;
            case 1:
                //0-1
                if (((temp[0][0][4] & newVariant[0][0]) + (temp[0][4][4] & newVariant[4][0])) != 0 ||
                        ((temp[0][1][4] ^ newVariant[1][0]) +
                                (temp[0][2][4] ^ newVariant[2][0]) +
                                (temp[0][3][4] ^ newVariant[3][0])) != 3)
                    return true;


                break;
            case 2:
                //1-2
                if (((temp[1][0][4] & newVariant[0][0]) + (temp[1][4][4] & newVariant[4][0])) != 0 ||
                        ((temp[1][1][4] ^ newVariant[1][0]) +
                                (temp[1][2][4] ^ newVariant[2][0]) +
                                (temp[1][3][4] ^ newVariant[3][0])) != 3)
                    return true;

                break;
            case 3:
                //1-3
                if (((temp[1][4][0] & newVariant[0][0]) + (temp[1][4][4] & newVariant[0][4])) != 0 ||
                        ((temp[1][4][1] ^ newVariant[0][1]) +
                                (temp[1][4][2] ^ newVariant[0][2]) +
                                (temp[1][4][3] ^ newVariant[0][3])) != 3)
                    return true;

                //0-3
                if (((temp[0][4][0] & newVariant[4][0]) + (temp[0][4][4] & newVariant[0][0])) != 0 ||
                        ((temp[0][4][1] ^ newVariant[3][0]) +
                                (temp[0][4][2] ^ newVariant[2][0]) +
                                (temp[0][4][3] ^ newVariant[1][0])) != 3)
                    return true;

                //2-3
                if (((temp[2][4][0] & newVariant[0][4]) + (temp[2][4][4] & newVariant[4][4])) != 0 ||
                        ((temp[2][4][1] ^ newVariant[1][4]) +
                                (temp[2][4][2] ^ newVariant[2][4]) +
                                (temp[2][4][3] ^ newVariant[3][4])) != 3)
                    return true;

                break;

            case 4:
                //3-4
                if (((temp[3][4][0] & newVariant[0][0]) + (temp[3][4][4] & newVariant[0][4])) != 0 ||
                        ((temp[3][4][1] ^ newVariant[0][1]) +
                                (temp[3][4][2] ^ newVariant[0][2]) +
                                (temp[3][4][3] ^ newVariant[0][3])) != 3)
                    return true;

                //0-4
                if (((temp[0][0][0] & newVariant[4][0]) + (temp[0][4][0] & newVariant[0][0])) != 0 ||
                        (temp[0][1][0] ^ newVariant[3][0]) +
                                (temp[0][2][0] ^ newVariant[2][0]) +
                                (temp[0][3][0] ^ newVariant[1][0]) != 3)
                    return true;

                //2-4
                if (((temp[2][0][4] & newVariant[4][4]) + (temp[2][4][4] & newVariant[0][4])) != 0 ||
                        ((temp[2][1][4] ^ newVariant[3][4]) +
                                (temp[2][2][4] ^ newVariant[2][4]) +
                                (temp[2][3][4] ^ newVariant[1][4])) != 3)
                    return true;

                break;

            case 5:
                //4-5
                if (((temp[4][4][0] & newVariant[0][0]) + (temp[4][4][4] & newVariant[0][4])) != 0 ||
                        ((temp[4][4][1] ^ newVariant[0][1]) +
                                (temp[4][4][2] ^ newVariant[0][2]) +
                                (temp[4][4][3] ^ newVariant[0][3])) != 3)
                    return true;

                //1-5
                if (((temp[1][0][0] & newVariant[4][0]) + (temp[1][0][4] & newVariant[4][4])) != 0 ||
                        ((temp[1][0][1] ^ newVariant[4][1]) +
                                (temp[1][0][2] ^ newVariant[4][2]) +
                                (temp[1][0][3] ^ newVariant[4][3])) != 3)
                    return true;

                //0-5
                if (((temp[0][0][0] & newVariant[0][0]) + (temp[0][0][4] & newVariant[4][0])) != 0 ||
                        ((temp[0][0][1] ^ newVariant[1][0]) +
                                (temp[0][0][2] ^ newVariant[2][0]) +
                                (temp[0][0][3] ^ newVariant[3][0])) != 3)
                    return true;

                //2-5
                if (((temp[2][0][0] & newVariant[4][4]) + (temp[2][0][4] & newVariant[0][4])) != 0 ||
                        (temp[2][0][1] ^ newVariant[3][4]) +
                                (temp[2][0][2] ^ newVariant[2][4]) +
                                (temp[2][0][3] ^ newVariant[1][4]) != 3)
                    return true;


                break;


            default:

                break;
        }
        return false;
    }
}
