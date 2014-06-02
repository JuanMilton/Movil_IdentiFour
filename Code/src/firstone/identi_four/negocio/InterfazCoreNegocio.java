package firstone.identi_four.negocio;

import java.io.IOException;
import java.util.List;

import android.os.Handler;
import android.util.Log;

import com.firstonesoft.client.Client;
import com.firstonesoft.client.event.EventClient;
import com.firstonesoft.client.util.ObjectUtil;

import firstone.identi_four.ccs.Accion;
import firstone.serializable.Alarma;
import firstone.serializable.Aviso;
import firstone.serializable.Contrato;
import firstone.serializable.HistorialIngresoSalida;
import firstone.serializable.Notificacion;
import firstone.serializable.Propietario;

public class InterfazCoreNegocio implements EventClient {

	private static final String TAG = "INTERFAZ_CORE";
	
	Client cliente;
	public String 	IP 		= "192.168.1.102";
	public int		PORT	= 4321;
	
	Propietario propietario;
	Handler handler;
	
	public InterfazCoreNegocio(final Propietario propietario, Handler handler)
	{
		this.handler = handler;
		this.propietario = propietario;
		
		cliente = new Client(IP, PORT);
        cliente.setEventClient(this);
        
        Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					cliente.connectOpened(propietario.getCi(), propietario);
				} catch (IOException ex) {
		            Log.e(TAG,"No se pudo conectar",ex);
		        }
			}
		});
    	t.start();
	}
	
	private void llegoAviso(byte[] contenido)
	{
		Aviso aviso = (Aviso)ObjectUtil.createObject(contenido);
		if (! aviso.getFrom().equalsIgnoreCase(propietario.getNombres()+" "+propietario.getApellidos()))
			handler.obtainMessage(Accion.AVISO, aviso).sendToTarget();
	}
	
	private void llegoAlarma(byte[] contenido)
	{
		Alarma alarma = (Alarma)ObjectUtil.createObject(contenido);
		if (! alarma.getEmisor().equalsIgnoreCase(propietario.getNombres()+" "+ propietario.getApellidos()))
			handler.obtainMessage(Accion.ALARMA, alarma).sendToTarget();
	}
	
	private void llegoNotificacion(byte[] contenido)
	{
		Notificacion notifi = (Notificacion)ObjectUtil.createObject(contenido);
		handler.obtainMessage(Accion.NOTIFICACION, notifi).sendToTarget();
	}
	
	private void llegoHistoriales(byte[] contenido)
	{
		
		List<HistorialIngresoSalida> lista = (List<HistorialIngresoSalida>)ObjectUtil.createObject(contenido);
//		Log.i(TAG,"Llego Historialessssssssssss " + lista.size());
		handler.obtainMessage(Accion.HISTORIALES, lista).sendToTarget();
	}
	
	public void enviar(final Contrato contrato)
	{
//		Log.i(TAG,"Contrato:"+contrato);
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					cliente.sendPackage(ObjectUtil.createBytes(contrato));
				} catch (IOException e) {
					Log.e(TAG,"Error al enviar consulta al core",e);
				}
			}
		});
		t.start();
	}
	
	
	@Override
	public void onDisconnectCore(IOException arg0) {
//		Log.i
	}

	@Override
	public void onNewPackage(long arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNewPackageComplet(byte[] data) {
		Object o = ObjectUtil.createObject(data);
        Contrato contrato = (Contrato)o;
        switch (contrato.getAccion())
        {
            case Accion.AVISO				: llegoAviso(contrato.getContenido()); break;
            case Accion.ALARMA				: llegoAlarma(contrato.getContenido()); break;
            case Accion.NOTIFICACION		: llegoNotificacion(contrato.getContenido()); break;
            case Accion.HISTORIALES			: llegoHistoriales(contrato.getContenido()); break;
        }
	}

	@Override
	public void onNewTrama(int arg0) {
		// TODO Auto-generated method stub
		
	}

	public void desconectar() {
		try {
			cliente.disconect();
		} catch (IOException e) {
			Log.e(TAG,"Error al desconectarse",e);
		}
	}
	
}
