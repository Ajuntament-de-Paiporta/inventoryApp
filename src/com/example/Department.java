package com.example.inventoryapp;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Department extends Activity implements OnClickListener{
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_pc);
		
		 final Button scan = (Button) findViewById(R.id.scan);
         scan.setOnClickListener(this);
         
         final Button act = (Button) findViewById(R.id.actualizar);
         act.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// abrir camara para scanear codigo de barras
		 if(v.getId()==R.id.scan){
			 //scan
			 IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			 scanIntegrator.initiateScan();
		 }
		 
		 if(v.getId()==R.id.actualizar){
			 // conectar con db para actualizar
		 }
		
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		//retrieve scan result
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult != null) {
			//we have a result
			
			String scanContent = scanningResult.getContents();
			// mostrar contingut en una pantalla a banda
			
		}else{
		    Toast toast = Toast.makeText(getApplicationContext(), 
		            "No scan data received!", Toast.LENGTH_SHORT);
		        toast.show();
		}
	}
}
