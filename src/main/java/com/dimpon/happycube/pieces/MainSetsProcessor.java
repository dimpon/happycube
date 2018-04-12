package com.dimpon.happycube.pieces;

import com.dimpon.happycube.exception.HappyCubeException;
import com.dimpon.happycube.pieces.helpers.CartesianProductFinder;
import com.dimpon.happycube.pieces.helpers.PerfectCubeChecker;
import com.dimpon.happycube.pieces.helpers.PerfectCubeCheckerImpl;
import com.dimpon.happycube.pieces.helpers.SolutionUniqueCheckerImpl;
import com.dimpon.happycube.write.SolutionWriter;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.dimpon.happycube.exception.HappyCubeException.ExceptionsType.WRITER_NOT_FOUND;

@Slf4j
public class MainSetsProcessor implements MainProcessor {


    private PiecesContainer container;

    private final SolutionWriter writer;


    @Builder
    public MainSetsProcessor(PiecesContainer container, SolutionWriter writer) {
        this.container = container;
        this.writer = writer;
        //this.cubeChecker = cubeChecker;
    }

    @Override
    public void letsRoll() {

        if (Objects.isNull(writer))
            throw new HappyCubeException(WRITER_NOT_FOUND);

        PerfectCubeChecker checker = PerfectCubeCheckerImpl.builder()
                .container(container)
                .findFirstSolutionOnly(false)
                .findUniqueSolutionsOnly(true)
                .writer(writer)
                .uniqueChecker(new SolutionUniqueCheckerImpl())
                .build();


        List<int[]> positionsSets = container.getPiecesCodesGroupedByOrigins().collect(Collectors.toList());

        final ExecutorService executor = Executors.newFixedThreadPool(3);

        CartesianProductFinder cpFinder = new CartesianProductFinder(positionsSets);
        cpFinder.combinationsWithoutSaving(ints -> {
            executor.submit(() -> {
                checker.checkAndTellNeedToSearchFurther(ints);
            });

        });


    }
}
