package ca.ufv.comp380.assignment.a1;

import ca.ufv.comp380.assignment.a1.model.AppState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExecutionControlsTest {

    @Test
    public void testAppStateRules() {
        // UNSTARTED state: Run can put into running state; pause/stop/reset disabled
        assertTrue(AppState.UNSTARTED.isRunEnabled());
        assertFalse(AppState.UNSTARTED.isPauseEnabled());
        assertFalse(AppState.UNSTARTED.isStopEnabled());
        assertFalse(AppState.UNSTARTED.isResetEnabled());

        // RUNNING state: Run disabled until pause is pressed; pause & stop enabled; reset enabled
        assertFalse(AppState.RUNNING.isRunEnabled());
        assertTrue(AppState.RUNNING.isPauseEnabled());
        assertTrue(AppState.RUNNING.isStopEnabled());
        assertTrue(AppState.RUNNING.isResetEnabled());

        // PAUSED state: Run re-enabled; pause & stop disabled; reset enabled
        assertTrue(AppState.PAUSED.isRunEnabled());
        assertFalse(AppState.PAUSED.isPauseEnabled());
        assertFalse(AppState.PAUSED.isStopEnabled());
        assertTrue(AppState.PAUSED.isResetEnabled());

        // STOPPED state: Run disabled; pause & stop disabled; reset enabled
        assertFalse(AppState.STOPPED.isRunEnabled());
        assertFalse(AppState.STOPPED.isPauseEnabled());
        assertFalse(AppState.STOPPED.isStopEnabled());
        assertTrue(AppState.STOPPED.isResetEnabled());
    }

    @Test
    public void testStateTransitions() {
        AppState state = AppState.UNSTARTED;

        // Pressing Run -> moves to RUNNING
        assertTrue(state.isRunEnabled());
        state = AppState.RUNNING;
        assertFalse(state.isRunEnabled());
        assertTrue(state.isPauseEnabled());
        assertTrue(state.isStopEnabled());
        assertTrue(state.isResetEnabled());

        // Pressing Pause -> moves to PAUSED
        state = AppState.PAUSED;
        assertTrue(state.isRunEnabled());
        assertFalse(state.isPauseEnabled());
        assertFalse(state.isStopEnabled());
        assertTrue(state.isResetEnabled());

        // Pressing Run again -> moves to RUNNING
        state = AppState.RUNNING;
        assertFalse(state.isRunEnabled());
        assertTrue(state.isPauseEnabled());

        // Pressing Stop -> moves to STOPPED
        state = AppState.STOPPED;
        assertFalse(state.isRunEnabled());
        assertFalse(state.isPauseEnabled());
        assertFalse(state.isStopEnabled());
        assertTrue(state.isResetEnabled());

        // Pressing Reset -> moves to UNSTARTED
        state = AppState.UNSTARTED;
        assertTrue(state.isRunEnabled());
        assertFalse(state.isPauseEnabled());
        assertFalse(state.isStopEnabled());
        assertFalse(state.isResetEnabled());
    }
}
