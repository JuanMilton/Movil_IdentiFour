package firstone.identi_four.movil.presentacion;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class SettingsActivity extends Activity{

	public static final String PREF_KEY = "Rompecabezas_movil"; //Llave para guardar las preferencias
	
	public static final String PROPIETARIO_CI = "ci";
	public static final String PROPIETARIO_NOMBRE = "nombre";
	public static final String PROPIETARIO_APELLIDO = "apellido";
	public static final String PROPIETARIO_LICENCIA = "licencia";
	
	public static final String NOTI_CUANDO_VEHICULO_SALE_KEY = "ncvs";
	public static final String NOTI_CUANDO_VEHICULO_INGRESA_KEY = "ncvi";
	public static final String NOTI_CUANDO_ENVIO_AVISOS = "ncea";
	public static final String NOTI_CUANDO_ENVIO_ALARMAS = "nceal";
	
	public SharedPreferences preferences;
	public SharedPreferences.Editor editor;
	
	//begin_region COMPONENTES DE LA VISTA
	CheckBox ch_vehiculo_ingresa;
	CheckBox ch_vehiculo_sale;
	CheckBox ch_enviar_avisos;
	CheckBox ch_enviar_alarmas;
	
	//end_region
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		preferences = this.getSharedPreferences(PREF_KEY, MODE_PRIVATE);
		editor = preferences.edit();
		
		InitializeComponent();
	}
	
	private void InitializeComponent()
	{
		ch_vehiculo_ingresa = (CheckBox)findViewById(R.id.ch_vehiculo_ingresa);
		ch_vehiculo_sale = (CheckBox)findViewById(R.id.ch_vehiculo_sale);
		ch_enviar_avisos = (CheckBox)findViewById(R.id.ch_enviar_avisos);
		ch_enviar_alarmas = (CheckBox)findViewById(R.id.ch_enviar_alarmas);
		
		Boolean nc_sale = new Boolean(preferences.getString(NOTI_CUANDO_VEHICULO_SALE_KEY, "true"));
		Boolean nc_ingresa = new Boolean(preferences.getString(NOTI_CUANDO_VEHICULO_INGRESA_KEY, "true"));
		Boolean nc_avisos = new Boolean(preferences.getString(NOTI_CUANDO_ENVIO_AVISOS, "true"));
		Boolean nc_alarmas = new Boolean(preferences.getString(NOTI_CUANDO_ENVIO_ALARMAS, "true"));
		
		ch_vehiculo_ingresa.setChecked(nc_ingresa);
		ch_vehiculo_sale.setChecked(nc_sale);
		ch_enviar_avisos.setChecked(nc_avisos);
		ch_enviar_alarmas.setChecked(nc_alarmas);
		
	}
	
	public void onGuardar(View w)
	{
		Boolean nc_sale = ch_vehiculo_sale.isChecked();
		Boolean nc_ingresa = ch_vehiculo_ingresa.isChecked();
		Boolean nc_avisos = ch_enviar_avisos.isChecked();
		Boolean nc_alarmas = ch_enviar_alarmas.isChecked();
		
		editor.putString(NOTI_CUANDO_VEHICULO_SALE_KEY, nc_sale+"");
		editor.commit();
		editor.putString(NOTI_CUANDO_VEHICULO_INGRESA_KEY, nc_ingresa+"");
		editor.commit();
		editor.putString(NOTI_CUANDO_ENVIO_AVISOS, nc_avisos+"");
		editor.commit();
		editor.putString(NOTI_CUANDO_ENVIO_ALARMAS, nc_alarmas+"");
		editor.commit();
		
		this.finish();
	}
	
}
