package com.instituto.projetodocurso.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instituto.projetodocurso.domain.Cliente;
import com.instituto.projetodocurso.repositories.ClienteRepository;
import com.instituto.projetodocurso.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repositorio;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> cat = repositorio.findById(id);
		return cat.orElseThrow(() -> new ObjectNotFoundException("Objeto Não Encontrado! "
				+ "id = "+id+", tipo = " + Cliente.class.getName()));
	}
	
}
