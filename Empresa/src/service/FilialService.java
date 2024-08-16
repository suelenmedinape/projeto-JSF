package service;

import javax.ejb.Stateless;

import modelo.Filial;

@Stateless
public class FilialService extends GenericService<Filial>{
	
	public FilialService() {
		super(Filial.class);
	}
	
}
