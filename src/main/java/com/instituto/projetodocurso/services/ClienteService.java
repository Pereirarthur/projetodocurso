package com.instituto.projetodocurso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.instituto.projetodocurso.domain.Cliente;
import com.instituto.projetodocurso.dto.ClienteDTO;
import com.instituto.projetodocurso.repositories.ClienteRepository;
import com.instituto.projetodocurso.services.exceptions.DataIntegrityException;
import com.instituto.projetodocurso.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repositorio;
	
	public Cliente find(Integer id) {
		Optional<Cliente> cat = repositorio.findById(id);
		return cat.orElseThrow(() -> new ObjectNotFoundException("Objeto Não Encontrado! "
				+ "id = "+id+", tipo = " + Cliente.class.getName()));
	}
	
	public Cliente update (Cliente obj) {
		Cliente cli = find(obj.getId());
		updateData(cli,obj);
		return repositorio.save(cli);
	}
	
	public void delete (Integer id) {
		find(id);
		try {
			repositorio.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar objetos com associação");
		}
	
	}
	
	public List<Cliente> findAll(){
		return repositorio.findAll();
	}
		
	public Page<Cliente> findPage (Integer page, Integer linesPerPage, String orderby, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderby);
		return repositorio.findAll(pageRequest);
	}
	
	public Cliente dtoParaObjeto(ClienteDTO objetoDTO) {
		return new Cliente(objetoDTO.getId(), objetoDTO.getNome(), objetoDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente cli, Cliente obj) {
		cli.setNome(obj.getNome());
		cli.setEmail(obj.getEmail());
	}
}
