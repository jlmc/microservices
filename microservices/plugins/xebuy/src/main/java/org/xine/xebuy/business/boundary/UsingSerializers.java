package org.xine.xebuy.business.boundary;

import java.io.IOException;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xine.xebuy.business.plugin.serializer.Serialization;
import org.xine.xebuy.business.plugin.serializer.Serialization.Type;
import org.xine.xebuy.business.plugin.serializer.Serializer;

@WebServlet(name = "UsingSerializers", urlPatterns = { "/UsingSerializers/*" })
public class UsingSerializers extends HttpServlet {

	@Inject
	Instance<Serializer> allPlugins;
	
	@Inject
	@Serialization(Type.OPTIMIZED)
	Serializer optimizer;

	@Inject
	@Serialization(Type.DEFAULT)
	Serializer standard;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

}

