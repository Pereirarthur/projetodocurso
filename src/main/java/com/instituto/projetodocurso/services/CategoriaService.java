package com.instituto.projetodocurso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.instituto.projetodocurso.domain.Categoria;
import com.instituto.projetodocurso.domain.Cliente;
import com.instituto.projetodocurso.dto.CategoriaDTO;
import com.instituto.projetodocurso.repositories.CategoriaRepository;
import com.instituto.projetodocurso.services.exceptions.DataIntegrityException;
import com.instituto.projetodocurso.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repositorio;
	
	
	public Categoria inserir(Categoria obj) {
		obj.setId(null);
		return repositorio.save(obj);
	}
	
	public Categoria find(Integer id) {
		Optional<Categoria> cat = repositorio.findById(id);
		return cat.orElseThrow(() -> new ObjectNotFoundException("Objeto Não Encontrado! "
				+ "id = "+id+", tipo = " + Categoria.class.getName()));
	}
	
	public Categoria update (Categoria obj) {
		Categoria cat = find(obj.getId());
		updateData(cat,obj);
		return repositorio.save(cat);
	}
	
	public void delete (Integer id) {
		find(id);
		try {
			repositorio.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar objetos com associação");
		}
	
	}
	
	public List<Categoria> findAll(){
		return repositorio.findAll();
	}
		
	public Page<Categoria> findPage (Integer page, Integer linesPerPage, String orderby, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderby);
		return repositorio.findAll(pageRequest);
	}
	
	public Categoria dtoParaObjeto(CategoriaDTO objetoDTO) {
		return new Categoria(objetoDTO.getId(), objetoDTO.getNome());
	}
	
	private void updateData(Categoria cat, Categoria obj) {
		cat.setNome(obj.getNome());
	}
	
}
