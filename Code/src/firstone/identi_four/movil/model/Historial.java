package firstone.identi_four.movil.model;

import java.util.Date;

public class Historial {
	private Date fechaHora;
	private String placa;
	private String Accion;
	private String tranca;
	private String Area;
	public Date getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getAccion() {
		return Accion;
	}
	public void setAccion(String accion) {
		Accion = accion;
	}
	public String getTranca() {
		return tranca;
	}
	public void setTranca(String tranca) {
		this.tranca = tranca;
	}
	public String getArea() {
		return Area;
	}
	public void setArea(String area) {
		Area = area;
	}
	
	
}
