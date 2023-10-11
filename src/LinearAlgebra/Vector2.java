package LinearAlgebra;

import java.util.Random;

public class Vector2 {
    double x;
    double y;

    static Random rand = new Random();

    public static Vector2 zeroVector = new Vector2(0,0);
    public Vector2(double s) {
        this.x = s;
        this.y = s;
    }
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vector2(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }
    public static Vector2 moveRand(Vector2 v, double d) {
        Vector2 moveVector = random(d,d);
        return add(v,moveVector);
    }
    public static Vector2 vector(Vector2 v1, Vector2 v2) {
        return new Vector2(v2.x-v1.x,v2.y-v1.y);
    }
    public static Vector2 vector(double length, double angle) {
        return new Vector2(length*Math.cos(angle), length*Math.sin(angle));
    }
    public static Vector2 add(Vector2 v1, Vector2 v2) {
        return new Vector2(v1.x+v2.x,v1.y+v2.y);
    }
    public static Vector2 subtract(Vector2 v1, Vector2 v2) {
        return new Vector2(v1.x-v2.x,v1.y-v2.y);
    }
    public static double angleDifference(Vector2 v1, Vector2 v2) {
        double a1 = angle(v1);
        double a2 = angle(v2);
        return a1 - a2;
    }
    public static double angle(Vector2 v) {
        double a;
        if (v.x != 0) {
            a = Math.atan2(v.y, v.x);
            return a;
        }
        if (v.y >= 0) {
            a = Math.PI/2;
            return a;
        }
        a = -Math.PI/2;
        return a;
    }
    public static double angle(Vector2 p1, Vector2 p2) {
        return angle(vector(p1,p2));
    }

    public static double distance(Vector2 p1, Vector2 p2) {
        return Math.hypot(p2.x-p1.x,p2.y-p1.y);
    }
    public static double length(Vector2 v) {
        return Math.hypot(v.x,v.y);
    }
    public static double dot(Vector2 v1,Vector2 v2) {
        return v1.x*v2.x + v1.y*v2.y;
    }
    public static Vector2 scale(Vector2 v, double c) {
        return new Vector2(v.x*c,v.y*c);
    }
    public static Vector2 negate(Vector2 v) {return new Vector2(-v.x,-v.y); }
    public static double component(Vector2 v1, Vector2 v2) {
        return dot(v1,v2) / length(v2);
    }
    public static Vector2 project(Vector2 v1, Vector2 v2) {
        return scale(v2,component(v1,v2));
    }
    public static Vector2 normalize(Vector2 v) {
        double c = length(v);
        if (c == 0) return zeroVector;
        return scale(v,1/c);
    }
    public static Vector2 rotate(Vector2 v, double a) {
        return new Vector2(Math.cos(a)*v.x - Math.sin(a)*v.y, Math.sin(a)*v.x + Math.cos(a)*v.y);
    }
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public static Vector2 random(double xRange, double yRange) {
        double randX = rand.nextDouble(2);
        double randY = rand.nextDouble(2);
        return new Vector2((randX-1)*xRange,(randY-1)*yRange);
    }
    public static Vector2 random(double r) {
        double randX = rand.nextDouble(2);
        double randY = rand.nextDouble(2);
        return new Vector2((randX-1)*r,(randY-1)*r);
    }
}
