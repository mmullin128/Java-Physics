package Particle;

import LinearAlgebra.Vector2;

import java.awt.*;

public class Particle {
    Vector2 pos;
    Vector2 vel;
    Vector2 acc;
    int size;
    double mass;
    Color color;
    int id;

    public static Color defaultColor = new Color(255,255,255);

    static double k = 20;
    static double collisionPadding = 2;

    public static int n = 0;
    public Particle(Vector2 pos, Vector2 vel, int size, double mass, Color color) {
        this.pos = pos;
        this.vel = vel;
        this.acc = new Vector2(0,0);
        this.size = size;
        this.mass = mass;
        this.color = color;
        this.id = n;
        n++;
    }
    public Particle(Particle p) {
        this.pos = new Vector2(p.pos);
        this.vel = new Vector2(p.vel);
        this.acc = new Vector2(p.acc);
        this.size = p.size;
        this.mass = p.mass;
        this.color = p.color;
        this.id = p.id; // copied particles have the same id
    }
    public Particle copy() {
        return new Particle(this);
    }
    public void collide(Particle p) {
        Vector2 rel = Vector2.subtract(this.pos,p.pos); // origin at p pointing to this
        double x = Vector2.length(rel)-(this.size+p.size+collisionPadding);
        if (x > 0) return; // return if particles aren't touching
        Vector2 impDir = Vector2.normalize(rel);
        double impMag = (-k*x)/this.mass; //positive x attracts
        Vector2 imp = Vector2.scale(impDir,impMag);
        //Vector2 dampVector = Vector2.scale(Vector2.project(this.vel,impDir),-damping);
        //Vector2 dAcc = Vector2.add(imp,dampVector);
        //this.vel = Vector2.add(this.vel,dAcc);
        this.acc = Vector2.add(this.acc,imp);

    }
    public void interact(Particle p) {
        this.collide(p);
    }
    public void integrate(double dT) {
        this.vel = Vector2.add(this.vel,Vector2.scale(this.acc,dT));
        this.pos = Vector2.add(this.pos,Vector2.scale(this.vel,dT));
        this.acc = new Vector2(0,0);
    }
    public Integer getId() { return this.id; }
    public double getX() { return this.pos.getX(); }
    public void setX(double x) { this.pos.setX(x);}
    public double getY() { return this.pos.getY(); }
    public void setY(double y) { this.pos.setY(y); }
    public Vector2 getVel() { return this.vel; }
    public void setVel(Vector2 v) { this.vel = v; }
    public Color getColor() {return color;}
    public Vector2 getPos() { return this.pos; }
    public void setPos(Vector2 v) { this.pos = v; }
    public int getSize() { return this.size; }
    public Vector2 getAcc() { return this.acc; }
    public void setAcc(Vector2 acc) { this.acc = acc; }

    public static Particle[] generate(int n, int x, int y) {
        Particle[] particles = new Particle[n];
        Vector2 pos;
        Vector2 vel;
        for (int i=0; i<n; i++) {
            particles[i] = new Particle(new Vector2(x+(i*30), y),Vector2.random(2,2),5,1,defaultColor);
        }
        return particles;
    }

}
