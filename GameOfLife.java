import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;


public class GameOfLife extends JFrame {
    
    //Create data fields to create dimensions of the board and size of individual cells
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private static final int CELL_SIZE = 5;
    private static final int ROWS = HEIGHT/CELL_SIZE;
    private static final int COLUMNS = WIDTH/CELL_SIZE;

    //Create a grid 
    private boolean[][] grid = new boolean[ROWS][COLUMNS];
    private Timer timer;
    private Board board;
    private boolean coloured = false;

    //Constructor
    public GameOfLife(){

        setTitle("Implementation of Conway's Game of Life");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create a new board
        board = new Board();
        add(board, BorderLayout.CENTER);

        //Create the buttons
        JPanel buttonPanel = new JPanel();
        JButton start = new JButton("Start");
        JButton stop = new JButton("Stop");
        JButton reset = new JButton("Reset");
        JButton randomize = new JButton("Randomize");
        JButton colour = new JButton("Colour");

        buttonPanel.add(start);
        buttonPanel.add(stop);
        buttonPanel.add(reset);
        buttonPanel.add(randomize);
        buttonPanel.add(colour);
        add(buttonPanel, BorderLayout.SOUTH);

        //Add the listeners to the button
        start.addActionListener(e -> startGame());
        stop.addActionListener(e -> stopGame());
        reset.addActionListener(e -> resetGame());
        randomize.addActionListener(e -> randomizeGrid());
        colour.addActionListener(e -> toggleColour());

        timer = new Timer(100, e-> updateGame());

    } //End of constructor

        //start game method
        private void startGame(){
            timer.start();
        }

        //StopGame() to pause
        private void stopGame(){
            timer.stop();
        }

        //Reset the game
        private void resetGame(){
            timer.stop();
            grid = new boolean[ROWS][COLUMNS];
            board.repaint();
        }

        //toggleColour()
        private void toggleColour(){
            coloured = !coloured;
        }

        //Randomize the grid
        private void randomizeGrid(){
            Random random = new Random();
            for(int i=0; i<ROWS; i++){
                for(int j=0; j<COLUMNS; j++){
                    grid[i][j] = random.nextBoolean();
                }
            }
            board.repaint();
        }

        //Update the game
        private void updateGame(){
            boolean[][] newGrid = new boolean[ROWS][COLUMNS];
            for(int i=0; i<ROWS; i++){
                for(int j=0; j<COLUMNS; j++){
                    int neighbours = countNeighbours(i,j);
                    if(grid[i][j]){
                        //The cell is alive under these conditions only
                        newGrid[i][j] = neighbours == 2 || neighbours == 3;
                    }else{
                        newGrid[i][j] = neighbours==3;
                    }
                }
            } //End of inner for()
            grid = newGrid;
            board.repaint();
        } //End of updateGame()

    //countNeighbours() to implement the Game of Life algorithm
    private int countNeighbours(int row, int column){
        int count=0;
        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                if(i==0 && j==0){
                    continue;
                }
                int r = (row + i +ROWS)%ROWS;
                int c = (column + j + COLUMNS)% COLUMNS;
                if(grid[r][c]){
                    count++;
                }
            }
        } //End of outer loop
        return count;
    }


    private class Board extends JPanel{
        public Board(){
            addMouseListener(new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent e){
                    toggleCell(e);
                }
            });

            addMouseListener(new MouseAdapter(){
                @Override
                public void mouseDragged(MouseEvent e){
                    toggleCell(e);
                }
            });
        }//ENd of constructor

        private void toggleCell(MouseEvent e){
            int col = e.getX()/CELL_SIZE;
            int row = e.getY()/CELL_SIZE;
            if((row>=0 && row<ROWS) && (col>=0 && col<COLUMNS)){
                grid[row][col] = !grid[row][col];
                repaint();
            }
        }

        //Override the paintComponent method
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            for(int i=0; i<ROWS; i++){
                for(int j=0; j< COLUMNS; j++){
                    if(grid[i][j]){

                        if(!coloured){
                            g.setColor(Color.BLACK); 
                            g.fillRect(j*CELL_SIZE, i*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        }else{
                            Random r = new Random();
                            g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
                            g.fillRect(j*CELL_SIZE, i*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        }
                        
                    }
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(j*CELL_SIZE, i*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    } // End of Board class

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new GameOfLife().setVisible(true));
    }
}
