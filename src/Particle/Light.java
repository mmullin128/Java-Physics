package Particle;

import LinearAlgebra.Vector2;

import java.awt.*;

public class Light extends Particle {
    static int size = 5;
    static double mass = .5;
    static Color color = new Color(250,250,150);

    public Light(Vector2 pos, Vector2 vel) {
        super(pos, vel, size, mass, color);
    }
    public Light(Light p) {
        super(p);
    }
    @Override
    public Light copy() {
        return new Light(this);
    }
}

