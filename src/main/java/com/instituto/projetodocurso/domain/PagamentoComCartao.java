package com.instituto.projetodocurso.domain;

import javax.persistence.Entity;

import com.instituto.projetodocurso.domain.enums.EstadoPagamento;

@Entity
public class PagamentoComCartao extends Pagamento{
	private static final long serialVersionUID = 1L;
	
	private int numeroDeParcelas;
	
	public PagamentoComCartao() {
	}

	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, int numeroDeParcelas) {
		super(id, estado, pedido);
		this.numeroDeParcelas=numeroDeParcelas;
		// TODO Auto-generated constructor stub
	}

	public int getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(int numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
	
	
	
}
