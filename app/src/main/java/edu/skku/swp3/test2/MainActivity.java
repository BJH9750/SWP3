package edu.skku.swp3.test2;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/*
* 추후 추가할 것
* 리스트 아이템 클릭시 토스트 대신 현황을 보여주는 액티비티 띄우기
* 즐겨찾기 기능 및 리스트 아이템 삭제 기능
* 리스트 아이템 추가 팝업에서 인원 수는 Artik Cloud 에서 받아오도록 수정
* 1) 리스트에 셀렉트박스 추가하고 실제 장소들 추가하기 - File 입출력으로 구현
    2) 리스트 눌러서 들어간 정보페이지 view 짜기
* */

public class MainActivity extends Activity {

    private ListView mListView;
    private Button mButton;
    private MyAdapter mMyAdapter_SR;
    private MyAdapter mMyAdapter_RIN;
    private MyAdapter mMyAdapter_ROUT;
    private ArrayList<MyAdapter> adapters;
    private ArrayAdapter spin_adapter;
    private Spinner mSpinner;
    public static String fileName[] = {"Study Room.json", "Restaurant_In.json", "Restaurant_Out.json"};
    public static String[] category_list = {"Study Room", "Student Restaurant", "Restaurant outside campus"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 위젯과 멤버변수 참조 획득 */
        mListView = findViewById(R.id.listView);
        mButton = findViewById(R.id.button);
        mSpinner = findViewById(R.id.spinner);

        adapters.add(mMyAdapter_SR);
        adapters.add(mMyAdapter_RIN);
        adapters.add(mMyAdapter_ROUT);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopUp.class);
                startActivityForResult(intent, 1);
            }
        });

        spin_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, category_list);
        mSpinner.setAdapter(spin_adapter);
        mSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListView.setAdapter(adapters.get(position));
            }
        });
        /* 아이템 추가 및 어댑터 등록 */
        checkPermission();
        dataSetting();
        WriteFIle wf = new WriteFIle(getApplicationContext(), "test.json");
        wf.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mMyAdapter_SR.mItems);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str=  mMyAdapter_SR.getItem(position).getName() + " is clicked ";
                Toast.makeText(getApplicationContext(),str, Toast.LENGTH_SHORT).show();

                Intent intent =new Intent(MainActivity.this, DetailActivity.class);
                String code = mMyAdapter_SR.getItem(position).getCode();
                intent.putExtra("Code", code);
                startActivity(intent);
            }
        });
        mListView.setClickable(true);
    }

    /* 다른 액티비티가 종료될 때 결과를 받아옴 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(MainActivity.this, "Canceled.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == 1) {
            MyItem oneItem = (MyItem) data.getSerializableExtra("where");
            mMyAdapter_SR.addItem(oneItem);        // 어댑터에 아이템 추가
            mMyAdapter_SR.notifyDataSetChanged();  // 아이템 추가 결과 반영
            Toast.makeText(MainActivity.this, "결과 : 성공" , Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "1이 아님", Toast.LENGTH_SHORT).show();
        }
    }

    private void dataSetting(){

        mMyAdapter_SR = new MyAdapter();

        ReadFile rf = new ReadFile(getApplicationContext(), "test.json",mListView,mMyAdapter_SR);
        rf.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/test";
        if(!(new File(sdPath,"test.json").exists())){
            for (int i=0; i<10; i++) {
                mMyAdapter_SR.addItem("Place_" + i, "Code_" + i, "Serial_" + i);
            }
        }

        /* 리스트뷰에 어댑터 등록 */
        mListView.setAdapter(mMyAdapter_SR);
    }

    @Override
    public void finish() {
        WriteFIle wf = new WriteFIle(getApplicationContext(), "test.json");
        wf.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mMyAdapter_SR.mItems);
        super.finish();
    }

    public void checkPermission()
    {
        int permissionInfo = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionInfo == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,
                    "SDCard 쓰기 권한 있음",Toast.LENGTH_SHORT).show();
        }
        else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Toast.makeText(this,
                        "어플리케이션 설정에서 저장소 사용 권한을 허용해주세요",Toast.LENGTH_SHORT).show();

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);
            }
            else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String str;
        if (requestCode == 100)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                str = "SD Card 쓰기권한 승인";
            else
                str = "SD Card 쓰기권한 거부";
            Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
        }}
}