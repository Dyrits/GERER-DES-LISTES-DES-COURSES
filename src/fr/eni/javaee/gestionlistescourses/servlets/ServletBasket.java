package fr.eni.javaee.gestionlistescourses.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.gestionlistescourses.ListeException;
import fr.eni.javaee.gestionlistescourses.bll.ListeManager;
import fr.eni.javaee.gestionlistescourses.bo.Article;
import fr.eni.javaee.gestionlistescourses.bo.Liste;

@WebServlet("/basket")
public class ServletBasket extends HttpServlet {
	protected ListeException errorsCodes = new ListeException();
	protected ListeManager listeManager = new ListeManager();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ToolsServlet.updateListes(request, listeManager, errorsCodes);

		int identifier = ToolsServlet.checkIdentifier("identifier", request, errorsCodes);
		Liste liste = Liste.getListe(identifier);

		// Uncheck all checkboxes:
		if(request.getParameter("uncheckAll") != null || request.getParameterValues("checkboxes") != null) {
			try {
				listeManager.uncheckAllArticles(identifier);
			} catch(ListeException exception) {
				ToolsServlet.setErrorsAttribute(exception, request, errorsCodes);
			}
		}
			
		if(!errorsCodes.hasCodes()) {
			request.setAttribute("errors", errorsCodes.getCodes());
		} else {
			try {
				listeManager.setListes();
				liste = Liste.getListe(identifier);
				request.setAttribute("liste", liste);
			} catch (ListeException exception) {
				ToolsServlet.setErrorsAttribute(exception, request, errorsCodes);
			}
		}

		if (liste != null) { ToolsServlet.setAttributes(request, liste); }
		request.getRequestDispatcher("/WEB-INF/basket.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ToolsServlet.updateListes(request, listeManager, errorsCodes);

		int identifier = ToolsServlet.checkIdentifier("identifier", request, errorsCodes);
		Liste liste = Liste.getListe(identifier);
		if (request.getParameterValues("checkboxes") != null) {
			ArrayList<String> checkboxes = new ArrayList<>(Arrays.asList(request.getParameterValues("checkboxes")));
			for (Article article : liste.getArticles()) {
				int identifierArticle = article.getIdentifier();
				try {
					if (checkboxes.contains(String.valueOf(identifierArticle))) { listeManager.checkArticle(identifierArticle); }
					else { listeManager.uncheckArticle(identifierArticle); }
				} catch (ListeException exception) {
					ToolsServlet.setErrorsAttribute(exception, request, errorsCodes);
				}
			}
		}
		doGet(request, response);
	}

}
