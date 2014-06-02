package firstone.identi_four.movil.presentacion;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.firstonesoft.client.util.ObjectUtil;

import firstone.identi_four.ccs.Accion;
import firstone.identi_four.movil.service.Servicio;
import firstone.serializable.Alarma;
import firstone.serializable.Aviso;
import firstone.serializable.Contrato;

public class AlarmaActivity extends Activity{
	
	private static final String TAG = "AlarmaActivity";
	
	private String CI = null;
	private String nombre = null;
	private String apellido = null;
	private int id_entorno;
	
	//begin_region COMPONENTES DE LA VISTA
	RadioButton rb_ctea;
	RadioButton rb_ctt;
	RadioButton rb_maa;
	
	EditText et;
	//end_region
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarma);
		
		Bundle b = getIntent().getExtras();
        CI = b.getString("ci");
        nombre = b.getString("nombre");
        apellido = b.getString("apellido");
		id_entorno = b.getInt("id_entorno");
		InitializeComponent();
	}
	
	private void InitializeComponent()
	{
		rb_ctea = (RadioButton)findViewById(R.id.alarma_rb_cerrar_trancas_en_esta_area);
		rb_ctt = (RadioButton)findViewById(R.id.alarma_rb_cerrar_todas_trancas);
		rb_maa = (RadioButton)findViewById(R.id.alarma_rb_me_atacaron);
		
		et = (EditText)findViewById(R.id.text_mensaje_aviso_alarma);
		
		rb_ctea.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rb_ctt.setChecked(false);
				rb_maa.setChecked(false);
//				et.setCursorVisible(false);
			}
		});
		
		rb_ctt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rb_ctea.setChecked(false);
				rb_maa.setChecked(false);
//				et.setCursorVisible(false);
			}
		});
		
		rb_maa.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rb_ctt.setChecked(false);
				rb_ctea.setChecked(false);
//				et.setCursorVisible(false);
			}
		});
	}
	
	public void onAlertar(View w)
	{
		Aviso aviso = new Aviso();
		aviso.setFecha_hora((new Date()).getTime());
		aviso.setFrom(nombre+" " + apellido);
		aviso.setMensaje(et.getText().toString());
		
		if (rb_ctea.isChecked())
			aviso.setTo(Aviso.DIRIGIDO_TODOS);
		else
		{
			if (rb_ctt.isChecked())
				aviso.setTo(Aviso.DIRIGIDO_TRANCAS);
			else
			{
				if (rb_maa.isChecked())
					aviso.setTo(Aviso.DIRIGIDO_PROPIETARIOS);
			}
		}
		
		Contrato contrato = new Contrato();
		contrato.setAccion(Accion.AVISO);
		contrato.setContenido(ObjectUtil.createBytes(aviso));
		contrato.setId_entorno(id_entorno);
		Servicio.icore.enviar(contrato);
		
		Toast.makeText(getBaseContext(), "Aviso enviada correctamente", Toast.LENGTH_LONG).show();
		this.finish();
	}
	
	public void onRojo(View w)
	{
		Alarma alarma = new Alarma();
		alarma.setEmisor(nombre+" " + apellido);
		alarma.setPrioridad("Rojo");
		lanzarAlarma(alarma);
	}
	
	public void onAmarillo(View w)
	{
		Alarma alarma = new Alarma();
		alarma.setEmisor(nombre+" " + apellido);
		alarma.setPrioridad("Amarillo");
		lanzarAlarma(alarma);
	}
	
	public void onVerde(View w)
	{
		Alarma alarma = new Alarma();
		alarma.setEmisor(nombre+" " + apellido);
		alarma.setPrioridad("Verde");
		lanzarAlarma(alarma);
	}
	
	private void lanzarAlarma(Alarma alarma)
	{
		Contrato contrato = new Contrato();
		contrato.setAccion(Accion.ALARMA);
		contrato.setContenido(ObjectUtil.createBytes(alarma));
		contrato.setId_entorno(id_entorno);
		Log.i(TAG,"ID_ENTORNO :" + id_entorno);
		Servicio.icore.enviar(contrato);
		
		Toast.makeText(getBaseContext(), "Alarma enviada correctamente", Toast.LENGTH_LONG).show();
		this.finish();
	}
	
	public void onVerAvisos(View w)
	{
		Intent i = new Intent(this,AvisosActivity.class);
		startActivity(i);
	}
	
}
