package floatingactionbutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;

import com.example.testbuton.R;

public class AddFloatingActionButton extends FloatingActionButton {
	int mPlusColor;

	public AddFloatingActionButton(Context context) {
		this(context, null);
	}

	public AddFloatingActionButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AddFloatingActionButton(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	void init(Context context, AttributeSet attributeSet) {
		TypedArray attr = context.obtainStyledAttributes(attributeSet,
				R.styleable.AddFloatingActionButton, 0, 0);
		mPlusColor = attr.getColor(
				R.styleable.AddFloatingActionButton_fab_plusIconColor,
				getColor(android.R.color.white));
		attr.recycle();

		super.init(context, attributeSet);
	}

	/**
	 * @return the current Color of plus icon.
	 */
	public int getPlusColor() {
		return mPlusColor;
	}

	public void setPlusColorResId(@ColorRes int plusColor) {
		setPlusColor(getColor(plusColor));
	}

	public void setPlusColor(int color) {
		if (mPlusColor != color) {
			mPlusColor = color;
			updateBackground();
		}
	}

	@Override
	public void setIcon(@DrawableRes int icon) {
		throw new UnsupportedOperationException(
				"Use FloatingActionButton if you want to use custom icon");
	}

	@Override
	Drawable getIconDrawable() {
		final float iconSize = getDimension(R.dimen.fab_icon_size);
		final float iconHalfSize = iconSize / 2f;

		final float plusSize = getDimension(R.dimen.fab_plus_icon_size);
		final float plusHalfStroke = getDimension(R.dimen.fab_plus_icon_stroke) / 2f;
		final float plusOffset = (iconSize - plusSize) / 2f;

		final Shape shape = new Shape() {
			@SuppressLint("NewApi")
			@Override
			public void draw(Canvas canvas, Paint paint) {
				// canvas.drawRect(plusOffset, iconHalfSize - plusHalfStroke,
				// iconSize - plusOffset, iconHalfSize + plusHalfStroke, paint);
				// canvas.drawRect(iconHalfSize - plusHalfStroke, plusOffset,
				// iconHalfSize + plusHalfStroke, iconSize - plusOffset, paint);
				// canvas.drawRect(0, 0, plusSize, plusSize, paint);
				// canvas.drawRect(plusOffset, 0,
				// iconSize - plusOffset, plusSize, paint);
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.home_normal);
				bitmap = zoomImage(bitmap, plusSize, plusSize);
				canvas.drawBitmap(bitmap, plusOffset * 2, 0, paint);
			}
		};

		ShapeDrawable drawable = new ShapeDrawable(shape);

		final Paint paint = drawable.getPaint();
		paint.setColor(mPlusColor);
		paint.setStyle(Style.FILL);
		paint.setAntiAlias(true);

		return drawable;
	}

	/***
	 * 图片的缩放方法
	 * 
	 * @param bgimage
	 *            ：源图片资源
	 * @param newWidth
	 *            ：缩放后宽度
	 * @param newHeight
	 *            ：缩放后高度
	 * @return
	 */
	public Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}
}
