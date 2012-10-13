package org.infominer.cognisearch.search.core;

/**
 * A simple DTO class that is used to describe the index entries within the LuceneTest framework
 * @author infominer
 *
 */
class IndexEntryDescriptor 
{
	
	private final String filePath;
	private final String fileContent;
	
	public IndexEntryDescriptor(String filePath, String fileContent)
	{
		this.filePath = filePath;
		this.fileContent = fileContent;
	}

	public String getFilePath() 
	{
		return filePath;
	}

	 public String getFileContent() 
	 {
		return fileContent;
	}
	
	

}
