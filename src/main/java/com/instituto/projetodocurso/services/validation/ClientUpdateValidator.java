package com.instituto.projetodocurso.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.instituto.projetodocurso.domain.Cliente;
import com.instituto.projetodocurso.dto.ClienteDTO;
import com.instituto.projetodocurso.repositories.ClienteRepository;
import com.instituto.projetodocurso.resources.exceptions.FieldMessage;

public class ClientUpdateValidator implements ConstraintValidator<ClientUpdate, ClienteDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void initialize(ClientUpdate ann) {
	}
	
	@Autowired
	private ClienteRepository repositorio;

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		Map <String, String> map = (Map <String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriID = Integer.parseInt(map.get("id"));
		
		Cliente aux = repositorio.findByEmail(objDto.getEmail());
		if (aux != null && !aux.getId().equals(uriID)) {
			list.add(new FieldMessage("email", "Email já cadastrado"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
