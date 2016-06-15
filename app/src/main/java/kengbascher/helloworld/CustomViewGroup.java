package kengbascher.helloworld;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * Created by Kengbascher on 5/13/2016.
 */
public class CustomViewGroup extends FrameLayout {

    private Button btnHello;

    public CustomViewGroup(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
    }

    @TargetApi(21)
    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
    }

    private void initInflate(){
        // Inflate Layout Here
        inflate(getContext(),R.layout.sample_layout,this);

    }

    private void initInstances(){
        // findViewById here

        btnHello = (Button) findViewById(R.id.btnCustomViewGroupHello);
    }

    public void setButtonText(String text){
        btnHello.setText(text);
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        //Save Children's state as a bundle
        Bundle childrenStates = new Bundle();
        for(int i = 0; i < getChildCount(); i++){
            int id = getChildAt(i).getId();
            if (id != 0){
                SparseArray childrenState = new SparseArray();
                getChildAt(i).saveHierarchyState(childrenState);
                childrenStates.putSparseParcelableArray(String.valueOf(id),childrenState);

            }
        }
        Bundle bundle = new Bundle();
        bundle.putBundle("childrenStates", childrenStates);

        //Save in to Parcelable
        BundleSavedState ss = new BundleSavedState(superState);
        ss.setBundle(bundle);
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        //Restore SparseArray
        Bundle childrenStates = ss.getBundle().getBundle("childrenStates");
        //Restore Children's state
        for (int i = 0; i < getChildCount(); i++){
            int id = getChildAt(i).getId();
            if (id != 0){
                if (childrenStates.containsKey(String.valueOf(id))){
                    SparseArray childrenState = childrenStates.getSparseParcelableArray(String.valueOf(id));
                    getChildAt(i).restoreHierarchyState(childrenState);
                }
            }
        }
    }
}
