/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import static com.sun.faces.facelets.util.Path.context;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import static java.nio.file.Files.list;
import static java.rmi.Naming.list;
import java.util.Enumeration;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import java.sql.*;
import static java.util.Collections.list;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;
import org.json.JSONArray;
import javax.servlet.http.HttpSession;

public class WelcomeUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getServletPath();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        String msg = "";
        //logger log=new logger();
        //context.log("");
        JSONObject jObj = null;
        JSONArray jarr = null;
        Logger LOGGER = Logger.getLogger(WelcomeUser.class.getName());
        LOGGER.log(Level.FINE, "processing {0} entries in loop", LOGGER.getName());
        try {

            LOGGER.log(Level.FINE, "processing {0} entries in loop", LOGGER.getName());
            try {
                StringBuilder sb = new StringBuilder();
                BufferedReader br = request.getReader();
                String str = null;
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
                jObj = new JSONObject(sb.toString());
            } catch (Exception e) {
            }
            DBConnector db = new DBConnector();
            Connection con = db.createDBConnection();
            if ("/DeleteEmp".equals(uri)) {
                String UserId = (String) request.getSession().getAttribute("UId");
                DeleteEmp del = new DeleteEmp();
                msg = del.de(con, UserId);
                out.println(constructWelcomeMsg(msg).toString());
            } else if ("/AddEmp".equals(uri)) {
                AddEmp AE = new AddEmp();
                msg = AE.ae(con, jObj);
                out.println(constructWelcomeMsg(msg).toString());
            } else if ("/SelectEmp".equals(uri)) {
                SelectEmp SE = new SelectEmp();
                 String UserId = "";
                try {
                  //  UserId = jObj.getString("UserId");
                    if (!(UserId=(String)request.getSession().getAttribute("UId")).equals("")) {
                       
                        //if (UserId.indexOf(',') < 0) {
                            jarr = SE.se(con, jObj, UserId);
                        //}
                    }
                } catch (Exception e) {
                    UserId = jObj.getString("UserId");
                    request.getSession(true).setAttribute("UId", UserId);
                    // String password=jObj.getString("password");
                    jarr = SE.se(con, jObj, "-99");
                }
                out.println(jarr);
            } else if ("/AddNotes".equals(uri)) {
                jObj.put("UserId", request.getSession().getAttribute("UId"));
                AddNotes AN = new AddNotes();
                msg = AN.an(con, jObj);
                out.println(constructWelcomeMsg(msg).toString());
            } else if ("/DeleteNotes".equals(uri)) {
                String Note = jObj.getString("name");
                DeleteNotes del = new DeleteNotes();
                msg = del.dn(con, Note, (String) request.getSession().getAttribute("UId"));
                out.println(constructWelcomeMsg(msg).toString());
            } else if (("/ShareNotes".equals(uri))) {
                AddNotes AN = new AddNotes();
                msg = AN.an(con, jObj);
                out.println(constructWelcomeMsg(msg).toString());
            } else if ("/UpdateEmp".equals(uri)) {
                UpdateEmp UE = new UpdateEmp();
                msg = UE.ue(con, jObj, (String) request.getSession().getAttribute("UId"));
                out.println(constructWelcomeMsg(msg).toString());
            } else if ("/SelectNotes".equals(uri)) {
                String UserId = (String) request.getSession().getAttribute("UId");
                SelectNotes SE = new SelectNotes();
                jarr = SE.se(con, UserId);
                out.println(jarr);
            } else if ("/logout".equals(uri)) {
                request.getSession().removeAttribute("UID");
                json.put("msg", "Logged Out");
                out.println(json.toString());
            }
        } catch (Exception e) {
            json.put("msgEx", e);
            out.println(json.toString());
        }
    }

    private JSONObject constructWelcomeMsg(String msg) {
        JSONObject json = new JSONObject();
        json.put("msg", msg);
        return json;
    }

}
