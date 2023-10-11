package Environment;

import LinearAlgebra.Vector2;

import Particle.*;
import java.awt.*;
import java.util.ArrayList;

public class Generator {
    Vector2 pos;
    double createRadius;
    int createNum;
    Color color;
    String type = "Water";
    int mode = 0;
    public Generator() {
        this.pos = new Vector2(0,0);
        this.createRadius = 20;
        this.createNum = 1;
        this.color = new Color(200,200,100);
    }
    public void setCreateRadius(double r) { this.createRadius = r; }
    public void setCreateNum(int n) { this.createNum = n; }
    public void setPos(Vector2 pos) { this.pos = pos; }
    public void setMode(int mode) { this.mode = mode; }
    public void setType(String type) { this.type = type; }
    public ArrayList<Particle> generate(Vector2 vel) {
        ArrayList<Particle> particles = new ArrayList<Particle>();
        if (type == "Water") {
            particles.add(new Water(new Vector2(pos),new Vector2(vel)));
        } else if (type == "Inert") {
            particles.add(new Inert(new Vector2(pos),new Vector2(vel)));
        } else if (type == "Carbon") {
            particles.add(new Carbon(new Vector2(pos), new Vector2(vel)));
        } else if (type == "Heavy") {
            particles.add(new Heavy(new Vector2(pos), new Vector2(vel)));
        } else if (type == "Light") {
            particles.add(new Light(new Vector2(pos), new Vector2(vel)));
        }
        return particles;
    }
}
