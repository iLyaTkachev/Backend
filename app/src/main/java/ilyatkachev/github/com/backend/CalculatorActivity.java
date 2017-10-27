package ilyatkachev.github.com.backend;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Version;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ilyatkachev.github.com.backend.constants.Constants;

public class CalculatorActivity extends AppCompatActivity {

    private TextView mResultTextView;
    private EditText mInputEditText;
    private View mCalculateButton;
    private BackendCalculator mBackendCalculator;
    private BackendVersion mBackendVersion;
    private boolean isVersionChecked;

    @Override
    protected void onCreate(@Nullable Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_calculator);
        isVersionChecked = false;
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isVersionChecked) {
            checkVersion();
            isVersionChecked = true;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("checkState", isVersionChecked);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isVersionChecked = savedInstanceState.getBoolean("checkState");
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
                            url = Constants.CALCULATOR_URL + URLEncoder.encode(mInputEditText.getText().toString(), "UTF-8");
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

    }

    //returns result of calculations
    private void showResult(String result) {
        mResultTextView.setText(result);
    }

    private void checkVersion() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                final String url = Constants.VERSION_URL;
                final Version ver = mBackendVersion.getLastVersion(url);
                final int currentVersion = BuildConfig.VERSION_CODE;
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (ver.getVersionCode() > currentVersion) {
                            if (ver.isHardUpdate()) {
                                showUpdateDialog(String.valueOf(ver.getVersionCode()), false);
                            } else {
                                showUpdateDialog(String.valueOf(ver.getVersionCode()), true);
                            }
                        }
                    }
                });
            }
        }).start();
    }

    private void showUpdateDialog(String version, boolean isCancelable) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Update notification");
        alertDialog.setMessage("New version of application is available: v" + version + ". Please update your current version!");
        alertDialog.setCancelable(isCancelable);
        alertDialog.setPositiveButton("Update Now", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface pDialogInterface, int pI) {
                Toast.makeText(CalculatorActivity.this, "Update Now", Toast.LENGTH_SHORT).show();
            }
        });
        if (isCancelable) {
            alertDialog.setNegativeButton("Later", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface pDialogInterface, int pI) {
                    Toast.makeText(CalculatorActivity.this, "Update Later", Toast.LENGTH_SHORT).show();
                }
            });
        }
        alertDialog.show();
    }
}
