/****************************************************************************** 
 *  @author  Ragini Patil
 *  @version 1.0
 *  @since   5-10-2017
 *	@purpose RegisterDao is class responsible for Database related operations
 *			 while registration.
 *			
 *			 
 ******************************************************************************/
package com.bridgelabz.DAO;

import java.sql.SQLException;

import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.bridgelabz.Model.Registration;

public class RegisterDao {
	private static SessionFactory factory;
	private static Registration registration = null;

	/**
	 * 
	 * @param user
	 *            Registration
	 * @return boolean
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 *             <p>
	 *             register accepts object user of type Registration having name
	 *             ,emailid,phonenumber,password and enter details in database
	 *             return true if insert is sucessfull
	 */
	@SuppressWarnings("static-access")
	public static boolean register(Registration user) throws SQLException, ClassNotFoundException {
		factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
			try {	
			Registration reg = new Registration();
			// save to database			
			int number=	(int) session.save(reg);
			System.out.println(number);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (null != transaction) {
			e.printStackTrace();
				transaction.rollback();
				System.out.println("Rollback !!!");
				return false;
			}
		} finally {
			session.close();
		}
		return false;
	}
}
