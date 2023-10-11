package Particle;

import LinearAlgebra.Vector2;

import java.awt.*;

public class Carbon extends CovalentParticle {

    static int size = 5;
    static double mass = 3;
    static Color color = new Color(80,80,80);
    static double interactRadius = 20;
    static double targetRadius = 15;
    static double c = 3;
    static double damping = .05;

    public Carbon(Vector2 pos, Vector2 vel) {
        super(pos, vel, size, mass, color, interactRadius, targetRadius,c,damping);
    }
    public Carbon(Carbon p) {
        super(p);
    }
    @Override
    public Carbon copy() {
        return new Carbon(this);
    }
}
