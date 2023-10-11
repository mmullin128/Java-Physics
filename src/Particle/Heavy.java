package Particle;

import LinearAlgebra.Vector2;

import java.awt.*;

public class Heavy extends Particle {
    static int size = 5;
    static double mass = 5;
    static Color color = new Color(80,20,20);

    public Heavy(Vector2 pos, Vector2 vel) {
        super(pos, vel, size, mass, color);
    }
    public Heavy(Heavy p) {
        super(p);
    }
    @Override
    public Heavy copy() {
        return new Heavy(this);
    }
}

