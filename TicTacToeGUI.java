import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import tictactoe.TicTacToe;

public class TicTacToeGUI extends JFrame {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    private Color black = Color.BLACK;
    private Color white = Color.WHITE;

    private TicTacToe ttt;
    private String user = null;
    private String[][] board;
    private boolean aiTurn = false;

    private JPanel panel;
    private JLabel titleLabel;
    private JButton playXButton;
    private JButton playOButton;
    private JButton[][] tiles;

    private Font mediumFont = new Font("OpenSans-Regular", Font.PLAIN, 28);
    private Font largeFont = new Font("OpenSans-Regular", Font.PLAIN, 40);
    private Font moveFont = new Font("OpenSans-Regular", Font.PLAIN, 60);

    public TicTacToeGUI() {
        ttt = new TicTacToe();
        board = ttt.initialState();
        initComponents();
    }

    private void initComponents() {
        setTitle("Tic Tac Toe");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };
        panel.setLayout(null);

        titleLabel = new JLabel("Play Tic-Tac-Toe", SwingConstants.CENTER);
        titleLabel.setFont(largeFont);
        titleLabel.setForeground(white);
        titleLabel.setBounds(WIDTH / 2 - 150, 20, 300, 50);
        panel.add(titleLabel);

        playXButton = new JButton("Play as X");
        playXButton.setFont(mediumFont);
        playXButton.setBounds(WIDTH / 8, HEIGHT / 2, WIDTH / 4, 50);
        playXButton.addActionListener(e -> {
            user = ttt.X;
            repaint();
        });
        panel.add(playXButton);

        playOButton = new JButton("Play as O");
        playOButton.setFont(mediumFont);
        playOButton.setBounds(5 * WIDTH / 8, HEIGHT / 2, WIDTH / 4, 50);
        playOButton.addActionListener(e -> {
            user = ttt.O;
            repaint();
        });
        panel.add(playOButton);

        add(panel);
    }

    private void drawBoard(Graphics g) {
        g.setColor(black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        if (user == null) {
            return;
        }

        int tileSize = 80;
        int tileOriginX = WIDTH / 2 - (3 * tileSize / 2);
        int tileOriginY = HEIGHT / 2 - (3 * tileSize / 2);

        tiles = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int x = tileOriginX + j * tileSize;
                int y = tileOriginY + i * tileSize;
                g.setColor(white);
                g.drawRect(x, y, tileSize, tileSize);

                String value = board[i][j];
                if (value != null) {
                    g.setFont(moveFont);
                    g.setColor(white);
                    g.drawString(value, x + tileSize / 2 - 10, y + tileSize / 2 + 10);
                }
            }
        }

        String title;
        if (ttt.terminal(board)) {
            String winner = ttt.winner(board);
            if (winner == null) {
                title = "Game Over: Tie.";
            } else {
                title = "Game Over: " + winner + " wins.";
            }
        } else if (user.equals(ttt.player(board))) {
            title = "Play as " + user;
        } else {
            title = "Computer thinking...";
        }

        titleLabel.setText(title);

        if (user != ttt.player(board) && !ttt.terminal(board)) {
            if (aiTurn) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int[] move = ttt.minimax(board);
                board = ttt.result(board, move);
                aiTurn = false;
            } else {
                aiTurn = true;
            }
        }

        if (ttt.terminal(board)) {
            JButton playAgainButton = new JButton("Play Again");
            playAgainButton.setFont(mediumFont);
            playAgainButton.setBounds(WIDTH / 3, HEIGHT - 65, WIDTH / 3, 50);
            playAgainButton.addActionListener(e -> {
                user = null;
                board = ttt.initialState();
                aiTurn = false;
                repaint();
            });
            panel.add(playAgainButton);
        }

        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeGUI::new);
    }
}
