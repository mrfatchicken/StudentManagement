import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;

public class addStudent extends JFrame {
    private JTextField idField;
    private JTextField nameField;
    private JTextField gpaField;
    private JTextField imgField;
    private JTextField adrField;
    private JTextField noteField;
    private JButton addButton;
    private JTextPane status;
    private JPanel panel1;
    private JTextPane notice;

    public addStudent(Student temp, LinkedList<Student> studentList, form f) {

        this.setVisible(true);
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean flag = true;
                    temp.setId(idField.getText());
                    if (!temp.setId(idField.getText())){
                        notice.setText("ID must contains only numbers");
                        flag = false;
                    }
                    temp.setName(nameField.getText());
                    if (!temp.setGPA(gpaField.getText())){
                        notice.setText("GPA must between 0.0 and 4.0");
                        flag = false;
                    }
                    temp.setImg(imgField.getText());
                    temp.setAddress(adrField.getText());
                    temp.setNotes(noteField.getText());

                    if (flag){
                        if (!f.checkIdExist(temp.getId())) {
                            studentList.add(temp);
                            f.setStatus("Add successfully.");
                        }
                        else f.setStatus("This ID exists.");
                        setVisible(false);
                        f.updateScreenList();
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    public static void main(String[] arg){

    }
}
