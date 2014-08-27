/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roborescue.tarefabusca;

import java.util.ArrayList;
import java.util.List;

import roborescue.astar.Action;
import roborescue.astar.Position;

/**
 *
 * @author guilherme
 */
public class AcoesProibidas {

    /**
     * Verifica quais posições estão ocupadas por inimigos e as retorna em uma
     * lista de ações.
     *
     * @param estadoAtual
     * @param inimigos
     * @return acoesProibidas
     */
    public List<Action> getAcoesProibidas(Position estadoAtual, List<Position> inimigos) {
        List<Action> acoesProibidas = new ArrayList<>();

        for (Position inimigo : inimigos) {
            if ((inimigo.getY() < estadoAtual.getY()
                    + 240.0 && inimigo.getY() > estadoAtual.getY() - 240.0)) {
                if ((estadoAtual.getX() <= inimigo.getX()) && estadoAtual.getX()
                        + 240.0 > inimigo.getX()) {
                    acoesProibidas.addAll(getAcoesProibidasFrente(estadoAtual, inimigo));
                }
                if ((estadoAtual.getX() - 120.0 <= inimigo.getX()) && estadoAtual.getX()
                        + 120.0 > inimigo.getX()) {
                    acoesProibidas.addAll(getAcoesProibidasParalelo(estadoAtual, inimigo));
                }
                if ((estadoAtual.getX() >= inimigo.getX()) && estadoAtual.getX()
                        - 240.0 < inimigo.getX()) {
                    acoesProibidas.addAll(getAcoesProibidasAtras(estadoAtual, inimigo));
                }
            }
        }

        return acoesProibidas;
    }

    /**
     * Retorna quais posições nas celulas frontais estão ocupadas por inimigos.
     *
     * @param estadoAtual
     * @param inimigo
     * @return acoesProibidas
     */
    private List<Action> getAcoesProibidasFrente(Position estadoAtual, Position inimigo) {
        List<Action> acoesProibidas = new ArrayList<>();

        if ((estadoAtual.getY() + 120.0 <= inimigo.getY()) && (estadoAtual.getY()
                + 240.0 > inimigo.getY()) && (inimigo.getX() != estadoAtual.getX())) {
            acoesProibidas.add(Action.NORDESTE);
        } else if ((estadoAtual.getY() < inimigo.getY()) && (estadoAtual.getY()
                + 120.0 > inimigo.getY() && (inimigo.getX() != estadoAtual.getX()))) {
            acoesProibidas.add(Action.NORDESTE);
            acoesProibidas.add(Action.LESTE);
        } else if (estadoAtual.getY() == inimigo.getY()) {
            acoesProibidas.add(Action.LESTE);
        } else if (estadoAtual.getY() > inimigo.getY() && estadoAtual.getY()
                - 120.0 < inimigo.getY() && (inimigo.getX() != estadoAtual.getX())) {
            acoesProibidas.add(Action.LESTE);
            acoesProibidas.add(Action.SUDESTE);
        } else if (estadoAtual.getY() - 120.0 >= inimigo.getY() && estadoAtual.getY()
                - 240.0 < inimigo.getY() && (inimigo.getX() != estadoAtual.getX())) {
            acoesProibidas.add(Action.SUDESTE);
        }

        return acoesProibidas;
    }

    /**
     * Retorna quais posições nas celulas paralelas estão ocupadas por inimigos.
     *
     * @param estadoAtual
     * @param inimigo
     * @return acoesProibidas
     */
    private List<Action> getAcoesProibidasParalelo(Position estadoAtual, Position inimigo) {
        List<Action> acoesProibidas = new ArrayList<>();

        if ((estadoAtual.getY() + 120.0 <= inimigo.getY()) && (estadoAtual.getY()
                + 240.0 > inimigo.getY())) {
            acoesProibidas.add(Action.NORTE);
        } else if ((estadoAtual.getY() < inimigo.getY()) && (estadoAtual.getY()
                + 120.0 > estadoAtual.getY())) {
            acoesProibidas.add(Action.NORTE);
            // Neste caso o inimigo está emcima do rescuer, situação impossível   
            //        } else if (estadoAtual.getY() == inimigo.getY()) {
            //            acoesProibidas.add(Action.NORTE);
        } else if (estadoAtual.getY() > inimigo.getY() && estadoAtual.getY()
                - 120.0 < inimigo.getY()) {
            acoesProibidas.add(Action.SUL);
        } else if (estadoAtual.getY() - 120.0 >= inimigo.getY() && estadoAtual.getY()
                - 240.0 < inimigo.getY()) {
            acoesProibidas.add(Action.SUL);
        }

        return acoesProibidas;
    }

    /**
     * Retorna quais posições nas celulas traseiras estão ocupadas por inimigos.
     *
     * @param estadoAtual
     * @param inimigo
     * @return acoesProibidas
     */
    private List<Action> getAcoesProibidasAtras(Position estadoAtual, Position inimigo) {
        List<Action> acoesProibidas = new ArrayList<>();

        if ((estadoAtual.getY() + 120.0 <= inimigo.getY()) && (estadoAtual.getY()
                + 240.0 > inimigo.getY()) && (inimigo.getX() != estadoAtual.getX())) {
            acoesProibidas.add(Action.NOROESTE);
        } else if ((estadoAtual.getY() < inimigo.getY()) && (estadoAtual.getY()
                + 120.0 > estadoAtual.getY()) && (inimigo.getX() != estadoAtual.getX())) {
            acoesProibidas.add(Action.NOROESTE);
            acoesProibidas.add(Action.OESTE);
        } else if (estadoAtual.getY() == inimigo.getY()) {
            acoesProibidas.add(Action.OESTE);
        } else if (estadoAtual.getY() > inimigo.getY() && estadoAtual.getY()
                - 120.0 < inimigo.getY()&& (inimigo.getX() != estadoAtual.getX())) {
            acoesProibidas.add(Action.SUDOESTE);
            acoesProibidas.add(Action.OESTE);
        } else if (estadoAtual.getY() - 120.0 >= inimigo.getY() && estadoAtual.getY()
                - 240.0 < inimigo.getY() && (inimigo.getX() != estadoAtual.getX())) {
            acoesProibidas.add(Action.SUDOESTE);
        }

        return acoesProibidas;
    }
}
