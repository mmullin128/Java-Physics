package Environment;

import Board.Board;
import Board.Space;
import LinearAlgebra.Vector2;
import Particle.Particle;
import Particle.Collider;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.Position;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Environment extends JComponent {
    Board board;
    Board newBoard;
    Vector2 savedOrigin;
    Vector2 cameraOrigin;
    double scale = 1;
    double fps;
    boolean drawAxisX = true;
    boolean drawAxisY = true;
    boolean drawBoard = true;
    Color axisColor;
    Color boardColor;
    GameLoop gameLoop;
    Collider collider;

    String status = "Running";

    Generator generator;

    double dT = .1;

    boolean closed = false;

    ArrayList<Particle> addList = new ArrayList<Particle>();
    ArrayList<Boundary> addBoundaryList  = new ArrayList<Boundary>();


    int action;

    static int CAMERA_MOVE = 1;
    static int CREATE = 2;
    static int PARTICLE_MOVE = 3;


    static double MAX_FPS = 30;

    static Color defaultBackGroundColor = new Color(0,0,0);
    static Color defaultAxisColor = new Color(200,200,200);
    static Color defaultBoardColor = new Color(200,100,255);
    public Environment(int width, int height) {
        super();
        this.generator = new Generator();

        this.cameraOrigin = new Vector2(0,0);
        this.savedOrigin = new Vector2(0,0);

        this.setPreferredSize(new Dimension(width,height));

        this.axisColor = defaultAxisColor;
        this.boardColor = defaultBoardColor;
        this.setBackground(defaultBackGroundColor);
        MouseListener listener = new MouseListener();
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
        this.addMouseWheelListener(listener);

        this.collider = new Collider(.8,-.1,10,9);

        this.board = new Board();
        this.newBoard = new Board();

        this.gameLoop = new GameLoop();
        this.gameLoop.start();
    }
    public void toggleDrawBoard() {
        this.drawBoard = !this.drawBoard;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return this.status;
    }
    public void setType(String type) {
        generator.setType(type);
    }
    public void setMode(int mode) {
        generator.setMode(mode);
    }
    public Vector2 computeGravity(Particle p) {
        return new Vector2(0,-1);
    }
    public void addParticle (Particle p) {
        this.addList.add(p);
    }
    public void addBoundary(Boundary b) {
        this.addBoundaryList.add(b);
    }
    class MouseListener extends MouseInputAdapter {
        Vector2 pressedPos;

        public MouseListener() {
            super();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (action == CREATE) {
                return;
            }
            Vector2 currentPos = new Vector2(e.getX(), e.getY());
            Vector2 distance = Vector2.subtract(currentPos, pressedPos);
            cameraOrigin.setX( savedOrigin.getX() - (distance.getX() / scale) );
            cameraOrigin.setY( savedOrigin.getY() + (distance.getY() / scale) );
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (pressedPos == null) pressedPos = new Vector2(e.getX(), e.getY());
            if (e.getButton() == 3) {
                //right mouse
                action = CAMERA_MOVE;
                return;
            }
            if (e.getButton() == 1) {
                //left click
                //Vector2 grabPos = screenToEnv(e.getX(),e.getY());
                //int[] location = board.getLocation(grabPos);
                //Space s = board.getSpace(location[0],location[1]);
                //for (Particle p : s.getParticles().values()) {
                //    if (Vector2.distance(grabPos,p.getPos()) < p.getSize()) {
                //        System.out.println("grab");
                //        action = PARTICLE_MOVE;
                //        return;
                //    }
                //}
                action = CREATE;
                return;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (action == CREATE) {
                if (pressedPos == null) pressedPos = new Vector2(e.getX(), e.getY());
                Vector2 currentPos = new Vector2(e.getX(), e.getY());
                Vector2 distance = Vector2.subtract(currentPos, pressedPos);
                distance.setY(-1 * distance.getY());
                generator.setPos(screenToEnv((int) pressedPos.getX(), (int) pressedPos.getY()));
                addList.addAll(generator.generate(Vector2.scale(distance,1/scale)));
            }
            savedOrigin = new Vector2(cameraOrigin);
            pressedPos = null;
        }

        public void mouseWheelMoved(MouseWheelEvent e) {
            int notches = e.getWheelRotation();
            if (notches > 0) {
                //zoom in / scale gets smaller
                scale *= (double) 4/5;
            }
            if (notches < 0) {
                //zoom out / scale gets bigger
                scale *= (double) 6/5;
            }
            repaint();
        }
    }


    public void setCameraOrigin(int x, int y) {
        this.cameraOrigin = new Vector2(x,y);
    }

    public Vector2 screenToEnv(int x, int y) {
        //set origin to (0,0) at center of component
        Vector2 p = new Vector2(x-getWidth()/2,getHeight()/2-y);
        //translate by camera origin;
        Vector2 pos = Vector2.add(cameraOrigin,Vector2.scale(p,1/scale));
        //origin[0] += (int) (Vector2.scale(cameraOrigin,scale).getX());
        //origin[1] += (int) (Vector2.scale(cameraOrigin,scale).getY());
        return pos;
    }

    public int[] envToScreen(Vector2 pos) {
        //get relative pos to camera origin
        Vector2 screenPos = Vector2.scale(Vector2.subtract(pos,cameraOrigin),scale);
        //env[0] = (env[0]-cameraOrigin[0])*scale;
        //env[1] = (env[1]-cameraOrigin[1])*scale;
        double[] env = new double[] { screenPos.getX(), screenPos.getY() };
        env[0] += getWidth()/2;
        env[1] = -env[1]+getHeight()/2;
        return new int[] { (int) env[0], (int) env[1] };
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getBackground());
        g2d.fillRect(0,0,getWidth(),getHeight());
        //draw axes
        g2d.setColor(this.axisColor);
        int[] pos1 = envToScreen(new Vector2(10,10));
        int[] pos2 = envToScreen(new Vector2(100,100));
        int[] envOrigin = envToScreen(new Vector2(0,0));
        if (this.drawAxisX) {
            g2d.drawLine(0,envOrigin[1],getWidth(),envOrigin[1]);
        }
        if (this.drawAxisY) {
            g2d.drawLine(envOrigin[0],0,envOrigin[0],getHeight());
        }
        int[] screenPos;
        for (Space space : this.board.getHash().values()) {
            if (this.drawBoard) {
                //System.out.println("space: " + space.getX() + "," + space.getY());
                g2d.setColor(boardColor);
                screenPos = envToScreen(new Vector2(space.getX()*space.getWidth(),space.getY()*space.getHeight()));
                int width = space.getWidth();
                int height = space.getHeight();
                g2d.drawRect(screenPos[0],screenPos[1] - (int)(height*this.scale),(int) (width*this.scale),(int) (height*this.scale));
            }
            for (BoundarySegment segment : space.getBoundarySegments()) {
                int[] start = envToScreen(segment.getStart());
                int[] end = envToScreen(segment.getEnd());
                if (segment.getOperator() > 0) {
                    g2d.setColor(new Color(100,250,100));
                } else if (segment.getOperator() < 0) {
                    g2d.setColor(new Color(250,100,100));
                }
                g2d.drawLine(start[0],start[1],end[0],end[1]);
            }
            for (Particle p : space.getParticles().values()) {
                screenPos = envToScreen(p.getPos());
                g2d.setColor(p.getColor());
                g2d.drawArc(screenPos[0]-(int)(p.getSize()*this.scale),screenPos[1]-(int)(p.getSize()*this.scale),(int) (p.getSize()*2*scale),(int) (p.getSize()*2*scale),0,360);
                //g.setColor(new Color(80,80,80));
                //Vector2 rotationVector = Vector2.add(this.pos,Vector2.vector(this.radius,this.rotation));
                //g.drawLine((int) this.pos.x, (int) this.pos.y, (int) rotationVector.x, (int) rotationVector.y);
                //g.setColor(new Color(180,180,180));
            }
        }
        if (true) {
        }
    }
    public class GameLoop extends Thread {
        Date date = new Date();
        static long desiredWait = (long) ( ( (1/MAX_FPS) * 1000) );
        public void run() {
            long lastRender = date.getTime();
            boolean done = false;
            while (!done) {
                long dR = date.getTime()-lastRender;
                if (dR < desiredWait) {
                    //System.out.println("waiting" + dR);
                    try {
                        long sleepTime = desiredWait-dR;
                        Thread.sleep(sleepTime);
                    } catch (Exception e) {
                        e.printStackTrace();
                        done = true;
                    }
                }
                //System.out.println("update: " + board.getHash().size());
                //add new particles;
                for (Particle p : addList) {
                    newBoard.addParticle(p);
                }
                for (Boundary b : addBoundaryList) {
                    newBoard.addBoundary(b);
                }
                if (status == "Running") {
                    update();
                } else if (status == "Clear") {
                    for (Space s : board.getHash().values()) {
                        s.clearParticles();
                    }
                    //newBoard = new Board();
                    status = "Running";
                }
                lastRender = date.getTime();
            }
        }
    }
    public void update() {
        ArrayList<Particle> otherParticles;
        for (Map.Entry<String, Space> entry : this.board.getHash().entrySet()) {
            otherParticles = new ArrayList<Particle>();
            String key = entry.getKey();
            Space space = entry.getValue();
            // add all adjacent space particles to list of particles to test;
            for (int i=-1; i<2; i++) {
                for (int j=-1; j<2; j++) {
                    int testX = space.getX()+i;
                    int testY = space.getY()+j;
                    if (this.board.has(testX,testY)) {
                        otherParticles.addAll(this.board.getSpace(testX,testY).getParticles().values());
                    }
                }
            }
            for (BoundarySegment b : space.getBoundarySegments()) {
                this.newBoard.addBoundarySegment(b,space.getX(),space.getY());
            }
            for (Particle p : space.getParticles().values()) {
                Particle newP = p.copy();
                newP.setAcc(Vector2.add(newP.getAcc(),computeGravity(newP)));
                for (Particle otherP: otherParticles) {
                    if (otherP.getId() == p.getId()) continue;
                    newP.interact(otherP);
                }
                for (BoundarySegment b : space.getBoundarySegments()) {
                    b.collide(newP);
                }
                newP.integrate(dT);
                this.newBoard.addParticle(newP);
            }
        }
        this.board = new Board(this.newBoard);
        this.newBoard = new Board();
        addList = new ArrayList<Particle>();
        addBoundaryList = new ArrayList<Boundary>();
        repaint();
    }

}
