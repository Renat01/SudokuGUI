//all of the needed imports

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//this class needs to extend JFrame so we can make a new frame and implement ActionListener so the buttons work

public class MainFrame extends JFrame implements ActionListener{

    //We declare the buttons here so they could be acessed in the actionperformed method

    JButton medium;
    JButton hard;
    JButton easy;
    JButton button1;

    //Create a new frame with title "Main Menu"
    MainFrame(){
        super("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new FlowLayout());
        setResizable(false);

    //Create 3 buttons for each mode we have available
    //we add the actionlistener "this" and customize them

        easy = new JButton("Easy");
        easy.addActionListener(this);
        easy.setPreferredSize(new Dimension(90, 50));
        easy.setFocusable(false);
        easy.setFont(new Font("Comic Sans MS",Font.BOLD,15));
        easy.setBackground(Color.white);
        easy.setForeground(Color.green);

        medium = new JButton("Medium");
        medium.addActionListener(this);
        medium.setPreferredSize(new Dimension(90, 50));
        medium.setFocusable(false);
        medium.setFont(new Font("Comic Sans MS",Font.BOLD,15));
        medium.setBackground(Color.white);
        medium.setForeground(Color.ORANGE);

        hard = new JButton("Hard");
        hard.addActionListener(this);
        hard.setPreferredSize(new Dimension(90, 50));
        hard.setFocusable(false);
        hard.setFont(new Font("Comic Sans MS",Font.BOLD,15));
        hard.setBackground(Color.white);
        hard.setForeground(Color.red);

        //Call the addpanel class so the buttons appear on the frame
        addpanel();

        //Create the quit button, customize it and add it to the frame
        button1 = new JButton("Quit");
        button1.addActionListener(this);
        button1.setPreferredSize(new Dimension(100, 50));
        button1.setFocusable(false);
        button1.setFont(new Font("Comic Sans MS",Font.BOLD,20));
        button1.setBackground(Color.lightGray);
        button1.setForeground(Color.red);

        add(button1);
    }

    //this method create a new panel, adds the easy,medium,hard buttons to that panel and adds it to the frame
    private void addpanel(){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 200));
        panel.add(easy);
        panel.add(medium);
        panel.add(hard);
        add(panel);
    }

    //this actionperformed method detects when a button is pressed and runs the corresponding code
    @Override
    public void actionPerformed(ActionEvent e) {
        //if the quit button is pressed we exit the program
        if(e.getSource()==button1)System.exit(0);
        //if the easy button is pressed we create a Frame object which starts a new sudoku game
        //we pass in 0 to the Frame object which means we have chose to play an easy sudoku game
        else if(e.getSource()==easy){
            SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Frame(0).setVisible(true);
            }
            
        });
        }
        //if the medium button pressed we do the same but we pass 1 instead
        else if(e.getSource()==medium){
            SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Frame(1).setVisible(true);
            }
            
        });
        }
        //if the hard button is pressed we do the same and pass in 1
        else if(e.getSource()==hard){
            SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Frame(2).setVisible(true);
            }
            
        });
        }

    }
    
}
