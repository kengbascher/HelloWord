package kengbascher.helloworld;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvHello;
    EditText edtHello;
    Button btnCopy;

    EditText edt1;
    EditText edt2;
    TextView tvResult;
    Button btnCalculate;

    RadioGroup rgOperator;
    CustomViewGroup viewGroup1;
    CustomViewGroup viewGroup2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getBoolean(R.bool.portrait_only)){
            // Fix Screen Orientation
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_main);

        initInstances();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x; //Screen Width
        int height = size.y; //Screen Height

        Toast.makeText(MainActivity.this,"Width = "+width+" Height = "+height,Toast.LENGTH_LONG).show();
    }

    private void initInstances() {
        tvHello = (TextView) findViewById(R.id.tvHello);
        tvHello.setMovementMethod(LinkMovementMethod.getInstance());
        tvHello.setText(Html.fromHtml("<b>He<u>llo</u></b> <i>Word</i> <font color=\"#ff0000\"> La la la </font> <a href=\"http://nuuneoi.com\">http://nuuneoi.com</a>"));

        edtHello = (EditText) findViewById(R.id.edtHello);
        edtHello.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){

                    tvHello.setText(edtHello.getText());
                    //Handle
                    return true;
                }
                return false;
            }
        });

        btnCopy = (Button) findViewById(R.id.btnCopy);
        btnCopy.setOnClickListener(this);

        edt1 = (EditText) findViewById(R.id.edt1);
        edt2 = (EditText) findViewById(R.id.edt2);
        tvResult = (TextView) findViewById(R.id.tvResult);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        rgOperator = (RadioGroup) findViewById(R.id.rgOperator);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num1 = 0;
                int num2 = 0;
                int num3 = 0;
                try {
                    num1 = Integer.parseInt(edt1.getText().toString());
                }catch (NumberFormatException e){

                }
                try{
                    num2 = Integer.parseInt(edt2.getText().toString());
                }catch (NumberFormatException e){

                }

                switch (rgOperator.getCheckedRadioButtonId()){
                    case R.id.rbPlus:
                        num3 = num1 + num2;
                        break;
                    case R.id.rbMinus:
                        num3 = num1 - num2;
                        break;
                    case R.id.rbMultiply:
                        num3 = num1 * num2;
                        break;
                    case R.id.rbDivide:
                        num3 = num1 / num2;
                        break;
                }
                //num3 = num1 + num2;
                tvResult.setText(num3 + "");

                Log.d("Calculation", "Result = " + num3);
                Toast.makeText(MainActivity.this, "Result = " + num3, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                intent.putExtra("result", num3);

                Coordinate c1 = new Coordinate();
                c1.x = 5;
                c1.y = 10;
                c1.z = 20;

                Bundle bundle = new Bundle();
                bundle.putInt("x", c1.x);
                bundle.putInt("y", c1.y);
                bundle.putInt("z", c1.z);
                intent.putExtra("cBundle",bundle);

                // Serializable ไม่แนะนำให้ใช้เพราะการทำงานบน Android ช้ามาก
                CoordinateSerializable c2 = new CoordinateSerializable();
                c2.x = 5;
                c2.y = 10;
                c2.z = 20;
                intent.putExtra("cSerializable", c2);

                // Parcelable ส่งค่า Object ข้าม Activity ได้และเร็วกว่า
                CoordinateParcelable c3 = new CoordinateParcelable();
                c3.x = 5;
                c3.y = 10;
                c3.z = 20;
                intent.putExtra("cParcelable", c3);

                //startActivity(intent);\
                startActivityForResult(intent,12345);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

            }
        });

        viewGroup1 = (CustomViewGroup) findViewById(R.id.viewGroup1);
        viewGroup2 = (CustomViewGroup) findViewById(R.id.viewGroup2);

        viewGroup1.setButtonText("Hello");
        viewGroup2.setButtonText("World");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check if it is a result from SecondActivity
        if (requestCode == 12345){
            if(resultCode == RESULT_OK){
                String testText = data.getStringExtra("result");
                Toast.makeText(MainActivity.this, testText, Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnCopy){
            tvHello.setText(edtHello.getText());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_settings){
            //Do what you want
            Toast.makeText(MainActivity.this,"Setting",Toast.LENGTH_SHORT).show();
            //Handle

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // ปิดเครื่อง เปิดใหม่ หายไป , Work with outState
        //Save thing(s) here
        //outState.putString("text", tvResult.getText().toString());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Restore thing(s) here
        //tvResult.setText(savedInstanceState.getString("text"));
    }
}
