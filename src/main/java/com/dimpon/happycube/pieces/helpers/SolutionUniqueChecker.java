package com.dimpon.happycube.pieces.helpers;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;

import static com.dimpon.happycube.utils.Matrix3dUtils.mirrorCube;
import static com.dimpon.happycube.utils.Matrix3dUtils.planeOneToTop;
import static com.dimpon.happycube.utils.Matrix3dUtils.rotateCubeZ;

@Slf4j
public class SolutionUniqueChecker implements BiPredicate<List<int[][][]>, int[][][]> {


    @Override
    public boolean test(List<int[][][]> uSolutions, int[][][] sol) {

        sol = planeOneToTop(sol);
        log.debug("top:" + sol[0][2][2]);

        for (int[][][] u : uSolutions) {
            if (check(u, sol)) {
                return false;
            }
        }

        return true;
    }

    private boolean checkOne(int[][][] u, int[][][] n) {

        if (Arrays.deepEquals(u, n)) {
            return true;
        }

        if (Arrays.deepEquals(u, rotateCubeZ(n))) {
            return true;
        }

        if (Arrays.deepEquals(u, rotateCubeZ(rotateCubeZ(n)))) {
            return true;
        }

        if (Arrays.deepEquals(u, rotateCubeZ(rotateCubeZ(rotateCubeZ(n))))) {
            return true;
        }

        return false;
    }

    private boolean check(int[][][] u, int[][][] n) {

        if (checkOne(u, n)) {
            return true;
        }

        if (checkOne(u, mirrorCube(n))) {
            return true;
        }

        return false;
    }
}
