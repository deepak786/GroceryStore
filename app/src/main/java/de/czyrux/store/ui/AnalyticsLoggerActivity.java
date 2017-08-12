package de.czyrux.store.ui;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.czyrux.store.R;
import de.czyrux.store.ui.util.MyAppLogReader;

public class AnalyticsLoggerActivity  extends AppCompatActivity {

	@BindView(R.id.text_anlayticslogs)
	TextView textLogs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.analytics_logger_activity);
		ButterKnife.bind(this);
		postRunnable();
	}

	Runnable fetchLogs = () -> {
		addMessage(MyAppLogReader.getLog().toString());
	//	postRunnable();
	};

	private void postRunnable(){
		textLogs.postDelayed(fetchLogs, 1000);
	}

	private void addMessage(String msg) {
		if (TextUtils.isEmpty(msg)){
			return;
		}
		textLogs.append(msg + "\n");
		// find the amount we need to scroll.  This works by
		// asking the TextView's internal layout for the position
		// of the final line and then subtracting the TextView's height
		final int scrollAmount = textLogs.getLayout().getLineTop(textLogs.getLineCount()) - textLogs.getHeight();
		// if there is no need to scroll, scrollAmount will be <=0
		if (scrollAmount > 0)
			textLogs.scrollTo(0, scrollAmount);
		else
			textLogs.scrollTo(0, 0);
	}
}
