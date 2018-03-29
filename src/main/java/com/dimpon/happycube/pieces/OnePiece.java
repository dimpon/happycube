package com.dimpon.happycube.pieces;

import com.dimpon.happycube.utils.MatrixUtils;

import java.util.Map;
import java.util.stream.Stream;

/**
 * Class contains all variants of positions of one piece. Call it Positions Set.
 * it is populated with initial piece as matrix from DataLoader, rotate, mirror, exclude duplicates,
 * make basic checks and generate additional matrices.
 * Every matrix has int code with contain order num of the piece and num of valiant.
 */
public interface OnePiece {

    /**
     * get order number of Positions Set
     * @return order number
     */
    int getOrderNumber();

    /**
     * populate OnePiece with data from file
     *
     * @param initialData matrix of init data
     */
    void populate(int[][] initialData);

    /**
     * Safe way to design API. Return Stream instead of List, Set, array.
     * Method name shouldn't have verb "get"
     *
     * @return stream of keys
     */
    Stream<Integer> positionsSetKeys();

    int[][] getPositionByKey(int key);

    Map<MatrixUtils.Edge,Integer> getEdgeMagicNumbers(int key);



}
