package com.example.testbuton;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

public class MenuService extends Service {
	WindowManager mWM;
	WindowManager.LayoutParams mWMParams;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (intent.getAction().equals("com.ohdroid.menu.action")) {
			
			System.out.println("......开始显示按钮");
			
			mWM = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
			final View win = LayoutInflater.from(getApplicationContext())
					.inflate(R.layout.test_layout, null);

			win.setOnTouchListener(new OnTouchListener() {
				float lastX, lastY;

				public boolean onTouch(View v, MotionEvent event) {
//					final int action = event.getAction();
//
//					float x = event.getX();
//					float y = event.getY();
//					if (action == MotionEvent.ACTION_DOWN) {
//						lastX = x;
//						lastY = y;
//					} else if (action == MotionEvent.ACTION_MOVE) {
//						mWMParams.x += (int) (x - lastX);
//						mWMParams.y += (int) (y - lastY);
//						mWM.updateViewLayout(win, mWMParams);
//					}
					return true;
				}
			});

			WindowManager wm = mWM;
			WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
			mWMParams = wmParams;
			wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
			// type是关键，这里的2002表示系统级窗口，你也可以试试2003。可取查帮助文档
			wmParams.format = 1;
			wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
					| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

			wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;// 设定大小
			wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
			wmParams.gravity=Gravity.BOTTOM|Gravity.RIGHT;
			wmParams.verticalMargin=0.1f;
			wm.addView(win, wmParams);
		}

		return super.onStartCommand(intent, flags, startId);
	}

}
