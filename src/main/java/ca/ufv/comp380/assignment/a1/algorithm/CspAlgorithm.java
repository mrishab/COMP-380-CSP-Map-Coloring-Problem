package ca.ufv.comp380.assignment.a1.algorithm;

import ca.ufv.comp380.assignment.a1.controller.AppController;
import ca.ufv.comp380.assignment.a1.model.ColorOption;
import ca.ufv.comp380.assignment.a1.model.CountryConfig;
import java.util.List;

/**
 * Interface for Constraint Satisfaction Problem (CSP) algorithms (such as Map Coloring).
 * Implementations are executed step-by-step by the AppRunner during the RUNNING state.
 */
public interface CspAlgorithm {

    /**
     * Initializes the algorithm with problem parameters before execution begins.
     *
     * @param controller the controller wrapping the map and application
     * @param country the current country configuration containing its provinces and adjacencies
     * @param availableColors the list of available colors selected in the UI
     */
    void initialize(AppController controller, CountryConfig country, List<ColorOption> availableColors);

    /**
     * Executes a single step of the CSP algorithm.
     * In each step, the algorithm can call controller.updateColorState(...) to update
     * the colors of provinces on the map in real time.
     *
     * @return true if more steps remain and the algorithm should continue running;
     *         false if the algorithm has completed (either solved or no solution exists).
     */
    boolean step();

    /**
     * Resets the algorithm's internal data structures and state back to unstarted.
     */
    void reset();

    /**
     * Indicates whether the algorithm has finished all steps.
     *
     * @return true if finished, false otherwise.
     */
    boolean isFinished();

    /**
     * Indicates whether a valid coloring solution was successfully found.
     *
     * @return true if solved, false otherwise.
     */
    default boolean isSolved() {
        return false;
    }
}
