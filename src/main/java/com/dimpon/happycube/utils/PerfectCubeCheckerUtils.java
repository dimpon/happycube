package com.dimpon.happycube.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.dimpon.happycube.utils.Matrix3dUtils.*;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PerfectCubeCheckerUtils {

    public static boolean isCubePerfect(List<int[][]> unfolded) {

        if (!isMakeSenseToCheckFurther(unfolded)) {
            return false;
        }

        int[][][] cube = foldTheCube(unfolded);
        return Matrix3dUtils.isCubePerfect(cube);
    }

}
