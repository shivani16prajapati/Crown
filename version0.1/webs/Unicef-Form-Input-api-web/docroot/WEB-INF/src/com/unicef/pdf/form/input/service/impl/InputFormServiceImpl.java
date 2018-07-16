package com.unicef.pdf.form.input.service.impl;

import com.unicef.pdf.form.input.dao.InputFormDAO;
import com.unicef.pdf.form.input.domain.InputForm;
import com.unicef.pdf.form.input.service.InputFormService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InputFormServiceImpl implements InputFormService{

	@Autowired
	private InputFormDAO inputFormDAO;
	
	public boolean saveORUpdate(InputForm inputForm) {
		return inputFormDAO.saveORUpdate(inputForm);
	}

	
}
