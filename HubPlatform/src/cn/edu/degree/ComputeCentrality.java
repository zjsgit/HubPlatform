/**
 * 
 */
package cn.edu.degree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.edu.bean.MultiDegree;
import utils.FileUtil;

/**
 * @author JiashuaiZhang
 *
 */
public class ComputeCentrality {
	
	public static List<MultiDegree> computeMultiCentrality(String centrality,
			String []fileContent,String path){
			
		List<MultiDegree> multiDegrees=new ArrayList<MultiDegree>();
		
		//计算时空动态网络中心性
		Map<String,Double> TSCentrality=DyWang.calculateTSCentrality(centrality, fileContent);
		
		//计算空间网络中心性
		Map<String,Double> SCentrality=SpatialMaximum.spatialCentrality(centrality, fileContent,path);
		
		//计算时间网络中心性
		Map<String,Double> TCentrality=TemporalMaximum.temporalCentrality(centrality, fileContent,path);
		
		//计算静态网络中心性
		Map<String,Double> dcMap=StaticDegree.calculateDC(fileContent[0],path);

		Map<String,Double> mncMap=StaticDegree.calculateMNC(fileContent[0],path);

		Map<String,Double> ncMap=StaticDegree.calculateNC(fileContent[0],path);
		
		//以时空动态网络为基准，将静态、时空、时间、空间中心性进行整合
		for(Entry<String, Double> entry:TSCentrality.entrySet()){
			String proteinName=entry.getKey();
			
			double tSDegree=TSCentrality.get(entry.getKey());//时空中心性
			double TDegree = 0 ,SDegree= 0 ,DCValue= 0,MNCValue= 0,NCValue= 0;
	
			if (TCentrality.containsKey(entry.getKey())) {//时间中心性
				TDegree = TCentrality.get(entry.getKey());
			}
			
			if (SCentrality.containsKey(entry.getKey())) {//空间中心性
				SDegree = SCentrality.get(entry.getKey());
			}
			
			//三种静态中心性
			if (dcMap.containsKey(entry.getKey())) {
				DCValue = dcMap.get(entry.getKey());
			}
			if (mncMap.containsKey(entry.getKey())) {
				MNCValue = mncMap.get(entry.getKey());
			}
			if (ncMap.containsKey(entry.getKey())) {
				NCValue = ncMap.get(entry.getKey());
			}
			
			multiDegrees.add(new MultiDegree(proteinName,tSDegree,
					TDegree ,SDegree ,DCValue,MNCValue,
					Math.round(NCValue*100)/100.0));
			
		}
		
		Collections.sort(multiDegrees);//以时空中心性排序
			
		FileUtil.writeResult2File(multiDegrees,path+"/downloadFiles/multiCentrality.txt");//将结果写入文件
		
		return multiDegrees;
	}
	
	/**
	 * 取出多种网络中心性，显示出前n个
	 * @param multiDegrees
	 * @param num
	 * @return
	 */
	public static List<MultiDegree> getTopProteins(List<MultiDegree> multiDegrees,int num){
		
		List<MultiDegree> topProteins=new ArrayList<MultiDegree>();
		int index=1;
		for (MultiDegree multiDegree : multiDegrees) {
			topProteins.add(multiDegree);
			index++;
			if(index>10){
				break;
			}
		}
		
		return topProteins;
		
	}
	

}
