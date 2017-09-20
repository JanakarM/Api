/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
/**
 *
 * @author Janakar-PT1585
 */
public class DeleteNotes {

    public String dn(Connection con, String Note, String UserId) {
        PreparedStatement ps;
        String msg = "";
        ResultSet rs=null;
        try {
             ps = con.prepareStatement("select * from notes where id=" + UserId+ " and name='" + Note + "'");
            rs = ps.executeQuery();
            String check = "";
            try {
                rs.next();
                check = rs.getString(1);
            } catch (Exception e) {
                msg = "no note with name = "+Note+" is available for " + UserId;
                return msg;
            }
            ps = con.prepareStatement("delete from notes where id=" + UserId + " and name='" + Note + "'");
            ps.executeUpdate();
            msg = "note with name= '"+Note+"' for  id= '" + UserId + "'is deleted";
        } catch (Exception e) {
        }
        return msg;
    }
}
