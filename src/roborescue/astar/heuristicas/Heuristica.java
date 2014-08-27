/**
 * 
 */
package roborescue.astar.heuristicas;

import roborescue.astar.Position;

/**
 * 
 * Heurísiticas. TODO Explicar no artigo a diferença entre as duas heurísticas
 * implementadas.
 * 
 * @author guilherme.barczyszyn
 *
 */
public interface Heuristica {
	/**
	 * Calcula a heurística e retorna com base no estado pretendido.
	 * 
	 * @param estadoPretendido
	 * @return valor da heurística
	 */
	double getHeuristica(Position estadoPretendido);
}
