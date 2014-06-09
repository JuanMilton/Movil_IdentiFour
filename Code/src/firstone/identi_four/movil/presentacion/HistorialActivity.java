package firstone.identi_four.movil.presentacion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import firstone.identi_four.ccs.Accion;
import firstone.identi_four.movil.model.Historial;
import firstone.identi_four.movil.service.ServiceTestActivity;
import firstone.identi_four.movil.service.Servicio;
import firstone.serializable.Alarma;
import firstone.serializable.Aviso;
import firstone.serializable.Contrato;
import firstone.serializable.HistorialIngresoSalida;
import firstone.serializable.Notificacion;

public class HistorialActivity extends Activity {

	private static final String TAG	= "Historial de Activacion";
	private static final int TAMANIO_TITULO=20;
	
	String CI = "";
	String nombre = "";
	String apellido = "";
	int id_entorno;
	
	public SharedPreferences preferences;
	public SharedPreferences.Editor editor;
	
	ProgressDialog progress;
	
	// begin_region
		TableLayout tl;
	// end_region
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        
        Servicio.ihandler = mHandler;
        iniciarServicio();
        
        Bundle b = getIntent().getExtras();
        CI = b.getString("ci");
        nombre = b.getString("nombre");
        apellido = b.getString("apellido");
        id_entorno = Integer.parseInt(b.getString("id_entorno"));
        Log.i(TAG,"ID_ENTORNO :" +id_entorno);
        preferences = this.getSharedPreferences(SettingsActivity.PREF_KEY, MODE_PRIVATE);
		editor = preferences.edit();
        
        
        InitializeComponent();
    }
    
    private void iniciarServicio()
    {
    	boolean servicio_run = false;
    	ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if ("firstone.identi_four.movil.service.Servicio".equals(service.service.getClassName())) {
	            servicio_run = true;
	        }
	    }
	    
	    if (!servicio_run)
	    {
	    	startService(new Intent(this,Servicio.class));
	    }else
	    	onActualizar(null);
	    
//	    	stopService(new Intent(this,Servicio.class));
	    
    }
    
    private void InitializeComponent()
    {
    	tl = (TableLayout)findViewById(R.id.historial_tabla);
    }
    
    int contador=0;
    
    private void cargarTabla(List<HistorialIngresoSalida> historiales)
    {
    	tl.removeAllViews();
    	TextView tx = new TextView(this), tx2 = new TextView(this), tx3 = new TextView(this), tx4 = new TextView(this), tx5 = new TextView(this);
    	setHeader(tl);
    	for (int i = 0; i < historiales.size(); i++) {
			TableRow tr = new TableRow(this);
			tr.setId(i+1);
			
			tx = new TextView(this);
			tx.setText(historiales.get(i).getPlaca());
			tx.setClickable(false);
			tx.setPadding(0, 0, 12,0);
			tx.setTextSize(17);
			
			tx2 = new TextView(this);
			if ("INGRESO".equalsIgnoreCase(historiales.get(i).getTipo()))
				tx2.setText("Ingreso");
			else
				tx2.setText("Salida");
			tx2.setClickable(false);
			tx2.setPadding(0, 0, 12,0);
			tx2.setTextSize(17);
			
			tx3 = new TextView(this);
			tx3.setText(historiales.get(i).getTranca());
			tx3.setClickable(false);
			tx3.setPadding(0, 0, 12,0);
			tx3.setTextSize(17);
			
			tx4 = new TextView(this);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String cad = sdf.format(new Date(historiales.get(i).getFecha_hora()));
			tx4.setText(cad);
			tx4.setClickable(false);
			tx4.setPadding(0, 0, 12,0);
			tx4.setTextSize(17);
			
			
			tr.addView(tx);
			tr.addView(tx2);
			tr.addView(tx3);
			tr.addView(tx4);

			tl.addView(tr);
		}
    	tl.requestLayout();
    }
    
    private void lanzarConfiguracion()
    {
    	Intent i = new Intent(this,ConfigurationActivity.class);
    	startActivity(i);
    }
    
    private void setHeader(TableLayout tl)
    {
    	TableRow tr = new TableRow(this);
    	tr.setId(0);
    	
    	TextView tx = new TextView(this);
    	tx.setText("PLACA");
    	tx.setClickable(false);
    	tx.setTextSize(TAMANIO_TITULO);
    	
    	TextView tx2 = new TextView(this);
    	tx2.setText("ACCION");
    	tx2.setClickable(false);
    	tx2.setTextSize(TAMANIO_TITULO);
    	
    	TextView tx3 = new TextView(this);
    	tx3.setText("TRANCA");
    	tx3.setClickable(false);
    	tx3.setTextSize(TAMANIO_TITULO);
    	
    	TextView tx4 = new TextView(this);
    	tx4.setText("FECHA HORA");
    	tx4.setClickable(false);
    	tx4.setTextSize(TAMANIO_TITULO);
    	
    	
    	tr.setBackgroundColor(Color.rgb(132, 195, 255));
    	tr.addView(tx);
    	tr.addView(tx2);
    	tr.addView(tx3);
    	tr.addView(tx4);
    	
    	tr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				contador++;
				if (contador > 5)
				{
					lanzarConfiguracion();
					contador = 0;
				}
			}
		});
    	
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
			i = new Intent(this,SettingsActivity.class);
			break;
		case R.id.menu_salir:
			editor.putString(SettingsActivity.PROPIETARIO_CI, "");
			editor.putString(SettingsActivity.PROPIETARIO_NOMBRE, "");
			editor.putString(SettingsActivity.PROPIETARIO_APELLIDO, "");
			editor.putString(SettingsActivity.PROPIETARIO_LICENCIA, "");
			editor.commit();
			
			stopService(new Intent(this,Servicio.class));
			
			i = new Intent(this,MainActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			this.finish();
			startActivity(i);
			break;
		}
    	i.putExtra("ci", CI);
    	i.putExtra("nombre", nombre);
    	i.putExtra("apellido", apellido);
    	Log.d(TAG,"id_entornooo : " + id_entorno);
    	i.putExtra("id_entorno", id_entorno);
    	
		startActivity(i);
		return super.onMenuItemSelected(featureId, item);
	}

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case Accion.HISTORIALES:
            	mostrarHistoriales(  (List<HistorialIngresoSalida>)msg.obj  );
            break;
            case 150:
            	onActualizar(null);
            }
        }
    };
    
    private void mostrarHistoriales(List<HistorialIngresoSalida> lista) {
    	Log.i(TAG, "Mostrar Historiales");
    	cargarTabla(lista);
//		for (HistorialIngresoSalida h : lista)
//		{
//			Log.i(TAG, "TIPO : " + h.getTipo() + "    PLACA : " + h.getPlaca());
//		}
	}
    
    public void onActualizar(View ss)
    {
    	progress = ProgressDialog.show(this, "dialog title",
      		  "dialog message", true);

		new Thread(new Runnable() {
		  @Override
		  public void run()
		  {
				Contrato contrato = new Contrato();
				contrato.setAccion(Accion.HISTORIALES);
				contrato.setContenido(null);
				Servicio.icore.enviar(contrato);
		    	
		    	Log.i(TAG, "["+CI+"] Actualizando ...");
			  
		    runOnUiThread(new Runnable() {
		      @Override
		      public void run()
		      {
		    	  
		        progress.dismiss();
		        
		      }
		    });
		  }
		}).start();

    	
    	
    }
    
}
