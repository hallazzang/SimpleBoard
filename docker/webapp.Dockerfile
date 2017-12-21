FROM tomcat:9.0-alpine

COPY out/artifacts/SimpleBoard_war/SimpleBoard_war.war \
     /usr/local/tomcat/webapps/ROOT.war

RUN rm -r /usr/local/tomcat/webapps/ROOT

EXPOSE 8080

CMD ["catalina.sh", "run"]
