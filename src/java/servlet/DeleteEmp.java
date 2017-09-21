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
        try {
            ps = con.prepareStatement("select * from emp where UserId=" + UserId);
            rs = ps.executeQuery();
            String check = "";
            try {
                rs.next();
                check = rs.getString(1);
            } catch (Exception e) {
                msg = "no entry for " + UserId;
                return msg;
            }

            ps = con.prepareStatement("delete from notes where UserId=" + UserId);
            ps.executeUpdate();
            ps = con.prepareStatement("delete from emp where UserId=" + UserId);
            ps.executeUpdate();

            msg = "employee with UserId= '" + UserId + "' is deleted and his notes are removed";

        } catch (Exception e) {
            return e.toString() + "from de";
        }
        return msg;
    }
}
