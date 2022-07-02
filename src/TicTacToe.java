import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TicTacToe implements ActionListener {

    Random random = new Random();

    JFrame frame = new JFrame("Tic Tac Toe");
    static final int WIDTH = 750;
    static final int HEIGHT = 750;

    /* We define the areas of the game */
    JPanel menu = new JPanel();
    JPanel title = new JPanel();
    JPanel score = new JPanel();
    JPanel button_area = new JPanel();

    JToolBar toolbar = new JToolBar();

    JButton newGame = new JButton("New game");
    //JButton playAgainstAI = new JButton("Play Against AI");
    //JButton playAgainstPlayer = new JButton("Play Against other Player");
    JButton resetScore = new JButton("Reset Score");

    JLabel text_turn = new JLabel();
    JLabel text_score = new JLabel();

    JButton[] gameButtons = new JButton[9];
    int player; // 1 : player1 | 2 : player2
    int score_player1 = 0, score_player2 = 0;
    String score_str = "X player : " + score_player1 + "   |   O player : " + score_player2;

    boolean playingVsAi = false;
    boolean finish_game;

    int lastBeginner;

    public TicTacToe() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        text_turn.setBackground(new Color(25,25,25));
        text_turn.setForeground(new Color(25,255,0));
        text_turn.setFont(new Font("TimesRoman", Font.BOLD, 75));
        text_turn.setHorizontalAlignment(JLabel.CENTER);
        text_turn.setOpaque(true);

        title.setLayout(new BorderLayout());
        title.setBounds(0,0,800,100);

        text_score.setBackground(new Color(25,25,25));
        text_score.setForeground(new Color(25,255,0));
        text_score.setFont(new Font("TimesRoman", Font.ITALIC, 40));
        text_score.setHorizontalAlignment(JLabel.CENTER);
        text_score.setText(score_str);
        text_score.setOpaque(true);

        score.setLayout(new BorderLayout());
        score.setBounds(100,0,300,HEIGHT);

        button_area.setLayout(new GridLayout(3,3));
        button_area.setBackground(new Color(150,150,150));

        for(int i=0 ; i<9 ; i++){
            gameButtons[i] = new JButton();
            button_area.add(gameButtons[i]);

            gameButtons[i].setFont(new Font("TimesRoman", Font.BOLD, 90));
            gameButtons[i].setFocusable(false);
            gameButtons[i].addActionListener(this);
        }


        newGame.addActionListener(this::newGame);
        resetScore.addActionListener(this::restart);
        //playAgainstAI.addActionListener(this::setAgainstAi);
        //playAgainstPlayer.addActionListener(this::setAgainstPlayer);

        /*
        Adding everything to the frame
         */

        toolbar.add(newGame);
        toolbar.add(resetScore);
        //toolbar.add(playAgainstAI);
        //toolbar.add(playAgainstPlayer);
        toolbar.add(title);
        menu.add(toolbar);
        frame.add(menu, BorderLayout.PAGE_START);

        title.add(text_turn);

        score.add(text_score);
        frame.add(score, BorderLayout.SOUTH);

        frame.add(button_area);

        firstPlay();
    }

    private void restart(ActionEvent actionEvent) {
        score_player1 = 0;
        score_player2 = 0;
        newGame(actionEvent);
    }

    private void setAgainstPlayer(ActionEvent actionEvent) {
        playingVsAi = false;
        newGame(actionEvent);
    }

    private void setAgainstAi(ActionEvent actionEvent) {
        playingVsAi = true;
        newGame(actionEvent);
    }

    /*
    Need to re-write actionPerformed due to the actionListener
     */
    @Override
    public void actionPerformed(ActionEvent e){

        for(int i=0 ; i<9 ; i++){
            if(e.getSource() == gameButtons[i]){

                if(!playingVsAi) {

                    if (player == 1) {
                        if (gameButtons[i].getText() == "") {
                            gameButtons[i].setForeground(new Color(200, 0, 50));
                            gameButtons[i].setText("X");
                            player = 2;
                            text_turn.setText("O turn");
                            checkWinningCondition();
                        }
                    } else {
                        if (gameButtons[i].getText() == "") {
                            gameButtons[i].setForeground(new Color(0, 204, 255));
                            gameButtons[i].setText("O");
                            player = 1;
                            text_turn.setText("X turn");
                            checkWinningCondition();
                        }
                    }
                }

                else{
                    if (gameButtons[i].getText() == "") {
                        gameButtons[i].setForeground(new Color(200, 0, 50));
                        gameButtons[i].setText("X");
                        player = 2;
                        text_turn.setText("O turn");
                        turnOfAI();
                        checkWinningCondition();
                    }
                }


            }
        }

    }

    public void turnOfAI() {

        if (gameButtons[4].getText() == "") {
            gameButtons[4].setForeground(new Color(0, 204, 255));
            gameButtons[4].setText("O");
            player = 1;
            text_turn.setText("X turn");
            checkWinningCondition();
        }
    }

    public void firstPlay(){
        if(random.nextInt(2) == 0){
            player = 1;
            text_turn.setText("X turn");
            lastBeginner = 1;
        }
        else {
            player = 2;
            text_turn.setText("O turn");
            lastBeginner = 2;
        }
    }

    public void newGame(ActionEvent e){

       for(int i=0 ; i<9 ; i++) {
            gameButtons[i].setText("");
            gameButtons[i].setEnabled(true);
            gameButtons[i].setBackground(Color.LIGHT_GRAY);
       }

        score_str = "X player : " + score_player1 + "   |   O player : " + score_player2;
        text_score.setText(score_str);

        if(lastBeginner == 2){
            lastBeginner = 1;
            player = 1;
            text_turn.setText("X turn");
        }
        else {
            lastBeginner = 2;
            player = 2;
            text_turn.setText("O turn");
        }

    }

    public void checkWinningCondition(){
        
        String player_checked = null;
        int i;
        for(i=1 ; i<=2 ; i++) {

            if (i == 1) {
                player_checked = "X";
            } else if (i == 2) {
                player_checked = "O";
            }

            /* Horizontal checks */

            if ((Objects.equals(gameButtons[0].getText(), player_checked))
                    && (Objects.equals(gameButtons[1].getText(), player_checked))
                    && (Objects.equals(gameButtons[2].getText(), player_checked))
            ) {
                victory(i, 0, 1, 2);
            } else if ((Objects.equals(gameButtons[3].getText(), player_checked))
                    && (Objects.equals(gameButtons[4].getText(), player_checked))
                    && (Objects.equals(gameButtons[5].getText(), player_checked))
            ) {
                victory(i, 3, 4, 5);
            } else if ((Objects.equals(gameButtons[6].getText(), player_checked))
                    && (Objects.equals(gameButtons[7].getText(), player_checked))
                    && (Objects.equals(gameButtons[8].getText(), player_checked))
            ) {
                victory(i, 6, 7, 8);
            }

            /* Vertical checks */

            else if ((Objects.equals(gameButtons[0].getText(), player_checked))
                    && (Objects.equals(gameButtons[3].getText(), player_checked))
                    && (Objects.equals(gameButtons[6].getText(), player_checked))
            ) {
                victory(i, 0, 3, 6);
            } else if ((Objects.equals(gameButtons[1].getText(), player_checked))
                    && (Objects.equals(gameButtons[4].getText(), player_checked))
                    && (Objects.equals(gameButtons[7].getText(), player_checked))
            ) {
                victory(i, 1, 4, 7);
            } else if ((Objects.equals(gameButtons[2].getText(), player_checked))
                    && (Objects.equals(gameButtons[5].getText(), player_checked))
                    && (Objects.equals(gameButtons[8].getText(), player_checked))
            ) {
                victory(i, 2, 5, 8);
            }

            /* diagonal checks */

            else if ((Objects.equals(gameButtons[0].getText(), player_checked))
                    && (Objects.equals(gameButtons[4].getText(), player_checked))
                    && (Objects.equals(gameButtons[8].getText(), player_checked))
            ) {
                victory(i, 0, 4, 8);
            } else if ((Objects.equals(gameButtons[2].getText(), player_checked))
                    && (Objects.equals(gameButtons[4].getText(), player_checked))
                    && (Objects.equals(gameButtons[6].getText(), player_checked))
            ) {
                victory(i, 2, 4, 6);
            }

            else {
                /* full grid */
                finish_game = true;
                for (int j = 0; j < 9; j++) {
                    if (gameButtons[j].getText() == "") {
                        finish_game = false;
                    }
                }

                if (finish_game) {
                    victory(3, 0, 0, 0);
                }
            }

        }

    }

    public void victory(int winningPlayer, int box1, int box2, int box3){

        for(int i=0 ; i<9 ; i++){
            gameButtons[i].setEnabled(false);
            gameButtons[i].setBackground(Color.RED);
        }
        if(winningPlayer == 1) {
            gameButtons[box1].setBackground(Color.GREEN);
            gameButtons[box2].setBackground(Color.GREEN);
            gameButtons[box3].setBackground(Color.GREEN);

            text_turn.setText("X wins");
            score_player1 += 1;
        }
        else if(winningPlayer == 2){
            gameButtons[box1].setBackground(Color.GREEN);
            gameButtons[box2].setBackground(Color.GREEN);
            gameButtons[box3].setBackground(Color.GREEN);

            text_turn.setText("O wins");
            score_player2 += 1;
        }
        else if(winningPlayer == 3) {
            text_turn.setText("Equality");
        }

    }

}
