/**
 * 
 */
package roborescue.astar.heuristicas;

import roborescue.astar.Position;

/**
 * Heurística de retorno à base.
 * 
 * @author guilherme.barczyszyn
 *
 */
public class HeuristicaRetorno implements Heuristica {
	private Position estadoObjetivo;

	public HeuristicaRetorno(Position estadoObjetivo) {
		this.estadoObjetivo = estadoObjetivo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * roborescue.astar.heuristicas.Heuristica#getHeuristica(roborescue.astar
	 * .Position)
	 */
	@Override
	public double getHeuristica(Position estadoPretendido) {
		// TODO implementar
		return 0;
	}

}
