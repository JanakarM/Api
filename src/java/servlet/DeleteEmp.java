/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.sql.*;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author Janakar-PT1585
 */
public class DeleteEmp extends HttpServlet  {
public DeleteEmp()
{
  
}
 public String de(Connection con,String UserId)
 {
     PreparedStatement ps ;
     String msg="";
     try
     {
            ps = con.prepareStatement("delete from emp where id="+UserId);
            ps.executeUpdate();
            msg="employee with id='"+UserId+"'is deleted";
     }catch(Exception e){}
       return msg;
 }
}
