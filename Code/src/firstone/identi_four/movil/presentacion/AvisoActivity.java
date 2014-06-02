package firstone.identi_four.movil.presentacion;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AvisoActivity extends Activity {

	String de;
	String sms;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_llego_aviso);
		
		Bundle b = getIntent().getExtras();
        de = b.getString("de");
        sms = b.getString("sms");
        
        TextView tx1 = (TextView)findViewById(R.id.text_de);
        TextView tx2 = (TextView)findViewById(R.id.text_mensaje);
        
        tx1.setText(de);
        tx2.setText(sms);
	}
	
	public void onCerrar(View w)
	{
		this.finish();
	}
	
}
