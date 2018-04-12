package com.dimpon.happycube.loader;

import com.dimpon.happycube.pieces.PiecesContainerImpl;

/**
 * Just interface for DataLoaderImpl.
 * Right now it is excessive, but might be useful for unit tests.
 */
public interface DataLoader {

    void loadData();

    void populate(PiecesContainerImpl.Piece piece);

    DataLoader STUB = new DataLoaderStub();

}
