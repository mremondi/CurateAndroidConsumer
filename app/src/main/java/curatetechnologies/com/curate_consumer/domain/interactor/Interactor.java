package curatetechnologies.com.curate_consumer.domain.interactor;

/**
 * Created by mremondi on 2/9/18.
 */

public interface Interactor {

    /**
     * This is the main method that starts an interactor. It will make sure that the interactor operation is done on a
     * background thread.
     */
    void execute();
}
