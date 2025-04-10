import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JPanel {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final int TILE_SIZE = 80;

    private Color black = Color.BLACK;
    private Color white = Color.WHITE;
    private Font mediumFont = new Font("OpenSans", Font.PLAIN, 28);
    private Font largeFont = new Font("OpenSans", Font.PLAIN, 40);
    private Font moveFont = new Font("OpenSans", Font.PLAIN, 60);

    private String user = null;
    private char[][] board = ttt.initial_state(); // Placeholder, replace with actual initial board state
    private boolean ai_turn = false;

    public TicTacToe() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(black);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                handleMouseClick(e);
            }
        });
    }

    private void handleMouseClick(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        // Handle user clicks here, similar to pygame.mouse.get_pressed()
    }

    private void resetGame() {
        user = null;
        board = ttt.initial_state(); // Placeholder, replace with actual initial board state
        ai_turn = false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(white);

        // Draw title
        if (user == null) {
            g2.setFont(largeFont);
            drawCenteredString(g2, "Play Tic-Tac-Toe", new Rectangle(WIDTH, 50), largeFont);

            // Draw buttons
            drawButton(g2, "Play as X", WIDTH / 8, HEIGHT / 2, WIDTH / 4, 50);
            drawButton(g2, "Play as O", 5 * (WIDTH / 8), HEIGHT / 2, WIDTH / 4, 50);
        } else {
            // Draw game board
            int tile_origin_x = WIDTH / 2 - (3 * TILE_SIZE / 2);
            int tile_origin_y = HEIGHT / 2 - (3 * TILE_SIZE / 2);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int x = tile_origin_x + j * TILE_SIZE;
                    int y = tile_origin_y + i * TILE_SIZE;
                    g2.drawRect(x, y, TILE_SIZE, TILE_SIZE);

                    if (board[i][j] != ttt.EMPTY) { // Placeholder, replace with actual board check
                        drawCenteredString(g2, String.valueOf(board[i][j]), new Rectangle(x, y, TILE_SIZE, TILE_SIZE), moveFont);
                    }
                }
            }

            // Update game state
            boolean game_over = ttt.terminal(board); // Placeholder, replace with actual game state check
            char player = ttt.player(board); // Placeholder, replace with actual player check

            String title;
            if (game_over) {
                char winner = ttt.winner(board); // Placeholder, replace with actual winner check
                if (winner == ttt.EMPTY) {
                    title = "Game Over: Tie.";
                } else {
                    title = "Game Over: " + winner + " wins.";
                }
            } else if (user.equals(String.valueOf(player))) {
                title = "Play as " + user;
            } else {
                title = "Computer thinking...";
            }
            g2.setFont(largeFont);
            drawCenteredString(g2, title, new Rectangle(WIDTH, 30), largeFont);

            // Check for AI move
            if (!user.equals(String.valueOf(player)) && !game_over) {
                if (ai_turn) {
                    try { Thread.sleep(500); } catch (InterruptedException ex) { ex.printStackTrace(); }
                    // Call minimax and update board state here
                    ai_turn = false;
                } else {
                    ai_turn = true;
                }
            }

            // Draw "Play Again" button if game over
            if (game_over) {
                drawButton(g2, "Play Again", WIDTH / 3, HEIGHT - 65, WIDTH / 3, 50);
            }
        }

        repaint();
    }

    private void drawButton(Graphics2D g2, String text, int x, int y, int width, int height) {
        g2.setColor(white);
        g2.fillRect(x, y, width, height);
        g2.setColor(black);
        drawCenteredString(g2, text, new Rectangle(x, y, width, height), mediumFont);
        g2.setColor(white);
    }

    private void drawCenteredString(Graphics2D g2, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g2.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2.setFont(font);
        g2.drawString(text, x, y);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tic Tac Toe");
        TicTacToe panel = new TicTacToe();
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
