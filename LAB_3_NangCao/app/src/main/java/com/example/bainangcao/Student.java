package com.example.bainangcao;

public class Student {
    private String Mssv;
    private String Hoten;
    private String Lop;

    public Student(String id, String fullName, String classes) {
        Mssv = id;
        Hoten = fullName;
        Lop = classes;
    }

    public Student() {

    }

    public String getmssv() {
        return Mssv;
    }

    public void setmssv(String id) {
        Mssv = id;
    }

    public String gethoten() {
        return Hoten;
    }

    public void sethoten(String fullName) {
        Hoten = fullName;
    }

    public String getlop() {
        return Lop;
    }

    public void setlop(String classes) {
        Lop = classes;
    }
}
