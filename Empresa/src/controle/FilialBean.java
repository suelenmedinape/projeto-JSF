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

@ViewScoped
@ManagedBean
public class FilialBean {
	@EJB
	private FilialService filialService;
	@EJB
	private EnderecoService enderecoService;
	
	private Filial filial = new Filial();
	private List<Filial> filiais = new ArrayList<Filial>();
	private List<Endereco> enderecos = new ArrayList<Endereco>();
	private Long idFuncionario=0L;
	
	private Boolean edicao = false;
	
	@PostConstruct
	public void inicializar() {
		atualizarLista();
	}
	
	public void atualizarLista() {
		filiais = filialService.listAll();
		enderecos = enderecoService.listAll();
	}
	
	public void gravar() {
		enderecoService.create(filial.getEndereco());
		filialService.create(filial);
		filial = new Filial();
		FacesContext.getCurrentInstance().addMessage("", 
				new FacesMessage("Filial Gravada com Sucesso!"));
		atualizarLista();
	}
	
	public void atualizar() {
		enderecoService.merge(filial.getEndereco());
		filialService.merge(filial);
		filial = new Filial();
		FacesContext.getCurrentInstance().addMessage("", 
				new FacesMessage("Filial Atualizada com Sucesso!"));
		atualizarLista();
		edicao = false;
	}
	
	public void carregarFilial(Filial f) {
		filial = f;
		edicao = true;
	}
	
	public void deletar(Filial f) {
		try {
			filialService.remove(f);
			atualizarLista();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage("Não Pode Ser Excluido"));
		}
	}
	
	// Método para formatar o CNPJ para exibição
    public String formatarCNPJ(String cnpj) {
        if (cnpj != null && cnpj.length() == 14) {
            return cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "/" + cnpj.substring(8, 12) + "-" + cnpj.substring(12, 14);
        }
        return cnpj;
    }

    // Método para remover a formatação do CNPJ ao salvar
    public String removerFormatacaoCNPJ(String cnpj) {
        if (cnpj != null) {
            return cnpj.replaceAll("[./-]", "");
        }
        return cnpj;
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
	
	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public List<Filial> getFiliais() {
		return filiais;
	}

	public void setFiliais(List<Filial> filiais) {
		this.filiais = filiais;
	}

	public Boolean getEdicao() {
		return edicao;
	}

	public void setEdicao(Boolean edicao) {
		this.edicao = edicao;
	}
	
	
	
}
