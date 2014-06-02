package firstone.identi_four.negocio;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import firstone.identi_four.movil.file.ArchivoG;
import firstone.serializable.Aviso;

public class AvisoNegocio {

	private static String FILENAME 			= Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"identifour.fos";
	private static final String TAG					= "AVISOS_NEGOCIO";
	
	private List<Aviso> avisos;
	
	public AvisoNegocio()
	{
		
//		FILENAME =  +File.separator+ FILENAME;
		
		Object o = ArchivoG.read(FILENAME);
		if (o != null)
			avisos = (List<Aviso>)o;
		else
			avisos = new ArrayList<Aviso>();
		
//		for (Aviso a : avisos)
//		{
//			Log.d(TAG,"DE :" + a.getFrom() + " MENSAJE :"+a.getMensaje());
//		}
	}
	 
	public void add(Aviso aviso)
	{
		avisos.add(aviso);
	}

	public void save()
	{
		if (! ArchivoG.write(FILENAME, avisos))
			Log.e(TAG, "No se pudo guardar los Avisos");
	}
	
	public List<Aviso> getAvisos() {
		return avisos;
	}

	public void setAvisos(List<Aviso> avisos) {
		this.avisos = avisos;
	}
	
	
	
}
