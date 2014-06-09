package firstone.identi_four.movil.presentacion;

import java.io.UnsupportedEncodingException;


import firstone.identi_four.ccs.Accion;
import firstone.identi_four.movil.service.Servicio;
import firstone.serializable.Contrato;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NotificacionActivity extends Activity {

	String ci_guardia;
	
	public SharedPreferences preferences;
	public SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_llego_notificacion);
		preferences = this.getSharedPreferences(SettingsActivity.PREF_KEY, MODE_PRIVATE);
		editor = preferences.edit();
		
		Bundle b = getIntent().getExtras();
		String ci = b.getString("ci");
		String placa = b.getString("placa");
		String marca = b.getString("marca");
		String tipo = b.getString("tipo");
		ci_guardia = b.getString("ci_guardia");
		
		TextView tx = (TextView)findViewById(R.id.text_sms_notificacion);
		
		if (tipo.equals("INGRESO"))
			tx.setText("Su vehiculo con placa " + placa + " y marca " + marca + " esta INGRESANDO");
		else
			tx.setText("Su vehiculo con placa " + placa + " y marca " + marca + " esta SALIENDO");
		
	}
	
	public void onAceptar(View w)
	{
		if (! Servicio.respondio)
		{
			Servicio.respondio = true;
			Contrato contrato = new Contrato();
			contrato.setAccion(Accion.DENEGAR_INGRESO_SALIDA);
			contrato.setId_entorno(Integer.parseInt(preferences.getString(SettingsActivity.PROPIETARIO_LICENCIA, "")));
			String cad = ci_guardia+",true";
			try {
				contrato.setContenido(cad.getBytes("UTF-8"));
				Servicio.icore.enviar(contrato);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
		}
		this.finish();
	}
	
	public void onDenegar(View w)
	{
		if (! Servicio.respondio)
		{
			Servicio.respondio = true;
			Contrato contrato = new Contrato();
			contrato.setAccion(Accion.DENEGAR_INGRESO_SALIDA);
			contrato.setId_entorno(Integer.parseInt(preferences.getString(SettingsActivity.PROPIETARIO_LICENCIA, "")));
			String cad = ci_guardia+",false";
			try {
				contrato.setContenido(cad.getBytes("UTF-8"));
				Servicio.icore.enviar(contrato);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		this.finish();
	}
	
}
