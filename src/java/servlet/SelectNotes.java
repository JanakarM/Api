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

    public JSONArray sn(Connection con, String UserId) {
        String msg = "";
        String sql = "";
        String sql1 = "";
        PreparedStatement ps = null;
        ResultSet rs = null;
        JSONArray jarr;
        JSONObject json;
        jarr = new JSONArray();
        json = new JSONObject();
        try {
//            if (UserId.equals("")) {
//                sql = "select * from notes";
//            } else {
            sql = "select distinct(id) from registry where UserId=" + UserId;
            //}

            int j = 0;
            try {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    sql1 += rs.getString(1);
                    if (rs.next()) {
                        sql1 += ",";
                        rs.previous();
                    }
                }
            } catch (Exception e1) {
                json.put("msg1", e1);
                jarr.put(0, json);
                return jarr;
            }
            if (sql1.equals("")) {
                json.put("msg", "no notes for this id");
                jarr.put(0, json);
                return jarr;
            }
            try {
                sql = "select * from notes where id in (" + sql1 + ")";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    json = new JSONObject();
                    int i = 1;
                    while (i <= rsmd.getColumnCount()) {
                        json.put(rsmd.getColumnLabel(i), rs.getString(i) == null ? null : rs.getString(i));
                        i++;
                    }
                    jarr.put(j++, json);
                }
            } catch (Exception e2) {
                json.put("sql", sql);
                jarr.put(0, json);
                return jarr;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            json.put("msg", e + " from sn");
            jarr.put(0, json);
            return jarr;
        }
        return jarr;
    }
}
