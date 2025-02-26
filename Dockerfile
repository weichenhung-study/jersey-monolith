FROM tomcat:9.0

COPY target/jersey-monolith.war /usr/local/tomcat/webapps/

CMD ["/usr/local/tomcat/bin/catalina.sh","run"]