package com.instituto.projetodocurso.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.instituto.projetodocurso.domain.Produto;
import com.instituto.projetodocurso.dto.ProdutoDTO;
import com.instituto.projetodocurso.resources.utils.URL;
import com.instituto.projetodocurso.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

	@Autowired
	ProdutoService servico;
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> buscar(@PathVariable Integer id) {
		Produto obj = servico.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> buscarEmPagina(
			@RequestParam(value = "nome",defaultValue = "")String nome, 
			@RequestParam(value = "categorias",defaultValue = "")String categorias, 
			@RequestParam(value = "page",defaultValue = "0")Integer page, 
			@RequestParam(value = "linesPerPage",defaultValue = "24")Integer linesPerPage, 
			@RequestParam(value = "orderby",defaultValue = "nome")String orderby, 
			@RequestParam(value = "direction",defaultValue = "ASC")String direction) {
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> lista = servico.search(nomeDecoded, ids, page, linesPerPage, orderby, direction);
		Page<ProdutoDTO> listaDTO = lista.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listaDTO);
	}
	
}
