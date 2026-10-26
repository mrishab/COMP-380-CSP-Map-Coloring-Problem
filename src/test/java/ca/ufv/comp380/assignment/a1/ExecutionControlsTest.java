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

        // Verify isStartEnabled alias matches isRunEnabled
        for (AppState s : AppState.values()) {
            assertEquals(s.isRunEnabled(), s.isStartEnabled(), "isStartEnabled must match isRunEnabled for " + s);
        }
    }

    @Test
    public void testStateTransitions() {
        AppState state = AppState.UNSTARTED;

        // Pressing Start -> moves to RUNNING
        assertTrue(state.isStartEnabled());
        state = AppState.RUNNING;
        assertFalse(state.isStartEnabled());
        assertTrue(state.isPauseEnabled());
        assertTrue(state.isStopEnabled());
        assertTrue(state.isResetEnabled());

        // Pressing Pause -> moves to PAUSED
        state = AppState.PAUSED;
        assertTrue(state.isStartEnabled());
        assertFalse(state.isPauseEnabled());
        assertFalse(state.isStopEnabled());
        assertTrue(state.isResetEnabled());

        // Pressing Start again -> moves to RUNNING
        state = AppState.RUNNING;
        assertFalse(state.isStartEnabled());
        assertTrue(state.isPauseEnabled());

        // When algorithm finishes (solution found or failed) -> moves to STOPPED
        state = AppState.STOPPED;
        assertFalse(state.isStartEnabled());
        assertFalse(state.isPauseEnabled());
        assertFalse(state.isStopEnabled());
        assertTrue(state.isResetEnabled());

        // Pressing Reset -> moves back to UNSTARTED
        state = AppState.UNSTARTED;
        assertTrue(state.isStartEnabled());
        assertFalse(state.isPauseEnabled());
        assertFalse(state.isStopEnabled());
        assertFalse(state.isResetEnabled());
    }
}
