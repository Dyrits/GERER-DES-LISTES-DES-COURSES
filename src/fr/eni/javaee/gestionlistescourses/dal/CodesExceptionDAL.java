package fr.eni.javaee.gestionlistescourses.dal;

public abstract class CodesExceptionDAL {

	public static final int INSERT_OBJET_NULL=10000;
	
	public static final int INSERT_OBJET_FAIL=10001;

	public static final int LECTURE_LISTES_FAIL = 10002;

	public static final int LECTURE_LISTE_FAIL = 10003;

	public static final int LECTURE_LISTE_INEXISTANTE = 10004;

	public static final int SUPPRESSION_ARTICLE_ERROR = 10005;

	public static final int SUPPRESSION_LISTE_ERROR = 10006;

	public static final int CHECK_ARTICLE_ERROR = 10007;

	public static final int UNCHECK_ARTICLE_ERROR = 10008;

	public static final int UNCHECK_ALL_ARTICLE_ERROR = 10009;

	public static final int CONNECTION_ERROR = 10010;

	public static final int CONNECTION_ROLLBACK_ERROR = 10011;
}












