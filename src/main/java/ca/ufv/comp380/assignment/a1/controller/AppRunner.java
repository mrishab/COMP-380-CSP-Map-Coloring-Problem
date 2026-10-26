package ca.ufv.comp380.assignment.a1.controller;

import ca.ufv.comp380.assignment.a1.algorithm.CspAlgorithm;
import ca.ufv.comp380.assignment.a1.model.AppState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Runner that executes the CSP algorithm step-by-step.
 * Uses the step-delay value to periodically call the algorithm's step function.
 */
public class AppRunner {
    private final AppController controller;
    private final Timeline timeline;
    private double stepDelaySeconds = 1.0;
    private int stepCount = 0;

    public AppRunner(AppController controller) {
        this.controller = controller;
        this.timeline = new Timeline();
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        rebuildTimeline();
    }

    /**
     * Reconfigures the Timeline keyframe according to the current step-delay.
     */
    public synchronized void rebuildTimeline() {
        boolean wasRunning = isRunning();
        timeline.stop();
        timeline.getKeyFrames().clear();

        // Ensure minimum duration (e.g. 20ms) to avoid zero-delay animation lock
        double safeSeconds = Math.max(0.02, stepDelaySeconds);
        Duration duration = Duration.seconds(safeSeconds);

        KeyFrame keyFrame = new KeyFrame(duration, e -> {
            boolean hasMore = stepOnce();
            if (!hasMore) {
                onExecutionFinished();
            }
        });

        timeline.getKeyFrames().add(keyFrame);

        if (wasRunning) {
            timeline.play();
        }
    }

    /**
     * Executes a single step of the algorithm.
     *
     * @return true if more steps remain; false if finished
     */
    public boolean stepOnce() {
        CspAlgorithm algorithm = controller.getAlgorithm();
        if (algorithm == null) {
            System.err.println("No CSP algorithm configured to step.");
            return false;
        }

        if (algorithm.isFinished()) {
            return false;
        }

        stepCount++;
        boolean continueRunning = algorithm.step();
        if (!continueRunning || algorithm.isFinished()) {
            return false;
        }
        return true;
    }

    /**
     * Starts or resumes running the algorithm step-by-step.
     */
    public void start() {
        controller.ensureAlgorithmInitialized();

        if (controller.getAppState() != AppState.RUNNING) {
            controller.setAppState(AppState.RUNNING);
        }
        controller.notifyRunning();

        rebuildTimeline();

        // Perform the first step immediately if starting from unstarted
        if (stepCount == 0) {
            boolean hasMore = stepOnce();
            if (!hasMore) {
                onExecutionFinished();
                return;
            }
        }

        timeline.play();
    }

    /**
     * Pauses execution of the algorithm.
     */
    public void pause() {
        timeline.pause();
        if (controller.getAppState() == AppState.RUNNING) {
            controller.setAppState(AppState.PAUSED);
        }
        controller.notifyPaused();
    }

    /**
     * Handles completion of algorithm execution (solution found or failed).
     */
    public void onExecutionFinished() {
        timeline.stop();
        if (controller.getAppState() == AppState.RUNNING || controller.getAppState() == AppState.PAUSED) {
            controller.setAppState(AppState.STOPPED);
        }

        CspAlgorithm algorithm = controller.getAlgorithm();
        if (algorithm != null && algorithm.isSolved()) {
            controller.notifySolutionFound();
        } else {
            controller.notifyFailed();
        }
    }

    /**
     * Resets the runner, algorithm, and step counter.
     */
    public void reset() {
        timeline.stop();
        stepCount = 0;
        CspAlgorithm algorithm = controller.getAlgorithm();
        if (algorithm != null) {
            algorithm.reset();
        }
        controller.resetColorState();
        controller.setAppState(AppState.UNSTARTED);
        controller.notifyReady();
    }

    public boolean isRunning() {
        return timeline.getStatus() == Timeline.Status.RUNNING;
    }

    public void setStepDelaySeconds(double seconds) {
        this.stepDelaySeconds = seconds;
        rebuildTimeline();
    }
}
