package com.dimpon.happycube.pieces;

import com.dimpon.happycube.exception.HappyCubeException;
import com.dimpon.happycube.loader.DataLoader;
import com.dimpon.happycube.pieces.helpers.CartesianProductFinder;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.dimpon.happycube.exception.HappyCubeException.ExceptionsType.PIECE_POSITION_NOT_FOUND;
import static com.dimpon.happycube.utils.MatrixUtils.*;

public class PotentialSetsPiecesContainerImpl implements PiecesContainer {

    private List<PotentialPiece> positionsSets = new ArrayList<>(6);


    @Override
    public void createInitialPieces(DataLoader loader) {

        positionsSets.add(new PotentialPiece(0));
        positionsSets.add(new PotentialPiece(1));
        positionsSets.add(new PotentialPiece(2));
        positionsSets.add(new PotentialPiece(3));
        positionsSets.add(new PotentialPiece(4));
        positionsSets.add(new PotentialPiece(5));

    }



    @Override
    public int[][] getPiecePositionByKey(int key) {
        for (PotentialPiece one : positionsSets) {
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

    @Slf4j
    public static class PotentialPiece {

        private final int order;

        private int variantCounter = 0;

        /**
         * Contains the  1 piece's variants.
         * key is int, like 1000001,1000002.. first digit is a number of positions set the last is number of variant.
         */
        private Map<Integer, int[][]> positions;


        public PotentialPiece(int order) {
            this.order = order;
            findAllVariants();
        }

        public int getOrderNumber() {
            return order;
        }


        public Stream<Integer> positionsSetKeys() {
            return positions.keySet().stream();
        }


        public int[][] getPositionByKey(int key) {
            return positions.get(key);
        }



        /**
         * This method buiul dall possible variants for 1 piece, the wrong variants, like a hanging corner or flat edge are excluded
         */
        private void findAllVariants() {
            positions =  new HashMap<>();

            List<int[]> input = IntStream.range(0, 16).mapToObj(i -> new int[]{0, 1}).collect(Collectors.toList());
            CartesianProductFinder finder = new CartesianProductFinder(input);
            List<int[]> combinations = finder.combinations().collect(Collectors.toList());
            combinations.forEach(this::buildPieceVariant);

            positions = Collections.unmodifiableMap(positions);
        }


        /**
         * fills the area around 3x3 with elements from input array and save.
         *
         * @param edgesLine input elements for edges
         */
        private void buildPieceVariant(int[] edgesLine) {

            int[][] variant = new int[][]{
                    {0, 0, 0, 0, 0},
                    {0, 1, 1, 1, 0},
                    {0, 1, 1, 1, 0},
                    {0, 1, 1, 1, 0},
                    {0, 0, 0, 0, 0}
            };

            for (int i = 0; i < edgesLine.length; i++) {

                if (i < 5)
                    variant[0][i] = edgesLine[i];

                if (i >= 5 && i < 8)
                    variant[i - 4][MATRIX_SIZE - 1] = edgesLine[i];

                if (i >= 8 && i < 13)
                    variant[MATRIX_SIZE - 1][i - 8] = edgesLine[i];

                if (i >= 13 && i < 16)
                    variant[i - 11][0] = edgesLine[i];

            }

            if (isGeneratedPieceOk(variant)) {
                variantCounter++;
                int index = (order + 1) * 1000000 + variantCounter;
                positions.put(index, variant);


                /*IntStream.range(0, MATRIX_SIZE).mapToObj(value -> {
                    return Arrays.stream(variant[value]).mapToObj(e -> (e == 0) ? " " : "x")
                            .collect(Collectors.joining());
                }).forEach(log::debug);

                log.debug(""+index);*/

            }


        }



    }
}
