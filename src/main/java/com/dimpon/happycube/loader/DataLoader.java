package com.dimpon.happycube.loader;

import com.dimpon.happycube.pieces.OnePiece;

/**
 * Just interface for DataLoaderImpl.
 * Right now it is excessive, but might be useful for unit tests.
 */
public interface DataLoader {

    void loadData();

    void populate(OnePiece piece);


}
