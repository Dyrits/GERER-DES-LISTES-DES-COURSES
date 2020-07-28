package fr.eni.javaee.gestionlistescourses.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.gestionlistescourses.ListeException;
import fr.eni.javaee.gestionlistescourses.bll.ListeManager;

/**
 * Servlet implementation class ServletListe
 */
@WebServlet(
		urlPatterns=  {
				"/listes",
				"/deleteListe"
		})
public class ServletListes extends HttpServlet {
	protected ListeException errorsCodes = new ListeException();
	protected ListeManager listeManager = new ListeManager();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ToolsServlet.updateListes(request, listeManager, errorsCodes);

		int identifier = 0;

		// Delete list:
		if (request.getServletPath().equals("/deleteListe")) {
			identifier = ToolsServlet.checkIdentifier("identifier", request, errorsCodes);
			if (errorsCodes.hasCodes()) { request.setAttribute("errors", errorsCodes.getCodes()); }
			else {
				try {
					listeManager.setListes();
					listeManager.deleteListe(identifier);
				} catch (ListeException exception) {
					ToolsServlet.setErrorsAttribute(exception, request, errorsCodes);
				}
			}
		}

		// Display lists
		try { request.setAttribute("listes", listeManager.selectAllListes()); }
		catch (ListeException exception) { ToolsServlet.setErrorsAttribute(exception, request, errorsCodes); }
		request.getRequestDispatcher("/WEB-INF/listes.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}









