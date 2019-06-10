package com.wings.utils;

/**
 * Purpose: <em>https://en.wikipedia.org/wiki/Block_cipher_modes_of_operation</em>
 *
 * @author NikunjD
 * Created on June 10, 2019
 * Modified on June 10, 2019
 */

public enum CipherModeType {
    /**
     * Cipher Block Chaining Mode
     */
    CBC("CBC"),

    /**
     * Electronic Codebook Mode
     */
    ECB("ECB");

    private String mName;

    private CipherModeType(String name) {
        mName = name;
    }

    /**
     * Get the algorithm name of the enum value.
     *
     * @return The algorithm name
     */
    public String getAlgorithmName() {
        return mName;
    }
}
