package curatetechnologies.com.curate_consumer;

import android.location.Location;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.network.CurateAPIClient;
import curatetechnologies.com.curate_consumer.storage.ItemRepository;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

public class API_GetItemById_Mock {
    @Mock
    Location mockLocation;

    @Captor
    ArgumentCaptor argCaptor;

    private static final int mockRadius = 1;

    //System under test
    @InjectMocks
    CurateAPIClient mockClient = Mockito.mock(CurateAPIClient.class);

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void mock_getItemById() {

        ItemRepository testItemRep = new ItemRepository();

        //Mock APIclient getItemById.
        Mockito.doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                if (arguments != null && arguments.length > 1 && arguments[0] != null) {
                    System.out.println("Inside do answer");
                    testItemRep.onItemRetrieved(any(ItemModel.class));
                }
                return null;
            }
        }).when(mockClient).getItemById(testItemRep, 1, mockLocation, mockRadius);

        //verify that onItemRetrieved called
        Mockito.verify(testItemRep).onItemRetrieved((ItemModel) argCaptor.capture());
        assertTrue(argCaptor.getValue() instanceof ItemModel);
    }
}
