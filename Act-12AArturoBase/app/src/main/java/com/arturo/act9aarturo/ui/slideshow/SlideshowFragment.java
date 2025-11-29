package com.arturo.act9aarturo.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.arturo.act9aarturo.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    // This class is deprecated and replaced by AdminPanelFragment.kt
    // Keeping it as a stub to prevent compilation errors if referenced elsewhere,
    // but clearing the logic that references views that no longer exist.

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}