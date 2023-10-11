package Environment;

import LinearAlgebra.Vector2;
import Particle.Particle;

public class BoundarySegment {
    Vector2 start;
    Vector2 end;
    int operator;
    public BoundarySegment(Vector2 start, Vector2 end, int operator) {
        this.start = start;
        this.end = end;
        this.operator = operator;
    }
    public Vector2 getStart() { return this.start; }
    public Vector2 getEnd() { return this.end; }
    public int getOperator() { return this.operator; }

    public void collide(Particle p) { //boundary collide mutates Particle
        boolean move = false;
        double dX = 0;
        double dY = 0;
        if (this.end.getX()-this.start.getX() < .001) {
            dY = 0;
            dX = this.end.getX()-p.getX();
        } else {
            double m = (this.end.getY()-this.start.getY())/(this.end.getX()-this.start.getX());
            if (m < .001) {
                dX = 0;
                dY = this.start.getY()-p.getY();
            }
        }
        if (dX == 0 && dY == 0) return;
        if (this.operator == 1) {
            if (dX < 0) {
                move = true;
            }
            if (dY < 0) {
                move = true;
            }
        }
        if (this.operator == -1) {
            if (dX > 0) {
                move = true;
            }
            if (dY > 0) {
                move = true;
            }
        }
        if (move) {
            //System.out.println("move");
            p.setPos(Vector2.add(p.getPos(),new Vector2(dX,dY)));
            Vector2 impDir = Vector2.normalize(new Vector2(dX,dY));
            Vector2 imp = Vector2.scale(Vector2.project(p.getVel(),impDir),-2);
            p.setVel(Vector2.add(p.getVel(),imp));
        }
    }
}
