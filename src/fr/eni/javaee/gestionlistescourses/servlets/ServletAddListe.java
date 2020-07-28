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
import fr.eni.javaee.gestionlistescourses.bo.Liste;

/**
 * Servlet implementation class ServletNouvelleListe
 */
@WebServlet(
		urlPatterns=  {
						"/display",
						"/addArticle",
						"/deleteArticle"
		})
public class ServletAddListe extends HttpServlet {
	protected ListeException errorsCodes = new ListeException();
	protected ListeManager listeManager = new ListeManager();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ToolsServlet.updateListes(request, listeManager, errorsCodes);

		// Display existing list:
		if (request.getServletPath().equals("/display")) {
			int identifier = ToolsServlet.checkIdentifier("identifier", request, errorsCodes);
			Liste liste =  Liste.getListe(identifier);
			if (liste != null) { ToolsServlet.setAttributes(request, liste); }
		}

		// Delete article:
		else if (request.getServletPath().equals("/deleteArticle")) {
			int identifier = ToolsServlet.checkIdentifier("identifier", request, errorsCodes);
			int identifierArticle = ToolsServlet.checkIdentifier("identifierArticle", request, errorsCodes);
			if (errorsCodes.hasCodes()) { request.setAttribute("errors", errorsCodes); }
			else {
				try {
					listeManager.deleteArticle(identifierArticle);
					listeManager.setListes();
					Liste liste = Liste.getListe(identifier);
					ToolsServlet.setAttributes(request, liste);
				} catch (ListeException exception) {
					ToolsServlet.setErrorsAttribute(exception, request, errorsCodes);
				}
			}
		}
		request.getRequestDispatcher("/WEB-INF/new_list.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ToolsServlet.updateListes(request, listeManager, errorsCodes);

		// Create and display a new list/article:
		if (request.getServletPath().equals("/addArticle")) {
			String name = ToolsServlet.checkName("name", request, errorsCodes);
			String nameArticle = ToolsServlet.checkName("nameArticle", request, errorsCodes);
			if (errorsCodes.hasCodes()) { request.setAttribute("errors", errorsCodes.getCodes()); }
			else {
				try {
					int identifier = ToolsServlet.checkIdentifier("identifier", request, errorsCodes); // 0 if new list.
					Liste liste = Liste.getListe(identifier); // null if new list.
					if (liste == null) { liste = listeManager.addListe(identifier, name); }
					identifier = liste.getIdentifier();
					listeManager.addArticle(identifier, nameArticle);
					ToolsServlet.setAttributes(request, liste);
				} catch (ListeException exception) {
					ToolsServlet.setErrorsAttribute(exception, request, errorsCodes);
				}
			}
		}
		doGet(request, response);
	}


}











