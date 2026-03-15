package br.com.lucaslima.cryptogram.feature.home.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.com.lucaslima.cryptogram.feature.home.data.PuzzleRepositoryImpl;
import br.com.lucaslima.cryptogram.feature.home.domain.GetPuzzleUseCase;
import br.com.lucaslima.cryptogram.feature.home.domain.PuzzleResult;

/**
 * ViewModel for the Home screen.
 *
 * <p>Survives configuration changes and exposes UI state through {@link LiveData}.
 * The ViewModel calls the use-case and posts results back to the UI on whatever
 * thread it runs on. In a production app, push the use-case call onto a background
 * executor or coroutine scope and use {@code postValue} for thread safety.
 */
public class HomeViewModel extends ViewModel {

    private final MutableLiveData<PuzzleResult> puzzleState = new MutableLiveData<>();
    private final GetPuzzleUseCase getPuzzleUseCase;

    public HomeViewModel() {
        this(new GetPuzzleUseCase(new PuzzleRepositoryImpl()));
    }

    HomeViewModel(GetPuzzleUseCase getPuzzleUseCase) {
        this.getPuzzleUseCase = getPuzzleUseCase;
    }

    /** Exposes puzzle-loading state to the UI. */
    public LiveData<PuzzleResult> getPuzzleState() {
        return puzzleState;
    }

    /** Triggers a puzzle load. Emits {@link PuzzleResult.Loading} first, then the result. */
    public void loadPuzzle() {
        puzzleState.setValue(new PuzzleResult.Loading());
        PuzzleResult result = getPuzzleUseCase.execute();
        puzzleState.setValue(result);
    }
}
