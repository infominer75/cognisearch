package indexers;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.index.IndexWriter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;

import exceptions.IndexingException;

import utils.FileSystemWalker;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

public class FileContentIndexerUTest 
{
	@Mock
	private IndexWriter indexWriterMock;
	
	@Mock
	private FileSystemWalker fileSystemWalkerMock;
	
	
	@Mock
	private File firstFileMock, secondFileMock;
	
	private List<File> fileList;
	
	private FileContentIndexer underTest;

	@Before
	public void setUp() throws Exception 
	{
		MockitoAnnotations.initMocks(this);
		
		fileList = Arrays.asList(firstFileMock, secondFileMock);
		
		when(fileSystemWalkerMock.walk()).thenReturn(fileList.iterator());
		
		when(firstFileMock.getPath()).thenReturn("/root/mockFile1");
		when(secondFileMock.getPath()).thenReturn("/root/mockFile2");
		when(firstFileMock.exists()).thenReturn(true);
		when(secondFileMock.exists()).thenReturn(true);
		
		underTest = new FileContentIndexer(fileSystemWalkerMock, 10, IndexingMode.CREATE, 
				mock(List.class), indexWriterMock);
		
	}

	@Test
	public void shouldIndexDirectoryTree() throws IndexingException
	{
		underTest.indexContent();
		verify(fileSystemWalkerMock).walk();
	}
	
	@Test
	public void shouldCreateNewDocumentWhenIndexWriterInNewMode()
	{
		
	}
	
	@Test
	public void shouldUpdateDocumentWhenIndexWriterInUpdateMode()
	{
		
	}

}
