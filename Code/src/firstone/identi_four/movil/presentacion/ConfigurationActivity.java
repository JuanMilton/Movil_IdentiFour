package firstone.identi_four.movil.presentacion;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigurationActivity extends Activity{
	
	EditText ed_ip;
	EditText ed_puerto;
	
	public SharedPreferences preferences;
	public SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuration);
		
		preferences = this.getSharedPreferences(SettingsActivity.PREF_KEY, MODE_PRIVATE);
		editor = preferences.edit();
		
		ed_ip = (EditText)findViewById(R.id.configuration_ed_ip);
		ed_puerto = (EditText)findViewById(R.id.configuration_ed_puerto);
		
		ed_ip.setText(preferences.getString(SettingsActivity.CORE_IP, "192.168.1.102"));
		ed_puerto.setText(preferences.getString(SettingsActivity.CORE_PORT,"4321"));
		
	}

	public void eventSave(View sd)
	{
		String ip = ed_ip.getText().toString();
		String puerto = ed_puerto.getText().toString();
		
		editor.putString(SettingsActivity.CORE_IP, ip);
		editor.putString(SettingsActivity.CORE_PORT, puerto);
		editor.commit();
		
		this.finish();
	}
	
}
