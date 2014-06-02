package firstone.identi_four.negocio;

import java.io.IOException;
import java.util.Random;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.firstonesoft.client.Client;
import com.firstonesoft.client.event.EventClient;
import com.firstonesoft.client.util.ObjectUtil;

import firstone.identi_four.ccs.Accion;
import firstone.serializable.Contrato;
import firstone.serializable.Propietario;

public class InterfazCoreLogInNegocio implements EventClient {
	
	private static final String TAG 		= "INTERFAZ_LOGIN_CORE";
	
	Client cliente;
	public String 	IP 		= "192.168.1.102";
	public int		PORT	= 4321;
	
	
	Handler handler;
	
	
	public InterfazCoreLogInNegocio(Handler handler, final Context context)
	{
		this.handler = handler;
		
		cliente = new Client(IP, PORT);
        cliente.setEventClient(this);
		
		final Propietario logeador = new Propietario();
        logeador.setApellidos("FirstOneSoft");
        Random r = new Random();
        String ci = r.nextInt(999999)+"";
        logeador.setCi(ci+"-1");
        logeador.setFoto(null);
        logeador.setNombres("IdentiFour");
        logeador.setNro_licencia(ci+"-C");
        
    	Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					cliente.connectOpened(logeador.getCi(), logeador);
					
				} catch (IOException ex) {
		            Log.e(TAG,"No se pudo conectar",ex);
		            Toast.makeText(context, "No se puede conectar al servidor", Toast.LENGTH_LONG).show();
		            
		        }
			}
		});
    	t.start();
	}
	
	public void solicitarDatos(String ci)
	{
		final Contrato contrato = new Contrato();
		contrato.setAccion(Accion.PROPIETARIO);
		try {
			contrato.setContenido(ci.getBytes("UTF-8"));
			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try{
						cliente.sendPackage(ObjectUtil.createBytes(contrato));
					}catch(IOException e)
					{
						e.printStackTrace();
					}
				}
			});
			t.start();
		} catch (IOException e) {
			Log.e(TAG,"Error al acceder al Core",e);
		}
	}
	
	private void responseAutenticacionPropietario(byte[] contenido)
	{
		if (contenido != null)
		{
			Object o = ObjectUtil.createObject(contenido);
			Propietario p = (Propietario)o;
			Log.i(TAG,"Contenido :: CI :" + p.getCi()+ " Nombre :" + p.getNombres());
			handler.obtainMessage(Accion.PROPIETARIO, p).sendToTarget();//			
		}else
			handler.obtainMessage(Accion.PROPIETARIO).sendToTarget();
	}
	
	@Override
	public void onDisconnectCore(IOException arg0) {
		// TODO Auto-generated method stub
		
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
            case Accion.PROPIETARIO                 : responseAutenticacionPropietario(contrato.getContenido()); break;
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
			Log.e(TAG,"Error al desconectarse con el Core",e);
		}
	}
}
