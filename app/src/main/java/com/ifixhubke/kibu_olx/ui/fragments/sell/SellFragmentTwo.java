package com.ifixhubke.kibu_olx.ui.fragments.sell;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.data.Sell;
import com.ifixhubke.kibu_olx.databinding.FragmentSellTwoBinding;
import com.ifixhubke.kibu_olx.others.CheckInternet;
import com.ifixhubke.kibu_olx.others.Utils;
import com.ifixhubke.kibu_olx.viewmodels.MainViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

import timber.log.Timber;

public class SellFragmentTwo extends Fragment implements AdapterView.OnItemSelectedListener {
    private final ArrayList<String> imagesUrls = new ArrayList<>();
    FragmentSellTwoBinding binding;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String imageUrl1;
    String imageUrl2;
    String imageUrl3;
    Sell sellArgs;
    String f_name;
    String s_name;
    private ArrayList<Uri> imagesList = new ArrayList<>();
    private MainViewModel viewModel;
    private String condition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSellTwoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(MainViewModel.class);

        if (getArguments() != null) {
            sellArgs = SellFragmentTwoArgs.fromBundle(getArguments()).getSellTwoArguments();
        } else {
            Timber.d("SellFragmentTwoArgs are null");
        }

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.condition, R.layout.spinner_row);
        binding.conditionSpinner.setAdapter(arrayAdapter);
        binding.conditionSpinner.setOnItemSelectedListener(this);

        imagesList = sellArgs.getImagesList();

        storageReference = FirebaseStorage.getInstance().getReference("images");
        databaseReference = FirebaseDatabase.getInstance().getReference("all_images");

        getCurrentUserDetails();

        binding.postAdButton.setOnClickListener(v -> {

            Utils.hideSoftKeyboard(requireActivity());

            if (!CheckInternet.isConnected(getContext())){
                Toast.makeText(requireContext(), "It seems you are not connected to the internet", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(binding.productNameEditText.getText().toString())) {
                binding.productNameEditText.setError("Field can't be empty!");
                return;
            } else if (binding.conditionSpinner.getSelectedItem().toString().equals("Condition")) {
                Toast.makeText(requireContext(), "Select Condition", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(binding.priceEditText.getText().toString())) {
                binding.priceEditText.setError("Field can't be empty!");
                return;
            } else if (TextUtils.isEmpty(binding.phoneNumberEditText.getText().toString())) {
                binding.phoneNumberEditText.setError("Field can't be empty!");
                return;
            } else if (TextUtils.isEmpty(binding.itemDescription.getText().toString())) {
                binding.itemDescription.setError("Field can't be empty!");
            } else {
                try {
                    uploadFirebase();
                } catch (IOException e) {
                    Timber.d(e);
                }
            }
        });

        return view;
    }

    public void uploadFirebase() throws IOException {
        Timber.d("upload method called");
        ProgressDialog pd = new ProgressDialog(requireContext());
        pd.setTitle("Uploading Item...");
        pd.setCancelable(false);
        pd.show();

        if (imagesList != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("items_image");
            for (int counter = 0; counter < imagesList.size(); counter++) {

                Uri individualImage = imagesList.get(counter);
                final StorageReference fileStorageReference = storageReference.child(UUID.randomUUID() + "" + individualImage.getLastPathSegment());

                Bitmap bmp = MediaStore.Images.Media.getBitmap(requireActivity().getApplicationContext().getContentResolver(), individualImage);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
                byte[] fileInBytes = byteArrayOutputStream.toByteArray();

                Timber.d(counter + " " + fileInBytes.length);

                UploadTask uploadTask = fileStorageReference.putBytes(fileInBytes);

                uploadTask.continueWithTask(task -> {
                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }
                    return fileStorageReference.getDownloadUrl();

                }).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        assert downloadUri != null;
                        imagesUrls.add(downloadUri.toString());
                        Timber.d("%s", imagesUrls.size());
                        if (imagesUrls.size() == imagesList.size()) {
                            Timber.d(" Now about to store data to FireB %s", (imagesUrls.size() == imagesList.size()));
                            storeUrl();
                            pd.dismiss();
                            Navigation.findNavController(requireView()).navigate(R.id.action_sellFragmentTwo_to_homeFragment2);
                        }
                    }

                }).addOnFailureListener(e -> Toast.makeText(requireContext(), "Failed to upload", Toast.LENGTH_SHORT).show());
            }
        } else {
            Toast.makeText(requireContext(), "It seems you did not select images", Toast.LENGTH_SHORT).show();
        }
    }

    public void storeUrl() {
        traverseList();
        Timber.d("method to store url called");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("all_items");
        String date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        String itemUniqueId = UUID.randomUUID().toString();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("userProfile", Context.MODE_PRIVATE);

        Sell sell = new Sell(
                sellArgs.getCategory(),
                sellArgs.getLocation(),
                binding.productNameEditText.getText().toString(),
                binding.priceEditText.getText().toString(),
                binding.conditionSpinner.getSelectedItem().toString(),
                binding.phoneNumberEditText.getText().toString(),
                date,
                imageUrl2,
                imageUrl1,
                imageUrl3,
                false,
                binding.itemDescription.getText().toString(),
                sharedPreferences.getString("USERNAME", "default"),
                "Thursday 2020",
                itemUniqueId);

        Item item = new Item(imageUrl2, binding.productNameEditText.getText().toString(),
                binding.priceEditText.getText().toString(),
                date, false, itemUniqueId);

        saveToRoomDb(item);
        databaseReference.push().setValue(sell);
    }

    private void traverseList() {
        Timber.d("Method to retrieve each image url called");
        for (int i = 0; i < imagesUrls.size(); i++) {
            if (i == 0) {
                imageUrl1 = imagesUrls.get(0);
            } else if (i == 1) {
                imageUrl2 = imagesUrls.get(1);
            } else if (i == 2) {
                imageUrl3 = imagesUrls.get(2);
            }
        }
    }

    private void getCurrentUserDetails() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String userid = user.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                f_name = Objects.requireNonNull(dataSnapshot.child("f_Name").getValue()).toString();
                s_name = Objects.requireNonNull(dataSnapshot.child("l_Name").getValue()).toString();

                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("userProfile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("USERNAME", f_name + " " + s_name);
                editor.apply();
                editor.commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void saveToRoomDb(Item item) {
        viewModel.insert(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.conditionSpinner) {
            condition = binding.conditionSpinner.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
