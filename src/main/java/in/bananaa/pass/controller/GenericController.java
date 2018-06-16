package in.bananaa.pass.controller;

import org.springframework.beans.factory.annotation.Autowired;

import in.bananaa.pass.helper.ResponseHelper;

public abstract class GenericController {
	
	protected static final String HEADER = "Accept=application/json";
	
	@Autowired
	protected ResponseHelper responseHelper;
}
