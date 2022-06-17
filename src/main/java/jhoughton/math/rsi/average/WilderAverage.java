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

import jhoughton.math.rsi.AverageCalculator;

/**
 * Wilder based his calculation on an exponential smoothed moving average
 * (SMMA). This approach can create variations in the RSI depending upon where
 * you start in a time series cycle.
 *
 * @author jay
 *
 */
public class WilderAverage implements AverageCalculator {

    protected final int length;
    protected final int lengthMinusOne; // pre-compute

    public WilderAverage(int length) {
        this.length = length;
        lengthMinusOne = length - 1;
    }

    @Override
    public double average(double change, double lastChange) {
        return (change + ((lastChange * lengthMinusOne))) / length;
    }

}
