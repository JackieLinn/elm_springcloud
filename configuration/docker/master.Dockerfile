FROM mysql:5.7
COPY configuration/conf/master.cnf /etc/mysql/conf.d/master.cnf
RUN chmod 644 /etc/mysql/conf.d/master.cnf
