package com.dimpon.happycube.pieces;

/**
 * Interface for checking permutation and managing the further flow of checks
 */
public interface PermutationChecker {

    /**
     * Check one permutation
     *
     * @param keys arrays of keys
     * @return true or false
     */
    boolean checkOnePermutation(int[] keys);

    /**
     * got signal that perfect cube is found
     */
    void perfectPermutationFound();

    /**
     * notify that search must continue
     *
     * @return true or false
     */
    boolean continueSearch();
}
