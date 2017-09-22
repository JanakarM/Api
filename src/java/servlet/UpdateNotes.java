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

public class UpdateNotes {

    public String un(Connection con, JSONObject json, String UserId) {
        String msg = "";
        PreparedStatement ps = null;
        String sql = "";
        ResultSet rs = null;
        String sql1 = "";
        char perm = '\0';
        String keys[] = {"name", "contents"};
        try {
            int i = 0;
            json.put("name",json.getString(keys[i])+"_"+UserId);
            while (i < 2) {
                sql1 += keys[i] + "='" + json.getString(keys[i]) + "'";
                if (i + 1 < 2) {
                    sql1 += ",";
                }
                i++;
            }
            ps = con.prepareStatement("select permission from registry where id=" + json.getString("id") + " and userid=" + UserId); //+ " and name='" + Note + "'");
            rs = ps.executeQuery();
            String check = "";
            try {
                if (rs.next()) {
                    perm = rs.getString(1).toCharArray()[0];
                }
            } catch (Exception e) {
                msg = "no note with id = " + json.getString("id") + " is available";
                return msg;
            }
            if (perm == 'r') {
                return "no write permissions granted";
            }
            sql = "update notes set " + sql1 + " where " + "id=" + json.getString("id");
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            msg = "notes with id= " + json.getString("id") + " was updated successfully";
        } catch (Exception e) {
            return sql;
        }
        return msg;
    }
}
