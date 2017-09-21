/* ResultSetMetaData rsmd = rs.getMetaData();
            jarr = new JSONArray();
            int j = 0;
            while (rs.next()) {
                json = new JSONObject();
                i = 1;
                while (i < rsmd.getColumnCount()) {
                    json.put(rsmd.getColumnLabel(i), rs.getString(i) == null ? null : rs.getString(i));
                    i++;
                }
                jarr.put(j++, json);
            }
 
//            else {
//                sql = "select * from emp where id=" + UserId;
//            }
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.sql.*;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public class SelectEmp {

    public JSONArray se(Connection con, JSONObject jso, String UserId) {
        String msg = "";
        String sql = "";
        String sql1 = "";
        PreparedStatement ps = null;
        ResultSet rs = null;
        JSONArray jarr;
        JSONObject json;
        String keys = null;
        String o = null;
        json = new JSONObject();
        jarr = new JSONArray();
        try {

            if (UserId.equals("-99")) {
                jarr = new JSONArray();
                UserId = jso.getString("UserId");
                String password = jso.getString("password");
                sql = "select * from emp where id=" + UserId + " and password=sha1('" + password + "')";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                try {
                    if (rs.next()) {
                        json.put("msg", "login successful");
                        jarr.put(0, json);
                        return jarr;
                    } else {
                        json.put("msg", "login failed");
                        jarr.put(0, json);
                        return jarr;
                    }
                } catch (Exception e) {
                    json.put("msg", "login failed");
                    jarr.put(0, json);
                    return jarr;
                }
            } else {
                Iterator it = jso.keys();
                int i;
                try {
                    while (!(keys = (String) it.next()).equals("")) {
                        o = jso.getString(keys);
                        if (keys.equals("UserId") || keys.equals("age") || keys.equals("isregistered") || keys.equals("isverified")) {
                            sql1 += keys + " in (" + o + ") or ";

                        } else {
                            sql1 += keys + "=('" + o + "') or ";
                        }
                        // return jarr;
                    }
                } catch (Exception e) {//jarr.put(0,"err");
                }
                sql1 += "UserId=" + UserId;
                sql = "select * from emp where " + sql1;
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                jarr = new JSONArray();
                int j = 0;
                while (rs.next()) {
                    json = new JSONObject();
                    i = 1;
                    while (i <= rsmd.getColumnCount()) {
                        json.put(rsmd.getColumnLabel(i), rs.getString(i) == null ? null : rs.getString(i));
                        i++;
                    }
                    jarr.put(j++, json);
                }
                return jarr;

            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("msg", e + " from se");
            jarr.put(0, json);
            return jarr;
        }
        //return jarr;
    }
}
