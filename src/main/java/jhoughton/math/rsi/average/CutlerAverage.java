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
 * Cutler's approach uses the simple moving average (SMA) instead of Wilder's
 * exponential. This calculation produces different RSI values but is not
 * sensitive to starting conditions on the time series we're calculating on.
 * Because this is not exponential the oscillator can adapt faster to quickly
 * changing price movements.
 *
 * @author jay
 *
 */
public class CutlerAverage implements AverageCalculator {
    private final double[] ring;
    private double sum = 0L;
    private int fill;
    private int position;

    public CutlerAverage(int size) {
        ring = new double[size];
    }

    @Override
    public double average(double point, double lastChange) {
        if (fill == ring.length) {
            sum -= ring[position];
        } else {
            fill++;
        }

        sum += point;
        ring[position++] = point;

        if (position == ring.length) {
            position = 0;
        }
        return sum / fill;
    }

}
