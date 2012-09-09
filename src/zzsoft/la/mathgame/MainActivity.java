package zzsoft.la.mathgame;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;

/**
 * Main activity
 * @author Luis Ángel Fernández Fernández
 */
public class MainActivity extends Activity {

	private ArrayList<ArrayList<Integer>> m_randomDigits;
	private ArrayList<ArrayList<Character>> m_randomSigns;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        // I'll initialize the board.
        initializeBoard();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * This method will return one of the signs in the array array_signs
     * @see array_signs
     * @return a Character with the random sign from array_signs
     */
    private String getASign() {    	  	
    	Resources res = getResources();
    	Random randomGenerator = getRandomizeGenerator();
    	
    	String[] strArray = res.getStringArray(R.array.array_signs);
    	int randomNumber = randomGenerator.nextInt(strArray.length - 1);
    	
    	return strArray[randomNumber];
    }
    
    /**
     * This method will initialize the board.
     */
    private void initializeBoard() {
      TableLayout tl = (TableLayout)findViewById(R.id.MainLayout);
      clearMemberLists();
      for (int i=0 ; i<tl.getChildCount() ; i++) {
      	LinearLayout ll = (LinearLayout)tl.getChildAt(i);
    		ArrayList<Integer> listOfDigits = new ArrayList<Integer>();
    		ArrayList<Character> listOfSigns = new ArrayList<Character>();
      	for (int j=0 ; j<ll.getChildCount() ; j++) {
      		Button button = (Button)ll.getChildAt(j);
      		Resources res = getResources();
      		if (!button.isClickable()) {
      			// The button contains a sign label
      			CharSequence charSeq = button.getText();
      			String str = charSeq.toString();
      			if (str == res.getString(R.string.button_label_sign_init)) {
      				String strAux = getASign();
      				listOfSigns.add(strAux.charAt(0));
      				button.setText(strAux);
      			}
      		}
      		else {
      			// The button will contain a digit entered by the user.
      			// Let's get a random number that we'll use to do the math.
      			Random random = getRandomizeGenerator();
      			listOfDigits.add(random.nextInt(9));
      		}
      	}
      	m_randomDigits.add(listOfDigits);
      	m_randomSigns.add(listOfSigns);
      }
      // Here I'll have a list with the digits
      // and a list with the signs I'll need to
      // do the math.
    }
    
    /**
     * This method will return a initialized random number generator.
     * @return A random number generator.
     */
    private Random getRandomizeGenerator() {
    	String pattern = new String("S"); // milliseconds
    	SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
    	int milliseconds = Integer.parseInt(dateFormat.format(new Date()));
    	Random randomGenerator = new Random(milliseconds*milliseconds);

    	return randomGenerator;
    }
    
    /**
     * A method to clear member lists.
     */
    private void clearMemberLists() {    	
      for (int k=0 ; k<m_randomDigits.size() ; k++) {
      	m_randomDigits.get(k).clear();
      } 
      m_randomDigits.clear();
      for (int k=0 ; k<m_randomSigns.size() ; k++) {
      	m_randomSigns.get(k).clear();
      } 
      m_randomSigns.clear();
    }
}
