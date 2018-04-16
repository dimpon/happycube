package com.dimpon.happycube.pieces;

import com.dimpon.happycube.exception.HappyCubeException;
import com.dimpon.happycube.pieces.helpers.*;
import com.dimpon.happycube.write.SolutionWriter;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.dimpon.happycube.exception.HappyCubeException.ExceptionsType.WRITER_NOT_FOUND;

@Slf4j
public class MainProcessorSets implements MainProcessor {


    private PiecesContainer<int[][][]> container;

    private final SolutionWriter writer;


    @Builder
    public MainProcessorSets(PiecesContainer<int[][][]> container, SolutionWriter writer) {
        this.container = container;
        this.writer = writer;
    }

    @Override
    public void letsRoll() {

        if (Objects.isNull(writer))
            throw new HappyCubeException(WRITER_NOT_FOUND);

        PerfectCubeChecker<List<int[][]>> checker = PerfectCubeCheckerSets.builder()
                .container(container)
                .writer(writer)
                .build();


        List<int[][][]> variantsSets = container.getObjectsForCombinations().collect(Collectors.toList());

        final ExecutorService executor = Executors.newFixedThreadPool(2);

        CartesianProductFinderAdvanced cpFinder = new CartesianProductFinderAdvanced(variantsSets);
        cpFinder.combinationsWithoutSaving(ints -> {
            executor.submit(() -> {
                log.debug("checker:" + Arrays.deepToString(ints));
                checker.checkAndTellNeedToSearchFurther(Arrays.stream(ints).collect(Collectors.toList()));
            });

        });


    }
}
