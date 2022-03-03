import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;

public class form extends JFrame{
    private JPanel panel1;
    private JButton viewByScoreButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton viewByIDbutton;
    private JTextPane Status;
    private JButton addStudentButton;
    private JPanel Up;
    private JPanel Left;
    private JPanel Center;
    private JScrollPane js;
    private JButton saveToDBButton;
    private LinkedList<Student> studentList = new LinkedList<Student>();
    private JFrame temp;
    private JMenuBar menuBar = new JMenuBar();
    private DbAccess db = new DbAccess();
    JTable list;


    public void updateScreenList(){
        String dat[][] = new String[studentList.size()][6];
        for (int i = 0; i< dat.length; i++){
            String temp[] = new String[6];
            temp[0] = studentList.get(i).getId();
            temp[1] = studentList.get(i).getName();
            temp[2] = String.valueOf(studentList.get(i).getGPA()) ;
            temp[3] = studentList.get(i).getImg();
            temp[4] = studentList.get(i).getAddress();
            temp[5] = studentList.get(i).getNotes();
            dat[i] = temp;
        }


        String column[] = { "ID", "NAME", "GPA","IMG","ADDRESS","NOTE" };

            DefaultTableModel model = new DefaultTableModel(dat, column) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) return false;
                else return true;
            }
        };


        list = new JTable(model);



        js.setViewportView(list);
        Center.add(js);
    }

    public void importCSV(String filename){
        studentList.clear();
        try(BufferedReader in = new BufferedReader(new FileReader(filename))){
            String str;
            while((str = in.readLine()) != null){
                Student temp = new Student();
                String[] arrStr = str.split(",");
                temp.setId(arrStr[0]);
                temp.setName(arrStr[1]);
                temp.setGPA(arrStr[2]);
                temp.setImg(arrStr[3]);
                temp.setAddress(arrStr[4]);
                temp.setNotes(arrStr[5]);
                studentList.add(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void exportCSV(String filename){

        FileWriter fileWrite;
        try{
            fileWrite = new FileWriter(filename);
            System.out.println("Writing to file");
            for(Student item: studentList){
                String str = item.getId()+","+item.getName()+","+item.getGPA()+","+item.getImg()+","+item.getAddress()+","+item.getNotes()+"\n";
                fileWrite.write(str);
            }
            System.out.println("Complete. See result at " + filename);
            fileWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean update(int index)  {
        try {
            studentList.get(index).setName((String) list.getValueAt(index,1));
            studentList.get(index).setGPA((String) list.getValueAt(index,2));
            studentList.get(index).setImg((String) list.getValueAt(index,3));
            studentList.get(index).setAddress((String) list.getValueAt(index,4));
            studentList.get(index).setNotes((String) list.getValueAt(index,5));
        }
        catch (Exception e){
            return false;
        }



        return true;
    }
    private boolean delete(int index){
        try {
            studentList.remove(index);
        }
        catch (Exception err){
            return false;
        }
        return true;
    }
    /**
     * print all student as ascending order of ID
     */
    public void viewIdAscending(){
        if (studentList.isEmpty()) return;
        Collections.sort(studentList, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Integer.parseInt(s1.getId())- Integer.parseInt(s2.getId());
            } } );

    }
    /**
     * print all student as ascending order of GPA
     */
    public void viewGPAAscending(){
        if (studentList.isEmpty()) return;
        Collections.sort(studentList, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Float.compare(s1.getGPA(), s2.getGPA());
            } } );



    }

    public boolean checkIdExist(String id){
        if (studentList.isEmpty()) return false;
        for(Student item: studentList){
            if (Objects.equals(item.getId(), id)) return true;
        }
        return false;
    }
    private boolean checkParseGPA(String gpa){
        try {
            float f = Float.parseFloat(gpa);
            if (f >= 0.0 && f <= 4.0) return true;
            return false;
        }
        catch (Exception e){
            return  false;
        }
    }

    /**
     * save database
     */
    public void saveDB(){
        exportCSV("input.csv");
        System.out.println("Save complete.");
    }
    private void prepareMenubar(){
        JMenu fileMenu = new JMenu("File");
        JMenu dbMenu = new JMenu("Database");
        JMenuItem importCSV = new JMenuItem("Import CSV");
        importCSV.setActionCommand("import");
        JMenuItem exportCSV = new JMenuItem("Export CSV");
        exportCSV.setActionCommand("export");
        JMenuItem saveCSV = new JMenuItem("Save");
        exportCSV.setActionCommand("save");

        JMenuItem settingConnect =  new JMenuItem("Config connection");
        settingConnect.setActionCommand("configDB");
        JMenuItem connectDefault =  new JMenuItem("Connect with database");
        connectDefault.setActionCommand("connectDB");
        JMenuItem saveDB =  new JMenuItem("Save data to database");
        connectDefault.setActionCommand("saveDB");

        fileMenu.add(importCSV);
        fileMenu.add(exportCSV);
        fileMenu.add(saveCSV);

        dbMenu.add(settingConnect);
        dbMenu.add(connectDefault);
        dbMenu.add(saveDB);
        settingConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configDatabase conf = new configDatabase(db, (form) temp);
            }
        });
        saveDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(db.testConnection()){
                    db.saveData(studentList);
                    Status.setText("Save database complete");
                }
                else {
                    Status.setText("Cannot connect to server");
                }
            }
        });
        connectDefault.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(db.testConnection()){
                    studentList.clear();
                    studentList = db.getData();
                    updateScreenList();
                    saveToDBButton.setVisible(true);
                    Status.setText("Load database complete");
                }
                else {
                    Status.setText("Cannot connect to server");
                }
            }
        });
        exportCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileDialog = new JFileChooser(System.getProperty("user.dir"));
                fileDialog.setDialogType(JFileChooser.SAVE_DIALOG);
                fileDialog.setDialogTitle("Save file");
                FileNameExtensionFilter filter = new FileNameExtensionFilter(".csv", "txt", "text", "csv");
                fileDialog.setFileFilter(filter);
                int returnVal = fileDialog.showSaveDialog(temp);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    java.io.File file = fileDialog.getSelectedFile();
                    exportCSV(file.getPath()+".csv");
                    Status.setText(file.getName() + ".csv is exported successfully");
                } else {

                }
            }
        });
        importCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileDialog = new JFileChooser(System.getProperty("user.dir"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter(".csv .txt", "txt", "text", "csv");
                fileDialog.setFileFilter(filter);
                fileDialog.setDialogTitle("Open csv file");
                int returnVal = fileDialog.showOpenDialog(temp);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    java.io.File file = fileDialog.getSelectedFile();
                    importCSV(file.getPath());
                    updateScreenList();
                    Status.setText(file.getName() + " loaded.");
                } else {

                }
            }
        });
        saveCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDB();
                Status.setText("Save successfully.");
            }
        });
        menuBar.add(fileMenu);
        menuBar.add(dbMenu);
        this.setJMenuBar(menuBar);
    }

    public void setStatus(String str){
        Status.setText(str);
    }
    public form() {
        temp = this;
        this.prepareMenubar();
        importCSV("input.csv");
        updateScreenList();
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        Status.setText("MAI HOANG ANH - 19127331");
        saveToDBButton.setVisible(false);

        viewByIDbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewIdAscending();
                updateScreenList();
                Status.setText("Reload successfully.");
            }
        });
        viewByScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewGPAAscending();
                updateScreenList();
                Status.setText("Reload successfully.");
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(delete(list.getSelectedRow())){
                    //DB

                    //update list
                    updateScreenList();
                    Status.setText("Delete successfully.");
                }
                else {
                    Status.setText("Please select row you want to delete.");
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = true;
                String falseID ="";
                for(int i = 0; i < studentList.size(); i++){

                    if(checkParseGPA((String) list.getValueAt(i,2))) {
                        update(i);
                    }
                    else {
                        falseID = (String) list.getValueAt(i,0);
                        flag = false;
                    }
                }
                updateScreenList();
                if(flag) {
                    Status.setText("Update successfully");
                }
                else {
                    Status.setText(falseID + "'s GPA must between 0.0 and 4.0. Please try again.");
                }
            }
        });
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student tempStudent = new Student();
                addStudent as = new addStudent(tempStudent, studentList, (form) temp);

            }
        });
        saveToDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(db.testConnection()){
                    db.saveData(studentList);
                    Status.setText("Save database complete");
                }
                else {
                    Status.setText("Cannot connect to server");
                }
            }
        });
    }

    public static void main(String[] arg){
        form screen = new form();

        screen.setVisible(true);

    }
}
