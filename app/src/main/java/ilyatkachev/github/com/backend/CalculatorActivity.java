package ilyatkachev.github.com.backend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CalculatorActivity extends AppCompatActivity {

    private TextView mResultTextView;
    private EditText mInputEditText;
    private View mCalculateButton;
    private BackendCalculator bCalc;

    @Override
    protected void onCreate(@Nullable Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_calculator);
        initView();
    }

    private void initView() {
        bCalc = new BackendCalculator();
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
                        final String res = bCalc.evaluate(url);

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
    }
    private void showResult(String result){
        mResultTextView.setText(result);
    }
}
