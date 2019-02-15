package application;

import java.sql.*;  

public class DB {
	
	private Connection con;

	public Connection getConnection() {
			
		try{
			Class.forName("com.mysql.jdbc.Driver");
			this.con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ui_stadtwerke_db","root","");
		}catch (Exception e) {}
		
		return this.con;
	}
	
	public ResultSet getResultSet() {
		PreparedStatement ps=null;
		ResultSet rs = null;
		try {
		    String query="select * from zahlung";
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
