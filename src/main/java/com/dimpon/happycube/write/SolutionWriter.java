package com.dimpon.happycube.write;

import java.util.List;

public interface SolutionWriter {

    /**
     * Writes matrices to file if following format:
     * <blockquote><pre>
     * 0 1 2
     *   3
     *   4
     *   5
     * </pre></blockquote>
     *
     * @param unfolded list of 6 matrices, 5x5
     */
    void writeSolutionToFile(List<int[][]> unfolded);
}
