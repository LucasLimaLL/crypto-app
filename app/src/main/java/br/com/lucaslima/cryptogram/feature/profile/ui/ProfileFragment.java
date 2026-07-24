package br.com.lucaslima.cryptogram.feature.profile.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import br.com.lucaslima.cryptogram.R;
import br.com.lucaslima.cryptogram.databinding.FragmentProfileBinding;
import br.com.lucaslima.cryptogram.feature.auth.data.UserSession;
import br.com.lucaslima.cryptogram.feature.credits.CreditsManager;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String username = UserSession.getInstance().getUsername();
        binding.textAvatarInitial.setText(String.valueOf(username.charAt(0)).toUpperCase());
        binding.textUsername.setText(username);
        binding.textCreditsBalance.setText(String.valueOf(
                CreditsManager.getInstance(requireContext()).getBalance()));
        binding.textPuzzlesCount.setText(String.valueOf(
                CreditsManager.getInstance(requireContext()).getPuzzlesCompleted()));
        binding.textStreak.setText(String.valueOf(
                CreditsManager.getInstance(requireContext()).getStreak()));

        binding.buttonBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        binding.buttonLogout.setOnClickListener(v -> {
            UserSession.getInstance().clear();
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_profileFragment_to_loginFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
