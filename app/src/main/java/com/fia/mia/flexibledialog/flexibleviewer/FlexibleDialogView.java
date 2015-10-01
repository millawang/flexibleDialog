package com.fia.mia.flexibledialog.flexibleviewer;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;


public class FlexibleDialogView extends ScrollView {
	
	private View inner;// 孩子

	private float downX;// 坐標
	private float downY;// 坐標
	private float y;// 坐標
	private float downTop;
	private float downLeft;

	private Rect normal = new Rect();// 矩形空白
	private FlexibleDialogViewListener mListener;
	private boolean mIsCloseFromTop = true;

	public FlexibleDialogView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/***
	 * 根據 XML 生成視圖工作完成.該函數在生成視圖的最後調用，在所有子視圖添加完之後. 即使子類覆蓋了 onFinishInflate
	 * 方法，也應該調用父類的方法，使該方法得以執行.
	 */
	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			inner = getChildAt(0);// 獲取其孩子
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (inner != null) {
			commOnTouchEvent(ev);
		}
		return super.onTouchEvent(ev);
	}

	/***
	 * 觸摸事件
	 * 
	 * @param ev
	 */
	public void commOnTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			y = ev.getY();// 獲取點擊y坐標
			downX = ev.getX();// 獲取點擊X坐標
			downY = ev.getY();// 獲取點擊y坐標
			downTop = inner.getTop();
			downLeft = inner.getLeft();
			break;
		case MotionEvent.ACTION_UP:
			if (isNeedAnimation()) {
				animation();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			final float preY = y;
			float nowY = ev.getY();
			int deltaY = (int) (preY - nowY);// 獲取滑動距離

			y = nowY;
			// 當滾動到最上或者最下時就不會再滾動，這時移動布局
			if (isNeedMove()) {
				if (normal.isEmpty()) {
					// 填充矩形，目的：就是告訴this:我現在已經有了，你鬆開的時候記得要執行回歸動畫.
					normal.set(inner.getLeft(), inner.getTop(),
							inner.getRight(), inner.getBottom());
				}
				// 移動布局
				inner.layout(inner.getLeft(), inner.getTop() - deltaY / 2,
						inner.getRight(), inner.getBottom() - deltaY / 2);
			}
			break;

		default:
			break;
		}
	}

	/***
	 * 開启動畫移動
	 */
	public void animation() {
        boolean isclickView =checkClickView();

		// 開启移動動畫
		TranslateAnimation ta = null;

		if(mIsCloseFromTop && !isclickView){
			if(mListener != null){
				mListener.onFinishDialog();
			}
		} else if(mIsCloseFromTop && (downTop -inner.getTop()) > 80){
			if(mListener != null){
				mListener.onFinishDialog();
			}
		} else {
			ta = new TranslateAnimation(0, 0, inner.getTop() - downTop,
					normal.top - downTop);
			ta.setDuration(300);
			inner.startAnimation(ta);
			// 設置回到正常的布局位置
			inner.layout(normal.left, normal.top, normal.right, normal.bottom);
		}

		normal.setEmpty();// 清空矩形

	}

	//是否點擊到訊息物件
	private boolean checkClickView(){
		boolean isClickView = true;
		if(downX > (inner.getWidth()+downLeft))
		  isClickView = false;

		if(downX < downLeft)
			isClickView = false;

		if(downY > (inner.getHeight()+downTop))
			isClickView = false;

		if(downY < downTop)
			isClickView = false;

		return isClickView;

	}

	/***
	 * 是否需要開启動畫
	 * 
	 * 如果矩形不为空，返回true，否則返回false.
	 * 
	 * 
	 * @return
	 */
	public boolean isNeedAnimation() {
		return !normal.isEmpty();
	}

	/***
	 * 是否需要移動布局 inner.getMeasuredHeight():獲取的是控件的高度
	 * getHeight()：獲取的是當前控件在屏幕中顯示的高度
	 * 
	 * @return
	 */
	public boolean isNeedMove() {
		int offset = inner.getMeasuredHeight() - getHeight();
		int scrollY = getScrollY();
		// 0是頂部，後面那個是底部
		if (scrollY == 0 || scrollY == offset) {
			return true;
		}
		return false;
	}

	
	@Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        // do what you need to with the event, and then...
		onTouchEvent(e);
        return super.dispatchTouchEvent(e);
    }

	public void setFlexibleDialogViewListener(FlexibleDialogViewListener listener){
		this.mListener = listener;
	}

	public interface FlexibleDialogViewListener {
		void onFinishDialog();
	}

	public void setIsCloseFromTop(boolean b){
		this.mIsCloseFromTop = b;
	}
}
