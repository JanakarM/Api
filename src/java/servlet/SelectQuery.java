package servlet;

import java.sql.*;
import org.json.*;

public class SelectQuery {

    String sql;
    String table;
    String columns;
    String condition;

    public enum conditions {
        equalto,
        lessthan,
        greaterthan
    }

    public enum in_condition {
        in
    }
     public enum orderby {
        asc,
        desc
    }

     SelectQuery()
     {
         
     }
    SelectQuery(String columns) {
        this.columns = columns;
        this.sql = "select " + columns;
    }

    SelectQuery from(String table) {
        this.table = table;
        this.sql += " from " + table;
        return this;
    }

    SelectQuery condition(String columnName, conditions condition, String value) {
        String oper = "";

        if (null != condition) {
            switch (condition) {
                case equalto:
                    oper = "=";
                    break;
                case greaterthan:
                    oper = ">";
                    break;
                case lessthan:
                    oper = "<";
                    break;
                default:
                    break;
            }
        }
        this.condition = columnName + oper + value;
        return this;
    }

    SelectQuery condition(String columnName, in_condition condition1, String[] values) {

        int len = values.length;
        String value = "(";
        while (--len < 0) {
            value += values[len] + ",";
        }
        value += values[0] + ")";
        this.condition = columnName + condition1 + value;
        return this;
    }

    SelectQuery where(String columnName, conditions condition, String value) {

        condition(columnName, condition, value);
        this.sql += " where " + this.condition;
        return this;
    }

    SelectQuery where(String columnName, in_condition condition1, String[] values) {
        condition(columnName, condition1, values);
        this.sql += " where " + this.condition;
        return this;
    }

    SelectQuery and(String columnName, in_condition condition1, String[] values) {
        condition(columnName, condition1, values);
        this.sql += " and " + this.condition;
        return this;
    }

    SelectQuery and(String columnName, conditions condition, String value) {
        this.sql += " and " + this.condition;
        condition(columnName, condition, value);
        return this;
    }

    SelectQuery groupby(String column) {
        this.sql += " group by " + column;
        return this;
    }
    SelectQuery having(String columnName, in_condition condition1, String[] values) {
        condition(columnName, condition1, values);
        this.sql += " having " + this.condition;
        return this;
    }

    SelectQuery having(String columnName, conditions condition, String value) {
        condition(columnName, condition, value);
        this.sql += " having " + this.condition;
        return this;
    }
     SelectQuery orderby(String column,orderby ob) {
        this.sql += " order by " + column+" "+ob;
        return this;
    }
    JSONArray select() {
        JSONArray jarr = new JSONArray();
        JSONObject json = new JSONObject();
        try {
            DBConnector db = new DBConnector();
            Connection con = db.createDBConnection();
            PreparedStatement ps = con.prepareStatement(this.sql);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int j = 0;
            while (rs.next()) {
                json = new JSONObject();
                int i = 1;
                while (i <= rsmd.getColumnCount()) {
                    json.put(rsmd.getColumnLabel(i), rs.getString(i) == null ? null : rs.getString(i));
                    i++;
                }
                jarr.put(j++, json);
            }
        } catch (Exception e) {
            json.put("msg", e + " from sn");
            jarr.put(0, json);
            return jarr;
        }
        return jarr;
    }

    String getSql() {
        return this.sql;
    }
}
