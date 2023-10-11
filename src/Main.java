import Environment.Boundary;
import Environment.Window;
import LinearAlgebra.Vector2;
import Particle.*;

public class Main {

    public static void main(String[] args) {
        Window window = new Window(500,500);
        window.getEnv().addBoundary(new Boundary(new Vector2(0,0),new Vector2(1000,1000),1));
        int n = 1700;
        int row = 50;
        Vector2 pos = new Vector2(-400,-400);
        int size = 5;
        for (int i=0; i<n; i++) {
            int x = (i % row ) * ( size*2 + 5 ); //3 unit x-padding
            int y = (i / row ) * ( size*2 + 5 ); //3 unit y-padding
            Particle p = new Light(Vector2.add(pos,new Vector2(x,y)),Vector2.random(1,1));
            window.getEnv().addParticle(p);
        }

        //window.getEnv().addBoundary(new Boundary(new Vector2(0,-250),new Vector2(20,200),-1));
        //window.getEnv().generate(16,4,new Vector2(10,100),5);
        //Particle p = new Particle(new Vector2(-100,100),new Vector2(10,0),5,1,Particle.defaultColor);
        //window.getEnv().addParticle(p);
    }

    public void generate(int n, int row, Vector2 pos, int size) {
    }
}
