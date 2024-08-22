package controle;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import modelo.Filial;
import modelo.Funcionario;
import service.FilialService;
import service.FuncionarioService;

@ViewScoped
@ManagedBean
public class RelatorioBean {
	
	@EJB
	private FuncionarioService funcionarioService;
	@EJB
	private FilialService filialService;
	
	private List<Funcionario> funcionarios = new ArrayList<Funcionario>();
	private Double valorMin, valorMax;
	private Boolean todasFiliais = false;
	private String nomeFilial;
	private Long idFilial=0L;
	private Double auxiliar;
	
	public void filtrar() {
		if(todasFiliais) {
			if(valorMin > valorMax) {
				auxiliar = valorMin;
				valorMin = valorMax;
				valorMax = auxiliar;
			}
			funcionarios = funcionarioService.listarTodasFilias(valorMin, valorMax);				
		}else {
			if(valorMin > valorMax) {
				auxiliar = valorMin;
				valorMin = valorMax;
				valorMax = auxiliar;
			}
			Filial filial = filialService.obtemPorId(idFilial);
			nomeFilial = filial.getNome();
			funcionarios = funcionarioService.listarPorFilial(nomeFilial, valorMin, valorMax);
		}
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public Boolean getTodasFiliais() {
		return todasFiliais;
	}

	public void setTodasFiliais(Boolean todasFiliais) {
		this.todasFiliais = todasFiliais;
	}

	public String getNomeFilial() {
		return nomeFilial;
	}

	public void setNomeFilial(String nomeFilial) {
		this.nomeFilial = nomeFilial;
	}

	public Double getValorMin() {
		return valorMin;
	}

	public void setValorMin(Double valorMin) {
		this.valorMin = valorMin;
	}

	public Double getValorMax() {
		return valorMax;
	}

	public void setValorMax(Double valorMax) {
		this.valorMax = valorMax;
	}

	public Long getIdFilial() {
		return idFilial;
	}

	public void setIdFilial(Long idFilial) {
		this.idFilial = idFilial;
	}
	
}
