package com.ifixhubke.kibu_olx.ui.fragments.sell;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.Sell1;
import com.ifixhubke.kibu_olx.databinding.FragmentScreenOneBinding;
import com.ifixhubke.kibu_olx.databinding.FragmentSellTwoBinding;

import java.util.ArrayList;
import java.util.HashMap;

import timber.log.Timber;

public class SellFragmentTwo extends Fragment {
    FragmentSellTwoBinding binding;
    private int uploadCount = 0;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private ArrayList<Uri> imagesList;
    Sell1 sell;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSellTwoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        sell = SellFragmentTwoArgs.fromBundle(getArguments()).getSellTwoArguments();

        imagesList = new ArrayList<>();

        imagesList = sell.getImagesList();

        storageReference = FirebaseStorage.getInstance().getReference("images");
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Toast.makeText(requireContext(), "Argument " + sell.getCategory(), Toast.LENGTH_SHORT).show();
       // Toast.makeText(requireContext(), "Argument " + sell.getLocation(), Toast.LENGTH_SHORT).show();
        binding.postAdButton.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.productNameEditText.getText().toString())) {
                binding.productNameEditText.setError("Field can't be empty!");
                return;
            } else if (TextUtils.isEmpty(binding.conditionEditTtext.getText().toString())) {
                binding.conditionEditTtext.setError("Field can't be empty!");
                return;
            } else if (TextUtils.isEmpty(binding.priceEditText.getText().toString())) {
                binding.priceEditText.setError("Field can't be empty!");
                return;
            } else if (TextUtils.isEmpty(binding.phoneNumberEditText.getText().toString())) {
                binding.phoneNumberEditText.setError("Field can't be empty!");
                return;
            } else if (TextUtils.isEmpty(binding.itemDescription.getText().toString())) {
                binding.itemDescription.setError("Field can't be empty!");
            }

            uploadFirebase();
            clearEditText();

        });

        return view;

    }

    public void uploadFirebase() {
        Timber.d("upload method called");

        if (imagesList != null) {
            ProgressDialog pd = new ProgressDialog(requireContext());
            pd.setTitle("Uploading");
            pd.show();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("items_image");
        for (uploadCount = 0; uploadCount < imagesList.size(); uploadCount++) {
            Timber.d("for loop for looping");

            Uri individualImage = imagesList.get(uploadCount);

            final StorageReference imageRefence = storageReference.child("Image" + individualImage.getLastPathSegment());

            imageRefence.putFile(individualImage).addOnSuccessListener(taskSnapshot -> imageRefence
                    .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageUrl = String.valueOf(uri);
                            StoreUrl(imageUrl);
                            pd.dismiss();
                            Toast.makeText(requireContext(), "Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
                        }


                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Timber.d("Failed " + e.getMessage());
                            pd.dismiss();
                        }
                    })).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Timber.d("Storage " + e.getMessage());
                    pd.dismiss();
                }
            });
        }
    }else {
            Toast.makeText(requireContext(), "Failed Uploading", Toast.LENGTH_LONG).show();
        }

}

    public void StoreUrl(String imageUrl) {
        Timber.d("method to store url called");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Items_imagesUrl");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Imglink", imageUrl);
        hashMap.put("Category",sell.getCategory());
        hashMap.put("Location", sell.getLocation());
        hashMap.put("ItemName", binding.productNameEditText.getText().toString());
        hashMap.put("ItemCondition", binding.conditionEditTtext.getText().toString());
        hashMap.put("ItemPrice", binding.priceEditText.getText().toString());
        hashMap.put("ItemDescription", binding.itemDescription.getText().toString());

        databaseReference.push().setValue(hashMap);


    }

    void clearEditText(){
        //setting the edittext to null after posting
        binding.productNameEditText.setText("");
        binding.conditionEditTtext.setText("");
        binding.priceEditText.setText("");
        binding.phoneNumberEditText.setText("");
        binding.itemDescription.setText("");
    }

}
