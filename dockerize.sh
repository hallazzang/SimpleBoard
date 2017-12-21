mysqldump -hlocalhost -usimpleboard -psimpleboard --no-data --compact simpleboard > dbschema.sql

docker build -t hallazzang/simpleboard:webapp -f docker/webapp.Dockerfile .
docker build -t hallazzang/simpleboard:mysql -f docker/mysql.Dockerfile .
