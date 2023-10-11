package Board;

import Environment.Boundary;
import Environment.BoundarySegment;
import LinearAlgebra.Vector2;
import Particle.Particle;

import java.util.HashMap;

public class Board {
    HashMap<String, Space> hash;
    int spaceWidth;
    int spaceHeight;

    static int defaultSize = 40;

    public Board() {
        this.hash = new HashMap<String, Space>();
        this.spaceWidth = defaultSize;
        this.spaceHeight = defaultSize;
    }
    public Board(Board b) {
        this.hash = new HashMap<String, Space>(b.hash);
        this.spaceHeight = b.spaceHeight;
        this.spaceHeight = b.spaceWidth;
    }
    public HashMap<String, Space> getHash() { return this.hash; }

    public boolean has(int x, int y) {
        String k = Space.createHash(x,y);
        if (this.hash.containsKey(k)) return true;
        return false;
    }
    public Space getSpace(int x, int y) {
        String k = Space.createHash(x,y);
        if (!this.hash.containsKey(k)) {
            this.hash.put(k,new Space(x,y,this.spaceWidth,this.spaceHeight));
        }
        return this.hash.get(k);
    }
    public void addSpace(Space s) {
        if (!this.hash.containsKey(s.hash)) {
            this.hash.put(s.hash,s);
        }
    }
    public void removeSpace(String k) {
        if (this.hash.containsKey(k)) {
            this.hash.remove(k);
        }
    }
    public int[] getLocation(Vector2 pos) {
        int spaceX = (int) Math.floor(pos.getX() / this.spaceWidth);
        int spaceY = (int) Math.floor(pos.getY() / this.spaceHeight);
        return new int[] {spaceX, spaceY};
    }
    public void addParticle(Particle p) {
        int[] location = getLocation(p.getPos());
        Space space = getSpace(location[0],location[1]);
        space.addParticle(p);
        //System.out.println("add particle: " + spaceX + "," + spaceY);
    }
    public void addBoundarySegment(BoundarySegment segment, int x, int y) {
        Space space = getSpace(x,y);
        space.addBoundarySegment(segment);
    }
    public void addBoundaryLine(Vector2 start,Vector2 end,int axis,int operator) {
        int[] startLocation = getLocation(start);
        int[] endLocation = getLocation(end);
        Space startSpace = getSpace(startLocation[0],startLocation[1]);
        Space endSpace = getSpace(endLocation[0],endLocation[1]);
        int[] currentLocation = startLocation;
        Space current = startSpace;
        BoundarySegment segment;
        if (axis == 0) { // axis 0 -> keep y the same
            segment = new BoundarySegment(start,new Vector2(startSpace.getBoundsX()[1],start.getY()),operator);
        } else { //axis 1 -> keep x the same
            segment = new BoundarySegment(start,new Vector2(start.getX(),startSpace.getBoundsY()[1]),operator);
        }
        startSpace.addBoundarySegment(segment);
        if (true) {
            //return;
        }
        boolean finished = false;
        while (!finished) {
            if (axis == 0) currentLocation[0] += 1;
            else currentLocation[1] += 1;
            current = getSpace(currentLocation[0],currentLocation[1]);
            if (current.contains(end)) {
                if (axis == 0) { // axis 0 -> keep y the same
                    segment = new BoundarySegment(new Vector2(current.getBoundsX()[0],start.getY()),new Vector2(end.getX(),start.getY()),operator);
                } else { //axis 1 -> keep x the same
                    segment = new BoundarySegment(new Vector2(start.getX(),current.getBoundsY()[0]),new Vector2(start.getX(),end.getY()),operator);
                }
                current.addBoundarySegment(segment);
                finished = true;
            }
            else {
                if (axis == 0) { // axis 0 -> keep y the same
                    segment = new BoundarySegment(new Vector2(current.getBoundsX()[0], start.getY()), new Vector2(current.getBoundsX()[1], start.getY()), operator);
                } else { //axis 1 -> keep x the same
                    segment = new BoundarySegment(new Vector2(start.getX(), current.getBoundsY()[0]), new Vector2(start.getX(), current.getBoundsY()[1]), operator);
                }
                current.addBoundarySegment(segment);
            }
        }
    }
    public void addBoundary(Boundary b){
        Vector2 pos = b.getPos();
        Vector2 dim = b.getDim();
        int operator = b.getOperator();
        double lowX = pos.getX() - dim.getX()/2;
        double highX = pos.getX() + dim.getX()/2;
        double lowY = pos.getY() - dim.getY()/2;
        double highY = pos.getY() + dim.getY()/2;
        //add left edge
        addBoundaryLine(new Vector2(lowX,lowY),new Vector2(lowX,highY),1,-operator);
        //add right edge
        addBoundaryLine(new Vector2(highX,lowY),new Vector2(highX,highY),1,operator);
        //add top edge
        addBoundaryLine(new Vector2(lowX,highY),new Vector2(highX,highY),0,operator);
        //add bottom edge
        addBoundaryLine(new Vector2(lowX,lowY),new Vector2(highX,lowY),0,-operator);
    }
}
