package com.codepath.android.lollipopexercise.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.android.lollipopexercise.R;
import com.codepath.android.lollipopexercise.models.Contact;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class DetailsActivity extends AppCompatActivity {
    public static final String EXTRA_CONTACT = "EXTRA_CONTACT";
    private Contact mContact;
    private ImageView ivProfile;
    private TextView tvName;
    private TextView tvPhone;
    private View vPalette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        tvName = (TextView) findViewById(R.id.tvName);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        vPalette = findViewById(R.id.vPalette);

        // Extract contact from bundle
        mContact = (Contact)getIntent().getExtras().getSerializable(EXTRA_CONTACT);

        // Fill views with data
        tvName.setText(mContact.getName());
        tvPhone.setText(mContact.getNumber());

        // Use Picasso to get a callback with a Bitmap which can then
        // be used to extract a vibrant color from the Palette.

        // Define a listener for image loading
        Target target = new Target() {
            // Fires when Picasso finishes loading the bitmap for the target
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                // TODO 1. Insert the bitmap into the profile image view
                ivProfile.setImageBitmap(bitmap);

                // TODO 2. Use generate() method from the Palette API to get the vibrant color from the bitmap
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        // Get the "vibrant" color swatch based on the bitmap
                        Palette.Swatch vibrant = palette.getVibrantSwatch();
                        if (vibrant != null) {
                            // Set the background color of a layout based on the vibrant color
                            vPalette.setBackgroundColor(vibrant.getRgb());
                            // Update the title TextView with the proper text color
                            tvName.setTextColor(vibrant.getTitleTextColor());
                        }
                    }
                });
            }

            // Fires if bitmap fails to load
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        // TODO: Clear the bitmap and the background color in adapter
        ivProfile.setImageResource(0);
        vPalette.setBackgroundColor(0);

        // Store the target into the tag for the profile to ensure target isn't garbage collected prematurely
        ivProfile.setTag(target);
        // Instruct Picasso to load the bitmap into the target defined above
        Picasso.with(this).load(mContact.getThumbnailDrawable()).into(target);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
