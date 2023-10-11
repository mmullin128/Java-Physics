package Board;

import Environment.BoundarySegment;
import LinearAlgebra.Vector2;

import Particle.Particle;

import java.util.ArrayList;
import java.util.HashMap;

public class Space {
    static int[] hashPrimes = new int[] {6037, 8741} ;
    String hash;
    int x;
    int y;
    int width;
    int height;
    HashMap<Integer, Particle> particleHash;
    ArrayList<BoundarySegment> boundarySegments;
    public Space(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.particleHash = new HashMap<Integer, Particle>();
        this.boundarySegments = new ArrayList<BoundarySegment>();
        this.hash = createHash(x,y);
    }
    public int getX () { return this.x; }
    public int getY () { return this.y; }
    public int[] getBoundsX() {
        int lowX = this.x*this.width;
        return new int[] {lowX,lowX+this.width};
    }
    public int[] getBoundsY() {
        int lowY = this.y*this.height;
        return new int[] {lowY,lowY+this.height};
    }
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
    public String getHash() { return this.hash; }

    public static String createHash(int x, int y) {
        String xHash = String.valueOf(hashPrimes[0] * x);
        String yHash = String.valueOf(hashPrimes[1] * y);
        String hash = xHash + yHash;
        return hash;
    }

    public boolean contains(Vector2 pos) {
        int posX = (int) pos.getX();
        int posY = (int) pos.getY();
        int[] xBounds = getBoundsX();
        int[] yBounds = getBoundsY();
        if (posX >= xBounds[0] && posX < xBounds[1] ) {
            if (posY >= yBounds[0] && posY < yBounds[1]) {
                return true;
            }
        }
        return false;
    }
    public void clearParticles() { this.particleHash = new HashMap<Integer, Particle>(); }
    public void addParticle(Particle p) {
        this.particleHash.put(p.getId(), p);
    }
    public void removeParticle(Particle p) {
        this.particleHash.remove(p.getId());
    }
    public HashMap<Integer, Particle> getParticles() {
        return this.particleHash;
    }

    public void addBoundarySegment(BoundarySegment segment) {
        this.boundarySegments.add(segment);
    }
    public ArrayList<BoundarySegment> getBoundarySegments() {
        return this.boundarySegments;
    }
}
