package adapter.range;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.kontakt.sdk.android.common.model.IProfile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;

public class ProfilesAdapter extends BaseAdapter {

    private final List<IProfile> profiles;
    private Context context;

    public ProfilesAdapter(final Context context) {
        this.profiles = new ArrayList<>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return profiles.size();
    }

    @Override
    public IProfile getItem(int position) {
        return profiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_profile_row, null);
            final ViewHolder viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        final ViewHolder holder = (ViewHolder) convertView.getTag();
        final IProfile profile = getItem(position);
        holder.titleText.setText(profile.getName());
        holder.summaryText.setText(profile.getDescription());
        return convertView;
    }

    public void replaceWith(final Collection<IProfile> profilesCollection) {
        profiles.clear();
        profiles.addAll(profilesCollection);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        final TextView titleText;
        final TextView summaryText;
        ViewHolder(final View convertView) {
            titleText = (TextView) convertView.findViewById(R.id.title);
            summaryText = (TextView) convertView.findViewById(R.id.summary);
        }
    }
}
