package com.dimpon.happycube.pieces;


import java.util.*;
import java.util.stream.Stream;

import static com.dimpon.happycube.utils.MatrixUtils.*;



public class OnePieceImpl implements OnePiece {

    private final int order;

    /**
     * Contains the  1 piece's positions.
     * key is int, like 101,102.. first digit is a number of positions set the last is number of position.
     */
    private Map<Integer, int[][]> positions = new HashMap<>();

    /**
     * The map contains the int representation of edges of position of piece.
     * It is used for fast calculation whether the edges intersect correctly, without gaps and overlaps
     */
    private Map<Integer,Map<Edge,Integer>> magicNumbersOfEdges = new HashMap<>();


    public OnePieceImpl(int order) {
        this.order = order;
    }

    @Override
    public int getOrderNumber() {
        return order;
    }

    /**
     * Populates and finds all positions. It worse case it is 8.
     *
     * @param matrix initial matrix from file
     */
    @Override
    public void populate(int[][] matrix) {

        add(matrix, 0);

        add(rotateToRight(matrix), 1);

        add(rotateToLeft(matrix), 2);

        add(rotate2times(matrix), 3);

        int[][] reflection = mirror(matrix);

        add(reflection, 4);

        add(rotateToRight(reflection), 5);

        add(rotateToLeft(reflection), 6);

        add(rotate2times(reflection), 7);

    }

    @Override
    public Stream<Integer> positionsSetKeys() {
        return positions.keySet().stream();
    }

    @Override
    public int[][] getPositionByKey(int key) {
        return positions.get(key);
    }


    private void add(int[][] matrix, int positionNumber) {
        Optional<int[][]> o = positions.values().stream().filter(i -> Arrays.deepEquals(i, matrix)).findFirst();
        if (!o.isPresent()) {
            positions.put((order + 1) * 10 + positionNumber, matrix);
        }

    }


}
