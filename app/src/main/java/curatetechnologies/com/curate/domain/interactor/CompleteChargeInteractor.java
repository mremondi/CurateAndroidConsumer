package curatetechnologies.com.curate.domain.interactor;

/**
 * Created by mremondi on 3/23/18.
 */

public interface CompleteChargeInteractor extends Interactor {

    interface Callback {

        void onChargeSuccessful();

        void onRetrievalFailed(String error);
    }
}