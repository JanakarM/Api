/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.sql.*;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author Janakar-PT1585
 */
public class DeleteEmp extends HttpServlet {

    public DeleteEmp() {

    }

    public String de(Connection con, String UserId) {
        PreparedStatement ps;
        ResultSet rs = null;
        String msg = "";
        String sql1 = "";
        String sql = "";
        try {
            try {
                sql = "delete from emp where UserId = " + UserId;
                ps = con.prepareStatement(sql);
                ps.executeUpdate();
            } catch (Exception e8) {
                return e8.toString() + "e8";
            }
            sql = "select distinct(id) from registry where userid=" + UserId;
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
                return UserId + " " + e1.toString();
            }

            ps = con.prepareStatement("delete from registry where id in (" + sql1 + ")");
            ps.executeUpdate();
            ps = con.prepareStatement("delete from notes where id in (" + sql1 + ")");
            ps.executeUpdate();

            msg = "employee with UserId= '" + UserId + "' is deleted and his notes are removed";

        } catch (Exception e) {
            return e.toString() + "from de";
        }
        return msg;
    }
}
