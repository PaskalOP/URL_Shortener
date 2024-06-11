package com.example.URL_Shortener;

import com.example.URL_Shortener.shorter.data.entity.EntityURL;
import com.example.URL_Shortener.shorter.repositoryService.RepositoryURL;
import com.example.URL_Shortener.shorter.repositoryService.URLServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class URLServiceImplTest {

    @Mock
    private RepositoryURL repositoryURL;

    @InjectMocks
    private URLServiceImpl urlServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetAllURLs() {
        // Arrange
        EntityURL url1 = new EntityURL();
        url1.setShortURL("NewShortURL01");
        EntityURL url2 = new EntityURL();
        url2.setShortURL("NewShortURL02");
        EntityURL url3 = new EntityURL();
        url3.setShortURL("NewShortURL03");
        EntityURL url4 = new EntityURL();
        url4.setShortURL("NewShortURL04");
        EntityURL url5 = new EntityURL();
        url5.setShortURL("NewShortURL05");
        when(repositoryURL.findAll()).thenReturn(Arrays.asList(url1, url2, url3, url4, url5));

        // Act
        List<EntityURL> urls = urlServiceImpl.getAllURLs();

        // Assert
        assertThat(urls).hasSize(5);
        assertThat(urls).extracting(EntityURL::getShortURL).containsExactly("NewShortURL01", "NewShortURL02", "NewShortURL03", "NewShortURL04", "NewShortURL05");
    }

    @Test
    public void testAddShortURL() {
        // Arrange
        EntityURL url = new EntityURL();
        url.setShortURL("shortURL1");
        when(repositoryURL.save(any(EntityURL.class))).thenReturn(url);

        // Act
        EntityURL savedURL = urlServiceImpl.addShortURL(url);

        // Assert
        assertThat(savedURL).isNotNull();
        assertThat(savedURL.getShortURL()).isEqualTo("shortURL1");
    }

    @Test
    public void testFindByShortURL() {
        // Arrange
        EntityURL url = new EntityURL();
        url.setShortURL("shortURL2");
        when(repositoryURL.findByShortURL(anyString())).thenReturn(url);

        // Act
        EntityURL foundURL = urlServiceImpl.findByShortURL("shortURL2");

        // Assert
        assertThat(foundURL).isNotNull();
        assertThat(foundURL.getShortURL()).isEqualTo("shortURL2");
    }

    @Test
    public void testUpdateShortURL() {
        // Arrange
        EntityURL url = new EntityURL();
        url.setShortURL("shortURL3");
        url.setOriginURL("originalURL3");
        url.setCountUse(0L);
        url.setUserID(UUID.randomUUID());

        // Mock the repository to return the URL
        when(repositoryURL.findByShortURL(anyString())).thenReturn(url);

        // Act
        EntityURL updatedURL = urlServiceImpl.updateShortURL(url);

        // Assert
        assertThat(updatedURL).isNotNull();
        assertThat(updatedURL.getShortURL()).isEqualTo("shortURL3");
        assertThat(updatedURL.getOriginURL()).isEqualTo("originalURL3");
        assertThat(updatedURL.getCountUse()).isEqualTo(0L);
        assertThat(updatedURL.getUserID()).isEqualTo(url.getUserID());
        assertThat(updatedURL.getCreatingDate()).isEqualTo(LocalDate.now());
        assertThat(updatedURL.getFinishDate()).isEqualTo(LocalDate.now().plusDays(10));
    }

    @Test
    public void testActiveURL() {
        // Arrange
        EntityURL url1 = new EntityURL();
        url1.setShortURL("activeURL1");
        url1.setFinishDate(LocalDate.now().plusDays(10));
        EntityURL url2 = new EntityURL();
        url2.setShortURL("activeURL2");
        url2.setFinishDate(LocalDate.now().plusDays(5));
        when(repositoryURL.activeURL(any(LocalDate.class))).thenReturn(Arrays.asList(url1, url2));

        // Act
        List<EntityURL> activeURLs = urlServiceImpl.activeURL();

        // Assert
        assertThat(activeURLs).isNotEmpty();
        assertThat(activeURLs).extracting(EntityURL::getShortURL).containsExactly("activeURL1", "activeURL2");
    }

    @Test
    public void testDeleteURL() {

        // Act
        urlServiceImpl.deleteURL("shortURL4");

        // Assert
        verify(repositoryURL, times(1)).deleteURL("shortURL4");
    }

    @Test
    public void testIncreaseCount() {
        // Act
        urlServiceImpl.increaseCount("shortURL5");

        // Assert
        verify(repositoryURL, times(1)).increaseCount("shortURL5");
    }
}
