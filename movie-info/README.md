# Movie Info
## using with docker

### build
./mvnw install dockerfile:build

### run
docker run -p 8080:8080 -t borkutip/movie-info:latest

### stop
docker ps
docker stop [CONTAINER_ID]

### usage
http://localhost:8080/movies/{title}?api={apiname}
http://localhost:8080/movies/flux/{title}?api={apiname}

where title: text to search in movie's title
apiname: omdbapi or themoviedb

Examples:

curl -i http://localhost:8080/movies/transformation?api=omdbapi
curl -i http://localhost:8080/movies/transformation?api=themoviedb
curl -i http://localhost:8080/movies/flux/transformation?api=omdbapi




