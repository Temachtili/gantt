package com.zb.gantt.DAO.Proyecto;

import Base.Conexion;
import Modelo.Proyecto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class sqlProyectoDAO implements ProyectoDAO {

    // Variables de Conexion
    private Connection conector;
    private final Conexion conexion = new Conexion();
    //Variables de uso Sql
    private ResultSet rs;
    private PreparedStatement ps;
    //Query's
    private final String INSERTAR = "insert into Proyecto(nombreProyecto) values (?);";
    private final String BORRAR = "delete from Proyecto where cveProyecto = ?;";
    private final String LISTAR = "select * from Proyecto;";

    public sqlProyectoDAO(){
        try{
            conector = conexion.connectDB();
            if (conector == null){  System.out.println("Fallida conexión a la base de datos en sqlTareaDAO()");  }
        }catch (Exception e){
            System.out.println(e.toString() + " en sqlProyectoDAO()");
        }
    }

    public int insertar(Proyecto ob) {
        int cveProyecto = 0;

        if (conector != null){
            try {
                //Se prepara el statement
                ps = conector.prepareStatement(INSERTAR);
                ps.setString(1, ob.getNombreProyecto());

                ps.executeUpdate(); //Se ejecuta el query
                System.out.println("Se registró correctamente la tarea");
                cveProyecto = 1;

            }catch (Exception e){
                System.out.println(e.toString() + " en insertar() - sqlProyectoDAO");
            }finally {
                closeConnections();
            }
        }else{
            System.out.println("Fallida conexión a la base de datos en sqlProyectoDAO()");
        }

        return cveProyecto;
    }

    public int eliminar(Proyecto ob) {
        int cveProyecto = 0;

        if (conector != null){
            try {
                //Se prepara el statement
                ps = conector.prepareStatement(BORRAR);
                ps.setInt(1, ob.getCveProyecto());

                ps.executeUpdate(); //Se ejecuta el Query
                System.out.println("Se eliminó el(los) registro(s)");
                cveProyecto = 1;

            }catch (Exception e){
                System.out.println(e.toString() + " en eliminar() - sqlProyectoDAO");
            }finally {
                closeConnections();
            }
        }

        return cveProyecto;
    }

    public int cambiar(Proyecto ob) {
        return 0;
    }

    public List<Proyecto> listar() {
        ArrayList<Proyecto> lista = new ArrayList<Proyecto>();

        try{
            ps = conector.prepareStatement(LISTAR);

            ps.execute();
            rs = ps.getResultSet();

            while (rs.next()){
                lista.add(new Proyecto(rs.getInt("cveProyecto"), rs.getString("nombreProyecto")));
            }

        }catch (Exception e){
            System.out.println(e.toString() + " en listar() - sqlProyectoDAO");
            lista = null;
        }finally {
            closeConnections();
        }

        return lista;
    }

    public void closeConnections() {
        try {
            if (conector != null){  conector.close();   }
            conexion.closeDB();
        }catch (SQLException e){
            System.out.println(e.toString() + " en closeConnections() - sqlProyectoDAO");
        }
    }
}
