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
        } catch (Exception e) {
            return e.toString() + "from de";
        }
        return msg;
    }
}
