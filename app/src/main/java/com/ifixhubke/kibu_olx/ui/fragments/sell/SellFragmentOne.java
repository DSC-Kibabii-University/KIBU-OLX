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

import net.alhazmy13.mediapicker.Image.ImagePicker;

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

    private ArrayList<Uri> imagesList;
    private static final int IMAGE_REQUEST1 = 1;
    private static final int IMAGE_REQUEST2 = 2;
    private static final int IMAGE_REQUEST3 = 3;

    private int uploadCount = 0;

    private String category,location;

    DatabaseReference databaseReference;
    private StorageReference storageReference;
    //private DatabaseReference mDatabaseReference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSellOneBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        imagesList = new ArrayList<>();


        storageReference = FirebaseStorage.getInstance().getReference("images");
        //Uploading spinner data to firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //categorySpinner  and locationSpinner
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(requireContext(),R.array.category, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(requireContext(),R.array.location, android.R.layout.simple_spinner_item);
        //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categorySpinner.setAdapter(arrayAdapter);
        binding.locationSpinner.setAdapter(arrayAdapter1);
        binding.categorySpinner.setOnItemSelectedListener(this);
        binding.locationSpinner.setOnItemSelectedListener(this);

        binding.imagePick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFileChooser(IMAGE_REQUEST1);
                binding.imagePick1.setVisibility(View.GONE);

                    binding.imageRemove1.setVisibility(View.VISIBLE);

            }
        });

        binding.imagePick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFileChooser(IMAGE_REQUEST2);
                binding.imagePick2.setVisibility(View.GONE);

                binding.imageRemove2.setVisibility(View.VISIBLE);

            }
        });

        binding.imagePick3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFileChooser(IMAGE_REQUEST3);
                binding.imagePick3.setVisibility(View.GONE);

                binding.imageRemove3.setVisibility(View.VISIBLE);

            }
        });


        binding.nextButton.setOnClickListener(v -> {

            //Passing data to sell2
            Sell1 sell = new Sell1(category,location,imageURI1,imageURI2,imageURI3);
            NavDirections action = SellFragmentOneDirections.actionSellFragmentOneToSellFragmentTwo(sell);
            Navigation.findNavController(v).navigate(action);
            //NavHostFragment.findNavController(SellFragmentOne.this).navigate(R.id.sellFragmentTwo);

            uploadFirebase();
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
            imagesList.add(imageURI1);
            Timber.d(imagesList.toString());
            binding.imageView1.setImageURI(imageURI1);
        }

        if(requestCode == IMAGE_REQUEST2  && resultCode == RESULT_OK && data != null && data.getData() != null){

            imageURI2 = data.getData();
            imagesList.add(imageURI2);
            Timber.d(imagesList.toString());
            binding.imageView2.setImageURI(imageURI2);
        }

        if(requestCode == IMAGE_REQUEST3  && resultCode == RESULT_OK && data != null && data.getData() != null){

            imageURI3 = data.getData();
            imagesList.add(imageURI3);
            Timber.d(imagesList.toString());
            binding.imageView3.setImageURI(imageURI3);


        }


    }





    //upload Image
  /* public void uploadImage(){
        if (imageURI1 != null){
            try {
                //compressing images
                Bitmap bm1 = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), imageURI1);
                Bitmap bm2 = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), imageURI2);
                Bitmap bm3 = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), imageURI3);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bm1.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
                bm2.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
                bm3.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
                byte[] fileInBytes = byteArrayOutputStream.toByteArray();

                //uploading images
                StorageReference imageStorageReference1 = storageReference.child(imageURI1.getLastPathSegment());
                StorageReference imageStorageReference2 = storageReference.child(imageURI2.getLastPathSegment());
                StorageReference imageStorageReference3 = storageReference.child(imageURI3.getLastPathSegment());

                UploadTask uploadTask1 = imageStorageReference1.putBytes(fileInBytes);

                uploadTask1.continueWithTask(task -> {
                    if (!task.isSuccessful()){
                        throw Objects.requireNonNull(task.getException());
                    }
                    return imageStorageReference1.getDownloadUrl();
                }).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        assert downloadUri != null;
                        String downloadURL1 = downloadUri.toString();
                        String downloadURL2 = downloadUri.toString();
                        String downloadURL3 = downloadUri.toString();

                        String date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                        Sell1 sell1 = new Sell1(binding.categorySpinner, binding.locationSpinner,downloadURL1,downloadURL2,downloadURL3,date);

                        databaseReference.child(UUID.randomUUID().toString()).setValue(sell1);
                    }

                }).addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), "Image1 Upload Failed", Toast.LENGTH_SHORT).show();
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }*/

    public void uploadFirebase(){
    Timber.d("upload method called");

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("items_image");
        for (uploadCount = 0; uploadCount < imagesList.size(); uploadCount++){
            Timber.d("for loop for looping");

            Uri individualImage = imagesList.get(uploadCount);

            final  StorageReference imageRefence = storageReference.child("Image"+individualImage.getLastPathSegment());

            imageRefence.putFile(individualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageRefence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageUrl = String.valueOf(uri);

                            StoreUrl(imageUrl);
                        }


                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Timber.d("Failed "+e.getMessage());
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Timber.d("Storage "+e.getMessage());
                }
            });
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

    private void StoreUrl(String imageUrl) {
        Timber.d("method to store url called");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Items_imagesUrl");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Imglink", imageUrl);

        databaseReference.push().setValue(hashMap);

        Toast.makeText(requireContext(),"Uploaded Successfully", Toast.LENGTH_SHORT).show();

    }
}