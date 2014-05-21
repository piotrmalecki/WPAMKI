package com.example.echoprint;

/**
 * A wrapper around the echoprint's library codegen feature, which generates a
 * code (fingerprint) to a given sampled audio data.
 * 
 * This class is based on the one described on masl's post
 * http://masl.cis.gvsu.edu/2012/01/25/android-echoprint/
 * 
 */

public class Codegen {
    static {
        System.loadLibrary("echoprint-jni");
    }

    native String codegen(float data[], int numSamples);

    /**
     * Invoke the echoprint native library and generate the fingerprint code.<br>
     * Echoprint REQUIRES PCM encoded audio with the following parameters:<br>
     * Frequency: 11025 khz<br>
     * Data: MONO - PCM enconded float array
     * 
     * @param data
     *            PCM encoded data as floats [-1, 1]
     * @param numSamples
     *            number of PCM samples at 11025 KHz
     * @return The generated fingerprint as a compressed - base64 string.
     */
    public String generate(float data[], int numSamples) {
        return codegen(data, numSamples);
    }

    /**
     * Invoke the echoprint native library and generate the fingerprint code.
     * Since echoprint requires the audio data to be an array of floats in the
     * range [-1, 1] this method will normalize the data array transforming the
     * 16 bit signed shorts into floats.
     * 
     * @param data
     *            PCM encoded data as shorts
     * @param numSamples
     *            number of PCM samples at 11025 KHz
     * @return The generated fingerprint as a compressed - base64 string.
     */
    public String generate(final short data[], final int numSamples) {
        float normalizedData[] = new float[numSamples];

        for(int i = 0; i < numSamples; i++) {
            // codegen expects the sample data in the range [-1 .. 1]
            // Android records audio samples as 16 bit shorts. Here we
            // normalize Android's data to pass it to codegen
            normalizedData[i] = data[i] / (float)Short.MAX_VALUE;
        }

        return this.codegen(normalizedData, numSamples);
    }
}
