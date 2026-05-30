package com.example.zakatgoldcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etValue;
    RadioButton rbWear, rbKeep;
    Button btnCalculate;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        androidx.appcompat.widget.Toolbar toolbar =
                findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Connect Components
        etWeight = findViewById(R.id.etWeight);
        etValue = findViewById(R.id.etValue);

        rbWear = findViewById(R.id.rbWear);
        rbKeep = findViewById(R.id.rbKeep);

        btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Validation
                if (etWeight.getText().toString().trim().isEmpty()) {
                    etWeight.setError("Enter gold weight");
                    return;
                }

                if (etValue.getText().toString().trim().isEmpty()) {
                    etValue.setError("Enter gold value");
                    return;
                }

                if (!rbWear.isChecked() && !rbKeep.isChecked()) {
                    Toast.makeText(
                            MainActivity.this,
                            "Please select Keep or Wear",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                // Input
                double weight =
                        Double.parseDouble(etWeight.getText().toString());

                double value =
                        Double.parseDouble(etValue.getText().toString());

                // Nisab
                double nisab;

                if (rbKeep.isChecked()) {
                    nisab = 85;
                } else {
                    nisab = 200;
                }

                // Calculation
                double totalGoldValue = weight * value;

                double excessWeight = weight - nisab;

                if (excessWeight < 0) {
                    excessWeight = 0;
                }

                double zakatPayable = excessWeight * value;

                double totalZakat = zakatPayable * 0.025;

                // Output
                String result =
                        "Total Gold Value : RM "
                                + String.format("%.2f", totalGoldValue)

                                + "\n\nGold Weight Minus Nisab : "
                                + String.format("%.2f", excessWeight)
                                + " g"

                                + "\n\nZakat Payable : RM "
                                + String.format("%.2f", zakatPayable)

                                + "\n\nTotal Zakat : RM "
                                + String.format("%.2f", totalZakat);

                tvResult.setText(result);
            }
        });
    }

    // MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // MENU ACTION
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_about) {

            Intent intent =
                    new Intent(MainActivity.this,
                            AboutActivity.class);

            startActivity(intent);

            return true;
        }

        if (item.getItemId() == R.id.menu_share) {

            Intent shareIntent =
                    new Intent(Intent.ACTION_SEND);

            shareIntent.setType("text/plain");

            String githubUrl =
                    "https://github.com/yourusername/zakatgoldcalculator";

            shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Check out my Gold Zakat Calculator App:\n"
                            + githubUrl
            );

            startActivity(
                    Intent.createChooser(
                            shareIntent,
                            "Share App"
                    )
            );

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}