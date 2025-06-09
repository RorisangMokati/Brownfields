package za.co.wethinkcode.robotworlds.Server.World.Obstacles;


import za.co.wethinkcode.robotworlds.Server.World.Position;

public class SquareObstacle extends AbstractObstacle {

    private Position obstacle;

    public SquareObstacle(Position position) {
        obstacle = position;
    }

    /**
     * Checks if this obstacle blocks access to the specified position.
     * @param position the position to check
     * @return return `true` if the x,y coordinate falls within the obstacle's area
     */
    public boolean blocksPosition(Position position) {

        return (getBottomLeftX() <= position.getX() && position.getX() < getBottomLeftX() + getSize()) &&
                (getBottomLeftY() <= position.getY() && position.getY() < getBottomLeftY() + getSize());

    }

    /**
     * Checks if path is blocked based on 2 co-ordinates.
     * Loops through co-ordinates changes from start to end position.
     * @param start starting co-ordinate.
     * @param end ending co-ordinate.
     * @param same unchanging axis co-ordinate.
     * @param axis the unchanged axis.
     * @return `true` if this obstacle is in the way
     */
    public boolean checkPath(int start,int end,int same,char axis){

        for ( ; start < end; start++){
            if (axis == 'x'){
                if (blocksPosition(new Position(start, same))){
                    return true;
                }
            }
            else {
                if (blocksPosition(new Position(same,start))){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if this obstacle blocks the path that goes from coordinate (x1, y1) to (x2, y2).
     * Since our robot can only move in horizontal or vertical lines (no diagonals yet), we can assume that either x1==x2 or y1==y2.
     * @param a first position
     * @param b second position
     * @return `true` if this obstacle is in the way
     */
    public boolean blocksPath(Position a, Position b){

        if (b.getX() > a.getX()){
            return checkPath(a.getX(), b.getX(), a.getY(), 'x');
        }
        else if (a.getX() > b.getX()){
            return checkPath(b.getX(), a.getX(), a.getY(), 'x');
        }
        else if (b.getY() > a.getY()){
            return checkPath(a.getY(), b.getY(), a.getX(), 'y');
        }
        else {
            return checkPath(b.getY(), a.getY(), a.getX(), 'y');
        }
    }

    /**
     * Get X coordinate of bottom left corner of obstacle.
     * @return x coordinate
     */
    public int getBottomLeftX(){
        return this.obstacle.getX();
    }

    /**
     * Get Y coordinate of bottom left corner of obstacle.
     * @return y coordinate
     */
    public int getBottomLeftY(){
        return this.obstacle.getY();
    }

    /**
     * Gets the side of an obstacle (assuming square obstacles)
     * @return the length of one side in nr of steps
     */
    public int getSize(){
        return 5;
    }

    public String getType() {
        return "obstacle";
    }

    // public String direction() {
    //     return "NORTH";
    // }

    public Position getObstacle() { return obstacle; }

    @Override
    public Position getPosition() {
        return obstacle;
    }

    public String toString(){
        return "("+ getBottomLeftX()+","+getBottomLeftY()+")";
    }

}

