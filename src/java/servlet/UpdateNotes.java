/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;
import org.json.JSONObject;

/**
 *
 * @author Janakar-PT1585
 */
public class UpdateNotes {
    public String un(Connection con,JSONObject json) {
        String msg = "";
        PreparedStatement ps = null;
         String sql = "";
         String sql1 = "";
         //String keys="";
//        String[] o = {"", "", "", "", "", "", "", "", "", "", "", ""};
//       
       String keys[] = {"name", "contents"}; //"gender", "isregistered", "isverified", "place", "dept", "contact", "doj", "password"};
        try {
           // Iterator it = json.keys();
            int i = 0;
            while (i<2) {
//                o[i] = json.getString(keys[i]);
sql1+=keys[i]+"='"+json.getString(keys[i])+"'";
  if(i+1<2)
      sql1+=",";
               i++;
            }
            //sql1+="id="+json.getString("id");
            sql = "update notes set "+sql1+" where " +"id="+json.getString("id"); //+ o[0] + "',age=" + o[1] + ",gender='" + o[2] + "',isregistered=" + o[3] + ",isverified=" + o[4] + ",place='" + o[5] + "',dept='" + o[6] + "',contact='" + o[7] + "',doj='" + o[8] + "',password=sha1('" + o[9] + "') where UserId=" + UserId;
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            msg = "notes with id= "+json.getString("id")+" was updated successfully";
        } catch (Exception e) {
            return sql;
        }
        return msg;
    }
}
