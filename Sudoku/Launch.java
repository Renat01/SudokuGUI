
import javax.swing.SwingUtilities;

//this class contains the main method which starts the whole program
public class Launch {
    public static void main(String[] args) {

        //we create an instance of the MainFrame class which creates the main frame of the game
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
            
        });

    }

}
