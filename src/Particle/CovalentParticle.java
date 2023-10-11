package Particle;

import LinearAlgebra.Vector2;

import java.awt.*;


public class CovalentParticle extends Particle {
    public double interactRadius;
    public double targetRadius;
    public double damping;

    public double c = 10;
    public CovalentParticle(Vector2 pos, Vector2 vel, int size, double mass, Color color,double interactRadius, double targetRadius,double c,double damping) {
        super(pos,vel,size,mass,color);
        this.interactRadius = interactRadius;
        this.targetRadius = targetRadius;
        this.c = c;
        this.damping = damping;
    }
    public CovalentParticle(CovalentParticle p) {
        super(p);
        this.interactRadius = p.interactRadius;
        this.targetRadius = p.targetRadius;
        this.damping = p.damping;
        this.c = p.c;
    }
    @Override
    public CovalentParticle copy() {
        return new CovalentParticle(this);
    }
    @Override
    public void interact(Particle p) {
        collide(p);
        Vector2 rel = Vector2.subtract(this.pos,p.pos);
        if (Vector2.length(rel) > this.interactRadius) return;
        if (p instanceof CovalentParticle) {
            double x = Vector2.length(rel)-(this.targetRadius);
            if (x < .001) return;
            Vector2 impDir = Vector2.normalize(rel);
            double impMag = (-c*x)/this.mass; //positive x attracts
            Vector2 imp = Vector2.scale(impDir,impMag);
            Vector2 dampVector = Vector2.scale(Vector2.project(this.vel,impDir),-this.damping);
            Vector2 dAcc = Vector2.add(imp,dampVector);
            //this.vel = Vector2.add(this.vel,dAcc);
            this.acc = Vector2.add(this.acc,dAcc);
        }
    }
}
