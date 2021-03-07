package com.ifixhubke.kibu_olx.ui.fragments.details;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.Favourites;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.databinding.FragmentDetailsBinding;
//import com.ifixhubke.kibu_olx.ui.fragments.favorites.FavoritesFragmentArgs;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import timber.log.Timber;

public class DetailsFragment extends Fragment {
    FragmentDetailsBinding binding;

    private Boolean clicked = false;
    DatabaseReference databaseReference;
    String userid, phone;
    FirebaseUser user;


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        assert getArguments() != null;

        Item data = DetailsFragmentArgs.fromBundle(getArguments()).getItemDetailsArgs();
        Timber.d(data.getItemName());

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        userid = user.getUid();

        binding.userName1.setText(data.getSellerName());
        binding.tvLastseen1.setText(data.getSellerLastSeen());
        binding.favPhoneNumber1.setText(data.getSellerPhoneNum());

        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(data.getItemImage(), data.getItemName(), ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(data.getItemImage2(), data.getItemName(), ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(data.getItemImage3(), data.getItemName(), ScaleTypes.CENTER_CROP));

        Timber.d("image 1 " + data.getItemImage() + " \n image 2 " + data.getItemImage2() + " \n image 3 " + data.getItemImage3());

        binding.imageSliderFav1.setImageList(imageList);

        binding.favoritesItemName1.setText(data.getItemName());
        binding.favItemPrice1.setText("Ksh. "+data.getItemPrice());
        binding.favDatePosted1.setText("Uploaded on " + data.getDatePosted());
        binding.favLocation1.setText(data.getLocation());
        binding.favDescription1.setText(data.getItemDescription());

        binding.showContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContactsButtonClicked();
            }
        });

        binding.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Message Clicked", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        binding.phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPhoneNumber();

                //Snackbar.make(v, "Phone Clicked", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        binding.whatsappButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Whatsapp Clicked", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });

        return view;
    }
    private void onContactsButtonClicked() {
        setVisibility(clicked);
        setAnimation(clicked);
        isButtonClicked(clicked);

        if (!clicked){
            clicked = true;
        }else {
            clicked = false;
        }
    }

    private void setVisibility(Boolean clicked) {
        if (!clicked){
            binding.messageButton.setVisibility(View.VISIBLE);
            binding.phoneButton.setVisibility(View.VISIBLE);
            binding.whatsappButton.setVisibility(View.VISIBLE);
        }
        else {
            binding.messageButton.setVisibility(View.INVISIBLE);
            binding.phoneButton.setVisibility(View.INVISIBLE);
            binding.whatsappButton.setVisibility(View.INVISIBLE);
        }

    }

    private void setAnimation(Boolean clicked){
        Animation fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.from_bottom_anim);
        Animation toBottom = AnimationUtils.loadAnimation(getContext(), R.anim.to_bottom_anim);
        if (!clicked){
            binding.messageButton.startAnimation(fromBottom);
            binding.phoneButton.startAnimation(fromBottom);
            binding.whatsappButton.startAnimation(fromBottom);
        }
        else {
            binding.messageButton.startAnimation(toBottom);
            binding.phoneButton.startAnimation(toBottom);
            binding.whatsappButton.startAnimation(toBottom);
        }
    }

    private void isButtonClicked(Boolean clicked) {
        if (!clicked){
            binding.phoneButton.setClickable(true);
            binding.messageButton.setClickable(true);
            binding.whatsappButton.setClickable(true);
        }
        else {
            binding.phoneButton.setClickable(false);
            binding.messageButton.setClickable(false);
            binding.whatsappButton.setClickable(false);
        }
    }

    private void getPhoneNumber() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                phone = snapshot.child("phone_No").getValue().toString();
                Timber.d("Phone Number:  "+phone);
                String myNumber = phone;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", myNumber, null));
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}