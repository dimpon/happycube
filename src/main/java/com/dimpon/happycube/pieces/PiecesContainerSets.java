package com.dimpon.happycube.pieces;

import com.dimpon.happycube.exception.HappyCubeException;
import com.dimpon.happycube.loader.DataLoader;
import com.dimpon.happycube.pieces.helpers.CartesianProductFinder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.dimpon.happycube.exception.HappyCubeException.ExceptionsType.PIECE_POSITION_NOT_FOUND;
import static com.dimpon.happycube.exception.HappyCubeException.ExceptionsType.WRONG_EDGE_ARRAY;
import static com.dimpon.happycube.utils.MatrixUtils.*;

public class PiecesContainerSets implements PiecesContainer<int[][][]> {

    private List<PotentialPiece> variantsSet = new ArrayList<>(6);


    @Override
    public void createInitialPieces(DataLoader loader) {

        variantsSet.add(new PotentialPiece(0));
        variantsSet.add(new PotentialPiece(1));
        variantsSet.add(new PotentialPiece(2));
        variantsSet.add(new PotentialPiece(3));
        variantsSet.add(new PotentialPiece(4));
        variantsSet.add(new PotentialPiece(5));

    }


    @Override
    public int[][] getPiecePositionByKey(int key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<int[][][]> getObjectsForCombinations() {
        return this.variantsSet.stream()
                .map(PotentialPiece::getAllVariants);
    }

    @Slf4j
    public static class PotentialPiece {

        private final int order;

        private int variantCounter = 0;

        /**
         * Contains the  1 piece's variants.
         * no key, it stores int[][] which are variants of 1 piece
         */
        private List<int[][]> positions;


        PotentialPiece(int order) {
            this.order = order;
            findAllVariants();
        }

        int getOrderNumber() {
            return order;
        }

        List<int[][]> getPositions() {
            return positions;
        }

        private int[][][] getAllVariants() {
            @SuppressWarnings("unchecked")
            int[][][] out = (int[][][]) Array.newInstance(Integer.TYPE, positions.size(), MATRIX_SIZE, MATRIX_SIZE);

            for (int i = 0; i < positions.size(); i++) {
                out[i] = positions.get(i);
            }
            return out;
        }


        /**
         * This method buiul dall possible variants for 1 piece, the wrong variants, like a hanging corner or flat edge are excluded
         */
        private void findAllVariants() {
            positions = new ArrayList<>();

            List<int[]> input = IntStream.range(0, 16).mapToObj(i -> new int[]{0, 1}).collect(Collectors.toList());
            CartesianProductFinder finder = new CartesianProductFinder(input);
            List<int[]> combinations = finder.combinations().collect(Collectors.toList());
            combinations.forEach(this::addPieceVariant);

            positions = Collections.unmodifiableList(positions);
        }


        /**
         * fills the area around 3x3 with elements from input array and save.
         *
         * @param edgesLine input elements for edges
         */
        private void addPieceVariant(int[] edgesLine) {

            int[][] variant = buildPieceVariant(edgesLine);

            if (isGeneratedPieceOk(variant)) {
                variantCounter++;
                positions.add(variant);


                /*IntStream.range(0, MATRIX_SIZE).mapToObj(value -> {
                    return Arrays.stream(variant[value]).mapToObj(e -> (e == 0) ? " " : "x")
                            .collect(Collectors.joining());
                }).forEach(log::debug);

                if(Arrays.deepEquals(romb,variant)){
                    System.exit(1);
                }*/




            }
        }

        /**
         * The edgesLine "wraps" 3x3 like a snake clockwise
         */
        int[][] buildPieceVariant(int[] edgesLine) {

            if (edgesLine.length != 16) {
                throw new HappyCubeException(WRONG_EDGE_ARRAY);
            }

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
                    variant[MATRIX_SIZE - 1][MATRIX_SIZE - 1 - (i - 8)] = edgesLine[i];

                if (i >= 13 && i < 16)
                    variant[MATRIX_SIZE - 1 - (i - 12)][0] = edgesLine[i];

            }

            return variant;
        }


    }
}
