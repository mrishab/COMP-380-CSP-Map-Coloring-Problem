package ca.ufv.comp380.assignment.a1;

import ca.ufv.comp380.assignment.a1.model.AppState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExecutionControlsTest {

    @Test
    public void testAppStateRules() {
        // UNSTARTED state: Start can put into running state; pause/reset disabled
        assertTrue(AppState.UNSTARTED.isStartEnabled());
        assertFalse(AppState.UNSTARTED.isPauseEnabled());
        assertFalse(AppState.UNSTARTED.isResetEnabled());

        // RUNNING state: Start disabled; pause & reset enabled
        assertFalse(AppState.RUNNING.isStartEnabled());
        assertTrue(AppState.RUNNING.isPauseEnabled());
        assertTrue(AppState.RUNNING.isResetEnabled());

        // PAUSED state: Start re-enabled; pause disabled; reset enabled
        assertTrue(AppState.PAUSED.isStartEnabled());
        assertFalse(AppState.PAUSED.isPauseEnabled());
        assertTrue(AppState.PAUSED.isResetEnabled());

        // STOPPED state: Start disabled; pause disabled; reset enabled
        assertFalse(AppState.STOPPED.isStartEnabled());
        assertFalse(AppState.STOPPED.isPauseEnabled());
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
        assertTrue(state.isResetEnabled());

        // Pressing Pause -> moves to PAUSED
        state = AppState.PAUSED;
        assertTrue(state.isStartEnabled());
        assertFalse(state.isPauseEnabled());
        assertTrue(state.isResetEnabled());

        // Pressing Start again -> moves to RUNNING
        state = AppState.RUNNING;
        assertFalse(state.isStartEnabled());
        assertTrue(state.isPauseEnabled());

        // When algorithm finishes (solution found or failed) -> moves to STOPPED
        state = AppState.STOPPED;
        assertFalse(state.isStartEnabled());
        assertFalse(state.isPauseEnabled());
        assertTrue(state.isResetEnabled());

        // Pressing Reset -> moves back to UNSTARTED
        state = AppState.UNSTARTED;
        assertTrue(state.isStartEnabled());
        assertFalse(state.isPauseEnabled());
        assertFalse(state.isResetEnabled());
    }
}
