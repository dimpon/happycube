package com.dimpon.happycube.write;

import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.dimpon.happycube.utils.MatrixUtils.MATRIX_SIZE;

@Slf4j
public class SolutionWriterImpl implements SolutionWriter {


    private String prefix, path, extension;

    @Builder
    public SolutionWriterImpl(String prefix, String path, String extension) {
        this.prefix = prefix;
        this.path = path;
        this.extension = extension;
    }

    /**
     * Several streams are using writer in prallel
     */
    private AtomicInteger solutionCounter = new AtomicInteger(1);

    /**
     * {@inheritDoc}
     * <p>
     */
    @Override
    @SneakyThrows(IOException.class)
    public void writeSolutionToFile(List<int[][]> unfolded) {
        log.debug("Write to file...");

        Stream<String> stream = generateFileContent(unfolded);

        String filePath = path + "/" + prefix + solutionCounter.getAndIncrement() + "." + extension;

        if (!Files.exists(Paths.get(path))) {
            //Files.createDirectory(Paths.get(path));
            File f = new File(path);
            f.mkdirs();
        }

        if (!Files.exists(Paths.get(filePath))) {
            Files.createFile(Paths.get(filePath));
        }

        Files.write(Paths.get(filePath), (Iterable<String>) stream::iterator);
    }

    private static final String OFFSET = "     ";

    private IntFunction<String> mapper = e -> (e == 0) ? " " : "o";

    /**
     * todo improve readability of code below. May be streams is not the best option here, or i used it in not optimal way
     * <p>
     * The method transforms matrices to Stream<String> in order to write file line-by-line
     * <p>
     * <blockquote><pre>
     * 00000 11111 22222
     * 00000 11111 22222
     * 00000 11111 22222
     * 00000 11111 22222
     * 00000 11111 22222
     * <p>
     *       33333
     *       33333
     *       33333
     *       33333
     *       33333
     * <p>
     *       44444
     *       44444
     *       44444
     *       44444
     *       44444
     * <p>
     *       55555
     *       55555
     *       55555
     *       55555
     *       55555
     * </pre></blockquote>
     */
    private Stream<String> generateFileContent(List<int[][]> unfolded) {

        Stream<String> res = IntStream.range(0, MATRIX_SIZE).mapToObj(value -> {
            return Stream.concat(
                    Stream.concat(
                            Arrays.stream(unfolded.get(0)[value]).mapToObj(mapper),
                            Arrays.stream(unfolded.get(1)[value]).mapToObj(mapper)
                    ), Arrays.stream(unfolded.get(2)[value]).mapToObj(mapper)).
                    collect(Collectors.joining());
        });

        Stream<String> res1 = IntStream.range(0, MATRIX_SIZE).mapToObj(value -> {
            return Arrays.stream(unfolded.get(3)[value]).mapToObj(mapper)
                    .collect(Collectors.joining());
        }).map(i -> OFFSET + i);

        res = Stream.concat(res, res1);

        Stream<String> res2 = IntStream.range(0, MATRIX_SIZE).mapToObj(value -> {
            return Arrays.stream(unfolded.get(4)[value]).mapToObj(mapper)
                    .collect(Collectors.joining());
        }).map(i -> OFFSET + i);

        res = Stream.concat(res, res2);

        Stream<String> res3 = IntStream.range(0, MATRIX_SIZE).mapToObj(value -> {
            return Arrays.stream(unfolded.get(5)[value]).mapToObj(mapper)
                    .collect(Collectors.joining());
        }).map(i -> OFFSET + i);

        res = Stream.concat(res, res3);

        return res;
    }
}
