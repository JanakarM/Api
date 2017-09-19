/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import java.sql.*;
import org.json.JSONArray;

public class WelcomeUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       response.setContentType("application/json");
response.setCharacterEncoding("utf-8");
        PrintWriter out=response.getWriter(); 
        JSONObject json=new JSONObject();
        String msg="";
        try
       {
           String uri=request.getRequestURI();
          StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str = null;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        JSONArray jarr;
        JSONObject jObj = new JSONObject(sb.toString());
        DBConnector db= new DBConnector();
         Connection con= db.createDBConnection();
         if("/WelcomeUser/DeleteEmp".equals(uri))
         {
         String UserId = jObj.getString("UserId");   
         DeleteEmp del=new DeleteEmp();
         msg=del.de(con,UserId);
         out.println(constructWelcomeMsg(msg).toString());
         }
         else if("/WelcomeUser/AddEmployee".equals(uri))
         {
             AddEmployee AE=new AddEmployee();
           msg=AE.ae(con,jObj) ;
           out.println(constructWelcomeMsg(msg).toString());
         }
         else if("/WelcomeUser/SelectEmp".equals(uri))
         {
         String UserId = jObj.getString("UserId");  
         SelectEmp se=new SelectEmp();
         jarr=se.se(con,UserId);
         out.println(jarr);
         }
       }
       catch(Exception e)
       {
           json.put("msg",e);
           out.println(json);
       }
    }
  private JSONObject constructWelcomeMsg(String msg)
{
 JSONObject json = new JSONObject();
 json.put("msg",msg);
    return json;
}
  
}
