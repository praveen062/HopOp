package com.hopop.myapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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

public class SignUpActivity extends AppCompatActivity {
        //implements NavigationView.OnNavigationItemSelectedListener {

    EditText editText_fn, editText_ln, editText_email, editText_mn, editText_psw;
    Button button_SignUp, button_Lin, button_fb;
    ImageButton Button_eye;
    TextView hop, op;
    private RequestQueue requestQueue;
    private StringRequest request;
    private static final String URL = "http://192.168.0.107/hari/Hopop/register_user.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_sign_up);

        editText_fn = (EditText) findViewById(R.id.editText_fn);
        editText_ln = (EditText) findViewById(R.id.editText_ln);
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_mn = (EditText) findViewById(R.id.editText_mn);
        editText_psw = (EditText) findViewById(R.id.editText_Psw);
        button_Lin = (Button) findViewById(R.id.button_Lin);
        button_fb = (Button) findViewById(R.id.button_fb);
        button_SignUp = (Button) findViewById(R.id.button_SignUp);
        Button_eye = (ImageButton) findViewById(R.id.Button_eye);
        requestQueue = Volley.newRequestQueue(this);
        //hop = (TextView) findViewById(R.id.textView_hop2);


        Button_eye.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        editText_psw.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                    case MotionEvent.ACTION_UP:
                        editText_psw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                }
                return true;
            }
        });

        button_fb.setOnClickListener(new View.OnClickListener() {

            public void onClick(View V) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/login/"));
                startActivity(browserIntent);

            }
        });
        button_Lin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View V) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/uas/login"));
                startActivity(browserIntent);

            }
        });

        button_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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

                        hashMap.put("editText_fn", editText_fn.getText().toString());
                        hashMap.put("editText_ln", editText_ln.getText().toString());
                        hashMap.put("editText_email", editText_email.getText().toString());
                        hashMap.put("editText_mn", editText_mn.getText().toString());
                        hashMap.put("editText_psw", editText_psw.getText().toString());

                        return hashMap;

                    }
                };
                requestQueue.add(request);
            }
        });
    }
               /* String Fname = editText_fn.getText().toString();
                String Lname = editText_ln.getText().toString();
                String Email = editText_email.getText().toString();
                String Mobile = editText_mn.getText().toString();
                String Password = editText_psw.getText().toString();

                if (Fname.length() == 0) {
                    editText_fn.requestFocus();
                    editText_fn.setError("FIELD CANNOT BE EMPTY");
                } else if (!Fname.matches("[a-zA-Z ]+")) {
                    editText_fn.requestFocus();
                    editText_fn.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                } else if (Lname.length() == 0) {
                    editText_ln.requestFocus();
                    editText_ln.setError("FIELD CANNOT BE EMPTY");
                } else if (!Lname.matches("[a-zA-Z ]+")) {
                    editText_ln.requestFocus();
                    editText_ln.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                } else if (Email.length() == 0) {
                    editText_email.requestFocus();
                    editText_email.setError("FIELD CANNOT BE EMPTY");
                } else if (!Email.matches("^[A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                    editText_email.requestFocus();
                    editText_email.setError("ENTER Valid Email Id");
                } else if (Mobile.length() == 0) {
                    editText_mn.requestFocus();
                    editText_mn.setError("FIELD CANNOT BE EMPTY");
                } else if (!Mobile.matches("^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$")) {
                    editText_mn.requestFocus();
                    editText_mn.setError("ENTER Valid Mobile Number");
                } else if (Password.length() == 0) {
                    editText_psw.requestFocus();
                    editText_psw.setError("FIELD CANNOT BE EMPTY");
                } else {
                    //Toast.makeText(SignUpActivity.this,"Validation Successful",Toast.LENGTH_LONG).show();
                    // Intent intent_1 = new Intent(SignUpActivity.this, AlertDialogActvity.class);
                    // startActivity(intent_1);


                }


            //}
                        return null;
                        //return getPostParams();
             }
                };
                requestQueue.add(request);
            }
        });
    }*/


        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {

        } else if (id == R.id.booking) {

        } else if (id == R.id.wallet) {

        } else if (id == R.id.route) {

        } else if (id == R.id.notification) {

        } else if (id == R.id.feedback) {

        } else if (id == R.id.about) {

        } else if (id == R.id.logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/

        @Override
        public void onConfigurationChanged (Configuration newConfig){
            super.onConfigurationChanged(newConfig);

            // Checks the orientation of the screen for landscape and portrait and set portrait mode always
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }
