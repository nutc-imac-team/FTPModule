package com.AndroidExample.Module;

import android.content.Context;
import android.util.TypedValue;
import android.widget.RelativeLayout;

public abstract class MainParentLayout extends RelativeLayout {
	protected int WRAP_CONTENT = LayoutParams.WRAP_CONTENT,
			MATCH_PARENT = LayoutParams.MATCH_PARENT,
			PX = TypedValue.COMPLEX_UNIT_PX;
	protected Context context;
	protected WH WH;

	public MainParentLayout(Context context) {
		super(context);
		this.context = context;
		WH = new WH(context);
		setMath_Wrap();
		init();
	}

	protected abstract void init();

	protected void setMath_Wrap() {
		LayoutParams LP = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		this.setLayoutParams(LP);
	}

	protected int getRandomId() {
		return (int) (Math.random() * 1000000);
	}

	protected LayoutParams getLayoutParams(int Widht, int Height) {
		return new LayoutParams(Widht, Height);
	}
}
