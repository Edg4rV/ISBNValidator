package com.virtualpariprogrammers.isbntools;

import com.virtualpairprogrammers.isbntools.Book;
import com.virtualpairprogrammers.isbntools.ExternalISBNDataService;
import com.virtualpairprogrammers.isbntools.StockManager;
import org.junit.Test;



import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class StockManagementTests {

    @Test
    public void testCanGetACorrectLocatorCode() {

        ExternalISBNDataService testWebService = mock(ExternalISBNDataService.class);
        when(testWebService.lookup(anyString())).thenReturn(new Book("0130177396", "of Mice and Man", "J. Stelnbeck"));

        ExternalISBNDataService testDatabaseService = mock(ExternalISBNDataService.class);
        when(testDatabaseService.lookup(anyString())).thenReturn(null);

        StockManager stockManager = new StockManager();
        stockManager.setWebService(testWebService);
        stockManager.setDatabaseService(testDatabaseService);
        String isbn = "0130177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("7396J4", locatorCode);
    }

    @Test
    public void databaseIsUsedIfDataIsPresent() {
        ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        when(databaseService.lookup("0130177396")).thenReturn(new Book("0130177396","abc","abc"));

        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(databaseService);

        String isbn = "0130177396";
        String locatorCode = stockManager.getLocatorCode(isbn);

        verify(databaseService).lookup("0130177396");
        verify(webService,never()).lookup(anyString());
    }
    @Test
    public void webserviceIsUsedIfDataIsNotPresent() {
        ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        when(databaseService.lookup("0130177396")).thenReturn(null);
        when(webService.lookup("0130177396")).thenReturn(new Book("0130177396","abc","abc"));

        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(databaseService);

        String isbn = "0130177396";
        String locatorCode = stockManager.getLocatorCode(isbn);

        verify(databaseService).lookup("0130177396");
        verify(webService).lookup("0130177396");
    }
}
