# HubPlatform

This web platform aims to predict key proteins from PPI data, subcellular location information
data and gen expression data according to constructing spatiotemporal dynamic network.

This web application include four section, Filter, Submit and Visualization.

Filter Section: This section's aim is to filter Protein Protein Interaction. That is to say, delete 
the edge that appears more than twice. At the same time, it can tell you the number of protein node
and edge after filtering.

There is a example.
before filtering:			after filtering:
protein1	protein2		protein1	protein2
protein3	protein5        protein3	protein5
protein1	protein2        protein9	protein5
protein2	protein1        protein4	protein8
protein9	protein5        
protein1	protein2        
protein4	protein8            

Submit Section:
In order to construct spatiotemporal dynamic network, you need to submit PPI data, subcellular location 
information data and gen expression data. The data format of the three files is as follows.
PPI data:
protein1	 protein2
protein3	 protein5
protein7	 protein5
protein4	 protein6

gen expression data: the first column is protein name, others are gen expression value at 12 time. 
protein1	0.055618618	0.073988438	0.120317824	0.055118114	0.030303033	0.026143791	0.013001083	0.09195403	0.088377729	0.062780268	0.079908676	0.06395939
protein2	1.994324565	1.805780292	1.173666358	1.147356629	0.940516353	0.930283248	0.83856988	1.067920566	3.21670723	1.644618869	2.239726067	2.690355301
protein3	22.51645851	11.07745647	19.84449577	17.49943733	17.20314407	18.38671112	19.45720482	23.99895477	50.00363159	33.29484177	36.15296936	24.93908501
protein5	0.005675369	0.004624277	0.049943246	0.013498314	0.004489338	0.030501088	0.006500542	0.008359457	0.012106538	0.005605381	0.050228313	0.007106599
protein7	2.220204353	1.906358242	2.366628885	1.8436445	1.734006763	1.860566497	1.684723616	2.013584137	3.176755428	1.928251266	2.340182781	2.655837536
protein4	0.657207727	0.635838151	0.656072617	0.905511856	1.127946138	1.001089334	1.069339037	1.022988558	2.917675495	0.491031408	0.603881299	1.389847636
protein6	3.346197367	3.784970999	3.570942163	3.767154217	3.144781351	3.406318188	2.66847229	2.531870604	1.211864471	1.84977591	2.005707741	1.730964422

subcellular location information data: the entire cell space is divided into 11 parts, such as 
Cytoskeleton, Cytosol, Endoplasmic, Endosome, Extracellular, Golgi, Mitochondrion, Nucleus, 
Peroxisome, Plasma and Vacuole.

protein6	Cell part
protein6	Cell part
protein6	Intracellular organelle lumen
protein4	cellular_component
protein4	cellular_component
protein4	cellular_component
protein4	Extracellular region
protein4	Cell wall
protein4	Intracellular
protein4	Cell
protein4	Cell
protein4	Cytoplasm
protein4	Cytosol
......

note you: if the files format is wrong, you can't use the platform. Please confirm your files's style is right.

Visualization Section:

first: the web can provide the all PPI network's visulation. You can also decide the number of the top
protein nodes to visualize. Besides, there are several Centrality Method algorithms which you can select
to get top protein nodes.

second: the web can support us to view of the top protein nodes network in different time. On the other 
hand, you can also select network layout (the default is circle layout). 

third: the web can offer the visulation of the top protein nodes network in which protein nodes 
in different subcellular location will display different colors.






