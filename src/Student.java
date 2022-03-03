import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * PACKAGE_NAME
 * Created by Admin
 * Date 10/24/2021 - 9:32 PM
 * Description: ...
 */
public class Student {
    private String id;
    private String name;
    private float GPA;
    private String img;
    private String address;
    private String notes;

    /**
     * default constructor
     */
    public Student(){
        this.id = null;
        this.name = null;
        this.GPA = 0;
        this.img = null;
        this.address = null;
        this.notes = null;
    }

    /**
     *
     * @param id
     * @param name
     * @param GPA
     * @param img
     * @param address
     * @param notes
     */
    public Student(String id, String name, float GPA, String img, String address, String notes) {
        this.id = id;
        this.name = name;
        this.GPA = GPA;
        this.img = img;
        this.address = address;
        this.notes = notes;
    }

    /**
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getGPA() {
        return GPA;
    }

    public String getImg() {
        return img;
    }

    public String getAddress() {
        return address;
    }

    public String getNotes() {
        return notes;
    }

    /**
     *
     * @param id ID of the student
     * @throws IOException
     */
    public boolean setId(String id) throws IOException {
        if (checkValidID(id)) return true;
        else return false;
    }

    /**
     *
     * @param name name of the student
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param GPA gpa grade of the student
     * @throws IOException
     */
    public boolean setGPA(float GPA) throws IOException {

        return checkValidGPA(GPA);

    }

    /**
     *
     * @param gpa Overload for a string type
     * @throws IOException
     */
    public boolean setGPA(String gpa)  {
        try {
            float GPA = Float.parseFloat(gpa);
            return checkValidGPA(GPA);
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     *
     * @param img link to the image
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     *
     * @param address where student live
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @param notes note for st
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     *
     * @param str
     * @return check if ID valid
     */
    private boolean checkValidID(String str){
        try {
            int temp = Integer.parseInt(str);
            this.id = str;
            return true;
        }
        catch (Exception e){
            System.out.println("Invalid id. ");
            return false;
        }
    }
    /**
     *
     * @param gpa
     * @return check if GPA valid
     */
    private boolean checkValidGPA(float gpa){
        if (gpa >= 0 && gpa <= 4) {this.GPA = gpa; return true;}
        else return false;
    }


    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", GPA=" + GPA +
                ", img='" + img + '\'' +
                ", address='" + address + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
