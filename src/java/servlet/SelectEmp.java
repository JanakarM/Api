/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;
import java.sql.*;
import org.json.JSONArray;
import org.json.JSONObject;
public class SelectEmp {
public JSONArray se(Connection con,String UserId)
{
       String msg="";
       String sql="";
       PreparedStatement ps=null;
       ResultSet rs=null;
       JSONArray jarr=null;
       JSONObject json;
       try
       {
           sql="select * from emp ";
           ps=con.prepareStatement(sql);
           rs=ps.executeQuery();
           ResultSetMetaData rsmd = rs.getMetaData();
           jarr=new JSONArray();
           int j=0;
           while(rs.next())
           {
               json=new JSONObject();
               int i=0;
               while(i<rsmd.getColumnCount())
               {
                   json.put(rsmd.getColumnLabel(i),rs.getString(i)==null? null:rs.getString(i));
               }
               jarr.put(j++,json);
           }
       }catch(Exception e){
       e.printStackTrace();}
return jarr;
}
}
