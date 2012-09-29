package zzsoft.la.mathgame;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 
 */

/**
 * @author Luis Ángel Fernández Fernández
 *
 */
public class KeypadActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_keypad);
	}
	
  public void onClickKeypad(View v) {
  	Intent intent = new Intent();
  	intent.putExtra("key_pressed", ((Button)v).getText());
  
  	setResult(2, intent);
  	finish();
  }
}
