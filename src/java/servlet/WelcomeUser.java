package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import java.sql.*;
import java.util.logging.*;
import org.json.JSONArray;
import org.json.JSONException;

public class WelcomeUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getServletPath();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        String msg;
        JSONObject jObj = null;
        JSONArray jarr = null;
        Logger LOGGER = Logger.getLogger(WelcomeUser.class.getName());
        LOGGER.log(Level.FINE, "processing {0} entries in loop", LOGGER.getName());
        try {

            LOGGER.log(Level.FINE, "processing {0} entries in loop");
            try {
                StringBuilder sb = new StringBuilder();
                BufferedReader br = request.getReader();
                String str;
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
                jObj = new JSONObject(sb.toString());
                LOGGER.log(Level.FINE, jObj.toString());
            } catch (IOException | JSONException e) {
            }
            DBConnector db = new DBConnector();
            Connection con = db.createDBConnection();
            if (null != uri) {
                switch (uri) {
                    case "/DeleteEmp": {
                        String UserId = (String) request.getSession().getAttribute("UId");
                        DeleteEmp del = new DeleteEmp();
                        msg = del.de(con, UserId);
                        request.getSession(false).removeAttribute("UId");
                        json.put("msg", "Logged Out");
                        // out.println();
                        out.println(constructWelcomeMsg(msg).toString() + json.toString());
                        break;
                    }
                    case "/AddEmp":
                        AddEmp AE = new AddEmp();
                        msg = AE.ae(con, jObj);
                        out.println(constructWelcomeMsg(msg).toString());
                        break;
                    case "/SelectEmp": {
                        SelectEmp SE = new SelectEmp();
                        String UserId;
                        try {
                            //  UserId = jObj.getString("UserId");
                            if (!((String) request.getSession().getAttribute("UId")).equals("")) {
                                UserId = (String) request.getSession().getAttribute("UId");
                                //if (UserId.indexOf(',') < 0) {
                                jarr = SE.se(con, jObj, UserId);
                                //}
                            }
                        } catch (Exception e) {

                            UserId = jObj.getString("UserId");
                            request.getSession(true).setAttribute("UId", UserId);
                            // String password=jObj.getString("password");
                            jarr = SE.se(con, jObj, "-99");
                            request.getSession(true).setAttribute("UId", UserId);
                        }
                        out.println(jarr);
                        break;
                    }
                    case "/AddNotes": {
                        jObj.put("UserId", request.getSession().getAttribute("UId"));
                        AddNotes AN = new AddNotes();
                        msg = AN.an(con, jObj, 'w', (String) request.getSession().getAttribute("UId"));
                        out.println(constructWelcomeMsg(msg).toString());
                        break;
                    }
                    case "/DeleteNotes": {
                        String Note = jObj.getString("id");
                        DeleteNotes del = new DeleteNotes();
                        msg = del.dn(con, Note, (String) request.getSession().getAttribute("UId"));
                        out.println(constructWelcomeMsg(msg).toString());
                        break;
                    }
                    case "/ShareNotes": {
                        AddNotes AN = new AddNotes();
                        msg = AN.an(con, jObj, 'r', (String) request.getSession().getAttribute("UId"));
                        out.println(constructWelcomeMsg(msg).toString());
                        break;
                    }
                    case "/UpdateEmp":
                        UpdateEmp UE = new UpdateEmp();
                        msg = UE.ue(con, jObj, (String) request.getSession().getAttribute("UId"));
                        out.println(constructWelcomeMsg(msg).toString());
                        break;
                    case "/SelectNotes": {
                        String UserId;
                        UserId = (String) request.getSession().getAttribute("UId");
                        SelectNotes SE1 = new SelectNotes();
                        jarr = SE1.sn(con, UserId);
                        out.println(jarr);
                        break;
                    }
                    case "/logout":
                        request.getSession(false).removeAttribute("UId");
                        json.put("msg", "Logged Out");
                        out.println(json.toString());
                        break;
                    case "/UpdateNotes":
                        //String Note = jObj.getString("id");
                        UpdateNotes UN = new UpdateNotes();
                        msg = UN.un(con, jObj, (String) request.getSession().getAttribute("UId"));
                        out.println(constructWelcomeMsg(msg).toString());
                        break;
                    default:
                        break;
                }
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
