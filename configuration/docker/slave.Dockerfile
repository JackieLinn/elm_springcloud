FROM mysql:5.7
COPY configuration/conf/slave.cnf /etc/mysql/conf.d/slave.cnf
RUN chmod 644 /etc/mysql/conf.d/slave.cnf
