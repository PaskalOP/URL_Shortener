package com.example.URL_Shortener;

import com.example.URL_Shortener.shorter.data.entity.EntityURL;
import com.example.URL_Shortener.shorter.repositoryService.RepositoryURL;
import com.example.URL_Shortener.shorter.repositoryService.URLServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class URLServiceImplTestV2 {
    @Mock
    private RepositoryURL repositoryURL;

    @InjectMocks
    private URLServiceImpl urlService;


    @Test
    public void testAddShortURL(){
        EntityURL url = new EntityURL();
        url.setOriginURL("https://example.com");
        url.setShortURL("https://short.url");
        url.setCountUse(0L);
        //url.setUserID(UUID.randomUUID());
        url.setCreatingDate(LocalDate.now());
        url.setFinishDate(LocalDate.now().plusDays(1));

        when(repositoryURL.save(any(EntityURL.class))).thenReturn(url);

        EntityURL savedURL = urlService.addShortURL(url);
        Assertions.assertThat(savedURL).isEqualTo(url);
    }

    @Test
    public void testDeleteURL(){
//        when(repositoryURL.deleteURL(anyString())).thenReturn(true);
//
//        boolean isDeleted = urlService.deleteURL("https://short.url");
//        verify(repositoryURL, times(1)).deleteURL("https://short.url");
//        Assertions.assertThat(isDeleted).isTrue();
    }

    @Test
    public void testGetAllURLs(){
        EntityURL url1 = new EntityURL();
        url1.setOriginURL("https://example1.com");
        url1.setShortURL("https://short1.url");
        url1.setCountUse(0L);
        //url1.setUserID(UUID.randomUUID());
        url1.setCreatingDate(LocalDate.now());
        url1.setFinishDate(LocalDate.now().plusDays(1));

        EntityURL url2 = new EntityURL();
        url2.setOriginURL("https://example22.com");
        url2.setShortURL("https://short22.url");
        url2.setCountUse(0L);
       // url2.setUserID(UUID.randomUUID());
        url2.setCreatingDate(LocalDate.now());
        url2.setFinishDate(LocalDate.now().plusDays(1));

        when(repositoryURL.findAll()).thenReturn(Arrays.asList(url1, url2));

        List<EntityURL> urls = urlService.getAllURLs();
        Assertions.assertThat(urls).hasSize(2);
        Assertions.assertThat(urls).containsExactly(url1, url2);
    }

    @Test
    public void testFindByShortURL() {
        EntityURL url = new EntityURL();
        url.setOriginURL("https://example1.com");
        url.setShortURL("https://short1.url");
        url.setCountUse(0L);
        //url.setUserID(UUID.randomUUID());
        url.setCreatingDate(LocalDate.now());
        url.setFinishDate(LocalDate.now().plusDays(1));
        when(repositoryURL.findByShortURL(anyString())).thenReturn(url);

        EntityURL foundURL = urlService.findByShortURL("https://short.url");
        Assertions.assertThat(foundURL).isEqualTo(url);
    }

    @Test
    public void testUpdateShortURL() {
        EntityURL existingURL = new EntityURL();
        existingURL.setOriginURL("https://example_existing.com");
        existingURL.setShortURL("https://short_existing.url");
        existingURL.setCountUse(0L);
       // existingURL.setUserID(UUID.randomUUID());
        existingURL.setCreatingDate(LocalDate.now());
        existingURL.setFinishDate(LocalDate.now().plusDays(1));

        EntityURL newURL = new EntityURL();
        newURL.setOriginURL("https://example_new.com");
        newURL.setShortURL("https://short_new.url");
        newURL.setCountUse(0L);
        //newURL.setUserID(UUID.randomUUID());
        newURL.setCreatingDate(LocalDate.now());
        newURL.setFinishDate(LocalDate.now().plusDays(1));

        when(repositoryURL.findByShortURL(anyString())).thenReturn(existingURL);
        when(repositoryURL.save(any(EntityURL.class))).thenReturn(newURL);

        EntityURL updatedURL = urlService.updateShortURL(newURL);
        Assertions.assertThat(updatedURL.getOriginURL()).isEqualTo(newURL.getOriginURL());
    }

    @Test
    public void testActiveURL() {
        EntityURL url1 = new EntityURL();
        url1.setOriginURL("https://example1.com");
        url1.setShortURL("https://short1.url");
        url1.setCountUse(0L);
       // url1.setUserID(UUID.randomUUID());
        url1.setCreatingDate(LocalDate.now());
        url1.setFinishDate(LocalDate.now().plusDays(1));

        EntityURL url2 = new EntityURL();
        url2.setOriginURL("https://example22.com");
        url2.setShortURL("https://short22.url");
        url2.setCountUse(0L);
        //url2.setUserID(UUID.randomUUID());
        url2.setCreatingDate(LocalDate.now());
        url2.setFinishDate(LocalDate.now().plusDays(1));

        when(repositoryURL.activeURL(any(LocalDate.class))).thenReturn(Arrays.asList(url1, url2));

        List<EntityURL> activeUrls = urlService.activeURL();

        Assertions.assertThat(activeUrls).hasSize(2);
        Assertions.assertThat(activeUrls).containsExactly(url1, url2);
    }

    @Test
    public void testIncreaseCount() {
        doNothing().when(repositoryURL).increaseCount(anyString());

        urlService.increaseCount("https://short.url");
        verify(repositoryURL, times(1)).increaseCount("https://short.url");
    }





}
