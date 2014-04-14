package firstone.identi_four.movil.presentacion;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class SettingsActivity extends Activity{

	public static final String PREF_KEY = "IdentiFour_Movil"; //Llave para guardar las preferencias
	
	public static final String USER_KEY = ""; //Llave para guardar el CI del usuario
	
	private static final String NOTI_CUANDO_VEHICULO_SALE_KEY = "ncvs";
	private static final String NOTI_CUANDO_VEHICULO_INGRESA_KEY = "ncvi";
	private static final String CONF_VISITA_KEY = "cv";
	
	public SharedPreferences preferences;
	public SharedPreferences.Editor editor;
	
	//begin_region COMPONENTES DE LA VISTA
	CheckBox ch_ncvs;
	CheckBox ch_ncvi;
	
	RadioButton rb_ps; //Preguntar Siempre
	RadioButton rb_dis; //Dejar Ingresar Siempre
	RadioButton rb_ndi; //Nunca dejar Ingresar
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
		ch_ncvs = (CheckBox)findViewById(R.id.settings_ch_not_veh_salga);
		ch_ncvi = (CheckBox)findViewById(R.id.settings_ch_not_veh_ing);
		
		rb_ps = (RadioButton)findViewById(R.id.settings_rb_preguntar_siempre);
		rb_dis = (RadioButton)findViewById(R.id.settings_rb_dejar_entrar_siempre);
		rb_ndi = (RadioButton)findViewById(R.id.settings_rb_nunca_dejar_entrar);
		
		Boolean ncvs = new Boolean(preferences.getString(NOTI_CUANDO_VEHICULO_SALE_KEY, "true"));
		Boolean ncvi = new Boolean(preferences.getString(NOTI_CUANDO_VEHICULO_INGRESA_KEY, "true"));
		
		ch_ncvs.setChecked(ncvs);
		ch_ncvi.setChecked(ncvi);
		
		int rb = Integer.parseInt(preferences.getString(CONF_VISITA_KEY, "0"));
		switch (rb) {
		case 0:
			rb_ps.setChecked(true);
			break;
		case 1:
			rb_dis.setChecked(true);
			break;
		case 2:
			rb_ndi.setChecked(true);
			break;
		}
	}
	
	public void onGuardar(View w)
	{
		boolean ncvs = ch_ncvs.isChecked();
		boolean ncvi = ch_ncvi.isChecked();
		
		int rb = -1;
		if (rb_ps.isChecked())
			rb = 0;
		else{ 
			if (rb_dis.isChecked())
				rb = 1;
			else
				rb = 2;
		}
		
		editor.putString(NOTI_CUANDO_VEHICULO_SALE_KEY, ncvs+"");
		editor.commit();
		editor.putString(NOTI_CUANDO_VEHICULO_INGRESA_KEY, ncvi+"");
		editor.commit();
		editor.putString(CONF_VISITA_KEY, rb+"");
		editor.commit();
		
		this.finish();
	}
	
}
