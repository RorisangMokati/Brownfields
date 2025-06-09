package za.co.wethinkcode.robotworlds.Server.World;

public class Position {

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public boolean isIn(Position topLeft, Position bottomRight) {
        boolean withinTop = this.y <= topLeft.getY();
        boolean withinBottom = this.y >= bottomRight.getY();
        boolean withinLeft = this.x >= topLeft.getX();
        boolean withinRight = this.x <= bottomRight.getX();
        return withinTop && withinBottom && withinLeft && withinRight;
    }
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Position position = (Position)o;
            if (this.x != position.x) {
                return false;
            } else {
                return this.y == position.y;
            }
        } else {
            return false;
        }
    }

    public String toString(){
        return ("["+x+", "+y+"]");
    }
}