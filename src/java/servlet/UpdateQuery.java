
package servlet;

import java.sql.*;
import org.json.*;
import servlet.SelectQuery.*;

public class UpdateQuery {
    String sql;
    String table;
    String columns;
    String condition;
    UpdateQuery()
    {
        
    }
UpdateQuery(String table)
{
    this.table=table;
    this.sql="update "+table;
}
UpdateQuery set(String column,String value)
{
    this.sql+=" set "+column+" = "+value;
    return this;
}
UpdateQuery where(String columnName, conditions condition, String value)
{
    SelectQuery sq=new SelectQuery();
    sq.where(columnName, condition, value);
    this.condition=sq.condition;
    return this;
}
UpdateQuery where(String columnName, in_condition condition1, String[] values)
        {
    SelectQuery sq=new SelectQuery();
    sq.where(columnName,condition1, values);
    this.condition=sq.condition;
    return this;
}
}
