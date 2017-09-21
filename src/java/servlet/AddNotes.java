/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import org.json.JSONObject;

/**
 *
 * @author Janakar-PT1585
 */
public class AddNotes {

    public String an(Connection con, JSONObject json, char perm, String UserId) {
        String msg = "";
        PreparedStatement ps = null;
        ResultSet rs = null;
        String[] o = {"", "", "", "", ""};
        String sql = "";
        String keys[] = {"name", "UserId", "contents"};//, "age", "gender", "isregistered", "isverified", "place", "dept", "contact", "doj"};
        try {
            //Iterator it = json.keys();
            int i = 0;
            while (i < 2) {
                o[i] = json.getString(keys[i]);
                i++;
                // return o[0];
            }
            if (perm == 'w') {
                o[2] = json.getString(keys[2]);
                sql = "insert into notes values('" + o[0] + "_" + o[1] + "',DEFAULT,'" + o[2] + "')";//"," + o[2] + ",'" + o[3] + "'," + o[4] + "," + o[5] + ",'" + o[6] + "','" + o[7] + "','" + o[8] + "','" + o[9] + "',sha1('"+o[10]+"'))";
                ps = con.prepareStatement(sql);
                ps.executeUpdate();
            }
            sql = "select id from notes where name='" + o[0] + "_" + UserId + "'";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            String id = "";
            if (rs.next()) {
                id = rs.getString(1);
            }
            sql = "insert into registry values (" + o[1] + "," + id + ",'" + perm + "')";
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            msg = "a note with name = '" + o[0] + "' was added/shared for the UserId = '" + o[1] + "' successfully";
        } catch (Exception e) {
            return e.toString() + "from an";
        }
        return msg;
    }

}
