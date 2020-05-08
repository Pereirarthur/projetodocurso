package com.instituto.projetodocurso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.instituto.projetodocurso.domain.Categoria;
import com.instituto.projetodocurso.domain.Produto;
import com.instituto.projetodocurso.repositories.CategoriaRepository;
import com.instituto.projetodocurso.repositories.ProdutoRepository;
import com.instituto.projetodocurso.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repositorio;
	
	@Autowired
	private CategoriaRepository repositoiroCategoria;
	
	public Produto buscar(Integer id) {
		Optional<Produto> cat = repositorio.findById(id);
		return cat.orElseThrow(() -> new ObjectNotFoundException("Objeto Não Encontrado! "
				+ "id = "+id+", tipo = " + Produto.class.getName()));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, 
			Integer linesPerPage, String orderby, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, 
				Direction.valueOf(direction), orderby);
		List<Categoria> categorias = repositoiroCategoria.findAllById(ids);
		return repositorio.findDistinctByNomeContainingAndCategoriasInIgnoreCase(nome, categorias, pageRequest);
	}
	
}
