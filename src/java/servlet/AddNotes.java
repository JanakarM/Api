
package servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public class AddNotes {

    public String an(Connection con, JSONObject json, char perm, String UserId) {
        String msg = "";
        PreparedStatement ps = null;
        ResultSet rs = null;
        String[] o = new String[5];
        String sql = "";
         String columns[]=new String[12];
        String values[]=new String[12];
        String keys;//[] = {"name", "UserId", "contents"};
        try {
                 int i = 0;
          Iterator it = json.keys();
            while (it.hasNext()) {
               keys=(String)it.next();
               columns[i]=keys;
               if(keys.equals("name"))
                   values[i]=json.getString(keys)+"_"+UserId;
               values[i]=json.getString(keys);
                i++;
            }
            if (perm == 'w') {
               msg=new InsertQuery("notes").specifyColumns(columns).values(values).insert();
            }
            JSONArray jarr=new SelectQuery("id").from("notes").where("name", SelectQuery.conditions.equalto, json.getString("name")+"_"+UserId).select();
            JSONObject jobj=jarr.getJSONObject(0);
            String id=jobj.getString("id");
                String val[]=new String[3];
                val[0]=json.getString("UserId");
                val[1]=id;
                val[2]=String.valueOf(perm);
          msg=new InsertQuery("registry").values(val).insert();
        } catch (Exception e) {
            return e.toString() + "from an";
        }
        return msg;
    }

}
