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

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.databinding.FragmentDetailsBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import timber.log.Timber;

public class DetailsFragment extends Fragment {
    FragmentDetailsBinding binding;
    DatabaseReference databaseReference;
    String userId;
    FirebaseUser user;
    Item data;
    private Boolean clicked = false;
    private String myNumber;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        userId = user.getUid();

        assert getArguments() != null;

        data = DetailsFragmentArgs.fromBundle(getArguments()).getItemDetailsArgs();
        Timber.d(data.getItemName());

        binding.userName1.setText(data.getSellerName());
        binding.tvLastseen1.setText("Seller");
        binding.favPhoneNumber1.setText(data.getSellerPhoneNum());

        myNumber = data.getSellerPhoneNum();

        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(data.getItemImage(), ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(data.getItemImage2(), ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(data.getItemImage3(), ScaleTypes.CENTER_CROP));

        Timber.d("image 1 " + data.getItemImage() + " \n image 2 " + data.getItemImage2() + " \n image 3 " + data.getItemImage3());

        binding.imageSliderFav1.setImageList(imageList);

        binding.favoritesItemName1.setText(data.getItemName());
        binding.favItemPrice1.setText("Ksh. " + data.getItemPrice());
        binding.favDatePosted1.setText("Uploaded on " + data.getDatePosted());
        binding.favLocation1.setText(data.getLocation());
        binding.favDescription1.setText(data.getItemDescription());

        binding.imageSliderFav1.setItemClickListener(position -> {

            Item item = new Item(data.getItemImage(), data.getItemImage2(), data.getItemImage3());
            NavDirections navDirections = DetailsFragmentDirections.actionDetailsFragmentToPictureBrowserFragment(item);
            Navigation.findNavController(requireView()).navigate(navDirections);
        });

        binding.showContacts.setOnClickListener(v -> onContactsButtonClicked());

        binding.messageButton.setOnClickListener(v -> {

            String sms = "Hey I have seen you are selling " + data.getItemName() + ". Can we do business";//The message you want to text to the phone

            Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", myNumber, null));
            smsIntent.putExtra("sms_body", sms);
            startActivity(smsIntent);
        });

        binding.phoneButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", myNumber, null));
            startActivity(intent);
        });

        binding.whatsappButton.setOnClickListener(v -> {
            String phone = myNumber.replaceFirst(String.valueOf(myNumber.charAt(0)), "+254");
            String url = "https://api.whatsapp.com/send?phone=" + phone;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url + "&text=" + "Hey I have seen you are selling " + data.getItemName() + ". Can we do business"));
            startActivity(intent);
            Timber.d(phone);
        });


        return view;
    }

    private void onContactsButtonClicked() {
        setVisibility(clicked);
        setAnimation(clicked);
        isButtonClicked(clicked);

        clicked = !clicked;
    }

    private void setVisibility(Boolean clicked) {
        if (!clicked) {
            binding.messageButton.setVisibility(View.VISIBLE);
            binding.phoneButton.setVisibility(View.VISIBLE);
            binding.whatsappButton.setVisibility(View.VISIBLE);
        } else {
            binding.messageButton.setVisibility(View.INVISIBLE);
            binding.phoneButton.setVisibility(View.INVISIBLE);
            binding.whatsappButton.setVisibility(View.INVISIBLE);
        }

    }

    private void setAnimation(Boolean clicked) {
        Animation fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.from_bottom_anim);
        Animation toBottom = AnimationUtils.loadAnimation(getContext(), R.anim.to_bottom_anim);
        if (!clicked) {
            binding.messageButton.startAnimation(fromBottom);
            binding.phoneButton.startAnimation(fromBottom);
            binding.whatsappButton.startAnimation(fromBottom);
        } else {
            binding.messageButton.startAnimation(toBottom);
            binding.phoneButton.startAnimation(toBottom);
            binding.whatsappButton.startAnimation(toBottom);
        }
    }

    private void isButtonClicked(Boolean clicked) {
        if (!clicked) {
            binding.phoneButton.setClickable(true);
            binding.messageButton.setClickable(true);
            binding.whatsappButton.setClickable(true);
        } else {
            binding.phoneButton.setClickable(false);
            binding.messageButton.setClickable(false);
            binding.whatsappButton.setClickable(false);
        }
    }

}