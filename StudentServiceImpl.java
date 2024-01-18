package com.greatLearning.studentManagement.service;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.greatLearning.studentManagement.entity.Student;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Repository
public class StudentServiceImpl implements StudentService {


	private SessionFactory sessionFactory;
	private Session session;
	@Autowired
	StudentServiceImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory=sessionFactory;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}
	}

	@Transactional
	public List<Student> findAll() {

		Transaction tx = session.beginTransaction();
		List<Student> students=session.createQuery("from Student").list();

		tx.commit();
		return students;
	}

	@Transactional
	public Student findById(int id) {

		Student student = new Student();
		Transaction tx = session.beginTransaction();

		student = session.get(Student.class, id);
		tx.commit();
		return student;
	}

	@Transactional
	public void save(Student theStudent) {

		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(theStudent);
		tx.commit();
	}
	@Transactional
	public void deleteById(int id) {
		Transaction tx = session.beginTransaction();
		Student student = session.get(Student.class, id);
		session.delete(student);
		tx.commit();
	}
}