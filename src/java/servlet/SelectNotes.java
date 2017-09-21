/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Janakar-PT1585
 */
public class SelectNotes {
    public JSONArray se(Connection con, String UserId) {
        String msg = "";
        String sql = "";
        PreparedStatement ps = null;
        ResultSet rs = null;
        JSONArray jarr = null;
        JSONObject json=null;
        try {
//            if (UserId.equals("")) {
//                sql = "select * from notes";
//            } else {
                sql = "select name from notes where id=" + UserId;
            //}
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            jarr = new JSONArray();
            int j = 0;
            while (rs.next()) {
                json = new JSONObject();
                int i = 1;
                while (i <= rsmd.getColumnCount()) {
                    json.put(rsmd.getColumnLabel(i), rs.getString(i) == null ? null : rs.getString(i));
                    i++;
                }
                jarr.put(j++, json);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            json.put("msg",e);
            jarr.put(0,json);
            return jarr;
        }
        return jarr;
    }
}
