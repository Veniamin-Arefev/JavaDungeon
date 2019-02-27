/*
Created by Veniamin_arefev
Special for School Project
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.lang.Math.abs;

public class Map  {
    enum direction {up , down, left, right}

    private static final int wallID = 1;
    private static final int wayID = 2;
    private static final int playerID = 3;
    private static final int enemyID = 4;
    private static final int exitID = 5;
    private static final int WIDTH = 40;
    private static final int HEIGHT = 30;
    private static int aggressiveDistance = 5;
    private final Random randomSeed;
    private Random random = new Random();
    private int seed;
    private SettingsWindow.difficult diff;
    private int[][] arrayMap = new int[WIDTH][HEIGHT];
    private int playerMAXHP = Window.STATS_DIALOG.getPlayerHealth();
    private int playerHP = playerMAXHP;
    private int playerX;
    private int playerY;
    private int playerDamage = Window.STATS_DIALOG.getPlayerDamage();
    private int playerDefence = Window.STATS_DIALOG.getPlayerDefence();
    private int playerEXP = 0;
    private int lvlUP = 0;
    private int overageExp;
    private int floor = 0;
    private int keyPiecesObtained;
    private final ArrayList<Enemy> enemysArray = new ArrayList<>();
    public Map(SettingsWindow.difficult diff) { //constructor
        seed = random.nextInt();
        randomSeed = new Random(seed);
        this.diff = diff;
        generateMap(seed);
    }
    public Map(SettingsWindow.difficult diff,int seed){ //constructor
        this.seed = seed;
        randomSeed = new Random(seed);
        this.diff = diff;
        generateMap(seed);
    }
    public int getFloor() {
        return floor;
    }
    public boolean isExitOpened(){
        return keyPiecesObtained == 9;
    }
    ArrayList<Enemy> getEnemysArray() {
        return enemysArray;
    }
    public int getPlHP() {
        return playerHP;
    }
    public void setPlHP(int HP) {
        playerHP = HP;
    }
    private void PLGetDmg(int Damage) {
        if (diff == SettingsWindow.difficult.godmod) return;
        if (Damage > playerDefence) {
            if (playerHP > Damage - playerDefence) playerHP = playerHP - (Damage- playerDefence);
            else playerHP = 0;
        }
        else if (playerHP != 0)playerHP--;
    }
    public int getPlMAXHP() {
        return playerMAXHP;
    }
    public void setPlMAXHP(int count) {
        playerMAXHP = count;
    }
    public int getPlDmg() {
        return playerDamage;
    }
    public void setPlDmg(int Damage) {
        playerDamage =  Damage;
    }
    public int getPlDef() {
        return playerDefence;
    }
    public void setPlDef(int Defence) {
        playerDefence =  Defence;
    }
    public int getId(int x, int y){
        return arrayMap[x][y];
    }
    public int getSeed(){
        return seed;
    }
    public boolean isGameover(){
        return playerHP <= 0;
    }
    public boolean isGamewin(){
        return floor == 11 && enemysArray.size() == 0;
    }
    public int getLvlUP() {
        return lvlUP;
    }
    public void removeLvlUp() {
        lvlUP--;
    }
    public int getPlayerEXP() {
        return playerEXP;
    }
    public boolean checkPositionToMove(direction direction) {
        boolean can = false;
        switch (direction) {
            case up: {
                if (getId(playerX , playerY - 1) == wayID) can = true;
                if (getId(playerX , playerY - 1) == enemyID) can = true;
                if ((getId(playerX , playerY - 1) == exitID) & (keyPiecesObtained == 9))
                    generateMap(randomSeed.nextInt());
            }
            break;
            case down: {
                if (getId(playerX , playerY + 1) == wayID) can = true;
                if (getId(playerX , playerY + 1) == enemyID) can = true;
                if ((getId(playerX , playerY + 1) == exitID) & (keyPiecesObtained == 9))
                    generateMap(randomSeed.nextInt());
            }
            break;
            case left: {
                if (getId(playerX - 1, playerY) == wayID) can = true;
                if (getId(playerX - 1, playerY) == enemyID) can = true;
                if ((getId(playerX - 1, playerY) == exitID) & (keyPiecesObtained == 9))
                    generateMap(randomSeed.nextInt());
            }
            break;
            case right: {
                if (getId(playerX + 1 , playerY) == wayID) can = true;
                if (getId(playerX + 1 , playerY) == enemyID) can = true;
                if ((getId(playerX + 1 , playerY) == exitID) & (keyPiecesObtained == 9))
                    generateMap(randomSeed.nextInt());
            }
            break;
        }
        return can;
    }
    public void playerMove(direction direction){
        switch (direction){
            case up : {
                if (arrayMap[playerX][playerY-1] == wayID) {
                    arrayMap[playerX][playerY]=wayID;
                    playerY--;
                    arrayMap[playerX][playerY] = playerID;
                } else {
                    damageOrKillEnemy(playerX,playerY-1);
                }
            }
            break;
            case down : {
                if (arrayMap[playerX][playerY+1] == wayID) {
                    arrayMap[playerX][playerY]=wayID;
                    playerY++;
                    arrayMap[playerX][playerY] = playerID;
                } else {
                    damageOrKillEnemy(playerX,playerY+1);
                }
            }
            break;
            case left : {
                if (arrayMap[playerX-1][playerY] == wayID) {
                    arrayMap[playerX][playerY]=wayID;
                    playerX--;
                    arrayMap[playerX][playerY] = playerID;
                } else {
                    damageOrKillEnemy(playerX-1,playerY);
                }
            }
            break;
            case right : {
                if (arrayMap[playerX+1][playerY] == wayID) {
                    arrayMap[playerX][playerY]=wayID;
                    playerX++;
                    arrayMap[playerX][playerY] = playerID;
                }else {
                    damageOrKillEnemy(playerX+1,playerY);
                }
            }
            break;
        }
        enemyTurn();
    }
    private void damageOrKillEnemy(int EnemyX, int EnemyY){
        Enemy enemy = searchEnemy(EnemyX,EnemyY);
        enemy.dealDamage(playerDamage);
        if (enemy.getHealth()==0) {
            playerEXP = playerEXP + overageExp + (int) (Math.random()*10);
            if (enemy.isHaveKeyPiece()) {
                if (keyPiecesObtained < 9) keyPiecesObtained++;
                try {
                    Game.keyIcon.setImage(ImageIO.read(getClass().getResource("/Textures/Keys/Key"+keyPiecesObtained+".png")));
                } catch (IOException e) {
                    Logger.getLogger(MainClassOfThisGame.class.getName()).log(Level.SEVERE, e, String::new);
                }
            }
            enemysArray.remove(enemy);
            arrayMap[enemy.getXcoord()][enemy.getYcoord()] = wayID;
        }
    }
    private Enemy searchEnemy(int x, int y){
        return enemysArray.get(enemysArray.indexOf(new Enemy(x,y)));
    }
    public void enemyTurn() {
        int[][] mapcopy = new int[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            System.arraycopy(arrayMap[x], 0, mapcopy[x], 0, HEIGHT);
        }
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (mapcopy[x][y] == playerID) mapcopy[x][y] = -playerID;
                if (mapcopy[x][y] == wallID) mapcopy[x][y] = -wallID;
                if (mapcopy[x][y] == enemyID) mapcopy[x][y] = -enemyID;
                if (mapcopy[x][y] == wayID) mapcopy[x][y] = 0;

            }
        }
        for (int count = 1; count < aggressiveDistance + 1; count++) {
            for (int x = 1; x < WIDTH; x++) {
                for (int y = 1; y < HEIGHT; y++) {
                    if ((mapcopy[x][y] == count - 1) && mapcopy[x][y] != 0) {
                        if (mapcopy[x + 1][y] == 0) mapcopy[x + 1][y] = count;
                        if (mapcopy[x - 1][y] == 0) mapcopy[x - 1][y] = count;
                        if (mapcopy[x][y + 1] == 0) mapcopy[x][y + 1] = count;
                        if (mapcopy[x][y - 1] == 0) mapcopy[x][y - 1] = count;
                    }
                    if (mapcopy[x][y] == -3) {
                        if (mapcopy[x + 1][y] == 0) mapcopy[x + 1][y] = count;
                        if (mapcopy[x - 1][y] == 0) mapcopy[x - 1][y] = count;
                        if (mapcopy[x][y + 1] == 0) mapcopy[x][y + 1] = count;
                        if (mapcopy[x][y - 1] == 0) mapcopy[x][y - 1] = count;
                    }
                }
            }
        }
        for (int x = 1; x < WIDTH; x++) {
            for (int y = 1; y < HEIGHT; y++) {
                direction dir = null;
                int lowest = 666;
                if (mapcopy[x][y] == -enemyID) {
                    if ((mapcopy[x + 1][y] > 0) && (mapcopy[x + 1][y] < aggressiveDistance + 1)) {
                        if (mapcopy[x + 1][y] < lowest) {
                            dir = direction.right;
                            lowest = mapcopy[x + 1][y];
                        }
                    }
                    if ((mapcopy[x - 1][y] > 0) && (mapcopy[x - 1][y] < aggressiveDistance + 1)) {
                        if (mapcopy[x - 1][y] < lowest) {
                            dir = direction.left;
                            lowest = mapcopy[x - 1][y];
                        }
                    }
                    if ((mapcopy[x][y + 1] > 0) && (mapcopy[x][y + 1] < aggressiveDistance + 1)) {
                        if (mapcopy[x][y + 1] < lowest) {
                            dir = direction.down;
                            lowest = mapcopy[x][y + 1];
                        }
                    }
                    if ((mapcopy[x][y - 1] > 0) && (mapcopy[x][y - 1] < aggressiveDistance + 1)) {
                        if (mapcopy[x][y - 1] < lowest) {
                            dir = direction.up;
                            lowest = mapcopy[x][y - 1];
                        }
                    }
                    if  (mapcopy[x + 1][y] == - playerID) {
                        PLGetDmg(searchEnemy(x, y).getDamage());
                        continue;
                    }
                    if (mapcopy[x - 1][y] == - playerID) {
                        PLGetDmg(searchEnemy(x, y).getDamage());
                        continue;
                    }
                    if (mapcopy[x][y + 1] == - playerID) {
                        PLGetDmg(searchEnemy(x, y).getDamage());
                        continue;
                    }
                    if (mapcopy[x][y - 1] == - playerID) {
                        PLGetDmg(searchEnemy(x, y).getDamage());
                        continue;
                    }
                    for (int i = 0; i < 1; i++) {
                        if ((mapcopy[x + 1][y] == lowest) && (dir != direction.right) && (mapcopy[x + 1][y] != 666)) {
                            if (Math.random() > 0.5) dir = direction.right;
                            break;
                        }
                        if ((mapcopy[x - 1][y] == lowest) && (dir != direction.left) && (mapcopy[x - 1][y] != 666)) {
                            if (Math.random() > 0.5) dir = direction.left;
                            break;
                        }
                        if ((mapcopy[x][y + 1] == lowest) && (dir != direction.down) && (mapcopy[x][y + 1] != 666)) {
                            if (Math.random() > 0.5) dir = direction.down;
                            break;
                        }
                        if ((mapcopy[x][y - 1] == lowest) && (dir != direction.up) && (mapcopy[x][y - 1] != 666)) {
                            if (Math.random() > 0.5) dir = direction.up;
                            break;
                        }
                    }
                    if (dir != null) {
                        moveEnemy(x, y, dir);
                        mapcopy[x][y] = 666;
                        switch (dir) {
                            case right: {
                                mapcopy[x + 1][y] = 666;
                            }
                            break;
                            case left: {
                                mapcopy[x - 1][y] = 666;
                            }
                            break;
                            case down: {
                                mapcopy[x][y + 1] = 666;
                            }
                            break;
                            case up: {
                                mapcopy[x][y - 1] = 666;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
    private void moveEnemy(int x0,int y0, direction dir){
        Enemy enemy = searchEnemy(x0,y0);
        arrayMap[x0][y0] = wayID;
        int x = enemy.getXcoord();
        int y = enemy.getYcoord();
        switch (dir) {
            case up: {
                x = x0;
                y = y0-1;
            }
            break;
            case down: {
                x = x0;
                y = y0+1;
            }
            break;
            case left: {
                x = x0-1;
                y = y0;
            }
            break;
            case right: {
                x = x0+1;
                y = y0;
            }
            break;
        }
        enemy.setXcoord(x);
        enemy.setYcoord(y);
        arrayMap[x][y] = enemyID;
    }
    private void generateMap(int seed){
        long start = System.nanoTime();
        keyPiecesObtained = 0;
        try {
            Game.keyIcon.setImage(ImageIO.read(getClass().getResource("/Textures/Keys/Key0.png")));
        } catch (IOException e) {
            Logger.getLogger(MainClassOfThisGame.class.getName()).log(Level.SEVERE, e, String::new);
        }
        Logger.getLogger(MainClassOfThisGame.class.getName()).info("Generating map...");
        Random enemyGen = new Random(seed);
        if (!(diff == SettingsWindow.difficult.godmod)) playerHP = playerMAXHP;
        floor ++;
        enemysArray.clear();
        for (int i = 0;i<40;i++){
            for (int j=0;j<30;j++){
                arrayMap[i][j]= wallID;
            }
        }
        switch (seed) {
            case 2281337: {
                for (int i = 0; i < 40; i++) {
                    for (int j = 0; j < 30; j++) {
                        arrayMap[i][j] = wayID;
                    }
                }
                arrayMap[9][9] = playerID;
                playerX = 9;
                playerY = 9;
                enemysArray.add(new Enemy(10, 11,Enemy.model.first));
                arrayMap[11][10] = enemyID;
                enemysArray.add(new Enemy(11, 10, Enemy.model.first));

            }
            break;
            case 666: {
                for (int i = 2; i < WIDTH-1; i++) {
                    for (int j = 1; j < HEIGHT-1; j++) {
                        arrayMap[i][j] = enemyID;//1
                        enemysArray.add(new Enemy(i,j, Enemy.model.first));
                    }
                }
                arrayMap[1][1] = playerID;
                playerX = 1;
                playerY = 1;
            }
            break;
            default: {
                if (floor == 11) {
                    for (int i = 10; i < WIDTH-10; i++) {  //generate first room without player
                        for (int j = 5; j < HEIGHT-6; j++) {
                            arrayMap[i][j] = wayID;//2
                        }
                    }
                    playerX = 10;
                    playerY = 5;
                    aggressiveDistance = 15;
                    arrayMap[playerX][playerY] = playerID;
                    arrayMap[playerX+1][playerY] = wayID;
                    arrayMap[playerX+1][playerY] = wayID;
                    arrayMap[playerX+2][playerY] = wayID;
                    arrayMap[playerX+3][playerY] = wayID;
                    arrayMap[WIDTH-11][HEIGHT-7] = enemyID;
                    Enemy enemy = new Enemy(WIDTH-11,HEIGHT-7, Enemy.model.boss);
                    enemysArray.add(enemy);
                    break;
                }
                int tempx1;
                int tempx2;
                int tempy1;
                int tempy2;
                int pathx1;
                int pathx2;
                int pathy1;
                int pathy2;
                int enemyX;
                int enemyY;
                random.setSeed(seed);

                tempx2 = 4 + random.nextInt(3);
                tempy2 = 4 + random.nextInt(3);
                for (int i = 1; i < tempx2; i++) {  //generate first room without player
                    for (int j = 1; j < tempy2; j++) {
                        arrayMap[i][j] = wayID;//2
                    }
                }

                tempx2 = 3 + random.nextInt(2);
                tempy2 = 3 + random.nextInt(2);
                for (int i = WIDTH - 5; i < WIDTH - 5 + tempx2; i++) {  //generate exit room without exit
                    for (int j = HEIGHT - 5; j < HEIGHT - 5 + tempy2; j++) {
                        arrayMap[i][j] = -wayID;//2
                    }
                }

                int exitX = WIDTH - 4 + random.nextInt(2); // exit
                int exitY = HEIGHT - 4 + random.nextInt(2);
                arrayMap[exitX][exitY] = exitID;

                tempx1 = tempx2;
                tempy1 = tempy2;
                playerX = random.nextInt(tempx1 - 1) + 1;
                playerY = random.nextInt(tempy1 - 1) + 1;
                arrayMap[playerX][playerY] = playerID; // player
                pathx1 = random.nextInt(tempx1 - 1) + 1;
                pathy1 = random.nextInt(tempy1 - 1) + 1;

                int roomsCount = 10 + random.nextInt(5);
                int i2 = 1;
                int reRollTimes = 0;
                while ((i2 < roomsCount) && (reRollTimes < 100000)) { //rooms gen
                    boolean reRoll = false;
                    reRollTimes++;
                    tempx1 = 1 + random.nextInt(35);
                    tempx2 = tempx1 + 4 + random.nextInt(3);
                    tempy1 = 1 + random.nextInt(25);
                    tempy2 = tempy1 + 4 + random.nextInt(3);
                    if ((tempx2 > 39) || (tempy2 > 29)) continue;
                    for (int i = tempx1; i < tempx2 + 2; i++) {
                        for (int j = tempy1; j < tempy2 + 2; j++) {
                            if (abs(arrayMap[i - 1][j - 1]) != wallID) reRoll = true;
                        }
                    }
                    if (reRoll) continue;
                    for (int i = tempx1; i < tempx2; i++) {
                        for (int j = tempy1; j < tempy2; j++) {
                            arrayMap[i][j] = wayID;//2
                        }
                    }
                    for (int i = 0; i < 1 + random.nextInt(3); i++) {
                        enemyX = tempx1 + random.nextInt(tempx2 - tempx1);
                        enemyY = tempy1 + random.nextInt(tempy2 - tempy1);
                        if (abs(arrayMap[enemyX][enemyY]) != wayID) {
                            i--;
                            continue;
                        }
                        int mobchance;
                        switch (diff) {
                            default:
                            case easy: {
                                mobchance = 10;
                                break;
                            }
                            case normal: {
                                mobchance = 20;
                                break;
                            }
                            case godmod:
                            case hard: {
                                mobchance = 30;
                                break;
                            }
                        }
                        mobchance+=enemyGen.nextInt(40);
                        if ((diff == SettingsWindow.difficult.hard) || (diff == SettingsWindow.difficult.godmod))
                            mobchance+=floor*8;
                        else mobchance+=floor*10;
                        if (mobchance < 70) enemysArray.add(new Enemy(enemyX, enemyY, Enemy.model.first));
                        else if (mobchance > 110) enemysArray.add(new Enemy(enemyX, enemyY, Enemy.model.third));
                        else enemysArray.add(new Enemy(enemyX, enemyY, Enemy.model.second));

                        arrayMap[enemyX][enemyY] = enemyID;
                    }
                    pathx2 = tempx1 + random.nextInt(tempx2 - tempx1);
                    pathy2 = tempy1 + random.nextInt(tempy2 - tempy1);
                    switch (random.nextInt(2)) { // paths gen
                        case 0: {
                            for (int i = pathx1; i != pathx2; ) {
                                if (pathx1 < pathx2) i++;
                                if (pathx1 > pathx2) i--;
                                if (arrayMap[i][pathy1] == wallID) arrayMap[i][pathy1] = -wayID;
                            }
                            for (int j = pathy1; j != pathy2; ) {
                                if (pathy1 < pathy2) j++;
                                if (pathy1 > pathy2) j--;
                                if (arrayMap[pathx2][j] == wallID) arrayMap[pathx2][j] = -wayID;
                            }
                            break;
                        }
                        case 1: {
                            for (int j = pathy1; j != pathy2; ) {
                                if (pathy1 < pathy2) j++;
                                if (pathy1 > pathy2) j--;
                                if (arrayMap[pathx1][j] == wallID) arrayMap[pathx1][j] = -wayID;
                            }
                            for (int i = pathx1; i != pathx2; ) {
                                if (pathx1 < pathx2) i++;
                                if (pathx1 > pathx2) i--;
                                if (arrayMap[i][pathy2] == wallID) arrayMap[i][pathy2] = -wayID;
                            }
                            break;
                        }
                    }
                    pathx1 = pathx2;
                    pathy1 = pathy2;
                    i2++;
                }
                int[][] copymap = new int[10][10];
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; i < 10; i++) {
                        if (arrayMap[WIDTH - 10 + i][HEIGHT - 10 + j] != wayID) copymap[i][j] = -1;
                        else copymap[i][j] = 0;
                    }
                }
                copymap[exitX - WIDTH + 10][exitY - HEIGHT + 10] = 1;
                for (int iterator = 2; iterator < 10; iterator++) {
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; i < 10; i++) {
                            if (copymap[i][j] == iterator - 1) {
                                if (copymap[i + 1][j] == 0) copymap[i + 1][j] = iterator;
                                if (copymap[i - 1][j] == 0) copymap[i - 1][j] = iterator;
                                if (copymap[i][j + 1] == 0) copymap[i][j + 1] = iterator;
                                if (copymap[i][j - 1] == 0) copymap[i][j - 1] = iterator;
                            }
                        }
                    }
                }
                int smallest = 666;
                int smallX = 0;
                int smallY = 0;
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; i < 10; i++) {
                        if (copymap[i][j] == wayID) {
                            if (copymap[i + 1][j] > 1) {
                                smallest = copymap[i + 1][j];
                                smallX = i + 1;
                                smallY = j;
                            }
                            if (copymap[i - 1][j] > 1) {
                                smallest = copymap[i - 1][j];
                                smallX = i - 1;
                                smallY = j;
                            }
                            if (copymap[i][j + 1] > 1) {
                                smallest = copymap[i][j + 1];
                                smallX = i;
                                smallY = j + 1;
                            }
                            if (copymap[i][j - 1] > 1) {
                                smallest = copymap[i][j - 1];
                                smallX = i;
                                smallY = j - 1;
                            }
                        }
                    }
                }
                if (smallest != 666) {
                    pathx1 = smallX + 20;
                    pathx2 = exitX;
                    pathy1 = smallY + 20;
                    pathy2 = exitY;
                } else {
                    pathx1 = 20;
                    pathx2 = exitX;
                    pathy1 = 20;
                    pathy2 = exitY;
                }
                switch (random.nextInt(2)) {
                    case 0: {
                        for (int i = pathx1; i != pathx2; ) {
                            if (pathx1 < pathx2) i++;
                            if (pathx1 > pathx2) i--;
                            if (arrayMap[i][pathy1] == wallID) arrayMap[i][pathy1] = -wayID;
                        }
                        for (int j = pathy1; j != pathy2; ) {
                            if (pathy1 < pathy2) j++;
                            if (pathy1 > pathy2) j--;
                            if (arrayMap[pathx2][j] == wallID) arrayMap[pathx2][j] = -wayID;
                        }
                        break;
                    }
                    case 1: {
                        for (int j = pathy1; j != pathy2; ) {
                            if (pathy1 < pathy2) j++;
                            if (pathy1 > pathy2) j--;
                            if (arrayMap[pathx1][j] == wallID) arrayMap[pathx1][j] = -wayID;
                        }
                        for (int i = pathx1; i != pathx2; ) {
                            if (pathx1 < pathx2) i++;
                            if (pathx1 > pathx2) i--;
                            if (arrayMap[i][pathy2] == wallID) arrayMap[i][pathy2] = -wayID;
                        }
                        break;
                    }
                }
                for (int i = 0; i < WIDTH; i++) {
                    for (int j = 0; j < HEIGHT; j++) {
                        if (arrayMap[i][j] == -wayID) arrayMap[i][j] = wayID;//2
                    }
                }
            }
        }
        float time = (System.nanoTime()-start);
        time = time / 1_000_000;
        time = Math.round(time);
        time = time / 1_000;
        if (enemysArray.size() > 1) {
            overageExp = ((1000 / enemysArray.size())) + 10;
            if (diff == SettingsWindow.difficult.normal) overageExp*=0.9;
            if ((diff == SettingsWindow.difficult.hard) || (diff == SettingsWindow.difficult.godmod)) overageExp*=0.8;
            for (int i = 0; i < 11; ) {
                int j = random.nextInt(enemysArray.size());
                if (enemysArray.get(j).isHaveKeyPiece()) continue;
                enemysArray.get(j).setHaveKeyPiece();
                i++;
            }
        }
        Timer expTimer = new Timer(100, e -> {
            if (playerEXP >= 1000) {
                playerEXP = playerEXP - 1000;
                lvlUP++;
            }
        });
        expTimer.setRepeats(true);
        expTimer.start();

        Logger.getLogger(MainClassOfThisGame.class.getName()).info("Map generated in "+time+" seconds");
    }
}

