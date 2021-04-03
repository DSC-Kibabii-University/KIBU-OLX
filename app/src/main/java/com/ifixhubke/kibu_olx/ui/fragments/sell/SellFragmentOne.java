package com.ifixhubke.kibu_olx.ui.fragments.sell;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.Sell;
import com.ifixhubke.kibu_olx.databinding.FragmentSellOneBinding;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class SellFragmentOne extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final int IMAGE_REQUEST1 = 1;
    private static final int IMAGE_REQUEST2 = 2;
    private static final int IMAGE_REQUEST3 = 3;
    private final ArrayList<Uri> imagesArrayList = new ArrayList<>();
    public Uri imageURI1;
    public Uri imageURI2;
    public Uri imageURI3;
    FragmentSellOneBinding binding;
    private String category, location;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSellOneBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //categorySpinner  and locationSpinner
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.category, R.layout.spinner_row);
        ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(requireContext(), R.array.location, R.layout.spinner_row);
        binding.categorySpinner.setAdapter(arrayAdapter);
        binding.locationSpinner.setAdapter(arrayAdapter1);
        binding.categorySpinner.setOnItemSelectedListener(this);
        binding.locationSpinner.setOnItemSelectedListener(this);

        binding.imagePick1.setOnClickListener(v -> {
            openFileChooser(IMAGE_REQUEST1);
            binding.imagePick1.setVisibility(View.GONE);
        });

        binding.imagePick2.setOnClickListener(v -> {
            openFileChooser(IMAGE_REQUEST2);
            binding.imagePick2.setVisibility(View.GONE);
        });

        binding.imagePick3.setOnClickListener(v -> {
            openFileChooser(IMAGE_REQUEST3);
            binding.imagePick3.setVisibility(View.GONE);

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
            if (binding.categorySpinner.getSelectedItem().toString().trim().equals("Choose Category")) {
                Toast.makeText(requireContext(), "Please select category", Toast.LENGTH_SHORT).show();
            } else if (binding.locationSpinner.getSelectedItem().toString().trim().equals("Choose Lgit addocation")) {
                Toast.makeText(requireContext(), "Please select location", Toast.LENGTH_SHORT).show();
            } else if (imageURI1 == null) {
                Toast.makeText(requireContext(), "Please upload your first image", Toast.LENGTH_SHORT).show();
            } else if (imageURI2 == null) {
                Toast.makeText(requireContext(), "Please upload your second image", Toast.LENGTH_SHORT).show();
            } else if (imageURI3 == null) {
                Toast.makeText(requireContext(), "Please upload your Third image", Toast.LENGTH_SHORT).show();
            } else {
                Sell sell = new Sell(category, location, imagesArrayList);
                NavDirections action = SellFragmentOneDirections.actionSellFragmentOneToSellFragmentTwo(sell);
                Navigation.findNavController(v).navigate(action);
            }
        });

        return view;
    }

    //choosing image
    public void openFileChooser(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageURI1 = data.getData();
            imagesArrayList.add(imageURI1);
            binding.imageRemove1.setVisibility(View.VISIBLE);
            binding.imageView1.setImageURI(imageURI1);
            binding.imagePick1.setVisibility(View.INVISIBLE);
        } else if (requestCode == IMAGE_REQUEST2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageURI2 = data.getData();
            imagesArrayList.add(imageURI2);
            binding.imageView2.setImageURI(imageURI2);
            binding.imageRemove2.setVisibility(View.VISIBLE);
            binding.imagePick2.setVisibility(View.INVISIBLE);
        } else if (requestCode == IMAGE_REQUEST3 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageURI3 = data.getData();
            imagesArrayList.add(imageURI3);
            binding.imageView3.setImageURI(imageURI3);
            binding.imageRemove3.setVisibility(View.VISIBLE);
            binding.imagePick3.setVisibility(View.INVISIBLE);

        } else if (requestCode == IMAGE_REQUEST1 && data == null) {
            binding.imagePick1.setVisibility(View.VISIBLE);
            binding.imageRemove1.setVisibility(View.INVISIBLE);
        } else if (requestCode == IMAGE_REQUEST2 && data == null) {
            binding.imagePick2.setVisibility(View.VISIBLE);
            binding.imageRemove2.setVisibility(View.INVISIBLE);
        } else if (requestCode == IMAGE_REQUEST3 && data == null) {
            binding.imagePick3.setVisibility(View.VISIBLE);
            binding.imageRemove3.setVisibility(View.INVISIBLE);
        }

       /* else{
            Toast.makeText(requireContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
            binding.imagePick1.setVisibility(View.VISIBLE);
            binding.imageRemove1.setVisibility(View.INVISIBLE);

            binding.imagePick2.setVisibility(View.VISIBLE);
            binding.imageRemove2.setVisibility(View.INVISIBLE);

            binding.imagePick3.setVisibility(View.VISIBLE);
            binding.imageRemove3.setVisibility(View.INVISIBLE);
        }*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.categorySpinner) {
            category = binding.categorySpinner.getSelectedItem().toString();
        } else if (parent.getId() == R.id.locationSpinner) {
            location = binding.locationSpinner.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}