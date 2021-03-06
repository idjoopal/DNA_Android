package com.konkuk.dna.auth;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonParser;
import com.konkuk.dna.utils.HttpReqRes;
import com.konkuk.dna.utils.ServerURL;
import com.konkuk.dna.utils.helpers.BaseActivity;
import com.konkuk.dna.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import static com.konkuk.dna.utils.JsonToObj.RegisterJsonToObj;

public class SignupFormActivity extends BaseActivity {
    private EditText IDEdit, emailEdit, PWEdit, PWcheckEdit, nicknameEdit, infoEdit;
    private ImageView avatarImage;
    private Button saveBtn;
    private Context context = this;

    private String id, pw, confirm_pw, email, nickname, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        init();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IDEdit.getText().toString().equals("")
                        || PWEdit.getText().toString().equals("")
                        || PWcheckEdit.getText().toString().equals("")
                        || emailEdit.getText().toString().equals("")){
                    Toast.makeText(context, "필수 정보를 채워주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    id = IDEdit.getText().toString();
                    pw = PWEdit.getText().toString();
                    confirm_pw = PWcheckEdit.getText().toString();
                    email = emailEdit.getText().toString();

                    if(!pw.equals(confirm_pw)){
                        Toast.makeText(context, "비밀번호가 확인과 다릅니다.", Toast.LENGTH_SHORT).show();
                    }else {
                        if (nicknameEdit.getText().toString().equals("")) {
                            nickname = id;
                        } else {
                            nickname = nicknameEdit.getText().toString();
                        }

                        if (infoEdit.getText().toString().equals("")) {
                            description = "";
                        } else {
                            description = infoEdit.getText().toString();
                        }

                        SignUpAsync suac = new SignUpAsync(context, id, pw, confirm_pw, email, nickname, description, null);
                        suac.execute();

                    }
                }

            }
        });
    }

    public void init() {
        IDEdit = (EditText) findViewById(R.id.IDEdit);
        emailEdit = (EditText) findViewById(R.id.EmailEdit);
        PWEdit = (EditText) findViewById(R.id.PWEdit);
        PWcheckEdit = (EditText) findViewById(R.id.PWcheckEdit);
        nicknameEdit = (EditText) findViewById(R.id.nicknameEdit);
        infoEdit = (EditText) findViewById(R.id.infoEdit);
        avatarImage = (ImageView) findViewById(R.id.avatarImage);
        saveBtn = findViewById(R.id.userSaveBtn);


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn: // 뒤로가기 버튼
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
                alt_bld.setMessage("되돌아 가시겠습니까? 입력하시던 내용은 저장되지 않습니다.").setCancelable(
                        false).setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(SignupFormActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                dialog.cancel();
                            }
                        }).setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = alt_bld.create();
                alert.show();
                break;

            case R.id.IDcheckBtn: // 아이디 중복 확인 버튼

                break;

//            case R.id.emailcheckBtn: // 이메일 중복 확인 버튼
//                break;

            case R.id.selectImgBtn: // 이미지 선택 버튼
                break;

            case R.id.userSaveBtn: // 저장하기 버튼
                break;

        }
    }
}

class SignUpAsync extends AsyncTask<String, Void, HashMap<String, String>> {
    private Context context;
    private String id;
    private String password;
    private String confirm_password;
    private String email;
    private String nickname;
    private String description;
    private String avatar;

    public SignUpAsync(Context context, String id, String password, String confirm_password, String email, String nickname, String description, String avatar) {
        this.id = id;
        this.password = password;
        this.confirm_password = confirm_password;
        this.email = email;
        this.nickname = nickname;
        this.description = description;
        this.avatar = avatar;
        this.context = context;
    }

    @Override
    protected HashMap<String, String> doInBackground(String... strings) {
        HttpReqRes httpReqRes = new HttpReqRes();
        //Log.e("Check", id+password+email+nickname+description);
        String result = httpReqRes.requestHttpPostSignup(ServerURL.DNA_SERVER+ServerURL.PORT_USER_API+"/users/register", id, password, confirm_password, email, nickname, description, avatar);

        Log.e("resultStr", result+"");
        HashMap<String, String> hm;
        hm =  RegisterJsonToObj(result);

        return hm;
    }

    @Override
    protected void onPostExecute(HashMap<String, String> hm) {
        super.onPostExecute(hm);


        if(Boolean.parseBoolean(hm.get("issuccess"))){
            Toast.makeText(context, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }else{
            if(Integer.parseInt(hm.get("code")) == 21400){
                Toast.makeText(context, "이미 사용중인 ID입니다.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "이미 등록된 이메일입니다.", Toast.LENGTH_SHORT).show();
            }
        }


    }
}