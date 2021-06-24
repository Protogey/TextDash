package edu.utep.cs.cs4330.textdash;
//Cesar Lopez
//80503346
//CS4330
//Dr. Cheon - Mobile Development

public class Timer {

    /** The start time of this timer in milliseconds. */
    private long startTime;

    /** Create a new timer. Initially it isn't running. */
    public Timer() {
        startTime = 0;
    }

    public Timer(long startTime) {
        this.startTime = startTime;
    }

    /**
     * Start this timer. If invoked when a timer is already running,
     * this method will restart it.
     */
    public void start() {
        startTime = System.currentTimeMillis();
    }

    /** Stop this timer. */
    public void stop() {
        startTime = 0;
    }

    /**
     * Is this timer running?
     *
     * @return true if this timer is running; false otherwise.
     */
    public boolean isRunning() {
        return startTime != 0;
    }

    public long getStartTime() {
        return startTime;
    }

    /**
     * Return the elapsed time since this timer has started; return 0 if this
     * timer is not running. The elapsed time is given in milliseconds.
     *
     * @return elapsed time in milliseconds; 0 if this timer is not running.
     */
    public long elapsedTime() {
        return isRunning() ? System.currentTimeMillis() - startTime : 0;
    }
}
