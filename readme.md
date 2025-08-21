# Description
API de gestion de films et séries favoris. La listes des films et séries est récupérée automatiquement en se connectant sur l’API « The Movie Database (TMDB) ». Mon API sert de lien entre TMDB et l’utilisateur pour qu’il puisse rechercher ou consulter des éléments et enregistrer ses favoris. Le projet est en cours de développement. 

# Technologies
* Java
* Spring Boot
* Spring Framework
* Spring Data
* Maven (API séparée en plusieurs modules)
* Principes SOLID
* Design patterns

# Installation
1. Installer un server MySQL et créer une base de données "cinemadb"
2. Créer un compte chez themoviedb.org pour pouvoir accéder à leur API
3. Renseigner les informations de la connexion à la base de données ainsi que les informations de la connexion à l'API themoviedb.org dans le fichier "cinemadb-consumer\src\main\resources\application.properties"
4. Lancer l'API depuis la méthode main se trouvant dans "cinemadb-api\src\main\java\ch\blutch\cinemadb\CinemaDBApplication.java"
