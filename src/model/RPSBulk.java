package model;

import java.util.List;

/**
 * Created by Vitor on 11/11/2015.
 */
public class RPSBulk {

	private String id;
	private String numeroLote;
	private String cnpj;
	private String inscricaoMunicipal;
	private List<RPS> listaRps;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumeroLote() {
		return numeroLote;
	}

	public void setNumeroLote(String numeroLote) {
		this.numeroLote = numeroLote;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public int getQuantidadeRps() {
		if(listaRps != null) {
			return listaRps.size();
		}
		else {
			return 0;
		}
	}

	public List<RPS> getListaRps() {
		return listaRps;
	}

	public void setListaRps(List<RPS> listaRps) {
		this.listaRps = listaRps;
	}
}
