package firstone.identi_four.movil.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends Activity {

	private static final String TAG	= "Loggeo de Usuario - CI";
	
	public SharedPreferences preferences;
	public SharedPreferences.Editor editor;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        preferences = this.getSharedPreferences(SettingsActivity.PREF_KEY, MODE_PRIVATE);
		editor = preferences.edit();
		
		String ci = preferences.getString(SettingsActivity.USER_KEY, "");
		if (ci != null && ci.length() > 0)
		{
			Intent i = new Intent(this,HistorialActivity.class);
    		i.putExtra("ci", ci);
    		this.finish();
    		startActivity(i);
		}
        
    }
    
    
    public void onIngresar(View ss)
    {
    	EditText ed = (EditText)findViewById(R.id.main_ed_ci);
    	String CI = ed.getText().toString();
    	boolean correcto = verificarPropietario(CI);
    	Log.i(TAG,"Verificando Usuario - CI : " + CI);
    	
    	if (correcto)
    	{
    		editor.putString(SettingsActivity.USER_KEY, CI);
    		editor.commit();
    		
    		Intent i = new Intent(this,HistorialActivity.class);
    		i.putExtra("ci", CI);
    		this.finish();
    		startActivity(i);
    	}else
    		Toast.makeText(getBaseContext(), "CI no registrado", Toast.LENGTH_SHORT).show();
    }
    
    private boolean verificarPropietario(String CI)
    {
    	if (CI != null && CI.length() > 0)
    		return true;
    	else
    		return false;
    }
}
