package edu.skku.swp3.test2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class PopUp extends Activity implements View.OnClickListener{

    private Button mConfirm, mCancel;
    private EditText eId, eName, eContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup);

        setPopup();
    }

    private void setPopup(){
        mConfirm = findViewById(R.id.btnConfirm);
        mCancel = findViewById(R.id.btnCancel);

        mConfirm.setOnClickListener(this);
        mCancel.setOnClickListener(this);

        eId = findViewById(R.id.first);
        eName = findViewById(R.id.second);
        eContents = findViewById(R.id.third);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnConfirm:
                Intent intent = new Intent(PopUp.this, MainActivity.class);
                MyItem oneItem = new MyItem();
                getItem(oneItem);
                intent.putExtra("where", oneItem);
                setResult(RESULT_OK, intent);
                this.finish();
                break;

            case R.id.btnCancel:
                setResult(RESULT_CANCELED);
                this.finish();
                break;
        }
    }

    public void getItem(MyItem oneItem){
        oneItem.setSerial(this.eId.getText().toString());
        oneItem.setName(this.eName.getText().toString());
        oneItem.setCode(this.eContents.getText().toString());
    }
}
