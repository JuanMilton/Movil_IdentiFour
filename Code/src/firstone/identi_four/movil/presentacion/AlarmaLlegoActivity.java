package firstone.identi_four.movil.presentacion;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AlarmaLlegoActivity extends Activity {

	String de;
	String prioridad;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_llego_alarma);
		
		Bundle b = getIntent().getExtras();
        de = b.getString("de");
        prioridad = b.getString("prioridad");
        
        TextView tx1 = (TextView)findViewById(R.id.text_de_alarma);
        TextView tx2 = (TextView)findViewById(R.id.text_mensaje_alarma);
        TextView tx3 = (TextView)findViewById(R.id.text_precausiones_alarma);
        
        tx1.setText(de);
         //SOLO LLEGA A LOS MOVILES EN LOS NIVELES DE PRIORIDAD ROJO Y AMARILLO
        if (prioridad.equalsIgnoreCase("Rojo"))
        {
        	tx3.setText("Refugiese en un lugar seguro, y espere la confirmacion de seguridad para poder salir.");
        	tx2.setText("Alarma Roja");
        	tx2.setBackgroundColor(Color.RED);
        }else if (prioridad.equalsIgnoreCase("Amarillo"))
        {
        	tx3.setText("Sea cuidadoso con las personas, y espere a que se le comunique que todo esta normal.");
        	tx2.setText("Alarma Amarilla");
        	tx2.setTextColor(Color.BLACK);
        	tx2.setBackgroundColor(Color.YELLOW);
        }
	}
	
	public void onCerrar(View w)
	{
		this.finish();
	}
	
}
