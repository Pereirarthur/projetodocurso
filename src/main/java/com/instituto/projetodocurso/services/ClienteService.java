package com.instituto.projetodocurso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.instituto.projetodocurso.domain.Cidade;
import com.instituto.projetodocurso.domain.Cliente;
import com.instituto.projetodocurso.domain.Endereco;
import com.instituto.projetodocurso.domain.enums.TipoCliente;
import com.instituto.projetodocurso.dto.ClienteDTO;
import com.instituto.projetodocurso.dto.ClienteNewDTO;
import com.instituto.projetodocurso.repositories.ClienteRepository;
import com.instituto.projetodocurso.repositories.EnderecoRepository;
import com.instituto.projetodocurso.services.exceptions.DataIntegrityException;
import com.instituto.projetodocurso.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repositorio;
	
	@Autowired
	private EnderecoRepository repositorioEnd;
	
	@Transactional
	public Cliente inserir(Cliente obj) {
		obj.setId(null);
		obj = repositorio.save(obj);
		repositorioEnd.saveAll(obj.getEnderecos());
		return obj;
		
	}
	
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
	

	public Cliente dtoParaObjeto(ClienteNewDTO objetoDTO) {
		Cliente cli = new Cliente(null, objetoDTO.getNome(), objetoDTO.getEmail(), 
				objetoDTO.getCpfOuCnpj(), TipoCliente.toEnum(objetoDTO.getTipo()));
		Cidade cid = new Cidade(objetoDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objetoDTO.getLogradouro(), objetoDTO.getNumero(), 
				objetoDTO.getComplemento(), objetoDTO.getBairro(), objetoDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objetoDTO.getTelefone1());
		if (objetoDTO.getTelefone2()!=null) {
			cli.getTelefones().add(objetoDTO.getTelefone2());
		}
		if (objetoDTO.getTelefone3()!=null) {
			cli.getTelefones().add(objetoDTO.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente cli, Cliente obj) {
		cli.setNome(obj.getNome());
		cli.setEmail(obj.getEmail());
	}
}
