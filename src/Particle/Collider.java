package Particle;

import LinearAlgebra.Vector2;

public class Collider {
    double E;
    double I;
    double R;
    double F;


    public Collider(double F, double E,double I, double R) {
        this.F = F;
        this.E = E;
        this.I = I;
        this.R = R;
    }
    public Vector2 compute(Vector2 pos) {
        double d = Vector2.length(pos);
        double A = this.E * Math.pow(Math.E, -this.I*Math.pow((d-this.R),2));
        double P = Math.pow(Math.E, -(.5*d - 1.5) ) - .1;
        double c = P;
        Vector2 impulse = Vector2.scale(Vector2.normalize(pos),c);
        return impulse;
    }
}
