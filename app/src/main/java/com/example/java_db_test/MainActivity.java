package com.example.java_db_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    myDBHelper myHelper;
    EditText edtLine, edtStation, edtLocaiton, edtLineResult, edtStationResult, edtLocationResult;
    Button btnInit, btnInsert, btnSelect, btnlineselect;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("휠체어리프트 위치 테이블");

        //xml버튼들 객체연결
        edtLine = findViewById(R.id.edtLine);
        edtStation = findViewById(R.id.edtStation);
        edtLocaiton = findViewById(R.id.edtLocation);
        edtLineResult = findViewById(R.id.edtLineResult);
        edtStationResult = findViewById(R.id.edtStationResult);
        edtLocationResult = findViewById(R.id.edtLocationResult);
        btnInit = findViewById(R.id.btnInit);
        btnInsert = findViewById(R.id.btnInsert);
        btnSelect = findViewById(R.id.btnSelect);
        btnlineselect = findViewById(R.id.btnlineselect);

        Intent intentlineSelect = new Intent(this, lineSelectActivity.class);

        btnlineselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentlineSelect);
            }
        });

        //DB클래스 객체 생성
        myHelper = new myDBHelper(this);

        //각 버튼에 리스너를 달아줍시당
        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //WheelChairDB를 쓰기용 DB로 열고, WheelChairTBL이 있으면 삭제한 후 새로 생성한다.
                sqlDB = myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqlDB, 1, 2);
                sqlDB.close();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //마찬가지로 쓰기용으로 열고, 뭐리문을 입력해준다. 따옴표에 주의합시다.
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO WheelChairTBL VALUES (" +
                        "'" + edtLine.getText().toString() + "', '"
                        + edtStation.getText().toString() + "', '"
                        + edtLocaiton.getText().toString() + "');");
                sqlDB.close();
                Toast.makeText(getApplicationContext(), "입력됨", Toast.LENGTH_SHORT).show();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //커서를 선언하고 모든 테이블을 조회한 후 커서에 대입한다.
                //이 과정이 줠라 이해가 안되는데, 이 커서 객체가 포인터 역할을 한다고 이해하면 된다.
                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM WheelChairTBL;", null);

                //노선명과 역명, 위치를 나타내 줄 문자열 선언
                String strLines = "노선명" + "\n\n" + "-------" + "\n\n";
                String strStations = "역명" + "\n\n" + "-------" + "\n\n";
                String strLocations = "위치" + "\n\n" + "-------" + "\n\n";

                //커서가 움직이면서 현재 커서의 열 번호 데이터값을 반환해서 문자열 변수에 계속 누적한다.
                //0은 0번째열(노선명), 1은 1번째열(역명), 2는 2번째열(위치)가 된다.
                while (cursor.moveToNext()) {
                    strLines += cursor.getString(0) + "\n\n";
                    strStations += cursor.getString(1) + "\n\n";
                    strLocations += cursor.getString(2) + "\n\n";
                }

                //출력해주기
                edtLineResult.setText(strLines);
                edtStationResult.setText(strStations);
                edtLocationResult.setText(strLocations);

                cursor.close();
                sqlDB.close();
            }
        });
    }

    //DB를 생성하고 초기화하는 DB생성자 정의
    public static class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "WheelChairDB", null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            //WheelChairTBL이라는 테이블명으로 lineName, stationName, Location 필드를 생성해주자
            db.execSQL("CREATE TABLE WheelChairTBL (lineName CHAR(10), stationName CHAR(20), Location CHAR(50));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //이곳에선 테이블이 존재하면 없애고 새로 만들어준다.
            db.execSQL("DROP TABLE IF EXISTS WheelChairTBL");
            onCreate(db);
        }
    }
}