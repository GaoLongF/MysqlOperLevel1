package com.soilder;

public class Students
{
    private int id;
    private String name;
    private int classnum;
    private char sex;
    @Override
    public String toString() {
        return "学生信息为：" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", classnum=" + classnum +
                ", sex=" + sex ;
    }

    public Students(int id, String name, int classnum, char sex) {
        this.id = id;
        this.name = name;
        this.classnum = classnum;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getClassnum() {
        return classnum;
    }

    public char getSex() {
        return sex;
    }



}
