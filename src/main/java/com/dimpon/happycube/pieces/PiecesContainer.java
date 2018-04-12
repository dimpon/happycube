package com.dimpon.happycube.pieces;

import com.dimpon.happycube.loader.DataLoader;

import java.util.List;
import java.util.stream.Stream;

public interface PiecesContainer {

    /**
     * Loads initial data and creates the variant for each pieces, save each piece with unique code
     *
     * @param loader data loader
     */
    void createInitialPieces(DataLoader loader);

    /**
     * Returns piece variant by unique code
     *
     * @param key code
     * @return piece variant
     */
    int[][] getPiecePositionByKey(int key);

    /**
     * Returns all codes for all pieces variants grouped by "sources"
     *
     * @return stream with codes
     */
    Stream<int[]> getPiecesCodesGroupedByOrigins();


}
