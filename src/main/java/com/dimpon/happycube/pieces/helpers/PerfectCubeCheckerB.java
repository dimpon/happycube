package com.dimpon.happycube.pieces.helpers;

import com.dimpon.happycube.pieces.PiecesAwareness;
import com.dimpon.happycube.utils.MatrixUtils;
import com.dimpon.happycube.utils.PerfectCubeCheckerUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PerfectCubeCheckerB implements PerfectCubeChecker {

    private PiecesAwareness awareness;

    public PerfectCubeCheckerB(PiecesAwareness awareness) {
        this.awareness = awareness;
    }

    @Override
    public boolean check(int[] keys) {

        List<Map<MatrixUtils.Edge, Integer>> edges = Arrays.stream(keys)
                .mapToObj(awareness::getEdgeMagicNumbersKey)
                .collect(Collectors.toList());

        return PerfectCubeCheckerUtils.isCubePerfectByEdges(edges);
    }
}
