package firstone.identi_four.movil.presentacion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import firstone.identi_four.negocio.AvisoNegocio;
import firstone.serializable.Aviso;

public class AvisosActivity extends Activity {

	private static final int TAMANIO_TITULO=20;
	
	AvisoNegocio avisoNegocio;
	
	// begin_region
		TableLayout tl;
	// end_region
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos);

    	avisoNegocio = new AvisoNegocio();
		
		InitializeComponent();
		cargarTabla();
        
    }
    
    private void InitializeComponent()
    {
    	tl = (TableLayout)findViewById(R.id.avisos_tabla);
    }
    
    private void cargarTabla()
    {
    	List<Aviso> lista = avisoNegocio.getAvisos();
    	
    	tl.removeAllViews();
    	TextView tx = new TextView(this), tx2 = new TextView(this), tx3 = new TextView(this);
    	setHeader(tl);
    	for (int i = lista.size() -1; i >= 0 ; i--) {
			TableRow tr = new TableRow(this);
			tr.setId(i+1);
			
			tx = new TextView(this);
			tx.setText(lista.get(i).getFrom());
			tx.setClickable(false);
			tx.setPadding(0, 0, 12,0);
			tx.setTextSize(17);
			
			tx2 = new TextView(this);
			tx2.setText(lista.get(i).getMensaje());
			tx2.setClickable(false);
			tx2.setPadding(0, 0, 12,0);
			tx2.setTextSize(17);
			
			tx3 = new TextView(this);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String cad = sdf.format(new Date(lista.get(i).getFecha_hora()));
			tx3.setText(cad);
			tx3.setClickable(false);
			tx3.setPadding(0, 0, 12,0);
			tx3.setTextSize(17);
			
			tr.addView(tx);
			tr.addView(tx2);
			tr.addView(tx3);
			
//			tr.setOnClickListener(l)
			
			tl.addView(tr);
		}
    	tl.requestLayout();
    }
    
    private void setHeader(TableLayout tl)
    {
    	TableRow tr = new TableRow(this);
    	tr.setId(0);
    	
    	TextView tx = new TextView(this);
    	tx.setText("De");
    	tx.setClickable(false);
    	tx.setTextSize(TAMANIO_TITULO);
    	
    	TextView tx2 = new TextView(this);
    	tx2.setText("Mensaje");
    	tx2.setClickable(false);
    	tx2.setTextSize(TAMANIO_TITULO);
    	
    	TextView tx3 = new TextView(this);
    	tx3.setText("Fecha Hora");
    	tx3.setClickable(false);
    	tx3.setTextSize(TAMANIO_TITULO);
    	
    	tr.setBackgroundColor(Color.rgb(132, 195, 255));
    	tr.addView(tx);
    	tr.addView(tx2);
    	tr.addView(tx3);
    	
    	tl.addView(tr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
