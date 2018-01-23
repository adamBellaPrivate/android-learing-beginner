package com.learn.bella.notelist.bo;

import com.learn.bella.notelist.bo.enums.DatabaseParameterType;

/**
 * Created by adambella on 1/22/18.
 */

public class DatabaseBuilder {

    private String sqlString = "create table ";

    public DatabaseBuilder(String tableName){
        sqlString += tableName;
        sqlString += " ";
        appendId();
    }

    private DatabaseBuilder appendId(){
        sqlString += "( _id " + DatabaseParameterType.INTEGER.toString() + " primary key autoincrement);";
        return this;
    }

    public DatabaseBuilder appendParameter(String name, DatabaseParameterType type, Boolean isNullable){
        String tempSqlString = sqlString.replace(");","");

        StringBuilder builder = new StringBuilder();
        builder.append(tempSqlString);
        builder.append(", ");
        builder.append(name);
        builder.append(" ");
        builder.append(type.toString());
        if(!isNullable) {
            builder.append(" ");
            builder.append("not null");
        }
        builder.append(" );");

        sqlString = builder.toString();
        return this;
    }

    @Override
    public String toString() {
        return sqlString;
    }
}
