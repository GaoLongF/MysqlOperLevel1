package com.soilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//该类实现增删改查，创建表的功能
public class MyOper
{
    private  Connection conn=null;
    private  PreparedStatement ps=null;
    private  ResultSet rs=null;

    public Connection getConn() {
        return conn;
    }

    public PreparedStatement getPs() {
        return ps;
    }

    public ResultSet getRs() {
        return rs;
    }

    public MyOper() {
        this.conn =JDBCUtil.getConnection();
    }

    //创建表
    public void createTable() throws SQLException
    {
        String sql="create table if not exists students(id int,name varchar(20),classnum int,sex varchar(1));";
        ps=conn.prepareStatement(sql);
        ps.executeUpdate();
    }

    //插入操作通过创建可变参数插入学生对象
    public  void insert(Students...std) throws SQLException
    {
        String sql = "insert into students(id,name,classnum,sex) values (?,?,?,?)";
        ps= conn.prepareStatement(sql);
       for (int i=0;i<std.length;i++) {
            ps.setObject(1, std[i].getId());
            ps.setObject(2, std[i].getName());
            ps.setObject(3, std[i].getClassnum());
            ps.setObject(4, std[i].getSex()+"");
            ps.executeUpdate();
       }
    }

    //删除操作
    public  void delete(int id) throws SQLException {
        String sql="delete from students where id=? ";
        ps=conn.prepareStatement(sql);
        ps.setObject(1,id);
        ps.executeUpdate();
    }

    //查找数据
    public void select(Students std) throws SQLException {
        String sql="select id,name,classnum,sex from students where id=?";
        ps=conn.prepareStatement(sql);
        ps.setInt(1,std.getId());
        rs=ps.executeQuery();
        if(rs.next())
        {
            int id=rs.getInt("id");
            String name=rs.getString("name");
            int classnum=rs.getInt("classnum");
            String sex=(String) rs.getObject("sex");
            System.out.println("学生信息为：" +"\n" +
                    "id=" + id + ", name=" + name + " , classnum=" + classnum + " , sex=" + sex);
        }
    }

    //更改学生信息（如：班级）
    public  void upDate(Students std,int num) throws SQLException {
        String sql="update students set classnum='" + num + "' where id='" + std.getId() + "'";
        ps=conn.prepareStatement(sql);
        ps.executeUpdate();
    }
    public static void main(String[] args)
    {
        //创建学生信息
        Students zs=new Students(201801254,"张三",1,'男');
        Students ls=new Students(201702726,"李四",2,'男');
        Students ww=new Students(201703791,"王五",3,'男');
        Students llr=new Students(201604474,"老腊肉",4,'男');
        Students xjj=new Students(201705214,"小姐姐",5,'女');
        //建立连接
        MyOper myOper=new MyOper();
        try {
            myOper.createTable();       //创建表
            myOper.insert(zs,ls,ww,llr,xjj);    //插入学生信息
            myOper.delete(llr.getId());         //通过学生id删除该学生记录
            myOper.upDate(zs,10);            //更改学生的班级
            myOper.select(xjj);             //查找学生信息
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
