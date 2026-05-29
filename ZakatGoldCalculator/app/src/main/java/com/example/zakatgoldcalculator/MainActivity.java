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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etValue;
    RadioButton rbKeep, rbWear;
    Button btnCalculate;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));

        etWeight = findViewById(R.id.editTextNumber2);
        etValue = findViewById(R.id.etValue);

        rbKeep = findViewById(R.id.radioButton);
        rbWear = findViewById(R.id.radioButton2);

        btnCalculate = findViewById(R.id.button2);
        tvResult = findViewById(R.id.textView2);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etWeight.getText().toString().trim().isEmpty()) {
                    etWeight.setError("Please enter gold weight");
                    etWeight.requestFocus();
                    return;
                }

                if (etValue.getText().toString().trim().isEmpty()) {
                    etValue.setError("Please enter gold value");
                    etValue.requestFocus();
                    return;
                }

                if (!rbKeep.isChecked() && !rbWear.isChecked()) {
                    Toast.makeText(MainActivity.this,
                            "Please select Keep or Wear",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                double weight =
                        Double.parseDouble(etWeight.getText().toString());

                double value =
                        Double.parseDouble(etValue.getText().toString());

                double X;

                if (rbKeep.isChecked()) {
                    X = 85;
                } else {
                    X = 200;
                }

                double totalGoldValue = weight * value;

                double weightMinusX = weight - X;

                if (weightMinusX < 0) {
                    weightMinusX = 0;
                }

                double zakatPayable = weightMinusX * value;

                double totalZakat = zakatPayable * 0.025;

                String result =
                        "Total Gold Value : RM "
                                + String.format("%.2f", totalGoldValue)

                                + "\n\nGold Weight Minus Nisab : "
                                + String.format("%.2f", weightMinusX)
                                + " g"

                                + "\n\nZakat Payable : RM "
                                + String.format("%.2f", zakatPayable)

                                + "\n\nTotal Zakat : RM "
                                + String.format("%.2f", totalZakat);

                tvResult.setText(result);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

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
                            "Share Application"
                    )
            );

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}