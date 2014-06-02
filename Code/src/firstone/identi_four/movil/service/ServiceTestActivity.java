package firstone.identi_four.movil.service;

import firstone.identi_four.movil.presentacion.R;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ServiceTestActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_test);
	}

	public void eventStopService(View w)
	{
		stopService(new Intent(this,Servicio.class));
	}
	
	public void eventStartService(View w)
	{
		startService(new Intent(this,Servicio.class));
	}
	
	public void eventValidateService(View w)
	{
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if ("firstone.identi_four.movil.service.Servicio".equals(service.service.getClassName())) {
	            Toast.makeText(getBaseContext(), "RUNNING", Toast.LENGTH_SHORT).show();
	            return;
	        }
	    }
	    Toast.makeText(getBaseContext(), "NOT RUNNING", Toast.LENGTH_SHORT).show();
	}
}
