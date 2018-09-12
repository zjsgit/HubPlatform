/**
 * 
 */
package cn.edu.bean;

import java.text.DecimalFormat;

/**
 * @author JiashuaiZhang
 * @version	2018.7.24
 *
 */
public class MultiDegree implements Comparable<MultiDegree>{
	
	private String proteinName;//蛋白质的名字
	private double TSDegree;//时空中心性
	private double TDegree;//时间中心性
	private double SDegree;//空间中心性
	private double DCValue;//度中心性
	private double MNCValue;//Maximum Neighborhood Component
	private double NCValue;//总和边聚集系数方法
		
	public static DecimalFormat df=new DecimalFormat("#.##");//设置double类型小数点后位数格式 
	
	public MultiDegree(){}
	
	//构造函数
	public MultiDegree(String proteinName, double tSDegree, double tDegree, double sDegree, double dCValue,
			double mNCValue, double nCValue) {
		super();
		this.proteinName = proteinName;
		TSDegree = tSDegree;
		TDegree = tDegree;
		SDegree = sDegree;
		DCValue = dCValue;
		MNCValue = mNCValue;
		NCValue = nCValue;
	}
	
	
	@Override  
    public int compareTo(MultiDegree o) {  
       
        if(this.TSDegree > o.getTSDegree()){
            return -1;
        }
        else if(this.TSDegree < o.getTSDegree()){
            return 1;
        }
        else{
            return this.getProteinName().compareTo(o.getProteinName()) ; // 调用String中的compareTo()方法
        }
         
    }  
	
	
	@Override
	public String toString() {
		return proteinName + "\t" + TSDegree + "\t" + TDegree + "\t" + SDegree
				 + "\t" + DCValue + "\t" + MNCValue + "\t" + df.format(NCValue) ;
				
	}

	/**
	 * @return the proteinName
	 */
	public String getProteinName() {
		return proteinName;
	}
	/**
	 * @param proteinName the proteinName to set
	 */
	public void setProteinName(String proteinName) {
		this.proteinName = proteinName;
	}
	/**
	 * @return the tSDegree
	 */
	public double getTSDegree() {
		return TSDegree;
	}
	/**
	 * @param tSDegree the tSDegree to set
	 */
	public void setTSDegree(double tSDegree) {
		TSDegree = tSDegree;
	}
	/**
	 * @return the tDegree
	 */
	public double getTDegree() {
		return TDegree;
	}
	/**
	 * @param tDegree the tDegree to set
	 */
	public void setTDegree(double tDegree) {
		TDegree = tDegree;
	}
	/**
	 * @return the sDegree
	 */
	public double getSDegree() {
		return SDegree;
	}
	/**
	 * @param sDegree the sDegree to set
	 */
	public void setSDegree(double sDegree) {
		SDegree = sDegree;
	}
	/**
	 * @return the dCValue
	 */
	public double getDCValue() {
		return DCValue;
	}
	/**
	 * @param dCValue the dCValue to set
	 */
	public void setDCValue(double dCValue) {
		DCValue = dCValue;
	}
	/**
	 * @return the mNCValue
	 */
	public double getMNCValue() {
		return MNCValue;
	}
	/**
	 * @param mNCValue the mNCValue to set
	 */
	public void setMNCValue(double mNCValue) {
		MNCValue = mNCValue;
	}
	/**
	 * @return the nCValue
	 */
	public double getNCValue() {
		return NCValue;
	}
	/**
	 * @param nCValue the nCValue to set
	 */
	public void setNCValue(double nCValue) {
		NCValue = nCValue;
	}
	
	

}
