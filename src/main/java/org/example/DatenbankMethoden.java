package org.example;

import java.sql.*;

public class DatenbankMethoden {
    Connection con = null;
    Statement stmt = null;

    //Connection zu den Datenbank
    public  void sqlConection(){        //Verbindet mit dem Datenbank

        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager
                    .getConnection("jdbc:postgresql://tyke.db.elephantsql.com:5432/vuccwqjh",
                            "vuccwqjh", "EMktTvotuSQRzewcY6IQTO2C3u3o6xXT");
            System.out.println("Opened database successfully");

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }
    public void deleteNetzplan(int id) throws SQLException {    //Löscht Netzplan mit allen KNoten die zu den gehören

        stmt = con.createStatement();
        String sql = "DELETE  FROM knoten WHERE netzplan_id="+id+";";
        stmt.executeUpdate(sql);
        //stmt = con.createStatement();
        sql = "DELETE  FROM netzplan WHERE id="+id+";";
        stmt.executeUpdate(sql);
        stmt.close();
    }


    public int idNetzplan(String name) throws SQLException {    //findet id von Netzplan durch Netzplan Name raus
        String sql = "SELECT id FROM netzplan WHERE name=?;";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1,name);

        ResultSet netid = preparedStatement.executeQuery();

        netid.next();
        int id=netid.getInt(1);
        return id;
    }
    public void deleteKnoten(int idNetzplan,int idKnoten) throws SQLException {     //Löscht Knoten aus einem Netzplan
        stmt = con.createStatement();
        String sql = "DELETE  FROM knoten WHERE id="+idKnoten+"and netzplan_id="+idNetzplan+";";
        stmt.executeUpdate(sql);
        stmt.close();
    }
    public int idKnoten(String name, int netzplanid) throws SQLException {      //findet id von Knoten durch Netzplan id und Knoten Name raus
        stmt = con.createStatement();
        String sql = "SELECT id FROM knoten WHERE name=? and netzplan_id="+netzplanid+";";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1,name);

        ResultSet knotenid = preparedStatement.executeQuery();

        knotenid.next();
        int id=knotenid.getInt(1);
        return id;
    }
    public void createNetzplan(String netzplanName) throws SQLException {
        String sql = "INSERT INTO netzplan (name) "
                + "VALUES (?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, netzplanName);
        pstmt.executeUpdate();

    }
    public  void createKnoten(String name,int dauer,int id, int netzplanID) throws SQLException {
        stmt = con.createStatement();
        String sql = "INSERT INTO knoten (name,dauer,id,netzplan_id) "
                + "VALUES ('"+name+"',"+dauer+","+id+","+netzplanID+");";
        stmt.executeUpdate(sql);
    }
    public  void createKnotenMitVorgänger(String name,int dauer,int vorgänger1,int id, int netzplanID) throws SQLException {
        stmt = con.createStatement();
        String sql = "INSERT INTO knoten (name,dauer,vorgaenger1,id,netzplan_id) "
                + "VALUES ('"+name+"',"+dauer+","+vorgänger1+","+id+","+netzplanID+");";
        stmt.executeUpdate(sql);
    }
    public  void createKnotenMitVorgänger(String name,int dauer,int vorgänger1,int vorgänger2,int id, int netzplanID) throws SQLException {
        stmt = con.createStatement();
        String sql = "INSERT INTO knoten (name,dauer,vorgaenger1,vorgaenger2,id,netzplan_id) "
                + "VALUES ('"+name+"',"+dauer+","+vorgänger1+","+vorgänger2+","+id+","+netzplanID+");";
        stmt.executeUpdate(sql);
    }
    public  void createKnotenMitVorgänger(String name, int dauer, Integer vorgänger1, Integer vorgänger2, Integer vorgänger3, int id, int netzplanID) throws SQLException {
        stmt = con.createStatement();
        String sql = "INSERT INTO knoten (name,dauer,vorgaenger1,vorgaenger2,vorgaenger3,id,netzplan_id) "
                + "VALUES ('"+name+"',"+dauer+","+vorgänger1+","+vorgänger2+","+vorgänger3+","+id+","+netzplanID+");";
        stmt.executeUpdate(sql);
    }
    public int idKnotenMax(int id) throws SQLException {    //Findet größtes id von Knoten wo der netzplan_id mit netzplan.id übereinstimmt
        stmt = con.createStatement();
        String sql = "SELECT MAX(id) FROM knoten WHERE netzplan_id="+id+";";
        ResultSet knotenid = stmt.executeQuery(sql);
        knotenid.next();
        int maxid=knotenid.getInt(1);
        return  maxid;
    }
    public void changeNetzplanName(int id,String newName) throws SQLException {
        stmt = con.createStatement();
        String sql = "UPDATE netzplan SET name='"+newName+"' WHERE id="+id+";";
        stmt.executeUpdate(sql);
    }
    public void changeKnotenName(int id,String oldName,String newName) throws SQLException {
        stmt = con.createStatement();
        String sql = "UPDATE knoten SET name='"+newName+"' WHERE netzplan_id="+id+"and name='"+oldName+"';";
        stmt.executeUpdate(sql);
    }
    public void changeKnotenDauer(int id,String Name,int newDauer) throws SQLException {
        stmt = con.createStatement();
        String sql = "UPDATE knoten SET dauer='"+newDauer+"' WHERE netzplan_id="+id+"and name='"+Name+"';";
        stmt.executeUpdate(sql);
    }
    public void changeVorgänger(int id,String Name,int newVorgänger,int number) throws SQLException {
        stmt = con.createStatement();
        String sql = "UPDATE knoten SET vorgaenger"+number+"="+newVorgänger+" WHERE netzplan_id="+id+"and name='"+Name+"';";
        stmt.executeUpdate(sql);
    }
    public String getNameKnoten(int idN,int idK) throws SQLException {
        stmt = con.createStatement();
        String sql = "SELECT name FROM knoten WHERE netzplan_id="+idN+"and id="+idK+";";
        ResultSet knotenid = stmt.executeQuery(sql);
        knotenid.next();
        return  knotenid.getString(1);
    }
    public int getDauerKnoten(int idN,int idK) throws SQLException {
        stmt = con.createStatement();
        String sql = "SELECT dauer FROM knoten WHERE netzplan_id="+idN+"and id="+idK+";";
        ResultSet knotenid = stmt.executeQuery(sql);
        knotenid.next();
        return knotenid.getInt(1);
    }
    public int getVorgänger(int idN, int idK,int i) throws SQLException {
        int vorgänger=0;
        stmt = con.createStatement();
        String sql = "SELECT vorgaenger"+i+" FROM knoten WHERE  netzplan_id=" + idN + "and id=" + idK + ";";
        ResultSet resultSet = stmt.executeQuery(sql);
        if (resultSet.next()) {
            vorgänger = resultSet.getInt("vorgaenger"+i+"");
        }

        return vorgänger ;

    }

}