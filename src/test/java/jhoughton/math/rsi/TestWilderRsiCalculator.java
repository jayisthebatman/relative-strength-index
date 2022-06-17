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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

/**
 * @author jay
 *
 */
class TestWilderRsiCalculator {

    @Test
    void testTrade() {

        // compute RSI over a two-week duration (or any 14 cycle period, could be hours)
        RsiCalculator calculator = new RsiCalculator(TimeUnit.SECONDS.toMillis(10), 14);

        // simulate 2-3 trades per day for 2 weeks
        List<Interval> list = new ArrayList<>(28);

        // run through our trades
        for (Trade t : trades) {
            Interval i = calculator.trade(t.price, t.timestamp * 1000); // multiplier is just a scale factor, not required
            // if our calculator produced an interval, track it
            if (null != i) {
                list.add(i);
            }
        }

        // verify against precomputed expectations for change up & down
        for (int i = 0; i < 28; i++) {
            Interval expectedValues = expected[i];
            assertEquals(expectedValues.getChangeUp(), list.get(i).getChangeUp(), "change up mismatch for index " + i);
            assertEquals(expectedValues.getChangeDown(), list.get(i).getChangeDown(), "change down mismatch for index " + i);
        }

        // check our last interval's averages, any incorrect previous intervals will
        // disturb these
        Interval last = list.get(list.size() - 1);
        assertEquals(1.42890170951016d, last.getAverageUp());
        assertEquals(0.6866201419158348d, last.getAverageDown());

        // check last interval RSI, again dependent upon having correctly computed ALL
        // previous intervals
        assertEquals(2.0810658212314914d, last.getRs());
        assertEquals(67.5436989009114d, last.getRsi());

    }

    static final Trade[] trades = new Trade[] {
            new Trade(2, 0),
            new Trade(2, 5),
            new Trade(1, 9),  // i1
            new Trade(2, 11),
            new Trade(2, 15),
            new Trade(5, 19), // i2
            new Trade(5, 21),
            new Trade(7, 28), // i3
            new Trade(2, 33),
            new Trade(1, 39), // i4
            new Trade(4, 42),
            new Trade(2, 49), // i5
            new Trade(4, 52),
            new Trade(2, 55), // i6
            new Trade(2, 62),
            new Trade(3, 65), // i7
            new Trade(5, 72),
            new Trade(3, 76), // i8
            new Trade(2, 81),
            new Trade(4, 89), // i9
            new Trade(2, 97),
            new Trade(4, 99), // i10
            new Trade(2, 100),
            new Trade(6, 105), // i11
            new Trade(7, 112),
            new Trade(9, 113), // i12
            new Trade(8, 122),
            new Trade(9, 128), // i13
            new Trade(7, 134),
            new Trade(8, 135), // i14
            new Trade(2, 142),
            new Trade(3, 149), // i15
            new Trade(7, 151),
            new Trade(4, 159), // i16
            new Trade(2, 160),
            new Trade(3, 161), // i17
            new Trade(2, 172),
            new Trade(1, 173), // i18
            new Trade(2, 188),
            new Trade(4, 189), // i19
            new Trade(2, 195),
            new Trade(6, 196), // i20
            new Trade(5, 201),
            new Trade(7, 202), // i21
            new Trade(6, 211),
            new Trade(8, 212), // i22
            new Trade(7, 222),
            new Trade(8, 223), // i23
            new Trade(8, 232),
            new Trade(6, 239), // i24
            new Trade(6, 242),
            new Trade(9, 248), // i25
            new Trade(9, 252),
            new Trade(7, 253), // i26
            new Trade(7, 261),
            new Trade(5, 262), // i27
            new Trade(5, 270),
            new Trade(9, 271),  // i28
            new Trade(1, 288)   // end cap

    };

    static final Interval[] expected = new Interval[] {
            Interval.make(0, 1),
            Interval.make(3, 0),
            Interval.make(2, 0),
            Interval.make(0, 1),
            Interval.make(0, 2),
            Interval.make(0, 2),
            Interval.make(1, 0),
            Interval.make(0, 2),
            Interval.make(2, 0),
            Interval.make(2, 0),
            Interval.make(4, 0),
            Interval.make(2, 0),  // 12
            Interval.make(1, 0),
            Interval.make(1, 0),
            Interval.make(1, 0),
            Interval.make(0, 3),
            Interval.make(1, 0),
            Interval.make(0, 1),
            Interval.make(2, 0),
            Interval.make(4, 0), // 20
            Interval.make(2, 0),
            Interval.make(2, 0),
            Interval.make(1, 0),
            Interval.make(0, 2),
            Interval.make(3, 0), // 25
            Interval.make(0, 2),
            Interval.make(0, 2),
            Interval.make(4, 0)

    };

    /**
     * helper class
     */
    static class Trade {
        double price;
        long timestamp;

        public Trade(double price, long timestamp) {
            super();
            this.price = price;
            this.timestamp = timestamp;
        }
    }

}
