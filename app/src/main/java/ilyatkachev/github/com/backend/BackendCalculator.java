package ilyatkachev.github.com.backend;

import com.example.Result;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;

import ilyatkachev.github.com.backend.http.HttpClient;

public class BackendCalculator {

    public String evaluate(final String value) {
        //final String url = new CalcApi(BuildConfig.BASE_CALC_URL).evaluate(value);
        final String url = "http://localhost:8080/calc";
        final MyResponseListener listener = new MyResponseListener();
        new HttpClient().request(url, listener);
        if (listener.getThrowable() != null) {

            throw new UnsupportedOperationException(listener.getThrowable());
        }
        return String.valueOf(listener.getResult().getResult());
    }

    private static class MyResponseListener implements HttpClient.ResponseListener {

        private Result result;
        private Throwable mThrowable;

        @Override
        public void onResponse(final InputStream pInputStream) throws Exception {
            InputStreamReader inputStreamReader = null;
            try {
                inputStreamReader = new InputStreamReader(pInputStream);
                result = new GsonBuilder()
                        .setLenient()
                        .create().fromJson(inputStreamReader, Result.class);
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

        public Result getResult() {
            return result;
        }

    }
}
