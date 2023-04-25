
package model;

import java.time.LocalDate;

/**
 *
 * @author ADMIN
 */
public class Student {
    private String id;
    private String name;
    private String gender;
    private LocalDate dob;
    private String classes;
    private String email;
    private String phone;
    private String address;

    public Student() {
    }

    public Student(String id, String name, String gender, LocalDate dob, String classes, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.classes = classes;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}
