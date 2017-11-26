function validateMap(map){
	var roads = map.roads;
	var intersections =map.intersections;
	var roadSet = new Set();
	var pointSet = new Set();
	
	for (i in intersections){
		pointSet.add(intersections[i].id);
	}
	for (i in roads){
		var points = roads[i].point_ids;
		if (!pointSet.has(points[0])){
			return {result:false,msg:"交点编号不存在："+points[0]};
		}
		for (j=0;j<points.length-1;j++){
			if (pointSet.has(points[j+1])){
				var newId1 = points[j]+'-'+points[j+1];
				var newId2 = points[j+1]+'-'+points[j];
				if (roadSet.has(newId1) || roadSet.has(newId2)){
					return {result:false,msg:"路径重复，路径编号："+roads[i].id};
				}else{
					roadSet.add(newId1);
					roadSet.add(newId2);
				}
			}else{
				return {result:false,msg:"交点编号不存在："+points[j+1]};
			}
		}
	}
	return {result:true};
}