package curatetechnologies.com.curate_consumer.domain.interactor;

public interface IsRestaurantOpenInteractor extends Interactor {
    interface Callback{
        void onOpenClosedRetrieved(boolean isOpen);
        void onRetrieveOpenClosedFailed(String error);
    }
}