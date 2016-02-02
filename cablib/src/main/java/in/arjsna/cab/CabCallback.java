package in.arjsna.cab;

import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by arjun on 31/1/16.
 */
public interface CabCallback {
    boolean onCreateCab(ContextualActionBar cab, Menu menu);

    boolean onCabItemClicked(MenuItem item);

    boolean onDestroyCab(ContextualActionBar cab);
}
