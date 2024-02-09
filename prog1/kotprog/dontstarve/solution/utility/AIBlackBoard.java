package prog1.kotprog.dontstarve.solution.utility;

import java.util.ArrayList;
import java.util.List;

public class AIBlackBoard {
    AIState currentState;
    List<GridCell> reservedCells;
    List<Direction> path;
    GridCell targetCell;
    boolean isArmed;
    AIState nextDesiredAction;


    public AIBlackBoard() {
        this.currentState = AIState.WAIT;
        this.reservedCells = new ArrayList<GridCell>();
        path = new ArrayList<Direction>();
    }

    public AIState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(AIState currentState) {
        this.currentState = currentState;
    }

    public List<GridCell> getReservedCells() {
        return reservedCells;
    }

    public void setReservedCells(List<GridCell> reservedCells) {
        this.reservedCells = reservedCells;
    }

    public List<Direction> getPath() {
        return path;
    }

    public void setPath(List<Direction> path) {
        this.path = path;
    }

    public GridCell getTargetCell() {
        return targetCell;
    }

    public void setTargetCell(GridCell targetCell) {
        this.targetCell = targetCell;
    }

    public boolean isArmed() {
        return isArmed;
    }

    public void setArmed(boolean armed) {
        isArmed = armed;
    }

    public AIState getNextDesiredAction() {
        return nextDesiredAction;
    }

    public void setNextDesiredAction(AIState nextDesiredAction) {
        this.nextDesiredAction = nextDesiredAction;
    }

    public enum AIState {
        WAIT, ENGAGE, GATHER_TWIG, ARM
    }
}




