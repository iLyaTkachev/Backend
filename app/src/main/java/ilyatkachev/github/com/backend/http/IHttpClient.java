package ilyatkachev.github.com.backend.http;

public interface IHttpClient {
    void request(String url, HttpClient.ResponseListener listener);
}
