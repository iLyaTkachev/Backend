package ilyatkachev.github.com.backend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Version;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CalculatorActivity extends AppCompatActivity {

    private TextView mResultTextView;
    private EditText mInputEditText;
    private View mCalculateButton;
    private BackendCalculator mBackendCalculator;
    private BackendVersion mBackendVersion;

    private View mVersionButton;

    @Override
    protected void onCreate(@Nullable Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_calculator);
        initView();
    }

    private void initView() {
        mBackendCalculator = new BackendCalculator();
        mBackendVersion = new BackendVersion();
        mInputEditText = (EditText) findViewById(R.id.input_edit_text);
        mCalculateButton = findViewById(R.id.calculate_button);
        mResultTextView = (TextView) findViewById(R.id.result_text_view);
        mCalculateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                /**/
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        final String url;
                        try {
                            url = "https://androidbackendepam-183311.appspot.com/calc?input=" + URLEncoder.encode(mInputEditText.getText().toString(), "UTF-8");
                        } catch (final UnsupportedEncodingException pE) {
                            throw new IllegalStateException(pE);
                        }
                        final String res = mBackendCalculator.evaluate(url);

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                showResult(res);
                            }
                        });
                    }
                }).start();
            }
        });

        checkVersion();

    }

    private void showResult(String result) {
        mResultTextView.setText(result);
    }

    private void checkVersion() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                final String url = "https://androidbackendepam-183311.appspot.com/ver";
                final Version ver = mBackendVersion.getLastVersion(url);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), String.valueOf(ver.getVersionCode()), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }
}
