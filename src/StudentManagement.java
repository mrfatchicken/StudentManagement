import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;


/**
 * PACKAGE_NAME
 * Created by Admin
 * Date 10/24/2021 - 10:40 PM
 * Description: ...
 */
public class StudentManagement {
    private LinkedList<Student> studentList = new LinkedList<Student>();

    public static void main(String[] arg) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, "utf8"));
        StudentManagement studentManagement = new StudentManagement();
        studentManagement.importCSV("input.csv");
        System.out.println("Import input.csv complete.");
        int choice = 0;
        do {
            System.out.println("\t\t~~Menu~~");
            System.out.println("1.Add student");
            System.out.println("2.View all student");
            System.out.println("3.Update student");
            System.out.println("4.Delete student");
            System.out.println("5.Import csv");
            System.out.println("6.Export file");
            System.out.println("6.Export csv");
            System.out.println("8.Save database");
            System.out.println("9.Exit");
            System.out.println("Enter your choice: ");
            choice = Integer.parseInt(bufferedReader.readLine());
            switch (choice) {
                case 1 -> studentManagement.addStudent();
                case 2 -> studentManagement.printAllStudent();
                case 3 -> studentManagement.updateStudent();
                case 4 -> studentManagement.deleteStudent();
                case 5 -> {
                    String str;
                    System.out.println("Enter filename: ");
                    str = bufferedReader.readLine();
                    studentManagement.importCSV(str);
                }
                case 6 -> {
                    String str;
                    System.out.println("Enter filename: ");
                    str = bufferedReader.readLine();
                    studentManagement.exportCSV(str);
                }
                case 7 -> {
                    String str;
                    System.out.println("Enter filename: ");
                    str = bufferedReader.readLine();
                    str += ".csv";
                    studentManagement.exportCSV(str);
                }
                case 8 -> studentManagement.saveDB();

            }
            System.out.println("Press enter to continue...");
            try{bufferedReader.readLine();}
            catch(Exception ignored){}

        }
        while (choice != 9);
        bufferedReader.close();
    }

    /**
     * constructor
     * @param studentList
     */
    public StudentManagement(LinkedList<Student> studentList) {
        this.studentList = studentList;
    }
    public StudentManagement(){LinkedList<Student> studentList = new LinkedList<Student>();};
    public LinkedList<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(LinkedList<Student> studentList) {
        this.studentList = studentList;
    }

    /**
     * add a new student to system
     * @throws IOException
     */
    public void addStudent() throws IOException {
        Student temp = new Student();
        String str;
        float f;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, "utf8"));
        do {
            try {
                System.out.print("Enter student's id: ");
                str = bufferedReader.readLine();
                System.out.println(str);
                temp.setId(str);
                if (checkIdExist(temp.getId())) System.out.println("This ID has already in use. Please re-enter.");
            }
            catch (Exception e){
                System.out.println("Invalid student's id. Please try again.");
            }
        } while (checkIdExist(temp.getId()) || temp.getId() == null);
        System.out.print("Enter student's name: ");
        str = bufferedReader.readLine();
        temp.setName(str);

        do{
            System.out.print("Enter student's GPA: ");
            str = bufferedReader.readLine();

            if (!checkParseGPA(str)) System.out.println("Invalid GPA. Please try again.");
            else {
                f = Float.parseFloat(str);
                temp.setGPA(f);
            }
        }  while (!checkParseGPA(str));


        System.out.print("Enter student's img link: ");
        str = bufferedReader.readLine();
        temp.setImg(str);

        System.out.print("Enter student's address: ");
        str = bufferedReader.readLine();
        temp.setAddress(str);

        System.out.print("Enter student's notes: ");
        str = bufferedReader.readLine();
        temp.setNotes(str);

        studentList.add(temp);

    }

    /**
     * print all students in system to the console
     * @throws IOException
     */
    public void printAllStudent() throws  IOException{
        System.out.println("1. View students with students' id in ascending order.");
        System.out.println("2. View students with students' GPA in ascending order.");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, "utf8"));
        System.out.print("Enter choice : ");
        int choice = Integer.parseInt(bufferedReader.readLine());
        switch (choice){
            case 1 ->{
                viewIdAscending();
            }
            case 2 ->{
                viewGPAAscending();
            }
        }


    }

    /**
     * print all student as ascending order of ID
     */
    public void viewIdAscending(){
        LinkedList secList = new LinkedList();
        secList = (LinkedList)studentList.clone();
        Collections.sort(secList, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Integer.parseInt(s1.getId())- Integer.parseInt(s2.getId());
            } } );

        if (secList.isEmpty()) return;
        for(Object item: secList){
            System.out.println(item.toString());
        }
    }
    /**
     * print all student as ascending order of GPA
     */
    public void viewGPAAscending(){
        LinkedList secList = new LinkedList();
        secList = (LinkedList)studentList.clone();
        Collections.sort(secList, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Float.compare(s1.getGPA(), s2.getGPA());
            } } );

        if (secList.isEmpty()) return;
        for(Object item: secList){
            System.out.println(item.toString());
        }
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
            return  true;
        }
        catch (Exception e){
            return  false;
        }
    }

    /**
     * delete a student
     * @throws IOException
     */
    public void deleteStudent() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        System.out.print("Enter student's id to delete: ");
        String str = bufferedReader.readLine();
        if(delete(str)) System.out.println("Delete successfully. ");
        else System.out.println("This id doesn't exist. ");

    }

    /**
     * update a student in the system
     * @throws Exception
     */
    public void updateStudent() throws Exception{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, "utf8"));
        System.out.print("Enter student's id to update: ");
        String str = bufferedReader.readLine();
        if(checkIdExist(str)){

            for (Student item: studentList){
                if (Objects.equals(item.getId(), str)){
                    System.out.println("1. Update name.");
                    System.out.println("2. Update GPA.");
                    System.out.println("3. Update img.");
                    System.out.println("4. Update address.");
                    System.out.println("5. Update notes.");
                    System.out.println("Enter choice: ");
                    int choice =Integer.parseInt(bufferedReader.readLine()) ;
                    switch (choice){
                        case 1:
                            System.out.print("Enter student's name: ");
                            str = bufferedReader.readLine();
                            item.setName(str);
                            break;
                        case 2:
                            do{
                                System.out.print("Enter student's GPA: ");
                                str = bufferedReader.readLine();

                                if (!checkParseGPA(str)) System.out.println("Invalid GPA. Please try again.");
                                else {
                                    float f = Float.parseFloat(str);
                                    item.setGPA(f);
                                }
                            }  while (!checkParseGPA(str));
                            break;
                        case 3:
                            System.out.print("Enter student's img link: ");
                            str = bufferedReader.readLine();
                            item.setImg(str);
                            break;
                        case 4:
                            System.out.print("Enter student's address: ");
                            str = bufferedReader.readLine();
                            item.setAddress(str);
                            break;
                        case 5:
                            System.out.print("Enter student's notes: ");
                            str = bufferedReader.readLine();
                            item.setNotes(str);
                            break;

                    }

                }

            }

            System.out.println("Update complete. ");
        }
        else{
            System.out.println("This id doesn't exist. ");
        }

    }
    private boolean delete(String id){
        for(int i = 0 ; i < studentList.size(); i++){

            if(Objects.equals(studentList.get(i).getId(), id)) {
                studentList.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param filename name of file
     */
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

    /**
     *
     * @param filename name of file
     */
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

    /**
     * save database
     */
    public void saveDB(){
        exportCSV("input.csv");
        System.out.println("Save complete.");
    }
}
