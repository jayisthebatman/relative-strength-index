/**
 * MIT License
 *
 * Copyright (c) 2022 Jay Houghton
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package jhoughton.math.rsi.average;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author jay
 *
 */
class TestCutlerAverage {

    private static final double[] samples = new double[] {
            2, 4, 6, 8, 12, 14, 16, 18, 20
    };

    private static final double[] expected = new double[] {
            2, 3, 5, 7, 10, 13, 15, 17, 19
    };

    private CutlerAverage cutlerAverage;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        cutlerAverage = new CutlerAverage(2);
    }

    @Test
    void test() {
        for (int i = 0; i < samples.length; i++) {
            double sample = samples[i];
            double expect = expected[i];
            double actual = cutlerAverage.average(sample, 0);
            assertEquals(expect, actual);
        }
    }

}
