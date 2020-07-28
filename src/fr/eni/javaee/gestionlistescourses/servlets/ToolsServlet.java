package fr.eni.javaee.gestionlistescourses.servlets;

import fr.eni.javaee.gestionlistescourses.ListeException;
import fr.eni.javaee.gestionlistescourses.bll.ListeManager;
import fr.eni.javaee.gestionlistescourses.bo.Liste;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class ToolsServlet {
    static String checkName(String parameter, HttpServletRequest request, ListeException errorsCodes) {
        try {
            return request.getParameter(parameter);
        } catch(Exception exception) {
            exception.printStackTrace();
            if (parameter.equals("name")) { errorsCodes.addCode(CodesExceptionServlets.NOM_LISTE_OBLIGATOIRE); }
            else if (parameter.equals("nameArticle")) { errorsCodes.addCode(CodesExceptionServlets.NOM_ARTICLE_OBLIGATOIRE); }
        }
        return null;
    }

    static int checkIdentifier(String parameter, HttpServletRequest request, ListeException errorsCodes) {
        try {
            if (request.getParameter(parameter) != null) { return Integer.parseInt(request.getParameter(parameter)); }
        } catch (NumberFormatException exception){
            exception.printStackTrace();
            if (parameter.equals("identifierArticle")) { errorsCodes.addCode(CodesExceptionServlets.FORMAT_ID_ARTICLE_ERREUR); }
            else if (parameter.equals("identifier")) { errorsCodes.addCode(CodesExceptionServlets.FORMAT_ID_LISTE_ERREUR); }
        }
        return 0;
    }

    static void setAttributes (HttpServletRequest request, Liste liste) {
        request.setAttribute("liste", liste);
        request.setAttribute("articles", liste.getArticles());
    }

    static void setErrorsAttribute(ListeException exception, HttpServletRequest request, ListeException errorsCodes) {
        exception.printStackTrace();
        errorsCodes.getCodes().addAll(exception.getCodes());
        request.setAttribute("errors", errorsCodes.getCodes());
    }

    static void updateListes(HttpServletRequest request, ListeManager listeManager, ListeException errorsCodes) {
        try {
            request.setCharacterEncoding("UTF-8");
            listeManager.setListes();
        } catch (ListeException exception) {
            setErrorsAttribute(exception, request, errorsCodes);
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
        }
    }
}
