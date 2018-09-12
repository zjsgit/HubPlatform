/**
 * 
 */
package cn.edu.degree;

import java.util.HashSet;
import java.util.List;

import utils.*;

/**
 * @author JiashuaiZhang
 *
 */
public class NetworkFilter {
	
	/**
	 * 
	 * @param fileContent
	 * 
	 */

	public static List<String> filterNetwork(String fileContent){
		
		List<String> netList = StrUtil.str2List(fileContent);
		
		List<String> netAfterFilter=NetUtil.filterNet(netList);
		
		return netAfterFilter;
	}
	
	/**
	 * 
	 * @param fileContent
	 * @return after filter content
	 */
	
	public static List<String> filterLoops(String fileContent){
		
		List<String> netList = StrUtil.str2List(fileContent);
		
		List<String> netAfterFilter=NetUtil.filterLoops(netList);
		
		return netAfterFilter;
	}
	
	/**
	 * 
	 * @param fileContent
	 * @return after filter content
	 */
	
	public static List<String> filterOverlop(String fileContent){
		
		List<String> netList = StrUtil.str2List(fileContent);
		
		List<String> netAfterFilter=NetUtil.filterOverlap(netList);
		
		return netAfterFilter;
	}
}
