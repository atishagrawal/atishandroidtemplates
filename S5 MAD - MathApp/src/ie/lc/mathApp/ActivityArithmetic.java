


package ie.lc.mathApp;

import ie.lc.R;
import android.os.Bundle;
import android.view.Menu;
import android.widget.*;





public class ActivityArithmetic extends ActivityGameBase
{
	private Operator gameOperator;
	private double   gameOperandLeft;
	private double   gameOperandRight;
	
	
	
	
	
	protected void onSaveInstanceState( Bundle out ) {
		super.onSaveInstanceState( out );
		out.putSerializable( "gameOperator",     gameOperator     );
		out.putDouble      ( "gameOperandLeft",  gameOperandLeft  );
		out.putDouble      ( "gameOperandRight", gameOperandRight );
	}
	
	
	
	
	
	protected void onRestoreInstanceState( Bundle in ) {
		super.onRestoreInstanceState( in );
		gameOperator     = (Operator) in.getSerializable( "gameOperator"     );
		gameOperandLeft  =            in.getDouble      ( "gameOperandLeft"  );
		gameOperandRight =            in.getDouble      ( "gameOperandRight" );
		updateUI();
	}
	
	
	
	
	
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState, R.layout.activity_arithmetic );
	}
	
	
	
	
	
	protected void setupGameActions() {
		setupAnswerActions(
			R.id.arithFieldAnswer,
			R.id.arithButtAnswer
		);
	}
	
	
	
	
	
	protected void setupNavActions() {
		setupNavActions(
			R.id.arithButtWave,	ActivityWave.class,
			R.id.arithButtSqrt,	ActivitySqrt.class
		);
	}
	
	
	
	
	
	protected void updateUI() {
		EditText fieldOpLeft  = (EditText) findViewById( R.id.arithFieldOperandLeft  );
		EditText fieldOpRight = (EditText) findViewById( R.id.arithFieldOperandRight );
		TextView textOperator = (TextView) findViewById( R.id.arithTextOperator      );
		EditText fieldAnswer  = (EditText) findViewById( R.id.arithFieldAnswer       );
		
		fieldOpLeft .setText( "" + (int) gameOperandLeft  );
		fieldOpRight.setText( "" + (int) gameOperandRight );
		textOperator.setText( "" +       gameOperator     );
		fieldAnswer .setText( "" );
	}
	
	
	
	
	
	protected void gameReset() {		
		gameOperandLeft  = Util.randomIntRange( 1, 10 );
		gameOperandRight = Util.randomIntRange( 1, 10 );
		gameOperator     = Operator.random();
		
		if (gameOperator == Operator.divide) // Keep it simple
			gameOperandLeft = gameOperandRight * Util.randomIntRange( 2, 10 );
		
		updateUI();
	}
	
	
	
	
	
	protected boolean isAnswerCorrect() {
		try {
			EditText fieldAnswer = (EditText) findViewById( R.id.arithFieldAnswer );
			double   userAnswer  = Double.parseDouble( fieldAnswer.getText().toString() );
			double   rightAnswer = gameOperator.evaluate( gameOperandLeft, gameOperandRight );
			return (userAnswer == rightAnswer);
		}
		catch (NumberFormatException ex) {
			return false;
		}
	}
}

























