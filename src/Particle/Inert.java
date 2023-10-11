package Particle;

import LinearAlgebra.Vector2;

import java.awt.*;

public class Inert extends Particle {
    static int size = 5;
    static double mass = 2;
    static Color color = new Color(200,200,250);

    public Inert(Vector2 pos, Vector2 vel) {
        super(pos, vel, size, mass, color);
    }
    public Inert(Inert p) {
        super(p);
    }
    @Override
    public Inert copy() {
        return new Inert(this);
    }
}

