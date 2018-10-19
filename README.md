## SAS Movie Project

This project provides a set of Rest API letting users store their favorite movies
according TMDB movies data https://www.themoviedb.org/documentation/api

### Build and unit tests
 ./mvnw clean install
 
### Running on localhost:8080
 ./mvnw spring-boot:run -Dspring-boot.run.arguments=--api.tmdb.key=<TMDB_API_KEY>
 Leave spring-boot.run.arguments empty for default (akka mine) apikey
 
### API Info and requests

For API definition please check on /swagger-ui.html 

#### Movies Resources:

   1. /api/v1/movies
   GET most popular movies according TMDB
   
   2. /api/v1/movies/top_favorite
   GET most favorites movies selected from users of this application
   
   3. /api/v1/movies/{query}
   GET list of movies based on name. performs a search operation on TMDB movies name.

#### User Resources:
  
  4. /api/v1/users/{userId}/favorite
  POST a tmdbId movie information into server representing a new favorite movie for user
    - check if movie already already in db. If so attach to user as new favorite movie
    - Otherwise fetch the movie from TMDB API and add a new movie to system and given user
  
  5. /api/v1/users/{userId}/favorites
  GET list of favorites movies from given {userId}
 

### Database: H2
  Check /h2-console, default credentials.

### Logging: SLF4J