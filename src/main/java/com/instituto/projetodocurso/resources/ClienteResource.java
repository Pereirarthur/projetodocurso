package com.instituto.projetodocurso.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.instituto.projetodocurso.domain.Cliente;
import com.instituto.projetodocurso.dto.ClienteDTO;
import com.instituto.projetodocurso.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

	@Autowired
	ClienteService servico;
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = servico.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@Valid  @RequestBody ClienteDTO objDTO, @PathVariable Integer id){
		Cliente obj = servico.dtoParaObjeto(objDTO);
		obj.setId(id);
		obj=servico.update(obj);
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable Integer id) {
		servico.delete(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> listar() {
		List<Cliente> lista = servico.findAll();
		List<ClienteDTO> listaDTO = lista.stream().
				map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@RequestMapping(value="/page",method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> buscarEmPagina(
			@RequestParam(value = "page",defaultValue = "0")Integer page, 
			@RequestParam(value = "linesPerPage",defaultValue = "24")Integer linesPerPage, 
			@RequestParam(value = "orderby",defaultValue = "nome")String orderby, 
			@RequestParam(value = "direction",defaultValue = "ASC")String direction) {
		Page<Cliente> lista = servico.findPage(page, linesPerPage, orderby, direction);
		Page<ClienteDTO> listaDTO = lista.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listaDTO);
	}
	
}
