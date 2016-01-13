import android.os.Parcel;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by adan on 1/13/16.
 */
public class MockParcel {

    public static Parcel obtain(){
        return new MockParcel().getMockedParcel();
    }

    Parcel mockParcel;
    int position;
    List<Object> objectList;

    public Parcel getMockedParcel(){
        return mockParcel;
    }

    public MockParcel(){
        mockParcel = mock(Parcel.class);
        objectList = new ArrayList<>();
        setupMock();
    }

    private void setupMock() {
        setupWrites();
        setupReads();
        setupOthers();
    }

    private void setupWrites() {
        Answer<Void> writeValueAnswer = new Answer<Void>() {
            @Override public Void answer(InvocationOnMock invocation) throws Throwable {
                Object parameter = invocation.getArguments()[0];
                objectList.add(parameter);
                return null;
            }
        };
        doAnswer(writeValueAnswer).when(mockParcel).writeLong(anyLong());
        doAnswer(writeValueAnswer).when(mockParcel).writeString(anyString());
    }

    private void setupReads() {
        when(mockParcel.readLong()).thenAnswer(new Answer<Long>() {
            @Override public Long answer(InvocationOnMock invocation) throws Throwable {
                return (Long) objectList.get(position++);
            }
        });
        when(mockParcel.readString()).thenAnswer(new Answer<String>() {
            @Override public String answer(InvocationOnMock invocation) throws Throwable {
                return (String) objectList.get(position++);
            }
        });
    }

    private void setupOthers() {
        doAnswer(new Answer<Void>() {
            @Override public Void answer(InvocationOnMock invocation) throws Throwable {
                position = ((Integer) invocation.getArguments()[0]);
                return null;
            }
        }).when(mockParcel).setDataPosition(anyInt());
    }
}
