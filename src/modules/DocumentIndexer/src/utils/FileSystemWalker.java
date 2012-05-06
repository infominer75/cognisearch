package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

/**
 * 
 * Walks a directory structure on the file system and returns a flat list of files present
 * within the directory.The entire walk of the directory happens on a single thread and can be paused and resumed
 * by clients on demand. 
 * <br>Clients can specify the maximum depth up to which the walk must be done before a list of files is returned.
 * By default the maximum depth is 1 - files in the same directory.
 * <br>The file system walker's journey through a deep directory tree may be time consuming.
 */

public class FileSystemWalker 
{

	private static final int MAX_DEPTH_DEFAULT = 1;
	
	private final Integer maxDepth;
	
	private  Integer currentDepth;

	private  final List<File> enumeratedFiles;
	
	private final File rootDirectory;
	
	
	public FileSystemWalker(String parentDirectory) throws FileNotFoundException
	{
		this(parentDirectory, MAX_DEPTH_DEFAULT);
	}
	public FileSystemWalker(String parentDirectory, Integer maxDepth) throws FileNotFoundException
	{
		this(maxDepth, new File(parentDirectory), new ArrayList<File>());
	}
	
	FileSystemWalker(Integer maxDepth, File parentDirectory, List<File> enumeratedFiles) throws FileNotFoundException
	{
		this.maxDepth = maxDepth;
		this.enumeratedFiles = enumeratedFiles;
		this.rootDirectory = parentDirectory;
		if(!this.rootDirectory.exists())
		{
			throw new FileNotFoundException("Invalid parent directory specified");
		}
		
		this.currentDepth = 0;
	}

	
	public Iterator<File> walk() 
	{
		
		walk_internal(this.rootDirectory);
		
		return this.enumeratedFiles.iterator();
		
	}
	
	private void walk_internal(File currentFile)
	{
		
		if(!currentFile.isDirectory())
		{
			this.enumeratedFiles.add(currentFile);
			return;
		}
		
		this.currentDepth++;
		
		if(this.currentDepth > this.maxDepth)
			return;
		
		List<File> files = Arrays.asList(currentFile.listFiles());
		
		for(File file : files)
		{
			
			
			walk_internal(file);
		}
	}
}
