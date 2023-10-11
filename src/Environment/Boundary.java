package Environment;

import LinearAlgebra.Vector2;

public class Boundary {
    Vector2 pos;
    Vector2 dim;
    int operator;
    public Boundary(Vector2 pos, Vector2 dim, int operator) {
        this.pos = pos;
        this.dim = dim;
        this.operator = operator;
    }
    public Vector2 getPos() { return this.pos; }
    public Vector2 getDim() { return this.dim; }
    public int getOperator() { return this.operator; }
}
