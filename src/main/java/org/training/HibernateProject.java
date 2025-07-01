package org.training;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateProject {
    public static void main(String[] args) {


        Employee employee = new Employee();
        employee.seteId(101);
        employee.seteName("Vijay Ganesh");
        employee.setDesignation("Manager");

        Configuration configuration = new Configuration();
        //-->Below command is to add the Entity class to be handled by Hibernate
        configuration.addAnnotatedClass(org.training.Employee.class);
        //-->In-case using different config file name for hibernate, use configuration.configure("<<FileName>>");
        configuration.configure();

        //-->OR  can be return as below
        //Configuration configuration1 = new Configuration()
        //  .addAnnotatedClass(org.training.Employee.class)
        //  .configure();

        //-->SessionFactory is used to create an Session Object and can be only one for an Application
        //-->It should be closed or to be configured for AutoClose (try with Resources)
        SessionFactory sf = configuration.buildSessionFactory();

        //-->Session can be of any Number in an application
        Session session = sf.openSession();

        //-->Creating and Beginning a Transaction for session
        Transaction transaction = session.beginTransaction();

        //-->To save the data in DB
        session.persist(employee);

        //-->To Update the data in DB
//        session.merge(employee);

        //-->To Remove the data in DB
//        Employee employee1 = session.find(Employee.class,105);
//        session.remove(employee1);

        //Committing the Transaction
        transaction.commit();

        //Fetching the record
//        Employee employee2 = session.find(Employee.class,102);
//        System.out.println(employee2);

        session.close();
        sf.close();

    }



}