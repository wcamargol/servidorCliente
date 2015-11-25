package model.dao;

import java.util.List;
import model.beans.AlarmeBean;
import model.beans.EventoBean;
import model.beans.SensorBean;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EventoMySQLDAO{
    
    private Session session;
    
    public List getEventoBean(AlarmeBean alarme) {
        List listEventos = null;
        if(alarme != null){
            session = FabricaSessoes.getSession();
            try{
                Query consulta = session.createQuery("select e from EventoBean e "
                    + "where e.alarme.codigoAlarme = ? ");
                consulta.setString(0, alarme.getCodigoAlarme());
                listEventos = consulta.list();
            }catch (HibernateException ex){
                ex.printStackTrace();
            }finally{
                session.close();
            }
        }
        return listEventos;
    }
    
    public List getEventoBean(SensorBean sensor) {
        List listEventos = null;
        if(sensor != null){
            session = FabricaSessoes.getSession();
            try{
                Query consulta = session.createQuery("select e from EventoBean e "
                    + "where e.sensor.codigoSensor = ? ");
                consulta.setString(0, sensor.getCodigoSensor());
                listEventos = consulta.list();
            }catch (HibernateException ex){
                ex.printStackTrace();
            }finally{
                session.close();
            }
        }
        return listEventos;
    }
    
    public List listEventoBean(){
        session = FabricaSessoes.getSession();
        List listaEventosBean = null;
        Query consulta = null;
        try{            
            consulta = session.createQuery("from EventoBean evento order by "
                + "evento.dataEvento desc, evento.horaEvento desc");
            listaEventosBean = consulta.list();
        }catch (HibernateException ex){
                ex.printStackTrace();            
        }finally{
            session.close();
        }
        return listaEventosBean;
    }
    
    public boolean updateEventoBean(EventoBean evento) {
        boolean sucesso = false;
        if(evento != null){
            session = FabricaSessoes.getSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.update(evento);
                tx.commit();
                sucesso = true;                
            }catch(HibernateException ex){
                ex.printStackTrace();
                tx.rollback();
            }finally{
                session.close();
            }
        }
        return sucesso;            
    }

    
    public boolean saveEventoBean(EventoBean evento) {
        boolean sucesso = false;
        if(evento != null){
            session = FabricaSessoes.getSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(evento);
                tx.commit();
                sucesso = true;                
            }catch(HibernateException ex){
                ex.printStackTrace();
                tx.rollback();
            }finally{
                session.close();
            }
        }
        return sucesso;   
    }
    
    public boolean deleteEventoBean(EventoBean evento) {
        boolean sucesso = false;
        if(evento != null){
            session = FabricaSessoes.getSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.delete(evento);
                tx.commit();
                sucesso = true;                
            }catch(HibernateException ex){
                ex.printStackTrace();
                tx.rollback();
            }finally{
                session.close();
            }
        }
        return sucesso;
    }    
}