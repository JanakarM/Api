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
public class AddEmp {

    public AddEmp() {

    }

    public String ae(Connection con, JSONObject json) {
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
            sql = "insert into emp values('" + o[0] + "',DEFAULT," + o[1] + ",'" + o[2] + "'," + o[3] + "," + o[4] + ",'" + o[5] + "','" + o[6] + "','" + o[7] + "','" + o[8] + "',sha1('" + o[9] + "'))";
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            msg = "employee with name=" + o[0] + " was added successfully";
        } catch (Exception e) {
            return e.toString() + "from ae";
        }
        return msg;
    }
}
