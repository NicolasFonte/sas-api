##SAS Movie Project

This project provides a set of Rest API letting users store their favorite movies
according TMDB movies data.

### Build and unit tests
 mvn clean install
 
### Run on localhost:8080
 mvn spring-boot:run -Dspring-boot.run.arguments=--api.tmdb.key=<TMDB_API_KEY>
 Leave arguments empty for default apikey
 
###API Info

For API definition please check on /swagger-ui.html 

####Movies Resources:

   1. /api/vi/movies
   GET most popular movies according TMDB
   
   2. /api/v1/movies/favorite_movies
   GET most favorites movies selected from users of this application
   
   3. /api/v1/movies/{name}
   GET list of movies based on name. performs a search operation on TMDB movies name.

####User Resources:
  
  /api/v1/users/{userId}/favorite
  POST a TMDB Movie data into our server as favorite movie from a user with {userId}
  
  /api/v1/users/{userId}/favorites
  GET list of favorites movies from given {userId}
 

###Database: H2
  - Check /h2-console, default credentials.

### Logging: SLF4J
  - 