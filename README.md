# MondoVespaPX
E-commerce per ricambi e accessori Vespa PX.

Progetto per il corso di Tecnologie Software per il Web
Università degli Studi di Salerno - A.A. 2025/2026

## Requisiti
- Java JDK 17
- Apache Tomcat 11
- MySQL 8
- Eclipse IDE for Enterprise Java

Eseguire lo script database.sql in MySQL Workbench.

Copiare mysql-connector-j-9.7.0.jar in C:\tomcat11\lib\.

Aggiungere in C:\tomcat11\conf\context.xml dentro <Context>:

```xml
<Resource
    name="jdbc/mondovespapx"
    auth="Container"
    type="javax.sql.DataSource"
    maxTotal="10"
    maxIdle="5"
    maxWaitMillis="10000"
    username="root"
    password="INSERIRE LA PROPRIA PASSWORD DI MYSQL"
    driverClassName="com.mysql.cj.jdbc.Driver"
    url="jdbc:mysql://localhost:3306/mondovespapx"/>
```

Credenziali admin:
Email: admin@mondovespapx.it
Password: admin1234 (Da cambiare subito appena si accede)