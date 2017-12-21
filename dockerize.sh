mysqldump -h localhost -u simpleboard -psimpleboard --no-data simpleboard > dbschema.sql

docker build -t hallazzang/simpleboard:webapp -f docker/webapp.Dockerfile .
docker build -t hallazzang/simpleboard:mysql -f docker/mysql.Dockerfile .
