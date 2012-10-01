/**
 * Copyright (C) 2012 by Luis Ángel Fernández Fernández <la@zzsoft.es>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package zzsoft.la.mathgame;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
