package com.dimpon.happycube.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.dimpon.happycube.utils.Matrix3dUtils.*;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PerfectCubeChecker {

    public static boolean isCubePerfect(List<int[][]> unfolded) {

        if (!isMakeSenseToCheckFurther(unfolded)) {
            return false;
        }

        int[][][] cube = foldTheCube(unfolded);
        return Matrix3dUtils.isCubePerfect(cube);
    }

}
