package com.fia.mia.flexibledialog.flexibleviewer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fia.mia.flexibledialog.R;

/**
 * Created by milla.wang on 8/1/15.
 */
public class FlexibleDialogBuilder extends Dialog {

	public final static int ALERT_DIALOG_TEST = 1;


	private class AlertDialogView extends LinearLayout {

		private Context mCxt;
		protected String mContent;
		protected FlexibleDialogView mFlexibleView;
		protected  int mViewType;

		public AlertDialogView(Context context, String content, int type) {
			super(context);
			mCxt = context;
			mContent = content;
			mViewType = type;
			init(type);
		}

		private void init(int type) {
			LayoutInflater li = LayoutInflater.from(getContext());

			switch (type) {

				case ALERT_DIALOG_TEST:
				default:
					li.inflate(R.layout.dialog_flexible_base, this);

					DialogDemoView vd = (DialogDemoView) findViewById(R.id.dialog_view);
					((FrameLayout.LayoutParams) vd.getLayoutParams()).gravity = Gravity.CENTER;
					vd.setAlertDialogListener(new AlertDialogListener() {
						@Override
						public void daligDismiss() {
							finishDialog();
						}
					});
					break;

			}
			mFlexibleView = (FlexibleDialogView)findViewById(R.id.alert_flexible);
			DisplayMetrics metrics = new DisplayMetrics();
			((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metrics);
			int width = (int)(metrics.widthPixels);
			int height = (int)(metrics.heightPixels);
			LayoutParams params = new LayoutParams(width, height);

			mFlexibleView.setLayoutParams(params);
			mFlexibleView.setFlexibleDialogViewListener(new FlexibleDialogView.FlexibleDialogViewListener() {
				@Override
				public void onFinishDialog() {
					finishDialog();
				}
			});

		}


		public void finishDialog(){
			Animation anim;
			anim = AnimationUtils.loadAnimation(mCxt, R.anim.anim_top_out);
			anim.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					dismiss();
				}
			});
			startAnimation(anim);
		}
	}

	protected Context mContext;
	protected AlertDialogView mView;
	protected OnDismissListener osDissmissListener;
	protected String mContent;
	protected int ViewType;

	public FlexibleDialogBuilder(Context context, int theme, int type) {
		super(context, theme);
		mContext = context;
		ViewType = type;
		setCancelable(false);
	}

	@Override
	public void setOnDismissListener(OnDismissListener listener) {
		osDissmissListener = listener;
		super.setOnDismissListener(listener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mView = new AlertDialogView(mContext, mContent, ViewType);
		setContentView(mView);


	}

	public interface  AlertDialogListener{
		void daligDismiss();
	}
}
