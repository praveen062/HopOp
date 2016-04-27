package com.hopop.myapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button Login,SignUp;
    EditText Ph,Pwd;
    private RequestQueue requestQueue;
    private StringRequest request;
    private static final String LOGIN_URL = "http://192.168.0.107/hari/Hopop/user_details.php";
    String data = "";

    //public static final String KEY_MOBILE = "mobile";
    //public static final String KEY_PASSWORD = "password";
    //public static final String KEY_EMAIL = "email";
    //DatabaseAdapter dbAdapter;

    //private String mobile;
    //private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTitle(R.string.LoginHeader);
        setContentView(R.layout.activity_main);
        //dbAdapter = new DatabaseAdapter(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //============================0ur Code==============================

        Ph = (EditText) findViewById(R.id.editText_mn);
        Pwd = (EditText) findViewById(R.id.editText_Psw);
        Login = (Button) findViewById(R.id.button_Login);

        requestQueue = Volley.newRequestQueue(this);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(getApplicationContext(), "SUCCESS" + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Error" + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError

                    {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("Phone",Ph.getText().toString());
                        hashMap.put("Password", Pwd.getText().toString());

                        return hashMap;

                    }
                };
                requestQueue.add(request);
            }
        });

                /*String Phone = Ph.getText().toString();
                String Password = Pwd.getText().toString();
                //dbAdapter.open();

                if(Phone.length()==0)
                {
                    Ph.requestFocus();
                    Ph.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!Phone.matches("^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$"))
                {
                    Ph.requestFocus();
                    Ph.setError("ENTER Valid Mobile Number");
                }

                else if(Password.length()==0)
                {
                    Pwd.requestFocus();
                    Pwd.setError("FIELD CANNOT BE EMPTY");
                }
                else
                {
                    //Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                    //startActivity(searchIntent);
                }

                //dbAdapter.close();
            }

        });*/

        //-------------------------------eof login----------------------------------

        SignUp = (Button) findViewById(R.id.button_signup);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(signupIntent);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen for landscape and portrait and set portrait mode always
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
