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

/**
 * time series element to track a day, hour, minute etc.
 *
 * @author jay
 *
 */
public class Interval {

    protected double open;
    protected double close;

    protected double changeUp;
    protected double changeDown;

    protected double averageUp;
    protected double averageDown;

    protected double rs;
    protected double rsi;

    public Interval() {}

    public Interval(double open, double close) {
        this.open = open;
        this.close = close;
        final double delta = close - open;
        if (delta > 0) {
            changeUp = delta;
        } else {
            changeDown = 0 - delta;
        }
    }

    /**
     * Helper for unit tests
     *
     * @param changeUp
     * @param changeDown
     * @return
     */
    static Interval make(double changeUp, double changeDown) {
        Interval i = new Interval();
        i.changeUp = changeUp;
        i.changeDown = changeDown;
        return i;
    }

    // normally I'd use lombok here, but it would be the only dependency, so just
    // keep it simple with concrete accessors & mutators

    public boolean hasSmma() {
        return (averageUp > 0) || (averageDown > 0);
    }

    public double getChangeUp() {
        return changeUp;
    }

    public void setChangeUp(double changeUp) {
        this.changeUp = changeUp;
    }

    public double getChangeDown() {
        return changeDown;
    }

    public void setChangeDown(double changeDown) {
        this.changeDown = changeDown;
    }

    public double getAverageUp() {
        return averageUp;
    }

    public void setAverageUp(double averageUp) {
        this.averageUp = averageUp;
    }

    public double getAverageDown() {
        return averageDown;
    }

    public void setAverageDown(double averageDown) {
        this.averageDown = averageDown;
    }

    public double getRsi() {
        return rsi;
    }

    public double getRs() {
        return rs;
    }

    public void setRs(double rs) {
        this.rs = rs;
    }

    public void setRsi(double rsi) {
        this.rsi = rsi;
    }

}
