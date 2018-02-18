package curatetechnologies.com.curate.domain.interactor;

/**
 * Created by mremondi on 2/18/18.
 */

public interface SaveUserPreferencesInteractor extends Interactor {

    interface Callback {

        void onUserPreferencesSaved();

        void onSavePreferencesFailed(String error);
    }
}