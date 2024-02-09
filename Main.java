import prog1.kotprog.dontstarve.solution.GameManager;
import prog1.kotprog.dontstarve.solution.character.BaseCharacter;
import prog1.kotprog.dontstarve.solution.character.actions.*;
import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.inventory.CharacterInventory;
import prog1.kotprog.dontstarve.solution.inventory.items.*;
import prog1.kotprog.dontstarve.solution.level.BaseField;
import prog1.kotprog.dontstarve.solution.level.Level;
import prog1.kotprog.dontstarve.solution.utility.Direction;
import prog1.kotprog.dontstarve.solution.utility.Position;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Main {
    private static BufferedImage image;

    public static void main(String[] args) {

        t4();


    }

    public static void t1() {
        Level l = new Level("level00.png");
        GameManager.getInstance().loadLevel(l);

        GameManager.getInstance().joinCharacter("player", true);
        GameManager.getInstance().joinCharacter("other1", false);
        GameManager.getInstance().joinCharacter("other2", false);
        GameManager.getInstance().joinCharacter("other3", false);
        GameManager.getInstance().joinCharacter("other4", false);
        GameManager.getInstance().joinCharacter("other5", false);
        GameManager.getInstance().joinCharacter("other6", false);
        GameManager.getInstance().joinCharacter("other7", false);
        GameManager.getInstance().joinCharacter("other8", false);
        GameManager.getInstance().joinCharacter("other9", false);
        GameManager.getInstance().joinCharacter("other10", false);
        GameManager.getInstance().joinCharacter("other11", false);
        GameManager.getInstance().joinCharacter("other12", false);
        GameManager.getInstance().joinCharacter("other13", false);
        GameManager.getInstance().joinCharacter("other14", false);

        System.out.println(GameManager.getInstance().startGame());

        BaseCharacter player = GameManager.getInstance().getCharacter("player");
        System.out.println(player.getCurrentPosition().getX() + " " + player.getCurrentPosition().getY());
        player.getInventory().addItem(new ItemAxe());

        player.getInventory().equipItem(9 - player.getInventory().emptySlots());
        System.out.println(player.getInventory().equippedItem().getType());

        Position playerPos = player.getCurrentPosition();
        playerPos.setX(0);
        playerPos.setY(23);

        Position enemyPos = GameManager.getInstance().getCharacter("other1").getCurrentPosition();


        player.getInventory().addItem(new ItemStone(10));
        player.getInventory().addItem(new ItemTwig(10));
        player.getInventory().addItem(new ItemLog(10));
        GameManager.getInstance().tick(new ActionCraft(ItemType.FIRE));

        Position wPos = playerPos.getNearestWholePosition().getNearestWholePosition();
        System.out.println("Fire: " + GameManager.getInstance().getField((int) wPos.getX(), (int) wPos.getY()).hasFire());

        for (int i = 0; i < 100; i++) {
            //System.out.println("Fire: " + GameManager.getInstance().getField((int)wPos.getX(), (int)wPos.getY()).hasFire());
            if (GameManager.getInstance().isGameEnded()) {
                break;
            }
            GameManager.getInstance().tick(new ActionStep(Direction.RIGHT));

            Position playerWholePos = player.getCurrentPosition().getNearestWholePosition();
            BaseField field = GameManager.getInstance().getField((int) playerWholePos.getX(), (int) playerWholePos.getY());


            if (field.hasTwig()) {
                System.out.println("Cutting Wood ");

                while (field.hasTwig()) {
                    GameManager.getInstance().tick(new ActionInteract());
                }
                System.out.println("Items on ground: " + field.items().length);
                GameManager.getInstance().tick(new ActionCollectItem());
                System.out.println("Items on ground: " + field.items().length);

            }
            System.out.println("Durability: " + player.getInventory().equippedItem().percentage());
        }
    }

    public static void t2() {

        try {
            image = ImageIO.read(new File("level00.png"));
        } catch (IOException e) {
            System.err.println("A pálya beolvasása nem sikerült!");
        }


        Level l = new Level("level00.png");
        GameManager.getInstance().loadLevel(l);

        //GameManager.getInstance().setTutorial(true);

        int botAmount = 1;
        GameManager.getInstance().joinCharacter("player", true);
        for (int i = 0; i < botAmount; i++) {
            GameManager.getInstance().joinCharacter("other" + i, false);

        }

        BaseCharacter player = GameManager.getInstance().getCharacter("player");
        BaseCharacter other = GameManager.getInstance().getCharacter("other0");
        BaseCharacter other1 = GameManager.getInstance().getCharacter("other1");

        Position pos = player.getCurrentPosition();
        player.getInventory().addItem(new ItemPickaxe());
        player.getInventory().equipItem(9 - player.getInventory().emptySlots());
        System.out.println(player.getInventory().equippedItem());

        System.out.println(GameManager.getInstance().startGame());


        System.out.println("Player at: " + player.getCurrentPosition().getX() + " " + player.getCurrentPosition().getY());
        System.out.println("Bot1 at: " + other.getCurrentPosition().getX() + " " + other.getCurrentPosition().getY());
        //System.out.println( "Bot2 at: " + other1.getCurrentPosition().getX() + " " + other1.getCurrentPosition().getY());
        Position playerWhole = player.getCurrentPosition().getNearestWholePosition();
        Position otherWhole = other.getCurrentPosition().getNearestWholePosition();
       /* List<Direction> path = GameManager.getInstance().getEnemyAI().getPathfinder().findDirection((int)otherWhole.getX(), (int)otherWhole.getY(), (int)playerWhole.getX(), (int)playerWhole.getY());
        //List<Direction> path = GameManager.getInstance().getEnemyAI().getPathfinder().findDirection(52, 32, 54, 62);

        int x = (int)otherWhole.getX();
        int y = (int)otherWhole.getY();
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.println(x + " " + y);
            System.out.println(path.get(i));
            try {
                image.setRGB(x, y, 0);
            } catch (ArrayIndexOutOfBoundsException e){
                try {
                    ImageIO.write(image, "png", new File("output.jpg"));
                } catch (IOException e1) {
                    throw new RuntimeException(e1);
                }
                throw  new RuntimeException(e);
            }

            switch (path.get(i)){
                case LEFT -> x--;
                case RIGHT -> x++;
                case DOWN -> y++;
                case UP -> y--;
            }
        }
        File outputfile = new File("output.jpg");
        try {
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        /*
        player.setCurrentPosition(new Position(0, 0));
        other.setCurrentPosition(new Position(1, 1));
        other1.setCurrentPosition(new Position(14, 80));
*/
        while (GameManager.getInstance().isGameStarted() && !GameManager.getInstance().isGameEnded()) {


            GameManager.getInstance().tick(new ActionAttack());
            /*System.out.println("PlayerHP: " + player.getHp());

            EquippableItem item = GameManager.getInstance().getCharacter("other0").getInventory().equippedItem();
            if (item != null){
                System.out.println(item.getType());
            }
            item = GameManager.getInstance().getCharacter("other1").getInventory().equippedItem();
            if (item != null){
                System.out.println(item.getType());
            }*/

        }

/*

        for (int i = 0; i < 100; i++) {
            GameManager.getInstance().tick(new ActionStep(Direction.RIGHT));
            System.out.println(player.getCurrentPosition().getX() + " " + player.getCurrentPosition().getY());
        }*/
        /*
        Set<String> posSet = new HashSet<>();

        for (int i = 0; i < botAmount; i++) {
            Position whPosition = GameManager.getInstance().getCharacter("other" + i).getCurrentPosition().getNearestWholePosition();
            int x = (int)whPosition.getX();
            int y = (int)whPosition.getY();

            if (!posSet.add(String.valueOf(x) + String.valueOf(y))){
                System.out.println("Dupe pos");
                break;
            }

            if (GameManager.getInstance().getField(x, y).getType() != Field.TileType.Empty){
                System.out.println("Field not empty");
                break;
            }
        }
*/
        /*
        BaseCharacter player = GameManager.getInstance().getCharacter("player");
        BaseCharacter other = GameManager.getInstance().getCharacter("other1");

        player.getInventory().addItem(new ItemSpear());

        player.getInventory().equipItem(9 - player.getInventory().emptySlots());

        //player.getCurrentPosition().setX(99);
        //player.getCurrentPosition().setY(1);

        other.getInventory().addItem(new ItemTorch());
        other.getInventory().equipItem(9 - other.getInventory().emptySlots());
        //other.getCurrentPosition().setX(99);
        //other.getCurrentPosition().setY(99);
        while (!GameManager.getInstance().isGameEnded()){
            //System.out.println(player.getHunger());
            //player.setHp(100);
            //other.setHp(100);

            GameManager.getInstance().tick(new ActionAttack());
            Position pPos = player.getCurrentPosition().getNearestWholePosition();

            System.out.println("Player: " + (int)pPos.getX() + " " + (int)pPos.getY() + "  HP: " + player.getHp());
            Position wpos = other.getCurrentPosition().getNearestWholePosition();
            System.out.println("Enemy: " + (int)wpos.getX() + " " + (int)wpos.getY() + "  HP: " + other.getHp());
            //System.out.println(other.getInventory().equippedItem().percentage());
        }*/
/*
        System.out.println("Hp: " + other.getHp());
        for (int i = 0; i < 6; i++) {
            GameManager.getInstance().tick(new ActionAttack());
            System.out.println("Hp: " + other.getHp());
            System.out.println("Durability: " + player.getInventory().equippedItem().percentage());

        }

        Position enemyPos = other.getCurrentPosition().getNearestWholePosition();

        System.out.println("Items on ground: " + GameManager.getInstance().getField((int)enemyPos.getX(), (int)enemyPos.getY()).items().length);


        System.out.println(GameManager.getInstance().getWinner().getName());
        */
    }

    public static void t3() {
        Level l = new Level("level00.png");
        GameManager.getInstance().loadLevel(l);

        GameManager.getInstance().joinCharacter("player", true);
        GameManager.getInstance().joinCharacter("other1", false);

        GameManager.getInstance().setTutorial(true);
        System.out.println(GameManager.getInstance().startGame());

        BaseCharacter player = GameManager.getInstance().getCharacter("player");
        Position whPosition = player.getCurrentPosition().getNearestWholePosition();
        BaseField f = GameManager.getInstance().getField((int) whPosition.getX(), (int) whPosition.getY());
        f.makeFire();

        System.out.println(player.getSpeed());
        player.setHunger(0f);
        System.out.println(player.getSpeed());

        for (int i = 0; i < 4; i++) {
            player.getInventory().dropItem(i);
        }

        player.getInventory().addItem(new ItemRawBerry(100));

        for (int i = 9; i >= 0; i--) {
            if (!f.hasTree()) {
                f.makeFire();
            }
            for (int j = 0; j < 10; j++) {
                GameManager.getInstance().tick(new ActionCook(i));
            }
        }

        printInventory(player.getInventory());
    }

    public static void printInventory(BaseInventory inventory) {
        for (int i = 0; i < CharacterInventory.getInventorySize(); i++) {
            AbstractItem item = inventory.getItem(i);
            if (item == null) {
                System.out.println("null");
                continue;
            }
            System.out.println(item.getType() + " " + item.getAmount());
        }
    }

    public static void t4(){

        Position pos1 = new Position(9,8);
        Position pos2 = new Position(4,20);

        System.out.println(pos1.getDistance(pos2));

    }
}