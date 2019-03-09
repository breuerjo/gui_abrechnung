package application;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;  

public class DB {
	
	private Connection con;

	public DB() {
			
		try{
			Class.forName("com.mysql.jdbc.Driver");
			this.con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ui_stadtwerke_db","root","");
			System.out.println(this.con);
		}catch (Exception e) {}
		
		
		
	}
	
	public Connection getConnection() {
		//Get actual connection to data base
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ui_stadtwerke_db","root","");
			System.out.println(this.con);
		} catch (Exception e) {}
		
		return this.con;
	}
	

	
	public int executeUpdate(PreparedStatement ps) {
		int generated_key =-1;
		try {			
		  ps.executeUpdate();
		  ResultSet rs = ps.getGeneratedKeys();
		  if(rs.next())
			  generated_key = rs.getInt(1);
		} catch (Exception e) {}
		
		return generated_key;
	}
	
	public ResultSet executeQueryWithResult(String query) {
		System.out.println(query);
		PreparedStatement ps=null;
		ResultSet rs = null;
		try {
		    ps= this.con.prepareStatement(query);

		    rs=ps.executeQuery();
		    		  
		} catch (Exception e) {}
		
		return rs;
	}
	
	public void printResultSet(ResultSet rs) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();	//Anzahl der Spalten/Felder des ResultsSets
			
			while (rs.next()) {
			    for (int i = 1; i <= columnsNumber; i++) {
			        if (i > 1) System.out.print(",  ");
			        String columnValue = rs.getString(i);
			        System.out.print(rsmd.getColumnName(i) + ": "+ columnValue);
			    }
			    System.out.println("");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
