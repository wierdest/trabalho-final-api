package br.org.serratec.api.cel.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate dataPedido;
	private LocalDate dataEntrega;
	//USAR ENUM
	private String status;
	private double valorTotal;
	private String descricao;
	
	@ManyToOne
	private Cliente cliente;

	
	@OneToMany(mappedBy = "pedido", cascade=CascadeType.ALL)
	@JsonManagedReference
	private List<ItemPedido> itensPedido = new ArrayList<ItemPedido>();
	
	public Pedido() {
		
	}
	

	public Pedido(Long id, LocalDate dataPedido, LocalDate dataEntrega, String status, double valorTotal,
			String descricao, Cliente cliente, List<ItemPedido> itemPedido) {
		super();
		this.id = id;
		this.dataPedido = dataPedido;
		this.dataEntrega = dataEntrega;
		this.status = status;
		this.valorTotal = valorTotal;
		this.descricao = descricao;
		this.cliente = cliente;
		this.itensPedido = itemPedido;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDate dataPedido) {
		this.dataPedido = dataPedido;
	}

	public LocalDate getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<ItemPedido> getItensPedido() {
		return itensPedido;
	}

	public void setItensPedido(List<ItemPedido> itemPedido) {
		this.itensPedido = itemPedido;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	

	
	
}
