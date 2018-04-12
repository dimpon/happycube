package com.dimpon.happycube.pieces;


import com.dimpon.happycube.exception.HappyCubeException;
import com.dimpon.happycube.pieces.helpers.*;
import com.dimpon.happycube.write.SolutionWriter;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dimpon.happycube.exception.HappyCubeException.ExceptionsType.*;

/**
 * Main class, manage solutions finding.
 * Start searching - find cartesian product and then for every combination run permutation finding in parallel.
 */
@Slf4j
public class MainSolutionsProcessor implements MainProcessor {


    private PiecesContainer container;

    private final SolutionWriter writer;

    private PerfectCubeChecker cubeChecker;

    private final boolean findFirstSolutionOnly;

    @Builder
    public MainSolutionsProcessor(PiecesContainer container, SolutionWriter writer, boolean findFirstSolutionOnly, boolean findUniqueSolutionsOnly, SolutionUniqueChecker checkSolutionUnique) {
        this.container = container;
        this.writer = writer;
        this.findFirstSolutionOnly = findFirstSolutionOnly;

        this.cubeChecker = PerfectCubeCheckerImpl.builder()
                .container(container)
                .findFirstSolutionOnly(findFirstSolutionOnly)
                .findUniqueSolutionsOnly(findUniqueSolutionsOnly)
                .writer(writer)
                .uniqueChecker(checkSolutionUnique)
                .build();
    }


    @Override
    public void letsRoll() {

        if (Objects.isNull(writer))
            throw new HappyCubeException(WRITER_NOT_FOUND);


        //here we have 6 "boxes" (one for each piece) with diff positions of planes. Need to find Cartesian Product of planes
        //and for every set from Cartesian Product checkAndTellNeedToSearchFurther all permutations.

        List<int[]> positionsSets = container.getPiecesCodesGroupedByOrigins().collect(Collectors.toList());

        CartesianProductFinder cpFinder = new CartesianProductFinder(positionsSets);
        Stream<int[]> combinations = cpFinder.combinations();

        PermutationsFinder pFinder = new PermutationsFinder(cubeChecker);

        //with findFirstSolutionOnly parallel execution creates racing and few results can be stored.
        //the introducing sync or locking is not justified
        if (findFirstSolutionOnly) {
            combinations.forEach(pFinder::permutations);
        } else {
            combinations.parallel().forEach(pFinder::permutations);
        }


    }

}
