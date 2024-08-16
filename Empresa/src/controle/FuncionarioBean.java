package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Endereco;
import modelo.Filial;
import modelo.Funcionario;
import service.EnderecoService;
import service.FilialService;
import service.FuncionarioService;

@ViewScoped
@ManagedBean
public class FuncionarioBean {
		@EJB
		private FuncionarioService funcionarioService;
		
		@EJB
		private EnderecoService enderecoService;
		
		@EJB
		private FilialService filialService;
		
		private Funcionario funcionario = new Funcionario();
		private List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		private List<Endereco> enderecos = new ArrayList<Endereco>();
		private List<Filial> filiais = new ArrayList<Filial>();
		private Long idFilial=0L;
		
		private Boolean edicao = false;
		private Boolean estado = false;
		
		@PostConstruct
		public void inicializar() {
			filiais = filialService.listAll();
			atualizarLista();
		}
		
		private void atualizarLista() {
			funcionarios = funcionarioService.listAll();
			enderecos = enderecoService.listAll();
		}
		
		public void gravar() {
			Filial f = filialService.obtemPorId(idFilial);
			funcionario.setFilial(f);
			if(funcionario.getId()==null) {
				enderecoService.create(funcionario.getEndereco());
				funcionarioService.create(funcionario);
			} else {
				enderecoService.merge(funcionario.getEndereco());
				funcionarioService.merge(funcionario);
			}
			
			atualizarLista();
			funcionario = new Funcionario();
			idFilial=0L;
		}
		
		public void carregarFuncionario(Funcionario f) {
			estado=true;
			funcionario = f;
			idFilial=funcionario.getFilial().getId();
		}
		
		public void atualizar() {
			funcionarioService.merge(funcionario);
			funcionario = new Funcionario();
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage("Funcionario Atualizado com Sucesso!"));
			atualizarLista();
			edicao=false;
		}
		
		public void deletar(Funcionario f) {
			funcionarioService.remove(f);
			atualizarLista();
		}
		
		// Método para formatar o CPF para exibição
	    public String formatarCPF(String cpf) {
	        if (cpf != null && cpf.length() == 11) {
	            return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
	        }
	        return cpf;
	    }

	    // Método para remover a formatação do CPF ao salvar
	    public String removerFormatacaoCPF(String cpf) {
	        if (cpf != null) {
	            return cpf.replaceAll("[.-]", "");
	        }
	        return cpf;
	    }
	    
	    // Método para formatar o CEP para exibição
	    public String formatarCEP(String cep) {
	        if (cep != null && cep.length() == 8) {
	            return cep.substring(0, 5) + "-" + cep.substring(5, 8);
	        }
	        return cep;
	    }

	    // Método para remover a formatação do CEP ao salvar
	    public String removerFormatacaoCEP(String cep) {
	        if (cep != null) {
	            return cep.replaceAll("-", "");
	        }
	        return cep;
	    }

		public Boolean getEstado() {
			return estado;
		}

		public void setEstado(Boolean estado) {
			this.estado = estado;
		}
		
		public FilialService getFilialService() {
			return filialService;
		}

		public void setFilialService(FilialService filialService) {
			this.filialService = filialService;
		}

		public List<Filial> getFiliais() {
			return filiais;
		}

		public void setFiliais(List<Filial> filiais) {
			this.filiais = filiais;
		}

		public Long getIdFilial() {
			return idFilial;
		}

		public void setIdFilial(Long idFilial) {
			this.idFilial = idFilial;
		}

		public List<Endereco> getEnderecos() {
			return enderecos;
		}

		public void setEnderecos(List<Endereco> enderecos) {
			this.enderecos = enderecos;
		}
		
		public Funcionario getFuncionario() {
			return funcionario;
		}

		public void setFuncionario(Funcionario funcionario) {
			this.funcionario = funcionario;
		}

		public List<Funcionario> getFuncionarios() {
			return funcionarios;
		}

		public void setPacientes(List<Funcionario> funcionarios) {
			this.funcionarios = funcionarios;
		}

		public Boolean getEdicao() {
			return edicao;
		}

		public void setEdicao(Boolean edicao) {
			this.edicao = edicao;
		}

		
	}
