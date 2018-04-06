package com.dimpon.happycube.pieces;


import com.dimpon.happycube.exception.HappyCubeException;
import com.dimpon.happycube.loader.DataLoader;
import com.dimpon.happycube.pieces.helpers.*;
import com.dimpon.happycube.utils.Matrix3dUtils;
import com.dimpon.happycube.utils.MatrixUtils;
import com.dimpon.happycube.utils.PerfectCubeCheckerUtils;
import com.dimpon.happycube.write.SolutionWriter;
import lombok.Builder;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dimpon.happycube.exception.HappyCubeException.ExceptionsType.*;
import static com.dimpon.happycube.utils.Matrix3dUtils.*;

/**
 * Main class, manage solutions finding.
 * 1. Prepare data (fill pieces with diff variants of position (rot right, left, 180 grad, mirror))
 * 2. Start searching - find cartesian product and then for every combination run permutation finding in parallel.
 */
@Slf4j
public class MainProcessorImpl implements MainProcessor, PermutationChecker, PiecesAwareness {


    private final DataLoader loader;

    private final SolutionWriter writer;

    private boolean findFirstSolutionOnly = false;

    private boolean findUniqueSolutionsOnly = true;

    private volatile boolean continueSearch = true;


    @Singular("position")
    private List<OnePiece> positionsSets;

    private PerfectCubeChecker cubeChecker;

    @Builder
    public MainProcessorImpl(DataLoader loader, SolutionWriter writer, boolean findFirstSolutionOnly, boolean findUniqueSolutionsOnly, List<OnePiece> positionsSets, SolutionUniqueChecker checkSolutionUnique) {
        this.loader = loader;
        this.writer = writer;
        this.findFirstSolutionOnly = findFirstSolutionOnly;
        this.findUniqueSolutionsOnly = findUniqueSolutionsOnly;
        this.positionsSets = positionsSets;
        this.checkSolutionUnique = checkSolutionUnique;

        this.cubeChecker = new PerfectCubeCheckerA(this);
    }


    /**
     * contains logic for checking is solution unique.
     */
    private SolutionUniqueChecker checkSolutionUnique;

    @Override
    public void prepareData() {

        if (Objects.isNull(loader))
            throw new HappyCubeException(LOADER_NOT_FOUND);

        loader.loadData();
        positionsSets.forEach(loader::populate);
    }

    @Override
    public void letsRoll() {

        if (Objects.isNull(writer))
            throw new HappyCubeException(WRITER_NOT_FOUND);


        //here we have 6 "boxes" (one for each piece) with diff positions of planes. Need to find Cartesian Product of planes
        //and for every set from Cartesian Product check all permutations.

        List<int[]> positionsSets = this.positionsSets.stream()
                .map(e -> e.positionsSetKeys()
                        .mapToInt(Integer::intValue)
                        .toArray())
                .collect(Collectors.toList());

        CartesianProductFinder cpFinder = new CartesianProductFinder(positionsSets);
        Stream<int[]> combinations = cpFinder.combinations();

        PermutationsFinder pFinder = new PermutationsFinder(this);

        combinations
                .parallel()
                .forEach(comb -> {
                    if (continueSearch)
                        pFinder.permutations(comb);
                });
    }

    /**
     * Finds matrix by key. Matrix is a result of distinct piece and rotation/mirroring
     *
     * @param key int key
     * @return matrix 5x5
     */
    @Override
    public int[][] getPiecePositionByKey(int key) {
        for (OnePiece set : positionsSets) {
            Optional<Integer> first = set.positionsSetKeys().filter((e) -> e.equals(key)).findFirst();
            if (first.isPresent())
                return set.getPositionByKey(key);
        }
        throw new HappyCubeException(PIECE_POSITION_NOT_FOUND);
    }

    @Override
    public Map<MatrixUtils.Edge, Integer> getEdgeMagicNumbersKey(int key) {
        for (OnePiece set : positionsSets) {
            Optional<Integer> first = set.positionsSetKeys().filter((e) -> e.equals(key)).findFirst();
            if (first.isPresent())
                return set.getEdgeMagicNumbers(key);
        }
        throw new HappyCubeException(PIECE_POSITION_NOT_FOUND);
    }


    /**
     * Checks whether permutation is a perfect cube and writes to file
     *
     * @param keys arrays of keys
     * @return true or false
     */
    @Override
    public boolean checkOnePermutation(int[] keys) {

        boolean isPerfect = cubeChecker.check(keys);

        if (isPerfect) {

            List<int[][]> matrices = Arrays.stream(keys)
                    .mapToObj(this::getPiecePositionByKey)
                    .collect(Collectors.toList());

            if (findUniqueSolutionsOnly) {
                checkUniquenessAndWriteToFile(keys, matrices);
            } else {
                writer.writeSolutionToFile(matrices);
            }
        }

        return isPerfect;
    }

    private void checkUniquenessAndWriteToFile(int[] keys, List<int[][]> matrices) {
        int[] colors = Arrays.stream(keys).map(i -> i / 10).toArray();

        int[][][] coloredCube = foldColoredCube(matrices, colors);

        boolean isUnique = checkSolutionUnique.check(coloredCube);
        if (isUnique) {
            writer.writeSolutionToFile(matrices);
        }

    }


    @Override
    public void perfectPermutationFound() {
        if (findFirstSolutionOnly) {
            continueSearch = false;
        }
    }

    @Override
    public boolean continueSearch() {
        return continueSearch;
    }


}
