package servlet;

import java.sql.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SelectNotes {

    public JSONArray sn(Connection con, String UserId) {
        JSONArray jarr = new JSONArray();
        JSONObject json = new JSONObject();
        try {
            jarr = new SelectQuery("*").from("notes").where("UserId", SelectQuery.conditions.equalto, UserId).select();
        } catch (Exception e) {
            json.put("msg", e + " from sn");
            jarr.put(0, json);
            return jarr;
        }
        return jarr;
    }
}
