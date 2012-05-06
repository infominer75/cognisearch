package utils;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class FileSystemWalkerUTest 
{

	@Mock
	private File directoryMock;
	
	@Mock
	private File subDirectoryMock;
	
	@Mock
	private File directoryFile1Mock;
	
	@Mock
	private File directoryFile2Mock;
	
	@Mock
	private File subDirectoryFile1Mock;
	
	@Mock
	private List<String> fileListMock;
	
	private FileSystemWalker underTest;
	
	
	@Before
	public void setUp() throws Exception 
	{
		MockitoAnnotations.initMocks(this);
		
		when(directoryMock.listFiles()).thenReturn(new File[] {subDirectoryMock, directoryFile1Mock, directoryFile2Mock});
		when(directoryMock.exists()).thenReturn(true);
		when(directoryMock.isDirectory()).thenReturn(true);
		when(subDirectoryMock.listFiles()).thenReturn(new File[]{ subDirectoryFile1Mock});
		
		when(subDirectoryMock.isDirectory()).thenReturn(true);
		when(directoryFile1Mock.isDirectory()).thenReturn(false);
		when(directoryFile2Mock.isDirectory()).thenReturn(false);
		when(subDirectoryFile1Mock.isDirectory()).thenReturn(false);
	
	}
	
	@Test(expected=FileNotFoundException.class)
	public void shouldThrowWalkDirectoryNotExists() throws FileNotFoundException
	{
		File nonExistentFileMock = mock(File.class);
		when(nonExistentFileMock.exists()).thenReturn(false);
		FileSystemWalker underTest2= new FileSystemWalker(1,nonExistentFileMock,mock(List.class));
	}
	
	@Test
	public void shouldEnumerateAllFilesCorrectly() throws FileNotFoundException
	{
		FileSystemWalker underTest = new FileSystemWalker(2,directoryMock,new ArrayList<File>());
		Iterator<File> fileIterator = underTest.walk();
		int counter = 0;
		while(fileIterator.hasNext())
		{
			counter = counter + 1;
			fileIterator.next();
		}
		assertTrue(3 == counter);
	}
	
	@Test
	public void shouldNotIncludeDirectoriesInWalkResult() throws FileNotFoundException
	{
		FileSystemWalker underTest = new FileSystemWalker(1,directoryMock,new ArrayList<File>());
		Iterator<File> fileIterator = underTest.walk();
		int counter = 0;
		while(fileIterator.hasNext())
		{
			File currentFile = fileIterator.next();
			assertFalse(currentFile.isDirectory());
		}
	}
	
	@Test
	public void shouldWalkDirectoryOnlyUptoSpecifiedDepth() throws FileNotFoundException
	{
		FileSystemWalker underTest = new FileSystemWalker(1,directoryMock,new ArrayList<File>());
		Iterator<File> fileIterator = underTest.walk();
		int counter = 0;
		while(fileIterator.hasNext())
		{
			counter = counter + 1;
			fileIterator.next();
		}
		
	}
	

}
