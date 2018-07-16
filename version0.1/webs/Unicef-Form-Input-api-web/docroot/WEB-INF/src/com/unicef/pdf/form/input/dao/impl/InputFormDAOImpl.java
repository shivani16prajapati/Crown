package com.unicef.pdf.form.input.dao.impl;

import com.unicef.pdf.form.input.dao.InputFormDAO;
import com.unicef.pdf.form.input.domain.InputForm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class InputFormDAOImpl implements InputFormDAO{
	
	private Logger logger = LoggerFactory.getLogger(InputFormDAO.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	public boolean saveORUpdate(InputForm inputForm) {
		//Session session = sessionFactory.getCurrentSession();
		
		boolean isUpdated = false;
		try{
			sessionFactory.getCurrentSession().saveOrUpdate(inputForm);
			isUpdated = true;
			logger.info("Form data submitted sucessfully");
		}catch(Exception e){
			logger.error("Error while saving form data:"+e.getMessage());	
		}
		return isUpdated;
		
	}

}
