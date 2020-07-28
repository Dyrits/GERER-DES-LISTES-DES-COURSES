# Gérer des liste des courses

## A propos de ce projet

### Cursus
ENI | Le développement Web côté serveur – BackEnd avec Java Enterprise Edition (JEE)
~ [Module 8 - Les JSP avancées](https://github.com/Dyrits/GERER-DES-LISTES-DES-COURSES/blob/master/Module%2006%20-%20Enonc%C3%A9%20TP%2001%20-%20G%C3%A9rer%20des%20listes%20de%20courses.pdf)

### Énoncés (par l'ENI)
A partir d'un nouveau Dynamic Web Project, créer l'application suivante.

#### Description fonctionnelle
L'application permet à un utilisateur de gérer des listes de courses et de les utiliser en magasin (il coche l'article sur la liste quand il le met dans son panier).
Voici les maquettes des écrans attendus (Attention, les servlets ne sont pas représentées) :

> Plus de détails au sein du fichier PDF correspondant.

L'écran de gauche permet de créer une nouvelle liste de courses.  L'écran du milieu permet de gérer les listes existantes (créées à partir de l'écran de gauche).  L'écran de droite est utilisé dans le magasin : en partant d'une liste prédéfinie l'utilisateur coche au fur et à mesure les articles qu'il met physiquement dans son panier

#### Eléments d'architecture
L'application se nomme TPGestionListesCourses.

Mettre en œuvre une architecture adaptée.

#### Etapes
Les étapes, par ordre de priorité :
1. Étape obligatoire : En partant d'une base de données alimentée manuellement, afficher les listes de courses disponibles (Ecran Listes prédéfinies).
2. Étape obligatoire : Gérer les erreurs potentielles sur l'écran des Listes prédéfinies.
3. Sur l'écran des Listes prédéfinies, gérer la suppression d'une liste avec rafraîchissement de l'écran.
4. Mettre en place l'ajout d'une nouvelle liste sans gestion des erreurs de saisie (Ecran Nouvelle liste).
5. Sur le formulaire d'ajout de l'écran Nouvelle liste, mettre en place la gestion des erreurs de saisies.
6. Mettre en place la gestion de l'utilisation d'une liste (Ecran Votre panier).

### Technologies principales
- Java
- Java EE

### Détails | Commentaires
Ce projet a été construit à partir de zéro en suivant des instructions spécifiques. Différents éléments ont été construits en s'inspirant d'un corrigé proposé.

L'application a été renommée "Gérer des liste des courses".

### Statut
Terminée  (V1.0)

#### Dernière mise à jour
28/07/2020
(README | 28/07/2020)