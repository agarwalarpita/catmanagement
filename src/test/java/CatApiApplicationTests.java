import org.example.CatApiApplication;
import org.example.CatPicture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CatApiApplicationTests {

    @Mock
    private Map<String, CatPicture> catPictures;

    @InjectMocks
    private CatApiApplication catApiApplication;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadCatPic() {
        // Prepare test data
        String name = "mycat.jpg";
        byte[] imageData = {1, 2, 3};
        MultipartFile file = new MockMultipartFile("file", name, MediaType.IMAGE_JPEG_VALUE, imageData);

        // Configure mock behavior
        String id = "1";
        CatPicture catPicture = new CatPicture(id, name, imageData);
        when(catPictures.put(eq(id), any(CatPicture.class))).thenReturn(null);

        // Execute the API call
        ResponseEntity<String> response = catApiApplication.uploadCatPic(name, file);

        // Verify the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().contains("Cat picture uploaded successfully. ID: " + id));

        // Verify the mock interactions
        verify(catPictures, times(1)).put(eq(id), any(CatPicture.class));
    }

    @Test
    void testDeleteCatPic() {
        // Prepare test data
        String id = "1";
        CatPicture catPicture = new CatPicture(id, "mycat.jpg", new byte[]{1, 2, 3});
        when(catPictures.containsKey(id)).thenReturn(true);
        when(catPictures.remove(id)).thenReturn(catPicture);

        // Execute the API call
        ResponseEntity<String> response = catApiApplication.deleteCatPic(id);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cat picture deleted successfully.", response.getBody());

        // Verify the mock interactions
        verify(catPictures, times(1)).containsKey(id);
        verify(catPictures, times(1)).remove(id);
    }

    @Test
    void testUpdateCatPic() {
        // Prepare test data
        String id = "1";
        String name = "updatedcat.jpg";
        byte[] imageData = {4, 5, 6};
        MultipartFile file = new MockMultipartFile("file", name, MediaType.IMAGE_JPEG_VALUE, imageData);
        CatPicture existingCatPicture = new CatPicture(id, "mycat.jpg", new byte[]{1, 2, 3});

        // Configure mock behavior
        when(catPictures.containsKey(id)).thenReturn(true);
        when(catPictures.get(id)).thenReturn(existingCatPicture);
        when(catPictures.put(eq(id), any(CatPicture.class))).thenReturn(null);

        // Execute the API call
        ResponseEntity<String> response = catApiApplication.updateCatPic(id, file);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cat picture updated successfully.", response.getBody());

        // Verify the mock interactions
        verify(catPictures, times(1)).containsKey(id);
        verify(catPictures, times(1)).put(eq(id), any(CatPicture.class));
    }

    @Test
    void testGetCatPicById() {
        // Prepare test data
        String id = "1";
        CatPicture catPicture = new CatPicture(id, "mycat.jpg", new byte[]{1, 2, 3});
        when(catPictures.containsKey(id)).thenReturn(true);
        when(catPictures.get(id)).thenReturn(catPicture);

        // Execute the API call
        ResponseEntity<byte[]> response = catApiApplication.getCatPicById(id);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(catPicture.getImageData(), response.getBody());
        assertEquals(MediaType.IMAGE_JPEG, response.getHeaders().getContentType());

        // Verify the mock interactions
        verify(catPictures, times(1)).containsKey(id);
        verify(catPictures, times(1)).get(id);
    }

    @Test
    void testGetAllCatPics() {
        // Prepare test data
        CatPicture catPicture1 = new CatPicture("1", "cat1.jpg", new byte[]{1, 2, 3});
        CatPicture catPicture2 = new CatPicture("2", "cat2.jpg", new byte[]{4, 5, 6});
        List<CatPicture> catPictureList = Arrays.asList(catPicture1, catPicture2);
        when(catPictures.values()).thenReturn(catPictureList);

        // Execute the API call
        ResponseEntity<List<CatPicture>> response = catApiApplication.getAllCatPics();

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(catPictureList, response.getBody());

        // Verify the mock interactions
        verify(catPictures, times(1)).values();
    }
}
