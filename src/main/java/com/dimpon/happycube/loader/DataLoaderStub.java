package com.dimpon.happycube.loader;

import com.dimpon.happycube.pieces.PiecesContainerImpl;

public class DataLoaderStub implements DataLoader {
    @Override
    public void loadData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void populate(PiecesContainerImpl.Piece piece) {
        throw new UnsupportedOperationException();
    }
}
