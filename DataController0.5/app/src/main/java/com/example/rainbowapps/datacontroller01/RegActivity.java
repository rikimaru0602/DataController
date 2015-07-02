package com.example.rainbowapps.datacontroller01;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rainbowapps.datacontroller01.utility.Account;


public class RegActivity extends ActionBarActivity implements View.OnClickListener{

    //とりあえずのユーザ名、OSアカウント名
//    private String ACC_NAME_test = "rikimaru0602t";
//    private String ACC_NAME_OS_test = "rikimaru0602@gmail.com";
    private String ACC_NAME_test = "abct";
    private String ACC_NAME_OS_test = "def";

    EditText edit_accNA;
    EditText edit_accOS;
    EditText edit_SSID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        edit_accNA = (EditText) findViewById(R.id.edit_account);
        edit_accOS = (EditText) findViewById(R.id.edit_accOS);
        edit_SSID = (EditText) findViewById(R.id.edit_SSID);
        View confirmButton = findViewById(R.id.button_confirm_reg);
        confirmButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reg, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if (id == R.id.button_confirm_reg) {
            checkCreateStats(checkAccNameCorrect(),checkAccOSCorrect());
        }
    }


    public boolean checkAccNameCorrect(){
        boolean correct= false;
        // エディットテキスト(Acc_NA)のテキストを全選択します
        edit_accNA.selectAll();
        // エディットテキスト(Acc_NA)のテキストを取得します
        String text_accNA = edit_accNA.getText().toString();
        if(!text_accNA.equals(ACC_NAME_test) && text_accNA.length() > 6 )correct = true;
        return correct;
    }

    public boolean checkAccOSCorrect(){
        boolean correct= false;
        // エディットテキスト(Acc_OS)のテキストを全選択します
        edit_accOS.selectAll();
        // エディットテキスト(Acc_OS)のテキストを取得します
        String text_accOS = edit_accOS.getText().toString();
        if(text_accOS.equals(ACC_NAME_OS_test))correct=true;
        return correct;
    }

    public boolean checkCreateStats(boolean accNA_Correct, boolean accOS_Correct){
        boolean correct= false;
        if(accNA_Correct && accOS_Correct) {
            correct = true;

            edit_accNA.selectAll();
            String text_accNA = edit_accNA.getText().toString();
            edit_accOS.selectAll();
            String text_accOS = edit_accOS.getText().toString();
            edit_SSID.selectAll();
            String text_SSID = edit_SSID.getText().toString();

            Account.saveAcc(this, text_accNA, text_accOS, text_SSID);
            Account.saveAccPresence(this);

            Toast.makeText(this, "アカウントの作成に成功しました", Toast.LENGTH_LONG).show();
            System.out.println("成功");

            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);

        }else{
            if(!checkAccNameCorrect()) {
                Toast.makeText(this, "このユーザー名はすでに使用されています", Toast.LENGTH_LONG).show();
            }else if(!checkAccOSCorrect()) {
                Toast.makeText(this, "アカウントが存在しません", Toast.LENGTH_LONG).show();
            }

            System.out.println("失敗");
        }
        return correct;
    }
}