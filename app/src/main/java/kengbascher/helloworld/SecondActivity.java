package kengbascher.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends Activity {

    int sum = 0;
    TextView tvSecoundResult;
    Button btnSecondOk;
    EditText edtSecond;
    int x1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        sum = intent.getIntExtra("result",0);

        Bundle bundle = intent.getBundleExtra("cBundle");
        int x = bundle.getInt("x");
        int y = bundle.getInt("y");
        int z = bundle.getInt("z");

        CoordinateSerializable c2 = (CoordinateSerializable)
                intent.getSerializableExtra("cSerializable");

        CoordinateParcelable c3 = intent.getParcelableExtra("cParcelable");
        x1 = c3.x;
        int y2 = c3.y;
        int z2 = c3.z;


        initInstances();
    }

    private void initInstances() {

        tvSecoundResult = (TextView) findViewById(R.id.tvSecondResult);
        tvSecoundResult.setText("Result = "+sum + "" + "Result X = " + x1 + "");
        edtSecond = (EditText) findViewById(R.id.edtSecond);
        btnSecondOk = (Button) findViewById(R.id.btnSecondOk);
        btnSecondOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",edtSecond.getText().toString());
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        //
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}
