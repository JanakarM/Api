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
public class UpdateEmp {

    public String ue(Connection con, JSONObject json, String UserId) {
        String msg = "";
        PreparedStatement ps = null;
        String[] o = {"", "", "", "", "", "", "", "", "", "", "", ""};
        String sql = "";
        String keys[] = {"name", "age", "gender", "isregistered", "isverified", "place", "dept", "contact", "doj", "password"};
        try {
            Iterator it = json.keys();
            int i = 0;
            while (i < 10) {
                o[i] = json.getString(keys[i]);
                i++;
            }
            sql = "update emp set name='" + o[0] + "',age=" + o[1] + ",gender='" + o[2] + "',isregistered=" + o[3] + ",isverified=" + o[4] + ",place='" + o[5] + "',dept='" + o[6] + "',contact='" + o[7] + "',doj='" + o[8] + "',password=sha1('" + o[9] + "') where UserId=" + UserId;
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            msg = "employee details for the UserId= '" + UserId + "' was updated successfully";
        } catch (Exception e) {
            return e.toString() + "from ue";
        }
        return msg;
    }
}
