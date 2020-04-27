package com.instituto.projetodocurso;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.instituto.projetodocurso.domain.Categoria;
import com.instituto.projetodocurso.domain.Cidade;
import com.instituto.projetodocurso.domain.Cliente;
import com.instituto.projetodocurso.domain.Endereco;
import com.instituto.projetodocurso.domain.Estado;
import com.instituto.projetodocurso.domain.ItemPedido;
import com.instituto.projetodocurso.domain.Pagamento;
import com.instituto.projetodocurso.domain.PagamentoComBoleto;
import com.instituto.projetodocurso.domain.PagamentoComCartao;
import com.instituto.projetodocurso.domain.Pedido;
import com.instituto.projetodocurso.domain.Produto;
import com.instituto.projetodocurso.domain.enums.EstadoPagamento;
import com.instituto.projetodocurso.domain.enums.TipoCliente;
import com.instituto.projetodocurso.repositories.CategoriaRepository;
import com.instituto.projetodocurso.repositories.CidadeRepository;
import com.instituto.projetodocurso.repositories.ClienteRepository;
import com.instituto.projetodocurso.repositories.EnderecoRepository;
import com.instituto.projetodocurso.repositories.EstadoRepository;
import com.instituto.projetodocurso.repositories.ItemPedidoRepository;
import com.instituto.projetodocurso.repositories.PagamentoRepository;
import com.instituto.projetodocurso.repositories.PedidoRepository;
import com.instituto.projetodocurso.repositories.ProdutoRepository;

@SpringBootApplication
public class ProjetodocursoApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepositorio;
	@Autowired
	private ProdutoRepository produtoRepositorio;
	@Autowired
	private EstadoRepository estadoRepositorio;
	@Autowired
	private CidadeRepository cidadeRepositorio;
	@Autowired
	private EnderecoRepository enderecoRepositorio;
	@Autowired
	private ClienteRepository clienteRepositorio;
	@Autowired
	private PedidoRepository pedidoRepositorio;
	@Autowired
	private PagamentoRepository pagamentoRepositorio;
	@Autowired
	private ItemPedidoRepository itemPedidoReposotorio;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjetodocursoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		
		Produto p1 = new Produto(null,"Computador", 2000.00);
		Produto p2 = new Produto(null,"Impressora", 800.00);
		Produto p3 = new Produto(null,"Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat2, cat1));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		
		
		Estado est1 = new Estado(null,"MinasGerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avendia Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		
		
		SimpleDateFormat dataFormato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, dataFormato.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, dataFormato.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1,6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, 
				ped2, dataFormato.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0, 1, 2000);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0, 2, 80);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100, 1, 800);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		estadoRepositorio.saveAll(Arrays.asList(est1,est2));
		cidadeRepositorio.saveAll(Arrays.asList(c1,c2,c3));
		clienteRepositorio.saveAll(Arrays.asList(cli1));
		enderecoRepositorio.saveAll(Arrays.asList(e1,e2));
		categoriaRepositorio.saveAll(Arrays.asList(cat1,cat2));
		produtoRepositorio.saveAll(Arrays.asList(p1,p2,p3));
		pedidoRepositorio.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepositorio.saveAll(Arrays.asList(pagto1,pagto2));
		itemPedidoReposotorio.saveAll(Arrays.asList(ip1,ip2,ip3));
	}

}
