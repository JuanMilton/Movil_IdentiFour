package firstone.identi_four.movil.presentacion;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import firstone.identi_four.movil.model.Alarma;

public class AlarmaActivity extends Activity{
	
	private static final String TAG = "AlarmaActivity";
	
	private String CI = null;
	
	//begin_region COMPONENTES DE LA VISTA
	RadioButton rb_ctea;
	RadioButton rb_ctt;
	RadioButton rb_maa;
	RadioButton rb_op;
	
	EditText et;
	//end_region
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarma);
		
		Bundle b = getIntent().getExtras();
        CI = b.getString("ci");
		
		InitializeComponent();
	}
	
	private void InitializeComponent()
	{
		rb_ctea = (RadioButton)findViewById(R.id.alarma_rb_cerrar_trancas_en_esta_area);
		rb_ctt = (RadioButton)findViewById(R.id.alarma_rb_cerrar_todas_trancas);
		rb_maa = (RadioButton)findViewById(R.id.alarma_rb_me_atacaron);
		rb_op = (RadioButton)findViewById(R.id.alarma_rb_otro);
		
		rb_ctea.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rb_ctt.setChecked(false);
				rb_maa.setChecked(false);
				rb_op.setChecked(false);
				et.setCursorVisible(false);
			}
		});
		
		rb_ctt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rb_ctea.setChecked(false);
				rb_maa.setChecked(false);
				rb_op.setChecked(false);
				et.setCursorVisible(false);
			}
		});
		
		rb_maa.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rb_ctt.setChecked(false);
				rb_ctea.setChecked(false);
				rb_op.setChecked(false);
				et.setCursorVisible(false);
			}
		});
		
		rb_op.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rb_ctt.setChecked(false);
				rb_maa.setChecked(false);
				rb_ctea.setChecked(false);
				et.setCursorVisible(true);
			}
		});
		
		et = (EditText)findViewById(R.id.alarma_ed_otro);
		et.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				rb_ctt.setChecked(false);
				rb_maa.setChecked(false);
				rb_ctea.setChecked(false);
				rb_op.setChecked(true);
			}
		});
	}
	
	public void onAlertar(View w)
	{
		Alarma alarma = null;
		if (rb_ctea.isChecked())
			alarma = new Alarma(1,"Cerrar trancas de esta area",CI);
		else
		{
			if (rb_ctt.isChecked())
				alarma = new Alarma(1, "Cerrar todas las trancas",CI);
			else
			{
				if (rb_maa.isChecked())
					alarma = new Alarma(2, "Me atacaron",CI);
				else	
				{
					if (et.getText() != null && et.getText().toString() != null)
						alarma = new Alarma(3, et.getText().toString(),CI);
					else
						alarma = new Alarma(3, "Sin mensaje",CI);
				}
			}
		}
		
		lanzarAlarma(alarma);
	}
	
	private void lanzarAlarma(Alarma alarma)
	{
		Log.i(TAG,"Lanzando alarma : DATOS\tPrioridad: " + alarma.getPrioridad()+"\tMensaje: " + alarma.getMensaje());
		
		Toast.makeText(getBaseContext(), "Alarma enviada correctamente", Toast.LENGTH_LONG).show();
	}
	
}
