package utils;

/**
 * A configuration for the file system walker.
 * Instances of this class are immutable.
 * Can be associated with an instance of {@link:FileSystemWalker} when the instance is created.
 * @author prahaladdeshpande
 *
 */
public class FileSystemWalkerConfig 
{
	private final Integer maxDepth;
	
	public FileSystemWalkerConfig(int maxDepth)
	{
		this.maxDepth = maxDepth;
	}

	public Integer getMaxDepth() 
	{
		return maxDepth;
	}
	

}
