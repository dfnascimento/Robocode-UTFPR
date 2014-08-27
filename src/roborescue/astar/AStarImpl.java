/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roborescue.astar;

import java.util.List;

import roborescue.astar.heuristicas.Heuristica;
import roborescue.astar.heuristicas.HeuristicaResgate;
import roborescue.astar.heuristicas.HeuristicaRetorno;
import roborescue.tarefabusca.AcoesProibidas;

/**
 *
 * @author guilherme
 */
public class AStarImpl implements AStar {

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

	/**
	 * Posição do estado objetivo.
	 */
	private Position estadoObjetivo;

	/**
	 * Lista das coordenadas de cada inimigo no campo.
	 */
	private List<Position> inimigos;

	/**
	 * Gerador de heurísticas
	 */
	private Heuristica heuristica;

	private double custoTotal;

	private Action acao;
	
	/**
	 * Escolhe as posições.
	 */
	private AcoesProibidas celulasInimigos;
	
	private Position ultimaPosicao;

	/**
	 * Construtora padrão, a heurística usada é a de resgate
	 * 
	 * @param estadoObjetivo
	 * @param inimigos
	 */
	public AStarImpl(Position estadoObjetivo, List<Position> inimigos, Action direcao) {
		this.estadoObjetivo = estadoObjetivo;
		this.inimigos = inimigos;
		this.ultimaPosicao = new Position(0, 0);
		
		custoTotal = 0;
		try {
			celulasInimigos = new AcoesProibidas(direcao);
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}

		heuristica = new HeuristicaResgate(estadoObjetivo, direcao);
	}

	/**
	 * Construtora que determina se a heurística é de resgate ou de retorno pelo
	 * parâmetro <b>isRescue</b>.
	 * 
	 * @param estadoObjetivo
	 * @param inimigos
	 * @param isRescue
	 */
	public AStarImpl(Position estadoObjetivo, List<Position> inimigos,
			boolean isRescue) {
		this.estadoObjetivo = estadoObjetivo;
		this.inimigos = inimigos;
		this.ultimaPosicao = new Position(0, 0);
		
		custoTotal = 0;
		
		try {
			celulasInimigos = new AcoesProibidas(Action.LESTE);
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}

		if (isRescue) {
			heuristica = new HeuristicaResgate(estadoObjetivo);
		} else {
			heuristica = new HeuristicaRetorno(estadoObjetivo);
		}
	}

	/**
	 * Core do algoritmo, escolhe o melhor caminho utilizando a ideia do A*.
	 *
	 * @param estadoAtual
	 * @return acao
	 */
	@Override
	public Action move(Position estadoAtual) {
		List<Action> acoesProibidas = celulasInimigos.getAcoesProibidas(
				estadoAtual, inimigos);

		// Verifica se está "travado" em uma parede
		if (estadoAtual.getX() == ultimaPosicao.getX() 
				&& estadoAtual.getX() == ultimaPosicao.getX()) {
			acoesProibidas.add(acao);
		}
		
		double menorCusto = 3000;
		
		Action acao = null;

		for (Action a : Action.values()) {
			try {
				if (!acoesProibidas.contains(a)
						&& getCustoEstimado(estadoAtual, a) <= menorCusto) {
					menorCusto = getCustoEstimado(estadoAtual, a);
					acao = a;
					
					System.out.println(a.toString()+ " - " + menorCusto);
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				System.err.println("Pulando esta ação.");
			}
		}
		
		System.out.println("Escolhida: " + acao.toString());
		
		custoTotal += menorCusto;
		return acao;
	}

	/**
	 * Calcula a heurística para uma ação, supondo que na célula destino não
	 * existem inimigos. Em Russel e Norvig, é a função <b>f(n)</b>.
	 *
	 * @param estadoAtual
	 * @param acao
	 * @return getCustoEstimado
	 * @throws Exception
	 */
	public double getCustoEstimado(Position estadoAtual, Action acao)
			throws Exception {
		Position estadoFuturo;
		double limiteLeste = 0;

		switch (acao) {
		case LESTE:
			// Calcula apenas a distancia em linha reta
			return limiteLeste + heuristica.getHeuristica(estadoAtual);
		case NORDESTE:
			// Supoe movimento para nordeste e adiciona distancia em linha reta
			estadoFuturo = new Position(estadoAtual.getX() + TAMANHO_CELULA,
					estadoAtual.getY() + TAMANHO_CELULA);

			return TAMANHO_DIAGONAL_CELULA + heuristica.getHeuristica(estadoFuturo);
		case SUDESTE:
			// Supoe movimento para sudeste e adiciona distancia em linha reta
			estadoFuturo = new Position(estadoAtual.getX() + TAMANHO_CELULA,
					estadoAtual.getY() - TAMANHO_CELULA);

			return TAMANHO_DIAGONAL_CELULA + heuristica.getHeuristica(estadoFuturo);
		case NORTE:
			// Supoe um movimento para o norte e adiciona distancia em linha
			// reta
			estadoFuturo = new Position(estadoAtual.getX(), estadoAtual.getY()
					+ TAMANHO_CELULA);

			return TAMANHO_CELULA + heuristica.getHeuristica(estadoFuturo);
		case SUL:
			// Supoe um movimento para o norte e adiciona distancia em linha
			// reta
			estadoFuturo = new Position(estadoAtual.getX(), estadoAtual.getY()
					- TAMANHO_CELULA);

			return TAMANHO_CELULA + heuristica.getHeuristica(estadoFuturo);
		case SUDOESTE:
			estadoFuturo = new Position(estadoAtual.getX() - TAMANHO_CELULA,
					estadoAtual.getY() - TAMANHO_CELULA);

			return TAMANHO_DIAGONAL_CELULA + heuristica.getHeuristica(estadoFuturo);
		case NOROESTE:
			estadoFuturo = new Position(estadoAtual.getX() - TAMANHO_CELULA,
					estadoAtual.getY() + TAMANHO_CELULA);

			return TAMANHO_DIAGONAL_CELULA + heuristica.getHeuristica(estadoFuturo);
		case OESTE:
			estadoFuturo = new Position(estadoAtual.getX() - TAMANHO_CELULA,
					estadoAtual.getY());

			return TAMANHO_CELULA + heuristica.getHeuristica(estadoFuturo);
		default:
			throw new Exception("Erro: Direção não especificada!");
		}
	}

	@SuppressWarnings("unused")
	@Deprecated
	/**
	 * Calcula getCustoEstimado em linha reta a partir das coordenadas passadas
	 * 
	 * @deprecated utiliza-se Heuristica.getHeuristica
	 * @param estadoPretendido
	 * @return getCustoEstimado
	 */
	private double heuristica(Position estadoPretendido) {
		double distY;
		double distX;
		double diagonal = 0;

		if (Math.sqrt(Math.pow(estadoObjetivo.getX() - estadoPretendido.getX(),
				2)) > Math.sqrt(Math.pow(estadoObjetivo.getY()
				- estadoPretendido.getY(), 2))) {
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

	public List<Position> getInimigos() {
		return inimigos;
	}

	public void setInimigos(List<Position> inimigos) {
		this.inimigos = inimigos;
	}

	public double getCustoTotal() {
		return this.custoTotal;
	}
}
