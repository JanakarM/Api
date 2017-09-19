/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;
import java.sql.*;
import java.util.Iterator;
import org.json.JSONObject;
/**
 *
 * @author Janakar-PT1585
 */
public class AddEmployee {
   public AddEmployee()
   {
       
   }
   public String ae(Connection con,JSONObject json)
   {
       String msg="";
       PreparedStatement ps=null;
       String o[]={null};
       String sql="";
       try
       {
       Iterator it=json.keys();
       int i=0;
       while(it.hasNext())
       {
           String key=(String)it.next();
           o[i++]=json.getString(key);
       }
           sql = "insert into emp values('" +o[0]+"','"+o[1]+"','"+o[2]+"','"+o[3]+"',"+o[4]+","+o[5]+",'"+o[6]+"','"+o[7]+"','"+o[8]+"',"+o[9]+")";
       ps=con.prepareStatement(sql);
       ps.executeUpdate();
       msg="employee with id="+o[1]+"was added successfully";
       }catch(Exception e){}
   return msg;
   }
}
