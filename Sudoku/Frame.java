//all of the needed imports

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.plaf.basic.BasicButtonUI;

//this class needs to extend JFrame so we can make a new frame and implement ActionListener so the buttons work
public class Frame extends JFrame implements ActionListener{

    //this are all the variables we need to be global so we can access them at different times in this class
    JButton[][] usersudoku = new JButton[9][9]; //this holds the buttons which in the sudoku grid
    JLabel heart;
    JPanel livespanel = new JPanel(); //this panel will hold the lives 
    String stored; // stores the number 
    boolean flag = false; //this flag is used to see if we have pressed the number buttons or the buttons on the grid
    ArrayList<JButton> numbuttons = new ArrayList<>(); //this arraylist stores the buttons from 1-9 in the bottom 
    ArrayList<JButton> emptybuttons = new ArrayList<>(); //this holds all of the buttons which are empty meaning the user will have to fill
    SudokuFrames newframe; // create an instance of SudokuFrames to access the sudoku grids 
    int strikes = 3; //this represents the lives we have left
    int seconds = 0;
    int minutes = 0;
    Timer timer; 
    JLabel timepanel; 
    String timestring; 
    int choice = 0; //identifier for which mode we will be playing
    ArrayList<Integer> solvednum = new ArrayList<>(); //this holds all of the numbers that are already shown on the board and the numbers guessed correctly
    JPanel buttonpanel; 
    boolean notesflag = false; //this flag is used to see if the notes button is active or not

    //we create a frame that takes an int which is the mode we are going to be playing
    //customize the frame
    Frame(int n){
        super("Sudoku Game");
        setSize(600, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setResizable(false);

        //we store the mode we are going to be playing in the choice variable
        choice = n;

        //call the empty method which resets all the needed values 
        empty();

        //this adds a text on the top which says what mode we are playing
        Level();

        //this adds the sudoku grid
        SudokuFrame();

        //we make a panel and call the lives method to add the lives at the bottom
        //we pass in strikes which means how many lives we have
        livespanel.setPreferredSize(new Dimension(250, 45));
        add(livespanel);
        Lives(strikes);

        //this adds the timer at the bottom and starts it
        Timer();

        buttons();

        //this adds the numbers from 1-9 at the bottom
        NumberButtons();
    }

    //this method creates a label, customizes it, based on the mode we want to play it adds the corresponding text
    //and adds it to the frame
    private void Level(){

        JLabel lvl = new JLabel();
        lvl.setPreferredSize(new Dimension(600,20));
        lvl.setHorizontalAlignment(SwingConstants.CENTER);
        lvl.setFont(new Font("Comic Sans MS",Font.BOLD,20));
        add(lvl);

        switch (choice) {
            case 0:lvl.setText("Easy");
            lvl.setForeground(Color.green);
                break;
            case 1:lvl.setText("Medium");
            lvl.setForeground(Color.orange);
                break;
            case 2:lvl.setText("Hard");
            lvl.setForeground(Color.red);
                break;
        }
    }

    //this method creates the sudoku grid
    private void SudokuFrame(){

        //we get the grid we will be playing from our SudokuFrames class and stores it
        newframe = new SudokuFrames(choice);
        int[][] emptygrid = newframe.emptyboard;

        //we create a panel which holds all the buttons of the 9x9 grid
        JPanel gridpanel = new JPanel();
        gridpanel.setLayout(new GridLayout(9,9));
        gridpanel.setPreferredSize(new Dimension(500, 500));
        //we add it to the frame
        add(gridpanel);
        
        //these for-loops create the buttons which are stored in the frame
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){

            JButton b = new JButton();

            //these values are used for the border
            int top = (i % 3 == 0) ? 4 : 1;
            int left = (j % 3 == 0) ? 4 : 1;
            int bottom = (i == 8) ? 4 : 1;
            int right = (j == 8) ? 4 : 1;
            
            //we add the numbers from the grid to the corresponding buttons(boxes on the grid) and customize them
            if(emptygrid[i][j] != 0){
                b.setText(""+emptygrid[i][j]);
                b.setForeground(Color.black);
                b.setUI(new CustomButtonUI());
                b.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                solvednum.add(emptygrid[i][j]);
            } else {
                b.setBackground(Color.white);
                b.addActionListener(this);
                b.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
                b.setHorizontalAlignment(SwingConstants.LEFT);
                b.setVerticalAlignment(SwingConstants.BOTTOM);
                emptybuttons.add(b);
            }

            //customize the buttons and add to the panel
            b.setEnabled(false);
            b.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.black));
            b.setFocusable(false);
            gridpanel.add(b);
            usersudoku[i][j] = b;
            }

        }
    }

    //this method adds a heart icon to the lives panel
    //it takes in s which means how many lives we will add
    private void Lives(int s){

        for(int i = s; i > 0; i--){
            ImageIcon hearticon = new ImageIcon("Hearts.png");

            heart = new JLabel(hearticon);
            livespanel.add(heart);
        }

    }

    //this adds the timer 
    private void Timer(){

        //create,customize and add the new panel to the frame
        timepanel = new JLabel();
        add(timepanel);
        timepanel.setHorizontalAlignment(SwingConstants.CENTER);
        timepanel.setFont(new Font("Comic Sans MS",Font.BOLD,20));
        timepanel.setPreferredSize(new Dimension(255, 40));

        //create the timer that changes every second
        timer = new Timer(1000, new ActionListener() {

            //this is the counter for our timer
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
            if(seconds == 60){
                seconds=0;
                minutes++;
            }
            //This method updates the time panel
            updatetimepanel();
            }
            
        });
        //Start the timer
        timer.start();
    }

    //this method uses String format to display the time that has passed
    private void updatetimepanel(){
        timestring = String.format("%02d:%02d", minutes, seconds);
        timepanel.setText(timestring);
    }

    //this method adds the Notes and Deselect buttons to the frame
    private void buttons(){
        //we create this panel to add our deselect and notes button
        buttonpanel = new JPanel();
        buttonpanel.setPreferredSize(new Dimension(550, 40));
        buttonpanel.setLayout(new BorderLayout());
        add(buttonpanel);

        //we create the notes button, customize it and add it to the panel
        JButton notes = new JButton("Notes");
        notes.setPreferredSize(new Dimension(100, 30));
        notes.setBackground(Color.white);
        notes.addActionListener(this);
        notes.setFocusable(false);
        buttonpanel.add(notes,BorderLayout.WEST);

        //we create the deselect button, customize it and add it to the panel
        JButton deselect = new JButton("Deselect");
        deselect.setPreferredSize(new Dimension(100, 30));
        deselect.setBackground(Color.white);
        deselect.addActionListener(this);
        deselect.setFocusable(false);
        buttonpanel.add(deselect,BorderLayout.EAST);
    }

    //this method adds the buttons 1-9
    private void NumberButtons(){

        //we use this for-loop to create,customize and add the 9 buttons to the frame
        for(int i = 0; i<9; i++){
            JButton b = new JButton(String.valueOf(i+1));
            b.setPreferredSize(new Dimension(55, 55));
            b.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            b.setBackground(Color.white);
            b.setFocusable(false);
            b.addActionListener(this);
            numbuttons.add(b);
            add(b);
        }
    }
 
// Custom ButtonUI to keep the text color black when the button is disabled
class CustomButtonUI extends BasicButtonUI {
    @Override
    public void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        b.setOpaque(true);
        b.setBackground(UIManager.getColor("Button.background"));
    }

    @Override
    protected void paintText(Graphics g, JComponent c, Rectangle textRect, String text) {
        AbstractButton button = (AbstractButton) c;
        ButtonModel model = button.getModel();
        FontMetrics fm = g.getFontMetrics();

        if (model.isEnabled()) {
            g.setColor(button.getForeground());
        } else {
            g.setColor(Color.BLACK); // Set color for disabled state
        }

        g.setFont(button.getFont());
        g.drawString(text, textRect.x, textRect.y + fm.getAscent());
    }
}

//this actionperformed method assigns each button the corresponding block of code 
@Override
public void actionPerformed(ActionEvent e) {

    //this variable is used to access the button we pressed
    JButton sourcebutton = (JButton) e.getSource();
    String text = sourcebutton.getText();

    //if we have pressed the Deselect button we disable the empty boxes buttons and enable the number buttons below
    if(text.equals("Deselect") && flag){
        flag = false;
        for (JButton b : numbuttons) {
            b.setEnabled(true);
            b.setBackground(Color.white);
        }
        for (JButton b : emptybuttons) {
            b.setEnabled(false);
        }
    //if we have pressed the notes button while it is not activated(that is when its background is white)
    //we make the background gray to let the user know 
    }else if(text.equals("Notes") && !flag && !notesflag){
        sourcebutton.setBackground(Color.LIGHT_GRAY);
        notesflag = true;
    //if we have pressed on an empty box
    }else if(notesflag && flag && !(text.equals("Deselect") || text.equals("Notes"))){
        //we make the stored number appear on the selected box
        if(text.indexOf(stored)==-1 || text.length()==0){
            sourcebutton.setText(text+stored);
        //if the stored number is already there we delete all notes in that box
        }else{
            sourcebutton.setText("");
        }
    //if we have pressed the notes button and it is activated we make make it white to let the user know and deselect any selected number
    }else if (text.equals("Notes") && notesflag) {
        if(flag){
            flag = false;
            for (JButton b : numbuttons) {
                b.setEnabled(true);
                b.setBackground(Color.white);
            }
            for (JButton b : emptybuttons) {
                b.setEnabled(false);
            }
        }
        sourcebutton.setBackground(Color.white);
        notesflag = false;
    }

        //if we have pressed the number buttons we store the number the number, set the flag to true,
        //disable all the number buttons and enable the empty buttons on the grid
        if(!flag && !(text.equals("Deselect") || text.equals("Notes"))){
            flag = true;
            stored = text;
            sourcebutton.setBackground(Color.lightGray);
            for (JButton b : numbuttons) {
                b.setEnabled(false);
            }
            for (JButton b : emptybuttons) {
                b.setEnabled(true);
            }

            //if we pressed the empty buttons on the grid we set the flag to false, store the buttons position,
            //and compare it to the solved grid
        } else if(!(text.equals("Notes") || text.equals("Deselect")) && !notesflag){
            flag = false;
            int x = getpos(sourcebutton)[0];
            int y = getpos(sourcebutton)[1];
            int[][] solvedsudoku = newframe.solved();

            if(Integer.parseInt(stored) == solvedsudoku[x][y]){
            sourcebutton.setText(stored);

            emptybuttons.remove(sourcebutton);
            solvednum.add(Integer.parseInt(stored));

            sourcebutton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            sourcebutton.setHorizontalAlignment(SwingConstants.CENTER);
            sourcebutton.setVerticalAlignment(SwingConstants.CENTER);
            sourcebutton.setEnabled(false);
            sourcebutton.setForeground(Color.black);
            sourcebutton.setUI(new CustomButtonUI());
            
            //if we have won the game we show the endscreen
            if(emptybuttons.isEmpty()){
                timer.stop();
                JOptionPane.showMessageDialog(null,"You Completed the Grid in " + timestring, "Winner's POV", JOptionPane.OK_OPTION);
                dispose();
            }

            //if we have lost we remove one life
            } else {
                livespanel.removeAll();;
                livespanel.revalidate();
                livespanel.repaint();
                strikes--;
                Lives(strikes);
                //if we have lost all our lives we show the endscreen
                if(strikes == 0){
                    timer.stop();
                    JOptionPane.showMessageDialog(null,"You lost in " + timestring, "Loser's POV", JOptionPane.OK_OPTION);
                    dispose();
                }
            }

            //enable the numbuttons and disable the emptybuttons
            for (JButton b : numbuttons) {
                b.setEnabled(true);
                b.setBackground(Color.white);
            }
            for (JButton b : emptybuttons) {
                b.setEnabled(false);
            }

            int counter = 0;
            //if there are 9 of the same numbers then we disable that button
            for (int i = 1; i < 10; i++) {
                for (int j = 0; j < solvednum.size(); j++) {
                    if(solvednum.get(j)==i)counter++;
                    if(counter==9){
                        for (int k = 0; k<numbuttons.size(); k++) {
                            if(numbuttons.get(k).getText().equals(""+i)){
                                numbuttons.get(k).setEnabled(false);
                                numbuttons.get(k).setBackground(Color.LIGHT_GRAY);
                                numbuttons.remove(k);
                            }
                        }
                        break;
                    }
                }
                counter=0;
            }
        }
        
}

    //this method gets the position of the button that is passed in on the sudoku grid
    private int[] getpos(JButton b){
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){

                if(b == usersudoku[i][j]){
                    int[] positions = new int[2];
                    positions[0] = i;
                    positions[1] = j;
                    return positions;
                }
            }
        }
        return null;
    }

    //this method is used when we create a new sudoku game after we just played one
    //resets all the needed values
    public void empty(){
        for (JButton jButton : emptybuttons) {
            emptybuttons.remove(jButton);
        }
        usersudoku = new JButton[9][9];

        for (JButton jButton : numbuttons) {
            numbuttons.remove(jButton);
        }

        strikes = 3;
        seconds = 0;
        minutes = 0;
        flag = false;
    }
}
