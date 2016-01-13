import android.os.Parcel;

import com.moreno.imgurviewer.models.GalleryItem;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by adan on 1/13/16.
 *
 * Unit Test class for the {@link com.moreno.imgurviewer.models.GalleryItem GalleryItem} class
 */
public class GalleryItemTest {
    GalleryItem item;

    @After
    public void destroy(){
        item = null;
    }

    @Test
    public void testJSONConstructor(){

        try {
            JSONObject mockValidJSON = mock(JSONObject.class);
            when(mockValidJSON.getString("id")).thenReturn("SbBGk");
            when(mockValidJSON.getString("title")).thenReturn("dummy title");
            when(mockValidJSON.getString("type")).thenReturn("image/jpeg");
            when(mockValidJSON.getString("link")).thenReturn("http://i.imgur.com/SbBGk.jpg");

            item = new GalleryItem(mockValidJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assertEquals("SbBGk", item.getId());
        assertEquals("dummy title", item.getTitle());
        assertEquals("image/jpeg", item.getType());
        assertEquals("http://i.imgur.com/SbBGk.jpg", item.getLink());

        try {
            JSONObject mockEmptyJSON = mock(JSONObject.class);
            item = new GalleryItem(mockEmptyJSON);
        }catch(JSONException je){
            assertNotNull(je);
            System.out.println(je.getMessage());
        }
    }



    @Test
    public void testParcelConstructor(){
        Parcel mockParcel = MockParcel.obtain();
        mockParcel.writeString("ABC123");
        mockParcel.writeString("dummy title");
        mockParcel.writeString("image/jpeg");
        mockParcel.writeString("http://imgur.mocklink.com");

        item = new GalleryItem(mockParcel);

        assertEquals("ABC123", item.getId());
        assertEquals("dummy title", item.getTitle());
        assertEquals("image/jpeg", item.getType());
        assertEquals("http://imgur.mocklink.com", item.getLink());

    }

}
