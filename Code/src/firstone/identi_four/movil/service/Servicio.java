package firstone.identi_four.movil.service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import firstone.identi_four.ccs.Accion;
import firstone.identi_four.movil.presentacion.AlarmaLlegoActivity;
import firstone.identi_four.movil.presentacion.AvisoActivity;
import firstone.identi_four.movil.presentacion.NotificacionActivity;
import firstone.identi_four.movil.presentacion.R;
import firstone.identi_four.movil.presentacion.SettingsActivity;
import firstone.identi_four.negocio.AvisoNegocio;
import firstone.identi_four.negocio.InterfazCoreNegocio;
import firstone.serializable.Alarma;
import firstone.serializable.Aviso;
import firstone.serializable.Contrato;
import firstone.serializable.Notificacion;
import firstone.serializable.Propietario;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class Servicio extends Service {

	private static final int TIEMPO_ESPERA = 10000;
	
	private static final String TAG = "Service";
	
	public static InterfazCoreNegocio icore;
	public static Handler ihandler;
	public static boolean respondio;
	
	
	public SharedPreferences preferences;
	public SharedPreferences.Editor editor;
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG,"onCreate ---------->>>>> ");
		
		preferences = this.getSharedPreferences(SettingsActivity.PREF_KEY, MODE_PRIVATE);
		editor = preferences.edit();
		
	}
	
	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand ---------->>>>> ");
        Propietario propietario = new Propietario();
		propietario.setCi(preferences.getString(SettingsActivity.PROPIETARIO_CI, ""));
		propietario.setNombres(preferences.getString(SettingsActivity.PROPIETARIO_NOMBRE, ""));
		propietario.setApellidos(preferences.getString(SettingsActivity.PROPIETARIO_APELLIDO, ""));
		propietario.setNro_licencia(preferences.getString(SettingsActivity.PROPIETARIO_LICENCIA, ""));
        
        icore = new InterfazCoreNegocio(propietario,mHandler);
        return START_STICKY;
    }
	
	@Override
    public void onDestroy() {
		Log.i(TAG,"onDestroy --------------------->>>>>> ");
		icore.desconectar();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	
	
	private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case Accion.AVISO:
            	mostrarAviso(  (Aviso)msg.obj  );
            break;  
            case Accion.ALARMA:
            	mostrarAlarma(  (Alarma)msg.obj  );
            break;
            case Accion.NOTIFICACION:
            	mostrarNotificacion(  (Notificacion)msg.obj  );
            break;
            case Accion.HISTORIALES:
            	Servicio.ihandler.obtainMessage(Accion.HISTORIALES, msg.obj).sendToTarget();
            	break;
            }
        }
    };
    
    private void mostrarAviso(Aviso aviso)
    {
    	if (new Boolean(preferences.getString(SettingsActivity.NOTI_CUANDO_ENVIO_AVISOS, "true")))
    	{
	    	NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
	                this).setAutoCancel(true)
	                .setContentTitle("IdentiFour")
	                .setSmallIcon(R.drawable.ic_launcher)
	                .setContentText(aviso.getFrom() + " : " + aviso.getMensaje());
	        
	        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	
	        Intent resultIntent = new Intent(this, AvisoActivity.class);
	        resultIntent.putExtra("de", aviso.getFrom());
	        resultIntent.putExtra("sms", aviso.getMensaje());
	        // Adds the back stack for the Intent (but not the Intent itself)
	        stackBuilder.addParentStack(AvisoActivity.class);
	
	        // Adds the Intent that starts the Activity to the top of the stack
	        stackBuilder.addNextIntent(resultIntent);
	        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
	                0, PendingIntent.FLAG_UPDATE_CURRENT);
	        mBuilder.setContentIntent(resultPendingIntent);
	
	        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	
	        mBuilder.setDefaults(Notification.DEFAULT_ALL);
	        // mId allows you to update the notification later on.
	        mNotificationManager.notify(100, mBuilder.build());
	        
	        AvisoNegocio avisoNegocio = new AvisoNegocio();
	        avisoNegocio.add(aviso);
	        avisoNegocio.save();
    	}
    }
    
    private void mostrarAlarma(Alarma alarma)
    {
    	if (new Boolean(preferences.getString(SettingsActivity.NOTI_CUANDO_ENVIO_ALARMAS, "true")))
    	{
	    	NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
	                this).setAutoCancel(true)
	                .setContentTitle("IdentiFour")
	                .setSmallIcon(R.drawable.ic_launcher)
	                .setContentText(alarma.getEmisor() + " : " + alarma.getPrioridad());
	        
	        
	        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	
	        Intent resultIntent = new Intent(this, AlarmaLlegoActivity.class);
	        resultIntent.putExtra("de", alarma.getEmisor());
	        resultIntent.putExtra("prioridad", alarma.getPrioridad());
	        // Adds the back stack for the Intent (but not the Intent itself)
	        stackBuilder.addParentStack(AlarmaLlegoActivity.class);
	
	        // Adds the Intent that starts the Activity to the top of the stack
	        stackBuilder.addNextIntent(resultIntent);
	        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
	                0, PendingIntent.FLAG_UPDATE_CURRENT);
	        mBuilder.setContentIntent(resultPendingIntent);
	
	        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	
	        mBuilder.setDefaults(Notification.DEFAULT_ALL);
	        // mId allows you to update the notification later on.
	        mNotificationManager.notify(100, mBuilder.build());
    	}
    }
    
    private void mostrarNotificacion(Notificacion notificacion)
    {
    	final String ci_guardia = notificacion.getCi_guardia();
    	if ((notificacion.getAccion().equals("INGRESO") && new Boolean(preferences.getString(SettingsActivity.NOTI_CUANDO_VEHICULO_INGRESA_KEY, "true"))) ||
    			(notificacion.getAccion().equals("SALIDA") && new Boolean(preferences.getString(SettingsActivity.NOTI_CUANDO_VEHICULO_SALE_KEY, "true"))) )
    	{
	    	respondio = false;
	    	
	    	Thread th = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(TIEMPO_ESPERA);
					} catch (InterruptedException e1) {
						Log.e(TAG,"No se puede dormir el hilo",e1);
					}
					if (! respondio)
					{
						Contrato contrato = new Contrato();
						contrato.setAccion(Accion.DENEGAR_INGRESO_SALIDA);
						contrato.setId_entorno(Integer.parseInt(preferences.getString(SettingsActivity.PROPIETARIO_LICENCIA, "")));
						String cad = ci_guardia+",true";
						try {
							contrato.setContenido(cad.getBytes("UTF-8"));
							Servicio.icore.enviar(contrato);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
						notificationManager.cancel(100);
						respondio = true;
					}
				}
			});
	    	th.start();
	    	
	    	NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setAutoCancel(true).setContentTitle("IdentiFour").setSmallIcon(R.drawable.ic_launcher).setContentText(notificacion.getCi() + " : " + notificacion.getPlaca());
	        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	        Intent resultIntent = new Intent(this, NotificacionActivity.class);
	        resultIntent.putExtra("ci", notificacion.getCi());
	        resultIntent.putExtra("placa", notificacion.getPlaca());
	        resultIntent.putExtra("marca", notificacion.getMarca());
	        resultIntent.putExtra("tipo", notificacion.getAccion());
	        resultIntent.putExtra("ci_guardia", notificacion.getCi_guardia());
	        stackBuilder.addParentStack(NotificacionActivity.class);
	        stackBuilder.addNextIntent(resultIntent);
	        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
	                0, PendingIntent.FLAG_UPDATE_CURRENT);
	        mBuilder.setContentIntent(resultPendingIntent);
	
	        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	
	        mBuilder.setDefaults(Notification.DEFAULT_ALL);
	        // mId allows you to update the notification later on.
	        mNotificationManager.notify(100, mBuilder.build());
    	}else
    	{
    		Contrato contrato = new Contrato();
			contrato.setAccion(Accion.DENEGAR_INGRESO_SALIDA);
			contrato.setId_entorno(Integer.parseInt(preferences.getString(SettingsActivity.PROPIETARIO_LICENCIA, "")));
			String cad = ci_guardia+",true";
			try {
				contrato.setContenido(cad.getBytes("UTF-8"));
				Servicio.icore.enviar(contrato);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
    	}
    }

}
