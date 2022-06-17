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
package jhoughton.math.rsi;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import jhoughton.math.rsi.average.WilderAverage;

/**
 * Calculator for Relative Strength Index (RSI)
 *
 *
 * @see https://en.wikipedia.org/wiki/Relative_strength_index
 * @see https://books.google.com/books?vid=ISBN0894590278
 * @author jay
 *
 */
public class RsiCalculator {

    // static final Log log = LogFactory.getLog(RsiCalculator.class);

    private final long period;
    private final int length;

    private final AverageCalculator averager;

    // --- operation

    private final Queue<Interval> intervals = new LinkedList<>();

    // --- current interval
    private double open;
    private long begin = -1;
    private long expectedEnd;

    private int n = 0; // intervalCounter
    private double lastPrice;
    private Interval lastInterval;

    public RsiCalculator(long period, int length) {
        this(period, length, new WilderAverage(length));
    }

    public RsiCalculator(long period, int length, AverageCalculator averager) {
        this.period = period;
        this.length = length;
        this.averager = averager;
    }

    public Interval trade(double price, long timestamp) {

        Interval i = null;

        if (begin == -1) {
            open = price;
            begin = timestamp;
            expectedEnd = (timestamp + period) - 1;
        } else if (timestamp >= expectedEnd) {
            i = new Interval(open, lastPrice);
            intervals.add(i);
            n++;

            if (n > length) {
                // we can now calculate
                if (!lastInterval.hasSmma()) {
                    // to kickstart this series, we'll use a SMA as the initial SMMA
                    double sumUp = 0;
                    double sumDown = 0;
                    // use SMA for first round calc
                    Iterator<Interval> examine = intervals.iterator();
                    for (int j = 0; j < length; j++) {
                        Interval interval = examine.next();
                        sumUp += interval.changeUp;
                        sumDown += interval.changeDown;
                    }
                    lastInterval.setAverageUp(sumUp / length);
                    lastInterval.setAverageDown(sumDown / length);

                    // log.debug("interval #" + (n - 1) + ":\n" + lastInterval);
                }

                double lastSmmaUp = lastInterval.getAverageUp();
                double lastSmmaDown = lastInterval.getAverageDown();

                double smmaUp = averager.average(i.getChangeUp(), lastSmmaUp);
                double smmaDown = averager.average(i.getChangeDown(), lastSmmaDown);

                i.setAverageUp(smmaUp);
                i.setAverageDown(smmaDown);

                i.setRs(i.getAverageUp() / i.getAverageDown());
                i.setRsi(100 - (100 / (1 + i.getRs())));

                intervals.remove();
            }

            // log.debug("interval #" + n + " [" + begin + "," + expectedEnd + "]
            // queue.size=" + intervals.size() + ":\n" + i);

            open = price;
            begin = expectedEnd + 1;
            expectedEnd = (expectedEnd + period);
            lastInterval = i;
        }

        lastPrice = price;
        return i;
    }

    /**
     * For unit testing
     *
     * @return collection-view of the interval queue
     */
    public Collection<Interval> intervals() {
        return intervals;
    }

}
