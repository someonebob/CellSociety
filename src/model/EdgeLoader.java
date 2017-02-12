package model;

import model.edgetypes.InfiniteEdge;
import model.edgetypes.ToroidalEdge;

public class EdgeLoader {
		public Edge getEdge(String edgeType){
			if(edgeType != null){
				if(edgeType.equals("Infinite")) return new InfiniteEdge();
				if(edgeType.equals("Toroidal")) return new ToroidalEdge();
			}
			return new Edge();
		}
}
