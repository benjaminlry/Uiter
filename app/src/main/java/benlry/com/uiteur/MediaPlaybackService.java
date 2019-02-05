//package benlry.com.uiteur;
//
//import android.os.Bundle;
//import android.support.v4.media.MediaBrowserCompat;
//import android.support.v4.media.session.MediaSessionCompat;
//import android.support.v4.media.session.PlaybackStateCompat;
//
//import java.util.List;
//
//import androidx.media.MediaBrowserServiceCompat;
//
//public class MediaPlaybackService extends MediaBrowserServiceCompat {
//    private static final String MY_MEDIA_ROOT_ID = "media_root_id";
//    private static final String MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id";
//
//    private MediaSessionCompat mMediaSession;
//    private PlaybackStateCompat.Builder mStateBuilder;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        // Create a MediaSessionCompat
//        mMediaSession = new MediaSessionCompat(this, "");
//
//        // Enable callbacks from MediaButtons and TransportControls
//        mMediaSession.setFlags(
//                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
//                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
//
//        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
//        mStateBuilder = new PlaybackStateCompat.Builder()
//                .setActions(
//                        PlaybackStateCompat.ACTION_PLAY |
//                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
//        mMediaSession.setPlaybackState(mStateBuilder.build());
//
//        // MySessionCallback() has methods that handle callbacks from a media controller
//        mMediaSession.setCallback(new MySessionCallback());
//
//        // Set the session's token so that client activities can communicate with it.
//        setSessionToken(mMediaSession.getSessionToken());
//    }
//
//    @Override
//    public BrowserRoot onGetRoot(String clientPackageName, int clientUid, Bundle rootHints) {
//        return null;
//    }
//
//    @Override
//    public void onLoadChildren(String parentId, Result<List<MediaBrowserCompat.MediaItem>> result) {
//
//    }
//}
