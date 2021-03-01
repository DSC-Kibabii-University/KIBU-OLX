package com.ifixhubke.kibu_olx.ui.fragments.sell;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.Sell1;
import com.ifixhubke.kibu_olx.databinding.FragmentScreenOneBinding;
import com.ifixhubke.kibu_olx.databinding.FragmentSellOneBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import timber.log.Timber;

import static android.app.Activity.RESULT_OK;
import static android.icu.text.DateTimePatternGenerator.PatternInfo.OK;
public class SellFragmentOne extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final int SELECT_PICTURES = 1;
    FragmentSellOneBinding binding;

    public Uri imageURI1;
    public Uri imageURI2;
    public Uri imageURI3;


    private static final int IMAGE_REQUEST1 = 1;
    private static final int IMAGE_REQUEST2 = 2;
    private static final int IMAGE_REQUEST3 = 3;

   private ArrayList<Uri> imagesArrayList = new ArrayList<>();

    private String category,location;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSellOneBinding.inflate(inflater,container,false);
        View view = binding.getRoot();



        //categorySpinner  and locationSpinner
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(requireContext(),R.array.category, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(requireContext(),R.array.location, android.R.layout.simple_spinner_item);
        binding.categorySpinner.setAdapter(arrayAdapter);
        binding.locationSpinner.setAdapter(arrayAdapter1);
        binding.categorySpinner.setOnItemSelectedListener(this);
        binding.locationSpinner.setOnItemSelectedListener(this);

        binding.imagePick1.setOnClickListener(v -> {

            openFileChooser(IMAGE_REQUEST1);
            binding.imagePick1.setVisibility(View.GONE);

                binding.imageRemove1.setVisibility(View.VISIBLE);

        });

        binding.imagePick2.setOnClickListener(v -> {

            openFileChooser(IMAGE_REQUEST2);
            binding.imagePick2.setVisibility(View.GONE);

            binding.imageRemove2.setVisibility(View.VISIBLE);

        });

        binding.imagePick3.setOnClickListener(v -> {

            openFileChooser(IMAGE_REQUEST3);
            binding.imagePick3.setVisibility(View.GONE);

            binding.imageRemove3.setVisibility(View.VISIBLE);

        });

        binding.imageRemove1.setOnClickListener(v -> {
            binding.imageView1.setImageURI(null);

            imagesArrayList.remove(0);
            openFileChooser(IMAGE_REQUEST1);
        });

        binding.imageRemove2.setOnClickListener(v -> {
            binding.imageView2.setImageURI(null);
            imagesArrayList.remove(1);
            openFileChooser(IMAGE_REQUEST2);
        });

        binding.imageRemove3.setOnClickListener(v -> {
            binding.imageView3.setImageURI(null);
            imagesArrayList.remove(2);
            openFileChooser(IMAGE_REQUEST3);
        });



        binding.nextButton.setOnClickListener(v -> {

            //Passing data to sell2
            Sell1 sell = new Sell1(category,location,imagesArrayList);
            NavDirections action = SellFragmentOneDirections.actionSellFragmentOneToSellFragmentTwo(sell);
            Navigation.findNavController(v).navigate(action);
        });

        return view;
    }

    //choosing image
    public void openFileChooser(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST1  && resultCode == RESULT_OK && data != null && data.getData() != null){

            imageURI1 = data.getData();
            try {
                Bitmap bm1 = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), imageURI1);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bm1.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
                byte[] fileInBytes = byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            imagesArrayList.add(imageURI1);
            binding.imageView1.setImageURI(imageURI1);
        }

        if(requestCode == IMAGE_REQUEST2  && resultCode == RESULT_OK && data != null && data.getData() != null){

            imageURI2 = data.getData();
            try {
                Bitmap bm2 = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), imageURI2);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bm2.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
                byte[] fileInBytes = byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            imagesArrayList.add(imageURI2);
            binding.imageView2.setImageURI(imageURI2);
        }

        if(requestCode == IMAGE_REQUEST3  && resultCode == RESULT_OK && data != null && data.getData() != null){

            imageURI3 = data.getData();
            try {
                Bitmap bm3 = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), imageURI3);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bm3.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
                byte[] fileInBytes = byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }

            imagesArrayList.add(imageURI3);
            binding.imageView3.setImageURI(imageURI3);


        }


    }


    //category spinner onitemselected methods
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

         category = parent.getItemAtPosition(position).toString();
         location = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(),category, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}