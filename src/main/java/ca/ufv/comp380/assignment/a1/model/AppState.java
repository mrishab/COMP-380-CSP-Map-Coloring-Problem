package ca.ufv.comp380.assignment.a1.model;

/**
 * Application execution states for map coloring CSP algorithm execution.
 * Governs the availability of Run, Pause, Stop, and Reset controls.
 */
public enum AppState {
    /**
     * Initial state where the execution has not yet started.
     */
    UNSTARTED,

    /**
     * State where the map coloring execution is actively running.
     */
    RUNNING,

    /**
     * State where the map coloring execution has been paused.
     */
    PAUSED,

    /**
     * State where the map coloring execution has been stopped.
     */
    STOPPED;

    /**
     * Start / Run is enabled in UNSTARTED and PAUSED states.
     * When RUNNING or STOPPED, Start / Run is disabled.
     */
    public boolean isRunEnabled() {
        return this == UNSTARTED || this == PAUSED;
    }

    /**
     * Alias for isRunEnabled().
     */
    public boolean isStartEnabled() {
        return isRunEnabled();
    }

    /**
     * Pause is disabled unless the application is actively running.
     */
    public boolean isPauseEnabled() {
        return this == RUNNING;
    }

    /**
     * Stop is disabled unless the application is actively running.
     */
    public boolean isStopEnabled() {
        return this == RUNNING;
    }

    /**
     * Reset is disabled until the application is not in the unstarted state.
     */
    public boolean isResetEnabled() {
        return this != UNSTARTED;
    }
}
