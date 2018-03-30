package com.dimpon.happycube.pieces;

import com.dimpon.happycube.utils.MatrixUtils;

import java.util.Map;

public interface PiecesAwareness {

    int[][] getPiecePositionByKey(int key);

    Map<MatrixUtils.Edge, Integer> getEdgeMagicNumbersKey(int key);
}
