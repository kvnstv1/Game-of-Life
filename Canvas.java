import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Canvas extends JFrame {
    
    //Data fields
    /**The width */
    private static final int WIDTH = 800;
    /**The height*/
    private static final int HEIGHT = 800;
    /**Point size */
    private static final int SIZE=3;
    /**The grid*/
    private Point[][] grid = new Point[WIDTH/SIZE][HEIGHT/SIZE];
    /**Random */
    public Random random = new Random();

    public Canvas(){

        setTitle("Canvas");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DrawingBoard b = new DrawingBoard();
        add(b); //Add to the frame

    } //End of constructor for Canvas

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new Canvas().setVisible(true));
        
    }

    /**The drawingBoard */
    public class DrawingBoard extends JPanel{

        public DrawingBoard(){

            addMouseListener(new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent e){
                    drawPoint(e.getX(), e.getY());
                }
            });

            addMouseMotionListener(new MouseAdapter(){
                @Override
                public void mouseDragged(MouseEvent e){
                    drawPoint(e.getX(), e.getY());
                }
            });

        } //End of constructor

        private void drawPoint(int x, int y){

            int gridX = x/SIZE;
            int gridY = y/SIZE;
            if((gridX >=0 && gridX < grid.length) && (gridY>=0 && gridY<grid[0].length)){
                Color c = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                grid[gridX][gridY] = new Point(c);
                repaint();
            }
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            for(int i=0; i<grid.length; i++){
                for(int j=0; j<grid[i].length; j++){
                    if(grid[i][j]!=null){
                        g.setColor(grid[i][j].getColour());
                        g.fillRect(i*SIZE,j*SIZE,SIZE,SIZE);
                    }
                }
            }
        }
    } //End of drawingBoard

   


    /**A class that makes a point to place on a canvas */
    private class Point{

        /**The colour of the point */
        private Color color;

        public Point(Color color){
            this.color = color;
        }

        public Color getColour(){
            return color;
        }

        
    }
}

