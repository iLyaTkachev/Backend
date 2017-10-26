package ilyatkachev.github.com.backend;

import com.example.Version;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;

import ilyatkachev.github.com.backend.http.HttpClient;

public class BackendVersion {

    public Version getLastVersion(final String value) {
        final MyResponseListener listener = new MyResponseListener();
        new HttpClient().request(value, listener);
        if (listener.getThrowable() != null) {
            return new Version();
        } else {
            return listener.getVersion();
        }
    }

    private static class MyResponseListener implements HttpClient.ResponseListener {

        private Version mVersion;
        private Throwable mThrowable;

        @Override
        public void onResponse(final InputStream pInputStream) throws Exception {
            InputStreamReader inputStreamReader = null;
            try {
                inputStreamReader = new InputStreamReader(pInputStream);
                mVersion = new GsonBuilder()
                        .setLenient()
                        .create().fromJson(inputStreamReader, Version.class);
            } finally {
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (final Exception ignored) {
                    }
                }
            }
        }

        public Throwable getThrowable() {
            return mThrowable;
        }

        @Override
        public void onError(final Throwable pThrowable) {
            mThrowable = pThrowable;
        }

        public Version getVersion() {
            return mVersion;
        }

    }

}
