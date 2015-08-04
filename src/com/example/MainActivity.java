package com.example.inventoryapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Cursor cursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// ListView -> listar departamentos de db 
		final ListView lista = (ListView) findViewById(R.id.listaDepartamentos);
		// click en un elemento de la lista -> Department
		
		lista.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	               Intent r = new Intent(MainActivity.this ,Department.class );
	               TextView textview = (TextView) findViewById(R.id.department);
	               String Name = getIntent().getStringExtra("extra");
	               textview.setText(Name);
	            }
	          });
		
		 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	     StrictMode.setThreadPolicy(policy);

	     connect();

	}
	
	private void connect(){
		String data;
	     List<String> r = new ArrayList<String>();
	     ArrayAdapter<String>adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,r);
	     ListView list=(ListView)findViewById(R.id.listaDepartamentos);
	     
	     try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://192.168.23.22/android/index.php");
            HttpResponse response = client.execute(request);
            HttpEntity entity=response.getEntity();
            data=EntityUtils.toString(entity);
            Log.e("STRING", data);
            
            try{
            	JSONArray json=new JSONArray(data);
                for(int i=0;i<json.length(); i++){
                	JSONObject obj=json.getJSONObject(i);
                    String dept = obj.getString("Departamento");
                    Log.e("STRING", dept);
                    r.add(dept);
                    list.setAdapter(adapter);
                }            	
            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }	     
	     }catch (ClientProtocolException e) {
	    	 Log.d("HTTPCLIENT", e.getLocalizedMessage());
	     } catch (IOException e) {
	         Log.d("HTTPCLIENT", e.getLocalizedMessage());
	     }
	}
	
	/*private void consultar(){
	      cursor = dbAdapter.getCursor();
	      startManagingCursor(cursor);
	      hipotecaAdapter = new InventoryCursorAdapter(this, cursor);
	      lista.setAdapter(hipotecaAdapter);
	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
