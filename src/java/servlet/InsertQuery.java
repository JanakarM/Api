
package servlet;

import java.util.HashMap;


public class InsertQuery {
     String sql;
    String table;
    String[] columns;
    String condition;
    HashMap<String,String> type;
    InsertQuery()
    {
        
    }
    InsertQuery(String table)
    {
        this.table=table;
        this.sql="insert into "+table;
    }
    InsertQuery hMap()
    {
        type=new HashMap();
        type.put("name","String");
        type.put("age","int");
        type.put("gender","String");
        type.put("isregistered","boolean");
        type.put("isverified","boolean");
        type.put("place","String");
        type.put("dept","String");
        type.put("contact","String");
        type.put("doj","String");
        type.put("password","String");
        return this;
    }
    InsertQuery specifyColumns(String[] columns)
    {
        String column="";
        this.columns=columns;
        int len = columns.length;
        int i=0;
        while(i<len-1)
        {
        column+=columns[i]+",";
        i++;
        }
        column+=columns[len-1];
        this.sql+="("+column+")";
        return this;
    }
    InsertQuery values(String[] values)
    {
        String value;
         value = "";
        hMap();
        int i=0,len=values.length;
        while(i<len)
        {
            if(this.type.get(this.columns[i]).equals("String"))
            {
            value+="'"+values[i]+"'";
            }
            else
            {
            value+=values[i];
            }
            if(i<len-1)
            {
            value+=",";
            }
        }
        this.sql+=" values "+"("+value+")";
        return this;
    }
}
