package in.arjsna.cab;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.arj.cab.R;

import java.io.Serializable;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ContextualActionBar implements Serializable, Toolbar.OnMenuItemClickListener {

    private TypeFaceTextView titleTextView;
    LayoutAnimationController animation;

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return mCallback != null && mCallback.onCabItemClicked(item);
    }

    private transient AppCompatActivity mContext;
    private transient Toolbar mToolbar;
    private transient CabCallback mCallback;

    @IdRes
    private int mAttacherId;
    private CharSequence mTitle;
    @StyleRes
    private int mPopupTheme;
    private int mContentInsetStart;
    @MenuRes
    private int mMenu;
    @ColorInt
    private int mBackgroundColor;
    @DrawableRes
    private int mCloseDrawable;
    private boolean mActive;

    public ContextualActionBar(@NonNull AppCompatActivity context, @IdRes int attacherId) {
        mContext = context;
        mAttacherId = attacherId;
        animation = AnimationUtils.loadLayoutAnimation(mContext, R.anim.layout_anim);
        reset();
    }

    public void setContext(@NonNull AppCompatActivity context) {
        mContext = context;
    }

    public boolean isActive() {
        return mActive;
    }

    @UiThread
    public ContextualActionBar reset() {
        mTitle = CabUtil.resolveString(mContext, R.attr.mcab_title);
        mPopupTheme = CabUtil.resolveResId(mContext, R.attr.mcab_popup_theme,
                R.style.ThemeOverlay_AppCompat_Light);
        mContentInsetStart = CabUtil.resolveDimension(mContext, R.attr.mcab_contentinset_start,
                R.dimen.mcab_default_content_inset);
        mMenu = CabUtil.resolveResId(mContext, R.attr.mcab_menu, 0);
        mBackgroundColor = CabUtil.resolveColor(mContext, R.attr.mcab_background_color,
                CabUtil.resolveColor(mContext, R.attr.colorPrimary, Color.GRAY));
        mCloseDrawable = CabUtil.resolveResId(mContext, R.attr.mcab_close_drawable,
                CabUtil.resolveResId(mContext, R.attr.actionModeCloseDrawable,
                        R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        if (mToolbar != null && mToolbar.getMenu() != null)
            mToolbar.getMenu().clear();
        return this;
    }

    @UiThread
    public ContextualActionBar start(@Nullable CabCallback callback) {
        mCallback = callback;
        invalidateVisibility(attach());
        View view = mContext.findViewById(android.R.id.content);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){
                    if(isActive()){
                        finish();
                        return true;
                    }
                }
                return false;
            }
        });
        return this;
    }

    public ContextualActionBar setLayoutAnim(int layoutAnim){
        if(layoutAnim != -1) {
            animation = AnimationUtils.loadLayoutAnimation(mContext, layoutAnim);
        }
        return this;
    }

    public ContextualActionBar setTitleTypeFace(Typeface titleTypeFace){
        titleTextView.setTypeface(titleTypeFace);
        return this;
    }

    @UiThread
    public ContextualActionBar setTitle(@Nullable CharSequence title) {
        mTitle = title;
        if(mToolbar != null) {
            titleTextView.setText(mTitle);
        }
        return this;
    }

    @UiThread
    public ContextualActionBar setTitleRes(@StringRes int titleRes) {
        return setTitle(mContext.getResources().getText(titleRes));
    }

    @UiThread
    public ContextualActionBar setTitleRes(@StringRes int titleRes, Object... formatArgs) {
        return setTitle(mContext.getResources().getString(titleRes, formatArgs));
    }

    @UiThread
    public ContextualActionBar setMenu(@MenuRes int menuRes) {
        mMenu = menuRes;
        if (mToolbar != null) {
            if (mToolbar.getMenu() != null)
                mToolbar.getMenu().clear();
            if (menuRes != 0)
                mToolbar.inflateMenu(menuRes);
            mToolbar.setOnMenuItemClickListener(this);
        }
        return this;
    }

    @UiThread
    public ContextualActionBar setPopupMenuTheme(@StyleRes int themeRes) {
        mPopupTheme = themeRes;
        if (mToolbar != null)
            mToolbar.setPopupTheme(themeRes);
        return this;
    }

    @UiThread
    public ContextualActionBar setContentInsetStart(int contentInset) {
        mContentInsetStart = contentInset;
        if (mToolbar != null)
            mToolbar.setContentInsetsRelative(contentInset, 0);
        return this;
    }

    @UiThread
    public ContextualActionBar setContentInsetStartRes(@DimenRes int contentInsetRes) {
        return setContentInsetStart((int) mContext.getResources().getDimension(contentInsetRes));
    }

    @UiThread
    public ContextualActionBar setContentInsetStartAttr(@AttrRes int contentInsetAttr) {
        return setContentInsetStart(CabUtil.resolveInt(mContext, contentInsetAttr, 0));
    }

    @UiThread
    public ContextualActionBar setBackgroundColor(@ColorInt int color) {
        mBackgroundColor = color;
        if (mToolbar != null)
            mToolbar.setBackgroundColor(color);
        return this;
    }

    @UiThread
    public ContextualActionBar setBackgroundColorRes(@ColorRes int colorRes) {
        return setBackgroundColor(mContext.getResources().getColor(colorRes));
    }

    @UiThread
    public ContextualActionBar setBackgroundColorAttr(@AttrRes int colorAttr) {
        return setBackgroundColor(CabUtil.resolveColor(mContext, colorAttr, 0));
    }

    @UiThread
    public ContextualActionBar setCloseDrawableRes(@DrawableRes int closeDrawableRes) {
        mCloseDrawable = closeDrawableRes;
        if (mToolbar != null)
            mToolbar.setNavigationIcon(mCloseDrawable);
        return this;
    }

    public Menu getMenu() {
        return mToolbar != null ? mToolbar.getMenu() : null;
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    @UiThread
    public void finish() {
        invalidateVisibility(!(mCallback == null || mCallback.onDestroyCab(this)));
    }

    private void invalidateVisibility(boolean active) {
        if (mToolbar == null) return;
        mToolbar.startLayoutAnimation();
        mToolbar.setVisibility(active ?
                View.VISIBLE : View.GONE);
        mActive = active;
    }

    private boolean attach() {
        final View attacher = mContext.findViewById(mAttacherId);
        if (attacher instanceof ViewStub) {
            ViewStub stub = (ViewStub) attacher;
            stub.setLayoutResource(R.layout.material_toolbar_with_textview_left);
            stub.setInflatedId(R.id.materialToolbar);
            mToolbar = (Toolbar) stub.inflate();
        } else if (attacher instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) attacher;
            mToolbar = (Toolbar) LayoutInflater.from(mContext)
                    .inflate(R.layout.material_toolbar_with_textview_left, parent, false);
            parent.addView(mToolbar);
        } else {
            throw new IllegalStateException("CustomCab was unable to attach to your Activity, attacher stub doesn't exist.");
        }
        mToolbar.setLayoutAnimation(animation);
        titleTextView = (TypeFaceTextView)mToolbar.findViewById(R.id.toolBarTitle);
        if (mToolbar != null) {
            if (mTitle != null)
                setTitle(mTitle);
            if (mPopupTheme != 0)
                mToolbar.setPopupTheme(mPopupTheme);
            if (mMenu != 0)
                setMenu(mMenu);
            if (mCloseDrawable != 0)
                setCloseDrawableRes(mCloseDrawable);
            setBackgroundColor(mBackgroundColor);
            setContentInsetStart(mContentInsetStart);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            return mCallback == null || mCallback.onCreateCab(this, mToolbar.getMenu());
        }
        return false;
    }

    @UiThread
    public void saveState(Bundle dest) {
        dest.putSerializable("[mcab_state]", this);
    }

    @UiThread
    public static ContextualActionBar restoreState(Bundle source, AppCompatActivity context, CabCallback callback) {
        if (source == null || !source.containsKey("[mcab_state]"))
            return null;
        ContextualActionBar cab = (ContextualActionBar) source.getSerializable("[mcab_state]");
        if (cab != null) {
            cab.mContext = context;
            if (cab.mActive)
                cab.start(callback);
        }
        return cab;
    }

    public void restore() {
        invalidateVisibility(mCallback == null || mCallback.onCreateCab(this, mToolbar.getMenu()));
    }
}