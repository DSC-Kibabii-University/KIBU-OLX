package com.ifixhubke.kibu_olx.ui.fragments.help;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.databinding.FragmentHelpFeedbackBinding;
import com.ifixhubke.kibu_olx.others.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HelpFeedbackFragment extends Fragment {
    FragmentHelpFeedbackBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHelpFeedbackBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.toolbar.setNavigationOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        binding.buttonSubmitFeedback.setOnClickListener(v -> {

            Utils.hideSoftKeyboard(requireActivity());
            if (binding.editTextSubject.getText().toString().isEmpty()) {
                binding.editTextSubject.setError("This field can't be empty!");
                return;
            } else if (binding.editTextFeedback.getText().toString().isEmpty()) {
                binding.editTextFeedback.setError("This field can't be empty!");
                return;
            }

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ifixhubke@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, binding.editTextSubject.getText().toString());
            intent.putExtra(Intent.EXTRA_TEXT, binding.editTextFeedback.getText());
            intent.setType("message/rfc822");
            final PackageManager pm = getActivity().getPackageManager();
            @SuppressLint("QueryPermissionsNeeded") final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
            ResolveInfo best = null;
            for (final ResolveInfo info : matches)
                if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                    best = info;
            if (best != null)
                intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
            getActivity().startActivity(intent);
            Navigation.findNavController(v).navigate(R.id.action_helpFeedbackFragment_to_homeFragment2);
        });

        return view;
    }
}