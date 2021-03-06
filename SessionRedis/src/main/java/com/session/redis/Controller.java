package com.session.redis;

import org.springframework.web.bind.annotation.RestController;

import com.session.redis.beans.Direccion;
import com.session.redis.beans.Persona;

import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class Controller {

    @RequestMapping("/crear")
    public String index(HttpServletRequest req) {
    	
    	if(req.getSession(false) == null){
    		HttpSession session = req.getSession();
    		System.out.println("****************************************************");
    		System.out.println("Se crea Session :)");
    		System.out.println("****************************************************");
    		Direccion direccion = new Direccion();
    		direccion.setCalle("Michoacan");
    		direccion.setColonia("Hidalgo");
    		direccion.setNumero("A201");
    		
    		Persona persona = new Persona();
    		persona.setDirecciones(Arrays.asList(direccion));
    		persona.setEdad(25);
    		persona.setFechaNacimiento(new Date());
    		persona.setNombre("Yisus");
    		
    		session.setAttribute("persona", persona);
    		
    	}else{
    		System.out.println("****************************************************");
    		System.out.println("Ya hay una session :)");
    		System.out.println("****************************************************");
    	}
		    	
        return "Hello Spring Boot!";
    }
    
    @RequestMapping("/agregar")
    public String add(HttpServletRequest req) {
    	
    	if(req.getSession(false) == null){
    		return "No se agrego valor :(";
    	}
    	Date date = new Date();
    	HttpSession session = req.getSession(false);
    	session.setAttribute(String.valueOf(date.getTime()), date.toString());
    	
    	return "Se agrego valor :) " +  String.valueOf(date.getTime());
    }
    
    @RequestMapping("/buscar")
    public String buscar(HttpServletRequest req) {
    	
    	HttpSession session = req.getSession(false);
    	
    	if(req.getSession(false) == null){
    		System.out.println("****************************************************");
    		System.out.println("No hay session");
    		System.out.println("****************************************************");
    		return "no hay session :( ";
    	}
    	
    	Enumeration<String> nombres = session.getAttributeNames();
    	String x = "";
    	
    	while (nombres.hasMoreElements()) {
    		String y = (String) nombres.nextElement();
    		x += y +" ";
		}
    	
        return "busqueda! \n" + x;
    }
    
    @RequestMapping("/eliminar")
    public String delete(HttpServletRequest req) {
    	
    	if(req.getSession(false) == null){
    		return "no hay session a elimnar";
    	}
    	
    	req.getSession(false).invalidate();
    	return"se elimino session";
    }
}