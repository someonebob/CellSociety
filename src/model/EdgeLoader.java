package model;

import model.edgetypes.InfiniteEdge;
import model.edgetypes.ToroidalEdge;

/**
 * Loader for grid edge types.
 * If new edge type is added, add corresponding keyword and class here
 * @author DhruvKPatel
 */
public class EdgeLoader {
	public Edge getEdge(String edgeType){
		if(edgeType != null){
			if(edgeType.equals("Infinite")) return new InfiniteEdge();
			if(edgeType.equals("Toroidal")) return new ToroidalEdge();
		}
		return new Edge();
	}
}
