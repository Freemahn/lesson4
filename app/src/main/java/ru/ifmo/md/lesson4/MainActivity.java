package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {
    private Button b;
    private EditText et;
    private TextView tw;
    private CalculationEngine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        engine = CalculationEngineFactory.defaultEngine();
        b = (Button) findViewById(R.id.calcBtn);
        et = (EditText) findViewById(R.id.inputView);
        tw = (TextView) findViewById(R.id.resultView);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String r = et.getText().toString();
                double result;
                try {
                    result = engine.calculate(r);
                    tw.setText(String.valueOf(result));
                } catch (CalculationException e) {
                    Log.e("CALCEXC", e.toString());
                    tw.setText("Error");
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
