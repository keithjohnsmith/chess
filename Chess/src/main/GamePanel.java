package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Queen;
import piece.Rook;

public class GamePanel extends JPanel  implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1100;
	public static final int HEIGHT = 800;
	final int FPS = 60;
	Thread gameThread;
	Board board = new Board();
	Mouse mouse = new Mouse();
	
	
	//Creating two array lists to hold the pieces
	public static ArrayList<Piece> pieces = new ArrayList<>();
	public static ArrayList<Piece> simPieces = new ArrayList<>();
	Piece activeP;
	
	//setting the color to integer variables
	public static final int WHITE = 0;
	public static final int BLACK = 1;
	int currentColor = WHITE;
	
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.black);
		addMouseMotionListener(mouse);
		addMouseListener(mouse);
		
		setPieces();
		copyPieces(pieces, simPieces);
	}
	
	public void launchGame() {
		gameThread = new Thread();
		gameThread.start();
	}
	
	public void setPieces() {
		
		//white pieces
		//pawns
		pieces.add(new Pawn(WHITE, 0, 6));
		pieces.add(new Pawn(WHITE, 1, 6));
		pieces.add(new Pawn(WHITE, 2, 6));
		pieces.add(new Pawn(WHITE, 3, 6));
		pieces.add(new Pawn(WHITE, 4, 6));
		pieces.add(new Pawn(WHITE, 5, 6));
		pieces.add(new Pawn(WHITE, 6, 6));
		pieces.add(new Pawn(WHITE, 7, 6));
		//rooks
		pieces.add(new Rook(WHITE, 0, 7));
		pieces.add(new Rook(WHITE, 7, 7));
		//knights
		pieces.add(new Knight(WHITE, 1, 7));
		pieces.add(new Knight(WHITE, 6, 7));
		//bishop
		pieces.add(new Bishop(WHITE, 2, 7));
		pieces.add(new Bishop(WHITE, 5, 7));
		//queen
		pieces.add(new Queen(WHITE, 3, 7));
		//king
		pieces.add(new King(WHITE, 4, 7));
		
		//white pieces
		//pawns
		pieces.add(new Pawn(BLACK, 0, 1));
		pieces.add(new Pawn(BLACK, 1, 1));
		pieces.add(new Pawn(BLACK, 2, 1));
		pieces.add(new Pawn(BLACK, 3, 1));
		pieces.add(new Pawn(BLACK, 4, 1));
		pieces.add(new Pawn(BLACK, 5, 1));
		pieces.add(new Pawn(BLACK, 6, 1));
		pieces.add(new Pawn(BLACK, 7, 1));
		//rooks
		pieces.add(new Rook(BLACK, 0, 0));
		pieces.add(new Rook(BLACK, 7, 0));
		//knights
		pieces.add(new Knight(BLACK, 1, 0));
		pieces.add(new Knight(BLACK, 6, 0));
		//bishop
		pieces.add(new Bishop(BLACK, 2, 0));
		pieces.add(new Bishop(BLACK, 5, 0));
		//queen
		pieces.add(new Queen(BLACK, 3, 0));
		//king
		pieces.add(new King(BLACK, 4, 0));
		
		
	}
	
	public void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
		
		target.clear();
		for(int i = 0; i < source.size(); i++) {
			target.add(source.get(i));
		}
		
	}
	
	@Override
	public void run() {
		
		//game loop
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime)/drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}
	
	private void update() {
		if(mouse.pressed) {
			if(activeP == null) {
				for(Piece piece : simPieces) {
					if(piece.color == currentColor && piece.col == mouse.x/Board.SQUARE_SIZE && piece.row == mouse.y/Board.SQUARE_SIZE) {
						activeP = piece;
					}
				}
			}
			else {
				simulate();
			}
		}
	}
	
	private void simulate() {
		
		activeP.x = mouse.x - Board.HALF_SQUARE_SIZE;
		activeP.y = mouse.y - Board.HALF_SQUARE_SIZE;
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		//drawing the board
		board.draw(g2);
		
		//Drawing the pieces
		for(Piece p : simPieces) {
			p.draw(g2);
		}
	}

	
	
}