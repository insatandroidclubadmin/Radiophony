package tn.iac.radiostreaming;

import tn.iac.radiostreaming.bd.RadioChannelTable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class FavoriteListener implements OnRatingBarChangeListener {

	RadioChannelTable radioChannels;
	
	
	
	public FavoriteListener(RadioChannelTable radioChannels) {
		super();
		this.radioChannels = radioChannels;
	}



	@Override
	public void onRatingChanged(RatingBar ratingBar, float arg1, boolean arg2) {
		ViewGroup viewGroup = (ViewGroup)ratingBar.getParent();
		TextView radioName = (TextView) viewGroup.getChildAt(1);
		radioChannels.setFavoriteChannel(radioName.getText().toString());

	}

}
