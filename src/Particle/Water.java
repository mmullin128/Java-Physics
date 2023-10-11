package Particle;

import LinearAlgebra.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class Water extends CovalentParticle {

    static int size = 5;
    static double mass = 1;
    static Color color = new Color(20,80,250);
    static double interactRadius = 20;
    static double targetRadius = 15;
    static double c = .1;
    static double damping = .05;

    public Water(Vector2 pos, Vector2 vel) {
        super(pos, vel, size, mass, color, interactRadius, targetRadius,c,damping);
    }
    public Water(Water p) {
        super(p);
    }
    @Override
    public Water copy() {
        return new Water(this);
    }
}
