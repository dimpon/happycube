package com.dimpon.happycube.pieces.helpers;

import com.dimpon.happycube.pieces.PiecesAwareness;
import com.dimpon.happycube.utils.MatrixUtils;
import com.dimpon.happycube.utils.PerfectCubeCheckerUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PerfectCubeCheckerA implements PerfectCubeChecker {
    private PiecesAwareness awareness;

    public PerfectCubeCheckerA(PiecesAwareness awareness) {
        this.awareness = awareness;
    }

    @Override
    public boolean check(int[] keys) {

        List<int[][]> matrices = Arrays.stream(keys)
                .mapToObj(awareness::getPiecePositionByKey)
                .collect(Collectors.toList());

        return PerfectCubeCheckerUtils.isCubePerfectByPlanes(matrices);

    }
}
