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
        String columns[]=new String[12];
        String values[]=new String[12];
        String keys;
     try {
         Iterator it = json.keys();
            int i = 0;
            while (it.hasNext()) {
               keys=(String)it.next();
               columns[i]=keys;
               values[i]=json.getString(keys);
                i++;
            }
           msg=new InsertQuery("emp").specifyColumns(columns).values(values).insert();
        } 
     catch (Exception e) {
            msg=e.toString() + "from ae";
        }
        return msg;
    }
}
