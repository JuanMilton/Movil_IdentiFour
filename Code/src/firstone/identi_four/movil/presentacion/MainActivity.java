package firstone.identi_four.movil.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends Activity {

	private static final String TAG	= "Loggeo de Usuario - CI";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
    }
    
    
    public void onIngresar(View ss)
    {
    	EditText ed = (EditText)findViewById(R.id.main_ed_ci);
    	String CI = ed.getText().toString();
    	boolean correcto = true;
    	Log.i(TAG,"Verificando Usuario - CI : " + CI);
    	
    	if (correcto)
    	{
    		Intent i = new Intent(this,HistorialActivity.class);
    		i.putExtra("ci", CI);
    		startActivity(i);
    	}else
    		Toast.makeText(getBaseContext(), "CI no registrado", Toast.LENGTH_SHORT).show();
    }
}
