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

        ExternalISBNDataService testWebService = new ExternalISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return new Book(isbn, "Of Mice And Men", "J. Steinbeck");
            }
        };

        ExternalISBNDataService testDatabaseService = new ExternalISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return null;
            }
        };

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
