package firstone.identi_four.movil.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.util.Log;

/**
 *
 * @author Milton
 */
public class ArchivoG{
    
	private static final String TAG = "FILE";
	
	public synchronized static boolean write(String filename, Object objetoSerializable)
    {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(objetoSerializable);
            oos.close();
            return true;
        } catch (IOException ex) {
        	Log.e(TAG,"Error al realizar el registro del archivo",ex);
        }
        return false;
    }
    
    public synchronized static Object read(String filename)
    {
        Object objectSerializable = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
            objectSerializable = ois.readObject();
            ois.close();
            return objectSerializable;
        } catch (ClassNotFoundException ex) {
        	Log.e(TAG,"Error al leer archivo, serializacion no se puede leer",ex);
        } catch (StreamCorruptedException e) {
        	Log.e(TAG,"Stream corrompido",e);
		} catch (FileNotFoundException e) {
//			Log.e(TAG,"Archivo no encontrado",e);
		} catch (IOException e) {
			Log.e(TAG,"Error al acceder al archivo",e);
		}
        return objectSerializable;
    }
    
}