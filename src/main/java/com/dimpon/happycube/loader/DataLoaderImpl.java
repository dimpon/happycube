package com.dimpon.happycube.loader;

import com.dimpon.happycube.exception.HappyCubeException;
import com.dimpon.happycube.pieces.OnePiece;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.MessageFormat;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.dimpon.happycube.utils.MatrixUtils.*;


/**
 * Loads the files content to matrices. Empty spaces(32) are 0, others symbols are 1.
 * If file contains more then 5 lines and more 5 symbols per line, the extra symbols are ignored.
 * <p>
 * todo might be not a good idea to "hardcode" everywhere the matrix size 5x5, but we collect cube, though?
 */
@Slf4j
public class DataLoaderImpl implements DataLoader {

    private String prefix, path, extension;

    private Map<Integer, int[][]> initialData = new HashMap<>();

    private int piecesCounter = 0;

    private final static String pattern = "^(.*?){0}(.*?)[.]({1})$";


    private BiPredicate<Path, BasicFileAttributes> matcherForFilesNames = (path, attr) -> {
        Pattern pattern = Pattern.compile(MessageFormat.format(DataLoaderImpl.pattern, prefix, extension));
        return pattern.matcher(String.valueOf(path)).matches() && attr.isRegularFile();
    };


    @Builder
    public DataLoaderImpl(String prefix, String path, String extension) {
        this.prefix = prefix;
        this.path = path;
        this.extension = extension;
    }

    @Override
    public void populate(OnePiece piece) {
        log.info("Populate piece, num:" + piece.getOrderNumber());
        piece.populate(initialData.get(piece.getOrderNumber()));
    }

    @SneakyThrows(IOException.class)
    public void loadData() {
        log.info("Load initial data...");
        try (Stream<Path> stream = Files.find(Paths.get(path), 1, matcherForFilesNames)) {
            stream
                    .sorted(Comparator.naturalOrder())
                    .forEach(this::readOneFile);
        }
    }

    /**
     * Reads file content to matrix 5x5
     *
     * @param filename path to file
     */
    @SneakyThrows(IOException.class)
    private void readOneFile(Path filename) {

        try (Stream<String> lines = Files.lines(filename)) {

            int[][] matrix = lines
                    .limit(MATRIX_SIZE)
                    .map(l -> l.chars().map((e) -> e != 32 ? 1 : 0).toArray())
                    .map(e -> Arrays.copyOf(e, MATRIX_SIZE))
                    .toArray(int[][]::new);


            if (!isCentralPartOk(matrix) || !isCornersOk(matrix)) {
                throw new HappyCubeException(HappyCubeException.ExceptionsType.WRONG_INIT_DATA);
            }

            log.info(Arrays.deepToString(matrix));
            initialData.put(piecesCounter, matrix);
            piecesCounter++;
        }
    }


}
