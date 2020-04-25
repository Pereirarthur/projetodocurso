package com.instituto.projetodocurso.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instituto.projetodocurso.domain.Categoria;
import com.instituto.projetodocurso.repositories.CategoriaRepository;
import com.instituto.projetodocurso.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repositorio;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> cat = repositorio.findById(id);
		return cat.orElseThrow(() -> new ObjectNotFoundException("Objeto Não Encontrado! "
				+ "id = "+id+", tipo = " + Categoria.class.getName()));
	}
	
}
