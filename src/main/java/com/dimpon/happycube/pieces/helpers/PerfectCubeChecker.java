package com.dimpon.happycube.pieces.helpers;

import com.dimpon.happycube.pieces.PiecesAwareness;
import lombok.NoArgsConstructor;

@FunctionalInterface
public interface PerfectCubeChecker {
     boolean check(int[] keys);
}
