package zzsoft.la.mathgame;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;

/**
 * Main activity.
 * @author Luis Ángel Fernández Fernández
 */
public class MainActivity extends Activity {

	private ArrayList<ArrayList<Integer>> m_randomDigits;
	private ArrayList<ArrayList<Character>> m_randomOperators;
	
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

    @Override
    protected void onDestroy() {
    	clearMemberLists();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (item.getItemId() == R.id.menu_new) {
    		clearMemberLists();
    		initializeBoard();
    	}
    	
    	return super.onOptionsItemSelected(item);
    }
    
    /**
     * This method will return one of the operators in the array array_operators.
     * @see array_operators
     * @return a Character with the random sign from array_operators.
     */
    private String getAnOperator() {    	  	
    	Resources res = getResources();
    	Random randomGenerator = getRandomizeGenerator();
    	
    	String[] strArray = res.getStringArray(R.array.array_operators);
    	int randomNumber = randomGenerator.nextInt(strArray.length);
    	
    	return strArray[randomNumber];
    }
    
    /**
     * This method will return a random Integer between 0 and 9.
     * @return an Integer between 0 and 9.
     */
    private Integer getADigit() {
    	Random randomGenerator = getRandomizeGenerator();
    	
    	return randomGenerator.nextInt(10);
    }
    
    /**
     * This method will initialize the board.
     */
    public void initializeBoard() {
      TableLayout tl = (TableLayout)findViewById(R.id.MainLayout);
      ArrayList<ArrayList<Character>> listOfOperators= new ArrayList<ArrayList<Character>>();
      ArrayList<ArrayList<Integer>> listOfDigits = new ArrayList<ArrayList<Integer>>();
      for (int i=0 ; i<tl.getChildCount() ; i++) {
      	LinearLayout ll = (LinearLayout)tl.getChildAt(i);
    		ArrayList<Character> rowOfOperators = new ArrayList<Character>();
    		ArrayList<Integer> rowOfDigits = new ArrayList<Integer>();
      	for (int j=0 ; j<ll.getChildCount() ; j++) {
      		Button button = (Button)ll.getChildAt(j);
      		//Resources res = getResources();
      		if (!button.isClickable()) {
      			// The button contains a sign label
      			CharSequence charSeq = button.getText();
      			String str = charSeq.toString();
      			if (str != null && !str.isEmpty() && isAnOperator(str.charAt(0))) {
      				String strAux = getAnOperator();
      				rowOfOperators.add(strAux.charAt(0));
      				button.setText(strAux);
      			}
      		}
      		else { // isClickable()
      			// The button will contain a number, so let's generate the number
      			// that will be part of the solution (at least one of them if there
      			// is more than one).
      			rowOfDigits.add(getADigit());
      		}
      	} // for j
      	listOfOperators.add(rowOfOperators);
      	listOfDigits.add(rowOfDigits);
      } // for i
      
      // Here I'll have a list with the digits
      // and a list with the signs I'll need to
      // do the math.
      
      // Let's fill the result cells.
      fillResultCells(listOfDigits, listOfOperators);
      if (m_randomDigits == null) m_randomDigits = new ArrayList<ArrayList<Integer>>();
      m_randomDigits.addAll(listOfDigits);
      if (m_randomOperators == null) m_randomOperators = new ArrayList<ArrayList<Character>>();
      m_randomOperators.addAll(listOfOperators);
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
     * This method will fill the buttons that will contain the results with the 
     * results values calculated.
     * @param digits the digits that will be part of the calculus.
     * @param operators the signs/operators used to do the math.
     */
    private void fillResultCells(ArrayList<ArrayList<Integer>> digits, 
    		ArrayList<ArrayList<Character>> operators) {
    	
    	ArrayList<Integer> results = doTheMath(digits, operators);
    	Button resultButton = (Button)findViewById(R.id.Button_result_row1);
    	resultButton.setText(results.get(0).toString());
    	resultButton = (Button)findViewById(R.id.Button_result_row2);
    	resultButton.setText(results.get(1).toString());
    	resultButton = (Button)findViewById(R.id.Button_result_row3);
    	resultButton.setText(results.get(2).toString());
    	resultButton = (Button)findViewById(R.id.Button_result_column1);
    	resultButton.setText(results.get(3).toString());
    	resultButton = (Button)findViewById(R.id.Button_result_column2);
    	resultButton.setText(results.get(4).toString());
    	resultButton = (Button)findViewById(R.id.Button_result_column3);
    	resultButton.setText(results.get(5).toString());
    }
    
    /**
     * A method to clear member lists.
     */
    private void clearMemberLists() {    
    	if (m_randomDigits != null) {
    		Iterator<ArrayList<Integer>> itr = m_randomDigits.iterator();
    		while (itr.hasNext()) {
    			Iterator<ArrayList<Integer>> itrAux = itr;
    	    itr.next();
    	    itrAux.remove();
    		}
    		m_randomDigits = null;
    	}
    	if (m_randomOperators != null) {
    		Iterator<ArrayList<Character>> itr = m_randomOperators.iterator();
    		while (itr.hasNext()) {
    			Iterator<ArrayList<Character>> itrAux = itr;
    	    itr.next();
    	    itrAux.remove();
    		}
    		m_randomOperators = null;
    	}
    }
    
    /**
     * This method will check whether or not a Character is an operator.
     * @param ch the Character to check.
     * @return true if ch is an operator, false otherwise.
     */
    private Boolean isAnOperator(Character ch) {
    	Resources res = getResources();
    	
    	String[] strArray = res.getStringArray(R.array.array_operators);
    	for (int i=0 ; i<strArray.length ; i++) {
    		if (strArray[i].contains(ch.toString())) {
    			return true;
    		}
    	}
    	if (ch == res.getString(R.string.button_label_operator_init).charAt(0)) {
    		return true;
    	}
    	
    	return false;
    }
    
    /**
     * This method will do the math.
     * @param digits the digits it'll use to do the math.
     * @param operators the operators it'll use to do the math.
     * @return an ArrayList of Integer with the results. First the results for
     * the rows (indexes 0, 1 and 2) then the columns (indexes 3, 4 and 5).
     */
    private ArrayList<Integer> doTheMath(ArrayList<ArrayList<Integer>> digits, 
    		ArrayList<ArrayList<Character>> operators) {
    	
    	ArrayList<Integer> results = new ArrayList<Integer>();
    	Integer result = Integer.valueOf(0);
    	
    	// First the rows
    	for (int i=0 ; i<digits.size() ; i++) {
    		ArrayList<Integer> list = digits.get(i);
    		if (list.size() != 0) {
	  			result = list.get(0);
	    		for (int j=0 ; j<list.size()-1 ; j++) {
	    			Integer m = list.get(j+1);
	    			if (operators.get(i).get(j) == '+') {
	    				result += m; 
	    			}
	    			else if (operators.get(i).get(j) == '-') {
	    				result -= m;
	    			}
	    			else if (operators.get(i).get(j) == '×') {
	    				result *= m;
	    			}
	    		}
	    		results.add(result);
    		}
    	}
    	// Now the columns
    	ArrayList<Integer> columnsResults = new ArrayList<Integer>(3);
  		columnsResults.add(digits.get(0).get(0));
  		columnsResults.add(digits.get(0).get(1));
  		columnsResults.add(digits.get(0).get(2));
    	for (int i=1 ; i<digits.size()-1 ; i++) {
    		ArrayList<Integer> list = digits.get(i);
    		if (list.size() != 0) {
	    		for (int j=0 ; j<list.size() ; j++) {
	    			Integer m = list.get(j);
	    			if (operators.get(i-1).get(j) == '+') {
	    				result = columnsResults.get(j);
	    				result += m;
	    				columnsResults.set(j, result);
	    			}
	    			else if (operators.get(i-1).get(j) == '-') {
	    				result = columnsResults.get(j);
	    				result -= m;
	    				columnsResults.set(j, result);
	    			}
	    			else if (operators.get(i-1).get(j) == '×') {
	    				result = columnsResults.get(j);
	    				result *= m;
	    				columnsResults.set(j, result);
	    			}
	    		}
    		}
    	}
    	
    	results.addAll(columnsResults);
    	
    	return results;
    }
    
    public void onClick(View v) {
    	
    }
}
