
## internet-shop

Internet-shop with basic functions.
 
In accordance with the N-tier architecture principles, this project is comprised of several layers:
* DB
* DAO
* Services
* Controllers
* Views

## main functions

### for the Admin: 
(login: admin, password: admin)
* Managing products
* Managing users
* Viewing and deleting orders

### for the User:
* Registration
* Login/Logout
* Buying products
* Viewing the shopping cart
* Completing orders
* Viewing own orders and their details

## technologies 
* Java 8 
* Servlets
* RBAC Filters
* Tomcat
* JSP
* JSTL
* JDBC
* MySQL
* Maven
 
## to run the project
1. Set up MySql DB and Workbench on your machine
2. Run the SQL-code from the _init_db.sql_ file located in the _resources_ package
3. Insert your MySQL Workbench login and password in the properties in the ConnectionUtil class
4. Configure Tomcat
