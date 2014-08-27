/**
 * 
 */
package roborescue.astar.heuristicas;

import roborescue.astar.Position;

/**
 * Heurística de resgate.
 * 
 * @author guilherme.barczyszyn
 *
 */
public class HeuristicaResgate implements Heuristica {
	/**
	 * Custo de uma célula para outra horizontal ou vertical. Em Russel e Norvig
	 * é a função de custo <b>g(n)</b>.
	 */
	private final double TAMANHO_CELULA = 120.0;

	/**
	 * Custo de uma célula para outra diagonal. Em Russel e Norvig é a função de
	 * custo <b>g(n)</b>.
	 */
	private final double TAMANHO_DIAGONAL_CELULA = Math.sqrt(Math.pow(
			TAMANHO_CELULA, 2) + Math.pow(TAMANHO_CELULA, 2));

	private Position estadoObjetivo;
	
	public HeuristicaResgate(Position estadoObjetivo) {
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
		double distY;
		double distX;
		double diagonal = 0;

		// Limpeza de sinal
		if (Math.sqrt(Math.pow(estadoObjetivo.getX() - estadoPretendido.getX(), 2)) > 
			Math.sqrt(Math.pow(estadoObjetivo.getY() - estadoPretendido.getY(), 2))) {
			// 1 - Calcular a distância em Y
			if (estadoPretendido.getY() < (estadoObjetivo.getY() + TAMANHO_CELULA / 2)
					&& estadoPretendido.getY() > (estadoObjetivo.getY() - TAMANHO_CELULA / 2)) {
				// Caso ele já esteja no mesmo X do refem
				distY = 0;
			} else if (estadoPretendido.getY() < estadoObjetivo.getY()) {
				// Não está no mesmo nó
				distY = estadoObjetivo.getY() - estadoPretendido.getY();
			} else {
				distY = estadoPretendido.getY() - estadoObjetivo.getY();
			}

			// 2 - Calcular a distancia em X
			if (estadoPretendido.getX() < (estadoObjetivo.getX() + TAMANHO_CELULA / 2)
					&& estadoPretendido.getX() > (estadoObjetivo.getX() - TAMANHO_CELULA / 2)) {
				distX = 0;
			} else if (estadoPretendido.getX() < estadoObjetivo.getX()) {
				// Não está no mesmo nó
				distX = estadoObjetivo.getX() - estadoPretendido.getX();
			} else {
				distX = estadoPretendido.getX() - estadoObjetivo.getX();
			}

			int celulasCatetos = 0;

			// 3 - Calcular a distancia Diagonal
			if (distY != 0) {
				celulasCatetos = (int) (distY / TAMANHO_CELULA);
				diagonal = celulasCatetos * TAMANHO_DIAGONAL_CELULA;
			}

			// 4 - Calcular a getCustoEstimado
			return distX - (celulasCatetos * TAMANHO_CELULA) + diagonal;
		} else {
			// células diagonais
			int celulasX = (int) (Math.sqrt(Math.pow(estadoObjetivo.getX()
					- estadoPretendido.getX(), 2)) / TAMANHO_CELULA);
			double heuristica = TAMANHO_DIAGONAL_CELULA * celulasX;

			// células cima
			int celulasY = (int) (Math.sqrt(Math.pow(estadoObjetivo.getY()
					- estadoPretendido.getY(), 2)) / TAMANHO_CELULA);
			heuristica += TAMANHO_CELULA * celulasY;

			return heuristica;
		}
	}

}
