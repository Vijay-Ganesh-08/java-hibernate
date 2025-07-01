# java-hibernate
Simple Java Application with Hibernate configuration connecting to MySQL Database and performing CRUD Operations.


- Hibernate is the best ORM (Object Relational Mapping) Framework available for Java Programming
- Concept of Storing data in Databases is called Persistance
- Database Connection is established and to perform CRUD Operation.
- JDBC is the connector between Application and Databases for Java Applications.
- Hibernate converts from Java Classes/Objects to Database Tables/Data.
- In Hibernate, Class is a Table and Objects of the class are the rows in the table.
- ACID Property - Atomicity , Consistency , Isolation , Durability.

### Config :

- `hibernate.connection.url` → DB URL with Database name. (jdbc://server/database)
- `hibernate.connection.username` → User name to connect to DB
- `hibernate.connection.password` → Password to connect to DB
- `hibernate.connection.driver_class`→ Driver used to connect to DB (mysql.jdbc/mongo.jdbc etc)
- `dialect` → Used to define the DB Schema (MySql/Mongo etc)
- `hibernate.hbm2ddl.autoddl.auto`
    - `Create` → will drop the old table create a new table every time we execute
    - `Update` → will insert the existing table if already exists, if not it will create a table once and inserts the rows in next executions.
- `hibernate.show_sql` → Used to display the sql query in console or logs

### Annotations :

- `@Entity` → Used to indicate the hibernate for Table Class
- `@Table(name="XXX")`→ Used to indicate the class is mapped to a table when it has different name, its not required when the Table name and Class name are same.
- `@Id` → used to indicate the primary key
- `@Column(name ="XXX")` → Used to indicate the attribute is mapped to a table column when it has different name, its not required when the column name and attribute name are same. it converts the camel casing to _ in SQL (Ex: FIRST_NAME in Table Column and firstName in Java Pojo)
- `@Transient` → Used only in the Pojo class and it will not be stored in the Database and its considered as Temporary data storage.
- `@Embeddable`→ Used to embed the class inside other entity class. (Ex. fName, lName, mName is provided in different class and being added in the main entity class as an attribute)
- **Relation Mapping**
    - `@OneToOne` → Used when there is mapping with 1 : 1 from different table (Ex. Employee mapped with Work Laptop), Hibernate creates a column in the table which is marked with this annotation and insert the rows of it.
    - `@OneToMany` → Used when there is mapping with 1 : many from different table (Ex. Student is mapped to different Courses). It creates a new table with the mapping of Ids from both entity.
    - `@OneToMany(mappedBy = "stud")` → Used to indicate the entity that the mapping is taken care by other entity class. which will avoid creating a new table with mapping records. stud is the attribute name of the other entity class which is defined with relation mapping
    - `@ManyToOne` → Used when there is mapping with many : 1 from different table (Ex. Course is mapped to different students)
    - `@ManyToMany` → Used when there is mapping with many : many from different table (Ex. Computers in the Computer Lab and can be access by any students)
    - `@ManyToMany(mappedBy = "studs")` → Used to indicate the entity that the mapping is taken care by other entity class. which will avoid creating a new table with mapping records. stud is the attribute name of the other entity class which is defined with relation mapping.
- **Fetch Type**
    - Lazy → it fetches the list of collections mapped to entity class with other class only when it called/used in the program, by default its lazy.
        - `@OneToMany(fetch = FetchType.*LAZY*)` → To enable Lazy fetch explicitly.
    - Eager → It fetches the list of collections mapper to entity class with other class object irrespective of the usage.
        - `@OneToMany(fetch = FetchType.*EAGER*)` → To enable Eager fetch.
        - Used Left Outer Join and extracts the data.
### HQL (Hibernate Query Language)

- When you want to select specific columns or with multiple conditions.
- Its a query similar to SQL but the Table and Column names are replaced with Class and Attribute Name.
- Select * from Employee can be replaced with from Employee since Select * is obvious for retrieval in HQL.
- We are use Joins with alias to other classes. GroupBy, OrderBy as same as in SQL.
- The response of HQL is list of Objects (Entity Class)
- Native Query is used to write a user defined SQL queries with specific columns but the result-set is array of Object and it needs to be iterated as Array.
- Parameters are passed with colon “:” in the query and its added with local variable.

### Hibernate Object State / Persistance Life Cycle

- new - Object Creation.
    - Ex : `Employee emp = new Employee();`
- Transient - Data Assignment or Usage at the application level, eligible for GC.
    - Ex : `emp.setName(”Vijay”)`;
- Persistent - To Save the Data or syncing b/w application and database like fetching.
    - Ex : `session.save(emp);` or `empRepo.save(emp);`
    - Once saved and when the data is changed, it gets in sync with DB.
    - Ex : After executing the save statement and when we change the name with `emp.setName(”Vijay Ganesh”);` and in the DB it will be Vijay Ganesh and not Vijay.
- Detached - Data Usage at the application level, eligible for GC.
    - Ex : `session.detach(emp);` will not let the DB sync happen if there’s any changes happened to the attributes.
    - its generally used after commit, by default once committed into DB all the objects are detached and enabled for GC.
- Removed - Data which is removed from Database but available in Java Application for usage, eligible for GC.
    - Ex : `session.remove(emp);` will remove the record from DB
- null - Object Destruction.
    - Ex : `emp = null;` will explicitly enable the object for GC.

### Hibernate Get/Load

- Get - Fetching the Data from Database, query is fired and it hits the database and it gives you the Object.
    - Ex : `Employee emp = session.get(Employee.class, 10);` → it hits the DB and gives the employee details.
    - It throws `NullPointerException` when you want to read the data which is not available in DB.
- Load - Fetching the Data from Database, query is fired and it hits the database only when the object is used until then it gives you the proxy Object.
    - Ex : `Employee emp = session.load(Employee.class, 10);` → it will not hit the DB and gives the proxy object for employee.
    - `System.out.println(emp);` → it will hit the DB and gets the data to the proxy object.
    - It throws `ObjectNotFoundException` when you want to read the data which is not available in DB.

### JPA (Java Persistence API → Jakarta Persistence API)

- ORM (Object Relational Mapping) Frameworks
    - Hibernate
    - iBatis
    - TopLink
- Saving Object directly to Database Tables
- JPA is a Standard Specification Template to be used by ORM frameworks and if the application is built if on JPA with Hibernate it will be easy to switch between ORM frameworks like JPA with iBatis or JPA with TopLink