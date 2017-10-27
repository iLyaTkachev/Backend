package ilyatkachev.github.com.backend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.Version;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ilyatkachev.github.com.backend.constants.Constants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startCalculatorActivity();
    }
    public void startCalculatorActivity() {
        startActivity(new Intent(this, CalculatorActivity.class));
    }
}
