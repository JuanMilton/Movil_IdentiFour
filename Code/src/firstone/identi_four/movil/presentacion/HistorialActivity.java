package firstone.identi_four.movil.presentacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import firstone.identi_four.movil.model.Historial;
import firstone.identi_four.movil.service.ServiceTestActivity;

public class HistorialActivity extends Activity {

	private static final String TAG	= "Historial de Activacion";
	private static final int TAMANIO_TITULO=20;
	
	List<Historial> historiales;
	String CI = "";
	
	public SharedPreferences preferences;
	public SharedPreferences.Editor editor;
	
	// begin_region
		TableLayout tl;
	// end_region
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        
        Bundle b = getIntent().getExtras();
        CI = b.getString("ci");
        
        preferences = this.getSharedPreferences(SettingsActivity.PREF_KEY, MODE_PRIVATE);
		editor = preferences.edit();
        
        cargarListaHistoriales();
        InitializeComponent();
    }
    
    private void InitializeComponent()
    {
    	tl = (TableLayout)findViewById(R.id.historial_tabla);
    }
    
    private void cargarTabla()
    {
    	tl.removeAllViews();
    	TextView tx = new TextView(this), tx2 = new TextView(this), tx3 = new TextView(this), tx4 = new TextView(this), tx5 = new TextView(this);
    	setHeader(tl);
    	for (int i = 0; i < historiales.size(); i++) {
			TableRow tr = new TableRow(this);
			tr.setId(i+1);
			
			tx.setText(historiales.get(i).getFechaHora().toString());
			tx.setClickable(false);
			tx.setPadding(0, 0, 12,0);
			tx.setTextSize(17);
			
			tx2.setText(historiales.get(i).getPlaca());
			tx2.setClickable(false);
			tx2.setPadding(0, 0, 12,0);
			tx2.setTextSize(17);
			
			tx3.setText(historiales.get(i).getAccion());
			tx3.setClickable(false);
			tx3.setPadding(0, 0, 12,0);
			tx3.setTextSize(17);
			
			tx4.setText(historiales.get(i).getTranca());
			tx4.setClickable(false);
			tx4.setPadding(0, 0, 12,0);
			tx4.setTextSize(17);
			
			tx5.setText(historiales.get(i).getArea());
			tx5.setClickable(false);
			tx5.setPadding(0, 0, 12,0);
			tx5.setTextSize(17);
			
			tr.addView(tx);
			tr.addView(tx2);
			tr.addView(tx3);
			tr.addView(tx4);
			tr.addView(tx5);
			
			tl.addView(tr);
		}
    	tl.requestLayout();
    }
    
    private void setHeader(TableLayout tl)
    {
    	TableRow tr = new TableRow(this);
    	tr.setId(0);
    	
    	TextView tx = new TextView(this);
    	tx.setText("Fecha Hora");
    	tx.setClickable(false);
    	tx.setTextSize(TAMANIO_TITULO);
    	
    	TextView tx2 = new TextView(this);
    	tx2.setText("Placa");
    	tx2.setClickable(false);
    	tx2.setTextSize(TAMANIO_TITULO);
    	
    	TextView tx3 = new TextView(this);
    	tx3.setText("Accion");
    	tx3.setClickable(false);
    	tx3.setTextSize(TAMANIO_TITULO);
    	
    	TextView tx4 = new TextView(this);
    	tx4.setText("Tranca");
    	tx4.setClickable(false);
    	tx4.setTextSize(TAMANIO_TITULO);
    	
    	TextView tx5 = new TextView(this);
    	tx5.setText("Area");
    	tx5.setClickable(false);
    	tx5.setTextSize(TAMANIO_TITULO);
    	
    	tr.setBackgroundColor(Color.RED);
    	tr.addView(tx);
    	tr.addView(tx2);
    	tr.addView(tx3);
    	tr.addView(tx4);
    	tr.addView(tx5);
    	
    	tl.addView(tr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    
    @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	Intent i = null;
    	switch (item.getItemId()) {
		case R.id.menu_alarma:
			i = new Intent(this,AlarmaActivity.class);
			break;
		case R.id.menu_settings:
//			i = new Intent(this,SettingsActivity.class);
			i = new Intent(this,ServiceTestActivity.class);
			break;
		case R.id.menu_salir:
			editor.putString(SettingsActivity.USER_KEY, "");
			editor.commit();
			
			i = new Intent(this,MainActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			this.finish();
			startActivity(i);
			break;
		}
    	i.putExtra("ci", CI);
		startActivity(i);
		return super.onMenuItemSelected(featureId, item);
	}


	private void cargarListaHistoriales()
    {
    	historiales = new ArrayList<Historial>();
    	Historial h1 = new Historial();
    	Historial h2 = new Historial();
    	Historial h3 = new Historial();
    	
    	h1.setAccion("Ingreso");
    	h1.setArea("Campus");
    	h1.setFechaHora(new Date());
    	h1.setPlaca("123ABC");
    	h1.setTranca("Noroeste");
    	
    	h2.setAccion("Salida");
    	h2.setArea("Campus");
    	h2.setFechaHora(new Date());
    	h2.setPlaca("123ABC");
    	h2.setTranca("Sur");
    	
    	h3.setAccion("Ingreso");
    	h3.setArea("Modulos");
    	h3.setFechaHora(new Date());
    	h3.setPlaca("123ABC");
    	h3.setTranca("Norte");
    	
    	historiales.add(h1);
    	historiales.add(h2);
    	historiales.add(h3);
    }
    
    public void onActualizar(View ss)
    {
    	Log.i(TAG, "["+CI+"] Actualizando ...");
    	
    	Historial h3 = new Historial();
    	
    	h3.setAccion("Ingreso");
    	h3.setArea("Modulos");
    	h3.setFechaHora(new Date());
    	h3.setPlaca("4321BCA");
    	h3.setTranca("Sur");
    	
    	historiales.add(h3);
    	cargarTabla();
    }
    
}
