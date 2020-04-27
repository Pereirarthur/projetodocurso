package com.instituto.projetodocurso.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instituto.projetodocurso.domain.Pedido;
import com.instituto.projetodocurso.repositories.PedidoRepository;
import com.instituto.projetodocurso.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repositorio;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> cat = repositorio.findById(id);
		return cat.orElseThrow(() -> new ObjectNotFoundException("Objeto Não Encontrado! "
				+ "id = "+id+", tipo = " + Pedido.class.getName()));
	}
	
}
