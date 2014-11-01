package com.elvizlai.httptester;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;


public class MainActivity extends ActionBarActivity {
    private Button confirmBtn;
    private TextView resultView;
    private EditText inputUrl, inputKey, inputValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        inputUrl = (EditText) findViewById(R.id.inputUrl);
        inputKey = (EditText) findViewById(R.id.inputKey);
        inputValue = (EditText) findViewById(R.id.inputValue);
        resultView = (TextView) findViewById(R.id.resultView);

        confirmBtn = (Button) findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    conncet2Server();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void conncet2Server() throws JSONException, UnsupportedEncodingException {
        final StringBuilder builder = new StringBuilder();
        String url = "http://" + inputUrl.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                builder.append("GET_Succeed->ResultCode:" + i + "\n"
                        + "--------------------------------------------------------" + "\n"
                        + Arrays.toString(headers) + "\n"
                        + "--------------------------------------------------------" + "\n"
                        + new String(bytes) + "\n\n");
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                builder.append("GET_Failed->ResultCode:" + i + "\n"
                        + "--------------------------------------------------------" + "\n"
                        + Arrays.toString(headers) + "\n"
                        + "--------------------------------------------------------" + "\n"
                        + new String(bytes) + "\n\n");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                resultView.setText(builder.toString());
            }
        });

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(inputKey.getText().toString().trim(), inputValue.getText().toString().trim());
        StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");

        client.post(this, url, entity, "", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                builder.append("POST_Succeed->ResultCode:" + i + "\n"
                        + "--------------------------------------------------------" + "\n"
                        + Arrays.toString(headers) + "\n"
                        + "--------------------------------------------------------" + "\n"
                        + new String(bytes) + "\n\n");
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                builder.append("POST_Failed->ResultCode:" + i + "\n"
                        + "--------------------------------------------------------" + "\n"
                        + Arrays.toString(headers) + "\n"
                        + "--------------------------------------------------------" + "\n"
                        + new String(bytes) + "\n\n");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                resultView.setText(builder.toString());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
