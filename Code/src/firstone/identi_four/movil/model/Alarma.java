package firstone.identi_four.movil.model;

public class Alarma {
	private int prioridad;
	private String mensaje;
	private String CI;
	
	public Alarma()
	{
		prioridad = -1;
		mensaje = "";
		CI = null;
	}
	
	public Alarma(int prioridad, String mensaje, String CI)
	{
		this.prioridad = prioridad;
		this.mensaje = mensaje;
		this.CI = CI;
	}

	public int getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getCI() {
		return CI;
	}

	public void setCI(String cI) {
		CI = cI;
	}
	
	
}
