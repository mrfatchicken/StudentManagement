import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

public class DbAccess {
    public DbAccess(){
        this.getInfo();
    }

    private String serverName, dataBaseName, user, password;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getURL() {
        String str = "jdbc:sqlserver://";
        str += serverName + ";databaseName=" + dataBaseName + ";user=" + user + ";password=" + password;
        return str;
    }
    public boolean testConnection(){
        try {


            Connection con = DriverManager.getConnection(this.getURL());
            if (con != null) {
                con.close();
                return true;
            }
            else return  false;

        } catch (Exception e) {
            return  false;
        }

    }
    public LinkedList<Student> getData() {
        LinkedList<Student> studentLinkedList = new LinkedList<Student>();
        try {

            Connection con = DriverManager.getConnection(this.getURL());
            if (con != null) {
                System.out.println("Connection Successful!");
                Statement statement = con.createStatement();
                String command = "Select * from STUDENT_DATA";
                ResultSet rs = statement.executeQuery(command);
                while(rs.next()){
                    Student temp= new Student();
                    temp.setId(rs.getString(1));
                    temp.setName(rs.getString("name"));
                    temp.setGPA(rs.getString("gpa"));
                    temp.setImg(rs.getString("img"));
                    temp.setAddress(rs.getString("address"));
                    temp.setNotes(rs.getString("notes"));
                    studentLinkedList.add(temp);
                }
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return studentLinkedList;
    }
    public void saveData(LinkedList<Student> studentLinkedList){
        try {

            Connection con = DriverManager.getConnection(this.getURL());
            if (con != null) {
                System.out.println("Connection Successful!");
                Statement statement = con.createStatement();
                String command = "DELETE FROM STUDENT_DATA";
                statement.execute(command);
                String insertCommand = "insert into STUDENT_DATA (id, name, gpa, img, address, notes) values (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(insertCommand);
                for(Student item: studentLinkedList){
                    preparedStatement.setString(1, item.getId());
                    preparedStatement.setString(2,item.getName());
                    preparedStatement.setFloat(3,item.getGPA());
                    preparedStatement.setString(4,item.getImg());
                    preparedStatement.setString(5,item.getAddress());
                    preparedStatement.setString(6,item.getNotes());
                    preparedStatement.execute();
                }
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void getInfo() {
        try (BufferedReader in = new BufferedReader(new FileReader("sqlserver.csv"))) {
            String str;
            str = in.readLine();
            String[] arrStr = str.split(",");
            serverName = arrStr[0];
            dataBaseName = arrStr[1];
            user = arrStr[2];
            password = arrStr[3];
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public void saveInfo(){
        FileWriter fileWrite;
        try{
            fileWrite = new FileWriter("sqlserver.csv");
            System.out.println("Writing connection info. ");
            String str = serverName+","+dataBaseName+","+user+","+password;
            fileWrite.write(str);
            System.out.println("Complete.");
            fileWrite.close();
        } catch (IOException e) {
            System.out.println(e);;
        }
    }

    public static void main(String[] arg) {

        DbAccess db = new DbAccess();
        db.getInfo();
        db.getData();

    }

}
