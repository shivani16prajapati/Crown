package com.unicef.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unicef.dao.GenericDAO;
import com.liferay.portal.kernel.util.Validator;

/**
 * <p>
 * This is generic DAO implementation class and has all generic DAO methods.
 * </p>
 * 
 * @author Divyang Patel
 */
@Transactional
@Repository
public abstract class GenericDAOImpl<T, PK extends Serializable> implements
		GenericDAO<T, PK> {
	
	@Autowired
	protected SessionFactory sessionFactory;
	private Class<T> type;

	public GenericDAOImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class) pt.getActualTypeArguments()[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.unicef.dao.GenericDao#create(java.lang.Object)
	 */
	@Override
	public PK create(T t) {
		return (PK) sessionFactory.getCurrentSession().save(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.unicef.dao.GenericDao#countAll()
	 */
	@Override
	public Integer countAll() {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.unicef.dao.GenericDao#delete(java.lang.Long)
	 */
	@Override
	public void delete(T t) {
		sessionFactory.getCurrentSession().delete(t);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.unicef.dao.GenericDao#find(java.lang.Long)
	 */
	@Override
	public T find(PK id) {
		return (T) sessionFactory.getCurrentSession().get(type.getName(), id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.unicef.dao.GenericDao#findAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return sessionFactory.getCurrentSession()
				.createQuery("from " + type.getName()).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.unicef.dao.GenericDao#update(java.lang.Object)
	 */
	@Override
	public T update(T t) {
		sessionFactory.getCurrentSession().update(t);
		return t;
	}

	@Override
	public List<T> finds(String orderParam, boolean desc, int first, int max) {
		String queryString = "from " + type.getName();
		if(Validator.isNotNull(orderParam)){
			queryString += " t ORDER BY t." + orderParam;
			if(desc){
				queryString += " DESC";
			}
		}
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		if(first >= 0) {
			q.setFirstResult(first);
		}
		if(max >= 0) {
			q.setMaxResults(max);
		}
		return q.list();
	}

	/**
	 * This method set sessionFactory object.
	 * 
	 * @param sessionFactory
	 */
	protected void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
