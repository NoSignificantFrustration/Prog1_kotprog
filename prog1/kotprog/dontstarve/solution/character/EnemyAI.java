package prog1.kotprog.dontstarve.solution.character;

import prog1.kotprog.dontstarve.solution.GameManager;
import prog1.kotprog.dontstarve.solution.character.actions.*;
import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;
import prog1.kotprog.dontstarve.solution.level.Field;
import prog1.kotprog.dontstarve.solution.utility.*;

import java.util.*;

public class EnemyAI {
    private final Pathfinder pathfinder;
    private final Set<GridCell> reservedSet;
    private final Map<BaseCharacter, AIBlackBoard> blackBoardMap;
    private final Set<Field.TileType> unavailableSet;
    private BaseCharacter currentCharacter;
    private AIBlackBoard blackBoard;


    public EnemyAI(Pathfinder pathfinder) {
        this.pathfinder = pathfinder;
        reservedSet = new HashSet<GridCell>();
        blackBoardMap = new HashMap<BaseCharacter, AIBlackBoard>();
        unavailableSet = new HashSet<Field.TileType>();

    }

    public Action makeDecision(BaseCharacter character) {

        currentCharacter = character;
        blackBoard = blackBoardMap.get(character);

        Position myPos = character.getCurrentPosition();
        Position myWholePos = myPos.getNearestWholePosition();

        BaseCharacter player = GameManager.getInstance().getPlayer();

        Position playerPos = player.getCurrentPosition();
        Position playerWholePos = playerPos.getNearestWholePosition();
/*
        if (blackBoard.getCurrentState() != AIBlackBoard.AIState.ENGAGE && myPos.getDistance(playerPos) < 4f && ){
            blackBoard.setCurrentState(AIBlackBoard.AIState.ENGAGE);
            System.out.println(currentCharacter.getName() + " too close, engaging!");
        }
*/

        if (blackBoard.getCurrentState() == AIBlackBoard.AIState.ARM) {
            int weaponIndex = findItemIndex(ItemType.AXE);
            if (weaponIndex >= 0) {
                blackBoard.setCurrentState(AIBlackBoard.AIState.ENGAGE);
                blackBoard.setArmed(true);
                return new ActionEquip(weaponIndex);
            }
            blackBoard.setCurrentState(AIBlackBoard.AIState.GATHER_TWIG);
        }


        if (!blackBoard.isArmed() && blackBoard.getCurrentState() == AIBlackBoard.AIState.WAIT) {
            blackBoard.setCurrentState(AIBlackBoard.AIState.GATHER_TWIG);
            if (!plotPathToResource(Field.TileType.Twig)) {
                blackBoard.setCurrentState(AIBlackBoard.AIState.ENGAGE);
            }


        }

        if (blackBoard.getCurrentState() == AIBlackBoard.AIState.GATHER_TWIG) {
            GridCell cell = blackBoard.getTargetCell();
            if (!GameManager.getInstance().getField(cell.getxPos(), cell.getyPos()).hasTwig()) {
                if (currentCharacter.getInventory().countItem(ItemType.TWIG) >= 3) {

                    blackBoard.setCurrentState(AIBlackBoard.AIState.ARM);
                    System.out.println(currentCharacter.getName() + " equip axe");
                    return new ActionCraft(ItemType.AXE);
                }
                if (!plotPathToResource(Field.TileType.Twig)) {
                    blackBoard.setCurrentState(AIBlackBoard.AIState.ENGAGE);
                }


            }

            if (blackBoard.getCurrentState() == AIBlackBoard.AIState.GATHER_TWIG) {
                List<Direction> path = blackBoard.getPath();

                if (path.size() == 0) {
                    return new ActionInteract();
                } else {
                    Direction moveDir = path.get(path.size() - 1);
                    path.remove(path.size() - 1);
                    return new ActionStep(moveDir);
                }
            }
        }


        if (blackBoard.getCurrentState() != AIBlackBoard.AIState.ENGAGE) {
            blackBoard.setCurrentState(AIBlackBoard.AIState.ENGAGE);
        }


        if (blackBoard.getTargetCell() == null || blackBoard.getTargetCell().getxPos() != (int) playerWholePos.getX() || blackBoard.getTargetCell().getyPos() != (int) playerWholePos.getY()) {
            plotPath((int) playerWholePos.getX(), (int) playerWholePos.getY());
            System.out.println(currentCharacter.getName() + " new path requested " + blackBoard.getTargetCell().getxPos() + " " + blackBoard.getTargetCell().getyPos());
            //System.out.println("MyPos: " + (int)myWholePos.getX() + " " + (int)myWholePos.getY());
            System.out.println("PlayerPos: " + (int) playerWholePos.getX() + " " + (int) playerWholePos.getY());

        }


        float distance = myPos.getDistance(playerPos);


        if (distance <= 2) {
            return new ActionAttack();
        } else {

            List<Direction> path = blackBoard.getPath();
            Direction stepDir = path.remove(path.size() - 1);
            /*
            try {
                stepDir = path.remove(path.size() - 1);
            } catch (IndexOutOfBoundsException e){
                System.out.println(character.getName() + " at " + myPos.getNearestWholePosition().getX() + " " + myPos.getNearestWholePosition().getY());
                throw new RuntimeException();
            }
*/

            /*System.out.println(currentCharacter.getName() + ": " + (int)myWholePos.getX() + " " + (int)myWholePos.getY());
            System.out.println(stepDir);*/
            //System.out.println(stepDir);

            if (distance <= 2 + character.getSpeed()) {

                return new ActionStepAndAttack(stepDir);
            } else {
                return new ActionStep(stepDir);
            }
        }

    }

    private GridCell findTile(Field.TileType type) {
        Position whPosition = currentCharacter.getCurrentPosition().getNearestWholePosition();
        int xPos = (int) whPosition.getX();
        int yPos = (int) whPosition.getY();

        if (GameManager.getInstance().getField(xPos, yPos).getType() == type && !reservedSet.contains(pathfinder.getGrid()[pathfinder.gridposToArrayPos(xPos, yPos)])) {
            return pathfinder.getGrid()[pathfinder.gridposToArrayPos(xPos, yPos)];
        }

        GameManager gameManager = GameManager.getInstance();

        int stalledSides = 0;

        int tlX = xPos;
        int tlY = yPos;
        int brX = xPos;
        int brY = yPos;
        int loopLimit = 1;


        boolean[] stalledArr = new boolean[4];

        while (stalledSides < 4) {
            loopLimit++;
            if (!stalledArr[0]) {
                if (tlX - 1 >= 0) {
                    tlX--;
                } else {
                    stalledArr[0] = true;
                    stalledSides++;
                }
            }
            if (!stalledArr[1]) {
                if (tlY - 1 >= 0) {
                    tlY--;
                } else {
                    stalledArr[1] = true;
                    stalledSides++;
                }
            }
            if (!stalledArr[2]) {
                if (brX + 1 < GameManager.getInstance().getLevelX()) {
                    brX++;
                } else {
                    stalledArr[2] = true;
                    stalledSides++;
                }
            }
            if (!stalledArr[3]) {
                if (brY + 1 < GameManager.getInstance().getLevelY()) {
                    brY++;
                } else {
                    stalledArr[3] = true;
                    stalledSides++;
                }
            }

            for (int i = 0; i < loopLimit; i++) {
                GridCell cell = pathfinder.getGrid()[pathfinder.gridposToArrayPos(tlX + i, tlY)];
                if (!reservedSet.contains(cell) && gameManager.getField(tlX + i, tlY).getType() == type) {
                    return cell;
                }
                cell = pathfinder.getGrid()[pathfinder.gridposToArrayPos(brX, tlY + i)];
                if (!reservedSet.contains(cell) && gameManager.getField(brX, tlY + i).getType() == type) {
                    return cell;
                }
                cell = pathfinder.getGrid()[pathfinder.gridposToArrayPos(brX - i, brY)];
                if (!reservedSet.contains(cell) && gameManager.getField(brX - i, brY).getType() == type) {
                    return cell;
                }
                cell = pathfinder.getGrid()[pathfinder.gridposToArrayPos(tlX, brY - i)];
                if (!reservedSet.contains(cell) && gameManager.getField(tlX, brY - i).getType() == type) {
                    return cell;
                }

            }
        }

        unavailableSet.add(type);

        return null;
    }

    private boolean plotPathToResource(Field.TileType type) {

        if (unavailableSet.contains(type)) {
            return false;
        }

        GridCell cell = findTile(type);
        if (cell == null) {
            return false;
        }
        reservedSet.add(cell);
        blackBoard.getReservedCells().add(cell);
        plotPath(cell.getxPos(), cell.getyPos());
        return true;
    }

    private void plotPath(int targetX, int targetY) {
        Position myWholePos = currentCharacter.getCurrentPosition().getNearestWholePosition();
        blackBoard.setPath(pathfinder.findDirection((int) myWholePos.getX(), (int) myWholePos.getY(), targetX, targetY));
        blackBoard.setTargetCell(pathfinder.getGrid()[pathfinder.gridposToArrayPos(targetX, targetY)]);
    }

    private int findItemIndex(ItemType type) {
        for (int i = 0; i < 10; i++) {
            AbstractItem item = currentCharacter.getInventory().getItem(i);
            if (item != null && item.getType() == type) {
                return i;
            }
        }
        return -1;
    }

    public void addBlackBoard(BaseCharacter character) {
        blackBoardMap.put(character, new AIBlackBoard());
    }

    public Pathfinder getPathfinder() {
        return pathfinder;
    }
}
