package benlry.com.uiteur.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import org.w3c.dom.Document;

import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import benlry.com.uiteur.MainActivity;
import benlry.com.uiteur.task.HTTPRequestTask;

public class MediaPlayerService extends Service {


    private Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void play() {
        final String url = "http://uiteur.struct-it.fr/login.php?user="+ "uiteur" +"&pwd="+ "test";

        URL serviceURL = null;

        try {
            serviceURL = new URL(url);
        } catch (MalformedURLException ex) {
            Log.e("test", ex.getMessage());
        }

        new HTTPRequestTask(this).execute(serviceURL);
    }

    private int end = 0;

    public void playlist(Document document) {

        if(this.end == 0) {
            final String url = "http://uiteur.struct-it.fr/playlist.php";

            URL serviceURL = null;

            try {
                serviceURL = new URL(url);
            } catch (MalformedURLException ex) {
                Log.e("test", ex.getMessage());
            }

            new HTTPRequestTask(this).execute(serviceURL);
            this.end = 1;
        } else {
            Intent playlistIntent = new Intent();
            playlistIntent.putExtra("playlist",XMLToString(document));
            playlistIntent.setAction("playlist");
            LocalBroadcastManager.getInstance(context).sendBroadcast(playlistIntent);
        }
    }

    public static String XMLToString(Document doc) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }
}
