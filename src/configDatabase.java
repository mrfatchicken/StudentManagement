import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class configDatabase extends JFrame {
    private JPanel panel1;
    private JButton connectButton;
    private JTextField serverNameField;
    private JTextField dbNameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextPane Status;
    private JPanel form;
    private JTextArea forExampleServerNameTextArea;

    public configDatabase(DbAccess db, form f) {
        this.setVisible(true);
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(serverNameField.getText().equals("")) Status.setText("Please fill server name field");
                else if(dbNameField.getText().equals("")) Status.setText("Please fill database name field");
                else if(usernameField.getText().equals("")) Status.setText("Please fill username field");
                else {
                    db.setDataBaseName(serverNameField.getText());
                    db.setDataBaseName(dbNameField.getText());
                    db.setUser(usernameField.getText());
                    String str = String.valueOf(passwordField.getPassword());
                    db.setPassword(str);
                    if(!db.testConnection()){
                        f.setStatus("Cannot connect to server.");
                    }
                    else {
                        db.saveInfo();
                        f.updateScreenList();
                        f.setStatus("Connect to server successfully.");
                    }
                    dispose();
                }
            }
        });
    }
    public static  void main(String arg[]){

    }
}
