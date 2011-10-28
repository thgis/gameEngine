package iglugis.thomasGame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestMenu extends Activity  implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button btn = (Button) findViewById(R.id.btnBack);
		btn.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		finish();
	}
}
