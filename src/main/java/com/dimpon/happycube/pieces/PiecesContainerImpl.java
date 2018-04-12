package com.dimpon.happycube.pieces;

import com.dimpon.happycube.exception.HappyCubeException;
import com.dimpon.happycube.loader.DataLoader;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.dimpon.happycube.exception.HappyCubeException.ExceptionsType.PIECE_POSITION_NOT_FOUND;
import static com.dimpon.happycube.utils.MatrixUtils.*;

/**
 * Class contains all pieces and its variants. Variants are result of rotation and mirroring the piece.
 */
public class PiecesContainerImpl implements PiecesContainer {

    private List<Piece> positionsSets = new ArrayList<>(6);

    @Override
    public void createInitialPieces(DataLoader loader) {
        positionsSets = IntStream.range(0, 6).mapToObj(Piece::new).collect(Collectors.toList());
        loader.loadData();
        positionsSets.forEach(loader::populate);
    }

    @Override
    public int[][] getPiecePositionByKey(int key) {
        for (Piece one : positionsSets) {
            Optional<Integer> first = one.positionsSetKeys().filter((e) -> e.equals(key)).findFirst();
            if (first.isPresent())
                return one.getPositionByKey(key);
        }
        throw new HappyCubeException(PIECE_POSITION_NOT_FOUND);
    }

    @Override
    public Stream<int[]> getPiecesCodesGroupedByOrigins() {
        return this.positionsSets.stream()
                .map(e -> e.positionsSetKeys().mapToInt(Integer::intValue)
                        .toArray());
    }

    /**
     * Class contains all variants of positions of one piece. Call it Positions Set.
     * it is populated with initial piece as matrix from DataLoader, rotate, mirror, exclude duplicates,
     * make basic checks and generate additional matrices.
     * Every matrix has int code with contain order num of the piece and num of valiant.
     */
    public static class Piece {

        private final int order;

        /**
         * Contains the  1 piece's positions.
         * key is int, like 101,102.. first digit is a number of positions set the last is number of position.
         */
        private Map<Integer, int[][]> positions = new HashMap<>();


        public Piece(int order) {
            this.order = order;
        }

        public int getOrderNumber() {
            return order;
        }

        /**
         * Populates and finds all positions. It worse case it is 8.
         *
         * @param matrix initial matrix from file
         */
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


        public Stream<Integer> positionsSetKeys() {
            return positions.keySet().stream();
        }


        public int[][] getPositionByKey(int key) {
            return positions.get(key);
        }


        private void add(int[][] matrix, int positionNumber) {
            Optional<int[][]> o = positions.values().stream().filter(i -> Arrays.deepEquals(i, matrix)).findFirst();
            if (!o.isPresent()) {
                int index = (order + 1) * 10 + positionNumber;
                positions.put(index, matrix);
            }
        }
    }
}
