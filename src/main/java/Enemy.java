/*
Created by Veniamin_arefev
Special for School Project
*/

public class Enemy {
    enum model{first,second,third,boss}
    private int health;
    private int maxHealth;
    private int damage;
    private int defence;
    private int xcoord;
    private int ycoord;
    private model model;
    private boolean haveKeyPiece = false;
    public Enemy(int x, int y) { //only for search methods initialization
        xcoord = x;
        ycoord = y;
    }
    public Enemy(int x, int y, model model) { //normal initialization
        xcoord = x;
        ycoord = y;
        this.model = model;
        initmodel(model);
    }
    private void initmodel(model model) {
        switch (model) {
            case first: {
                health = Window.STATS_DIALOG.getFirstHealth();
                damage = Window.STATS_DIALOG.getFirstDamage();
                defence = Window.STATS_DIALOG.getFirstDefence();
            }
            break;
            case second: {
                health = Window.STATS_DIALOG.getSecondHealth();
                damage = Window.STATS_DIALOG.getSecondDamage();
                defence = Window.STATS_DIALOG.getSecondDefence();
            }
            break;
            case third: {
                health = Window.STATS_DIALOG.getThirdHealth();
                damage = Window.STATS_DIALOG.getThirdDamage();
                defence = Window.STATS_DIALOG.getThirdDefence();
            }
            break;
            case boss: {
                health = 100;
                damage = 10;
                defence = 6;
            }
            break;
        }
        maxHealth = health;
        }

    public void dealDamage(int damage){
        if (damage > defence) {
            if (health > damage - defence) health = health - (damage- defence);
            else health = 0;
        }
        else if (health != 0)health--;
    }
    public int getDamage() {
        return damage;
    }
    public int getXcoord() {
        return xcoord;
    }
    public void setXcoord(int xcoord) {
        this.xcoord = xcoord;
    }
    public int getYcoord() {
        return ycoord;
    }
    public void setYcoord(int ycoord) {
        this.ycoord = ycoord;
    }
    public int getHealth() {
        return health;
    }
    public Enemy.model getModel() {
        return model;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public boolean isHaveKeyPiece() {
        return haveKeyPiece;
    }
    public void setHaveKeyPiece(){
        haveKeyPiece = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Enemy enemy = (Enemy) o;

        return xcoord == enemy.xcoord && ycoord == enemy.ycoord;
    }
    @Override
    public int hashCode() {
        int result = xcoord;
        result = 31 * result + ycoord;
        return result;
    }
}
