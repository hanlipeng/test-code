 docker stop postgres
 docker rm postgres
 docker run --name postgres -e POSTGRES_PASSWORD=root -e POSTGRES_USER=root -v ~/data/postgres:/var/lib/postgresql/data -p 5432:5432 -d postgres