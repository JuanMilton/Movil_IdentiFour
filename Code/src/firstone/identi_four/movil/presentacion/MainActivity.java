package firstone.identi_four.movil.presentacion;

import firstone.identi_four.ccs.Accion;
import firstone.identi_four.negocio.InterfazCoreLogInNegocio;
import firstone.serializable.Propietario;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends Activity {

	private static final String TAG	= "Loggeo de Usuario - CI";
	
	InterfazCoreLogInNegocio ilogin;
	
	public SharedPreferences preferences;
	public SharedPreferences.Editor editor;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        preferences = this.getSharedPreferences(SettingsActivity.PREF_KEY, MODE_PRIVATE);
		editor = preferences.edit();
		
		String ci = preferences.getString(SettingsActivity.PROPIETARIO_CI, "");
		String nombre = preferences.getString(SettingsActivity.PROPIETARIO_NOMBRE, "");
		String apellido = preferences.getString(SettingsActivity.PROPIETARIO_APELLIDO, "");
		String id_entorno = preferences.getString(SettingsActivity.PROPIETARIO_LICENCIA, "");
		Log.i(TAG,"CI :" + ci + " NOMBRE:" + nombre + " APELLIDO:" + apellido+ " LICENCIA:" + id_entorno);
		if (ci != null && ci.length() > 0)
		{
			Intent i = new Intent(this,HistorialActivity.class);
    		i.putExtra("ci", ci);
    		i.putExtra("nombre", nombre);
    		i.putExtra("apellido", apellido);
    		i.putExtra("id_entorno", id_entorno);
    		this.finish();
    		startActivity(i);
		}else
		{
			ilogin = new InterfazCoreLogInNegocio(this.mHandler, this, preferences.getString(SettingsActivity.CORE_IP, "192.168.1.102"),
					Integer.parseInt(preferences.getString(SettingsActivity.CORE_PORT, "4321")));
			
		}
        
    }
    
    
    public void onIngresar(View ss)
    {
    	EditText ed = (EditText)findViewById(R.id.main_ed_ci);
    	String CI = ed.getText().toString();
    	Log.i(TAG,"Verificando Usuario - CI : " + CI);
    	ilogin.solicitarDatos(CI);
    }
    
    

    @Override
	protected void onDestroy() {
    	if (ilogin != null)
    		ilogin.desconectar();
		super.onDestroy();
	}



	private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case Accion.PROPIETARIO:{
            	if (msg.obj != null)
            	{
	                Propietario propietario = (Propietario)msg.obj;
	                autenticacionPropietario(propietario);
            	}else
            		Toast.makeText(getBaseContext(), "CI no registrado", Toast.LENGTH_SHORT).show();
            }break;            
            }
        }
    };
    
	public void autenticacionPropietario(Propietario propietario) {
		Log.d("LOGIN","Se ha autenticado");
		editor.putString(SettingsActivity.PROPIETARIO_CI, propietario.getCi());
		editor.putString(SettingsActivity.PROPIETARIO_NOMBRE, propietario.getNombres());
		editor.putString(SettingsActivity.PROPIETARIO_APELLIDO, propietario.getApellidos());
		editor.putString(SettingsActivity.PROPIETARIO_LICENCIA, propietario.getNro_licencia());
		editor.commit();
		
		Intent i = new Intent(this,HistorialActivity.class);
		i.putExtra("ci", propietario.getCi());
		i.putExtra("nombre", propietario.getNombres());
		i.putExtra("apellido", propietario.getApellidos());
		i.putExtra("id_entorno", propietario.getNro_licencia());
		this.finish();
		startActivity(i);
	}
}
