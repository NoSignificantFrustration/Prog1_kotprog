package prog1.kotprog.dontstarve.solution.utility;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Pathfinder {


    private final int sizeX;
    private final int sizeY;
    private final GridCell[] grid;
    private final BitSet traversable;
    private final BitSet openSet;
    private final BitSet closedSet;
    private final int[] openHeap;
    private final int[] heapIndexes;
    private int currentLength;


    public Pathfinder(int sizeX, int sizeY, BitSet traversable) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.traversable = traversable;
        grid = new GridCell[sizeX * sizeY];
        openHeap = new int[grid.length];
        heapIndexes = new int[grid.length];
        openSet = new BitSet();
        closedSet = new BitSet();
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                grid[i * sizeX + j] = new GridCell(j, i);
            }
        }
    }

    public List<Direction> findDirection(int startX, int startY, int endX, int endY) {
        openSet.set(0, grid.length, false);
        closedSet.set(0, grid.length, false);

        currentLength = 0;

        int startCell = gridposToArrayPos(startX, startY);
        int endCell = gridposToArrayPos(endX, endY);

        if (startCell == endCell) {
            return null;
        }

        addHeapItem(startCell);
        openSet.set(startCell);

        int lovestH = Integer.MAX_VALUE;
        int result = startCell;

        while (currentLength > 0) {


            int currentCell = removeFirst();
            //System.out.println(grid[currentCell].getxPos() + " " + grid[currentCell].getyPos());


            openSet.set(currentCell, false);
            closedSet.set(currentCell);

            if (currentCell == endCell) {
                result = currentCell;
                //System.out.println("Got it");
                break;
            }

            List<Integer> neighbours = getNeighbourFlatIndexes(grid[currentCell].getxPos(), grid[currentCell].getyPos());
            //System.out.println("Neighbours: " + neighbours.size());

            for (int i = 0; i < neighbours.size(); i++) {
                int index = neighbours.get(i);

                if (!traversable.get(index) || closedSet.get(index)) {
                    continue;
                }


                if (grid[currentCell].getgCost() + 1 < grid[index].getgCost() || !openSet.get(index)) {
                    grid[index].setgCost(grid[currentCell].getgCost() + 1);
                    grid[index].sethCost(getDistance(grid[index].getxPos(), grid[index].getyPos(), grid[endCell].getxPos(), grid[endCell].getyPos()));
                    grid[index].setParentIndex(currentCell);

                    if (!openSet.get(index)) {
                        openSet.set(index);
                        addHeapItem(index);
                        if (grid[index].gethCost() < lovestH) {
                            lovestH = grid[index].gethCost();
                        }
                    } else {
                        sortUp(heapIndexes[index]);
                    }

                }
            }
        }

        int current = result;

        ArrayList<Direction> dirList = new ArrayList<Direction>();

        while (current != startCell) {

            if (grid[current].getyPos() < grid[grid[current].getParentIndex()].getyPos()) {
                dirList.add(Direction.UP);
            } else if (grid[current].getyPos() > grid[grid[current].getParentIndex()].getyPos()) {
                dirList.add(Direction.DOWN);
            } else if (grid[current].getxPos() > grid[grid[current].getParentIndex()].getxPos()) {
                dirList.add(Direction.RIGHT);
            } else {
                dirList.add(Direction.LEFT);
            }

            current = grid[current].getParentIndex();
            //System.out.println(current);
        }


        return dirList;
    }

    public List<Integer> getNeighbourFlatIndexes(int posX, int posY) {
        List<Integer> nList = new ArrayList<>();

        if (posY > 0) {
            nList.add((posY - 1) * sizeX + posX);
            //System.out.print(grid[nList.get(nList.size() - 1)].getxPos() + " " + grid[nList.get(nList.size() - 1)].getxPos() + "               ");
            //System.out.print((posY - 1) * sizeX + posX + " ");

        }
        if (posY < sizeY - 1) {
            nList.add((posY + 1) * sizeX + posX);
            //System.out.print(grid[nList.get(nList.size() - 1)].getxPos() + " " + grid[nList.get(nList.size() - 1)].getxPos() + "               ");
            //System.out.print((posY + 1) * sizeX + posX + " ");
        }
        if (posX > 0) {
            nList.add(posY * sizeX + posX - 1);
            //System.out.print(grid[nList.get(nList.size() - 1)].getxPos() + " - " + grid[nList.get(nList.size() - 1)].getxPos() + "             ");
            //System.out.print(posY * sizeX + posX - 1 + " ");
        }
        if (posX < sizeX - 1) {
            nList.add(posY * sizeX + posX + 1);
            //System.out.print(grid[nList.get(nList.size() - 1)].getxPos() + " " + grid[nList.get(nList.size() - 1)].getxPos() + "               ");
            //System.out.print(posY * sizeX + posX + 1 + " ");
        }
        //System.out.println();

        return nList;
    }

    public int gridposToArrayPos(int posX, int posY) {
        return posY * sizeX + posX;
    }

    private void addHeapItem(int gridIndex) {
        openHeap[currentLength] = gridIndex;
        heapIndexes[gridIndex] = currentLength;
        sortUp(currentLength);
        currentLength++;
    }

    private void sortUp(int heapIndex) {
        int parentIndex = (heapIndex - 1) / 2;

        while (true) {

            if (grid[openHeap[parentIndex]].getfCost() > grid[openHeap[heapIndex]].getfCost()) {
                int temp = openHeap[parentIndex];
                openHeap[parentIndex] = openHeap[heapIndex];
                openHeap[heapIndex] = temp;

                int heapIndexTemp = heapIndexes[openHeap[parentIndex]];
                heapIndexes[openHeap[parentIndex]] = heapIndexes[openHeap[heapIndex]];
                heapIndexes[openHeap[heapIndex]] = heapIndexTemp;

                heapIndex = parentIndex;
            } else {
                break;
            }

            parentIndex = (heapIndex - 1) / 2;
        }
    }

    private int removeFirst() {
        int firstItem = openHeap[0];
        currentLength--;
        openHeap[0] = openHeap[currentLength];

        sortDown(0);

        return firstItem;
    }

    private void sortDown(int heapIndex) {
        while (true) {
            int childIndexLeft = heapIndex * 2 + 1;
            int childIndexRight = heapIndex * 2 + 2;

            int swapIndex = 0;

            if (childIndexLeft < currentLength) {
                swapIndex = childIndexLeft;

                if (childIndexRight < currentLength) {
                    if (grid[openHeap[childIndexRight]].getfCost() < grid[openHeap[childIndexLeft]].getfCost()) {
                        swapIndex = childIndexRight;
                    }
                }


                if (grid[openHeap[swapIndex]].getfCost() < grid[openHeap[heapIndex]].getfCost()) {
                    int temp = openHeap[swapIndex];
                    openHeap[swapIndex] = openHeap[heapIndex];
                    openHeap[heapIndex] = temp;

                    int heapIndexTemp = heapIndexes[openHeap[swapIndex]];
                    heapIndexes[openHeap[swapIndex]] = heapIndexes[openHeap[heapIndex]];
                    heapIndexes[openHeap[heapIndex]] = heapIndexTemp;

                    heapIndex = swapIndex;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private int getDistance(int aX, int aY, int bX, int bY) {
        int distX = Math.abs(aX - aY);
        int distY = Math.abs(bX - bY);

        return distX + distY;
    }

    public GridCell[] getGrid() {
        return grid;
    }
}
