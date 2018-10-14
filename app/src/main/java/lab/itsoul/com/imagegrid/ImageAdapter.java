package lab.itsoul.com.imagegrid;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<Image> images;
    private ConstraintLayout layout;

    public ImageAdapter(Context c, List<Image> images) {
        mContext = c;
        this.images = images;
    }

    public int getCount() {
        return images.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        ImageView imageView;
        if (convertView == null) {

            view = inflatetr.inflate(R.layout.image_view, parent, false);
            // if it's not recycled, initialize some attributes
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
        } else {
//            imageView = (ImageView) convertView;
            view = convertView;
        }

//        imageView.setImageURI(Uri.parse(images.get(position).getPhoto()));
//        Picasso.get().load(images.get(position).getPhoto()).into(imageView);
        ImageView photo = view.findViewById(R.id.photo2);
        TextView title = view.findViewById(R.id.imageTitle);
        Picasso.get().load(images.get(position).getPhoto()).into(photo);
        title.setText(images.get(position).getAuthor());
        return view;
    }

//    // references to our images
//    private Integer[] mThumbIds = {
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7,
//            R.drawable.sample_0, R.drawable.sample_1,
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7,
//            R.drawable.sample_0, R.drawable.sample_1,
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7
//    };
}