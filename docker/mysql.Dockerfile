FROM mysql:5.7

ENV MYSQL_DATABASE=simpleboard
ENV MYSQL_ROOT_PASSWORD=simpleboard
ENV MYSQL_USER=simpleboard
ENV MYSQL_PASSWORD=simpleboard

COPY dbschema.sql /docker-entrypoint-initdb.d
COPY mysqld.cnf /etc/mysql/conf.d

EXPOSE 3306
