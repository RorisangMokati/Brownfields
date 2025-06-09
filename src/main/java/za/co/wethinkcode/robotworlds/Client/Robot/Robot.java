package za.co.wethinkcode.robotworlds.Client.Robot;

public abstract class Robot {
    private final int maxShots;
    private final int reloadTime;
    private final int repairTime;
    private final int shieldStrength;

    public Robot(int shots, int reload, int repair, int shields) {
        this.maxShots = shots;
        this.reloadTime = reload;
        this.repairTime = repair;
        this.shieldStrength = shields;
    }

    public int getMaxShots() {return this.maxShots;}

    public int getReloadTime() {return this.reloadTime;}

    public int getRepairTime() {return this.repairTime;}

    public int getShieldStrength() {return this.shieldStrength;}
}
